const panelSelector = document.getElementById("panel-selector");

let panelId;

const getPanels = async () => {
  const response = await fetch("/api/user-panel/getPanels?id=" + USER_ID, {
    headers: {
      "Content-Type": "application/json",
      Accept: "application/json",
      Credentials: "same-origin",
    },
  });
  const data = await response.json();
  return data.panels;
};

const setPanels = async () => {
  const panels = await getPanels();
  for (const panel of panels) {
    const option = document.createElement("option");
    option.value = panel;
    option.text = panel;
    panelSelector.add(option);
  }
};

const handlePanelChange = async () => {
  panelId = panelSelector.value;
  reloadRealTimeData();
  setGeneralData();
};

setPanels();
panelSelector.addEventListener("change", handlePanelChange);
