const fetchEcoData = async () => {
  const response = await fetch("/api/user-panel/eco?id=" + USER_ID);
  const data = await response.json();
  return data;
};

const getData = async () => {
  const data = await fetchEcoData();
  const { co2, mminc, tempC } = data;
  document.getElementById("eco-avoided-co2-total").textContent = parseFloat(co2).toFixed(2);
  document.getElementById("eco-avoided-mminc-total").textContent = parseFloat(mminc).toExponential(2).toString().replace("e", "x10^");
  document.getElementById("eco-avoided-tempC-total").textContent = parseFloat(tempC).toExponential(2).toString().replace("e", "x10^");
};

getData();
