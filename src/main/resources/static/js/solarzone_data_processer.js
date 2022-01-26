let realTimeCanvas;

if (REAL_TIME_ENABLED) {
  realTimeCanvas = document.getElementById("realTimeChart").getContext("2d");
}

const historicalCanvas = document.getElementById("historical-chart").getContext("2d");

const dayInput = document.getElementById("select-day");
const weekInput = document.getElementById("select-week");
const monthInput = document.getElementById("select-month");
const yearInput = document.getElementById("select-year");

const groupButtons = document.querySelectorAll(".group-button");
const dateSelectors = document.querySelectorAll(".date-selector");

const panelCover = document.querySelector(".load-cover");

const IMPUESTO_SOLAR = 0.113;

let realTimeChart;
let historicalChart;

let groupMode = "DAY";

const createRTDataGraph = (data) => {
  const chartData = {
    type: "line",
    data: data,
    options: {
      maintainAspectRatio: false,
      plugins: {
        title: {
          display: true,
          text: REAL_TIME_TITLE,
        },
      },
    },
  };
  realTimeChart = new Chart(realTimeCanvas, chartData);
};

const createHistoricalDataGraph = (data) => {
  console.log(data);
  const chartData = {
    type: "bar",
    data: data,
    options: {
      maintainAspectRatio: false,
      plugins: {
        title: {
          display: true,
          text: HISTORICAL_TITLE,
        },
      },
    },
  };
  historicalChart = new Chart(historicalCanvas, chartData);
};

const clearDataGraph = (chart) => {
  if (chart) {
    chart.destroy();
  }
};

const getHistoricalData = async (startDate, endDate) => {
  const params = {
    groupMode: groupMode,
    start: startDate,
    end: endDate,
  };
  let url = HISTORICAL_URL;
  if (SINGLE_PANEL) url = url + panelId;
  else url = url + USER_ID;
  const response = await fetch(url + "&" + new URLSearchParams(params), {
    headers: {
      "Content-Type": "application/json",
      Accept: "application/json",
      Credentials: "same-origin",
    },
  });
  return response.json();
};

const getRealTimeData = async () => {
  let url = REAL_TIME_URL;
  if (SINGLE_PANEL) url = url + panelId;
  else url = url + USER_ID;
  const response = await fetch(url, {
    headers: {
      "Content-Type": "application/json",
      Accept: "application/json",
      Credentials: "same-origin",
    },
  });
  return response.json();
};

const reloadRealTimeData = async () => {
  const data = await getRealTimeData();
  clearDataGraph(realTimeChart);
  createRTDataGraph(data.data);
};

// Generate a function that takes a week number an a year and returns the start and end date of that week
const getDateOfISOWeek = (w, y) => {
  const mom = moment(y + "-" + w, "YYYY-w");
  return mom.toDate();
};

const getStartEnd = () => {
  let startDate, endDate;
  if (groupMode === "DAY") {
    startDate = new Date(dayInput.value);
    endDate = new Date(dayInput.value);
  } else if (groupMode === "WEEK") {
    const year = yearInput.value;
    const week = weekInput.value;
    startDate = getDateOfISOWeek(week, year);
    endDate = new Date(startDate);
    endDate.setDate(endDate.getDate() + 8);
  } else if (groupMode === "MONTH") {
    const year = yearInput.value;
    const month = monthInput.value;
    startDate = new Date(year, month - 1, 1);
    endDate = new Date(year, month, 0);
  } else if (groupMode === "YEAR") {
    const year = yearInput.value;
    startDate = new Date(year, 0, 1);
    endDate = new Date(year, 11, 31);
  }
  console.log(startDate, endDate);
  return [startDate, endDate];
};

const reloadHistoricalData = async () => {
  const [startDate, endDate] = getStartEnd();
  const startDateString = startDate.toISOString().split("T")[0];
  const endDateString = endDate.toISOString().split("T")[0];
  panelCover.classList.add("show");
  const data = await getHistoricalData(startDateString, endDateString);
  clearDataGraph(historicalChart);
  createHistoricalDataGraph(data.data);
  panelCover.classList.remove("show");
};

const changeGroupMode = (mode, button) => {
  groupMode = mode;
  if (mode == "DAY") {
    dayInput.classList.add("show");
    weekInput.classList.remove("show");
    monthInput.classList.remove("show");
    yearInput.classList.remove("show");
  } else if (mode == "WEEK") {
    dayInput.classList.remove("show");
    weekInput.classList.add("show");
    monthInput.classList.remove("show");
    yearInput.classList.add("show");
  } else if (mode == "MONTH") {
    dayInput.classList.remove("show");
    weekInput.classList.remove("show");
    monthInput.classList.add("show");
    yearInput.classList.add("show");
  } else if (mode == "YEAR") {
    dayInput.classList.remove("show");
    weekInput.classList.remove("show");
    monthInput.classList.remove("show");
    yearInput.classList.add("show");
  }
  groupButtons.forEach((bt) => {
    bt.classList.remove("button-selected");
  });
  button.classList.add("button-selected");
};

const getGeneralData = async () => {
  let url = GENERAL_DATA_URL;
  if (SINGLE_PANEL) url = url + panelId;
  else url = url + USER_ID;

  const response = await fetch(url, {
    headers: {
      "Content-Type": "application/json",
      Accept: "application/json",
      Credentials: "same-origin",
    },
  });
  return response.json();
};

const getReadableValue = (value) => {
  const valFloat = parseFloat(value).toFixed(2);
  if (CONVERSION == "NONE") {
    if (valFloat < 1000) {
      return valFloat + " KWh";
    } else if (valFloat > 1000 && valFloat < 1000000) {
      return valFloat / 1000 + " MWh";
    } else if (valFloat > 1000000) {
      return (valFloat / 1000000).toFixed(2) + " GWh";
    }
  } else {
    const val = ((valFloat - valFloat * IMPUESTO_SOLAR) * 1000).toFixed(2) + CONVERSION.replace("TO_", " ");
    return val;
  }
};

const setGeneralData = async () => {
  const data = await getGeneralData();
  const { energyThisMonth, energyLast30Days, energyAllTime } = data;
  document.getElementById("energy-this-month").textContent = getReadableValue(energyThisMonth);
  document.getElementById("energy-last-30-days").textContent = getReadableValue(energyLast30Days);
  document.getElementById("energy-all-time").textContent = getReadableValue(energyAllTime);
};

window.addEventListener("DOMContentLoaded", () => {
  if (REAL_TIME_ENABLED) {
    reloadRealTimeData();
    setInterval(reloadRealTimeData, 60000);
  }
  if (GENERAL_DATA_ENABLED) setGeneralData();
  groupButtons.forEach((button) => {
    button.addEventListener("click", () => changeGroupMode(button.textContent, button));
  });
  dateSelectors.forEach((selector) => {
    selector.addEventListener("change", () => reloadHistoricalData());
  });

  dayInput.value = new Date().toISOString().split("T")[0];

  reloadHistoricalData();
});
