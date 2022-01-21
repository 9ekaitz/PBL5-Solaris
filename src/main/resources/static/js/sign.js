const CSRF_TOKEN = document.querySelector("meta[name='_csrf']").content;
const CSRF_HEADER = document.querySelector("meta[name='_csrf_header']").content;

let canvas_container;
let task_container;
let canvas;
let ctx;

window.addEventListener("DOMContentLoaded", function () {
  canvas = document.getElementById('sign');
  canvas_container = document.getElementById('canvas-container');
  task_container = document.getElementById('task-container');

  document.getElementById("sign-btn").addEventListener("click", showCanvas);
  document.getElementById("send-btn").addEventListener("click", uploadFile);

  ctx = canvas.getContext("2d");

  document.addEventListener("mousedown", startPainting);
  document.addEventListener("mouseup", stopPainting);
  document.addEventListener("mousemove", sketch);
  window.addEventListener("resize", resize);
});

function resize() {
  ctx.canvas.width = canvas.clientWidth;
  ctx.canvas.height = canvas.clientHeight;
}

let coord = { x: 0, y: 0 };

let paint = false;

function getPosition(event) {
  coord.x = event.clientX - canvas.offsetParent.offsetLeft;
  coord.y = event.clientY - canvas.offsetParent.offsetTop;
}

function startPainting(event) {
  paint = true;
  getPosition(event);
}
function stopPainting() {
  paint = false;
}

function sketch(event) {
  if (!paint) return;

  ctx.beginPath();
  ctx.lineWidth = 3;
  ctx.lineCap = 'round';
  ctx.strokeStyle = 'black';
  ctx.moveTo(coord.x, coord.y);

  getPosition(event);

  ctx.lineTo(coord.x, coord.y);
  ctx.stroke();
}

function showCanvas() {
  let exit;
  document.querySelectorAll("form .check").forEach(function (item) {
    if (!item.checked) {
      document.getElementById("sign-error").classList.remove("hide");
      exit = true;
    }
  })
  if (exit) return;

  task_container.classList.remove("current");
  canvas_container.classList.add("current");
  document.getElementById("sign-btn").classList.add("hide");
  document.getElementById("save-btn").classList.add("hide");
  document.querySelector(".error").classList.add("hide");
  document.getElementById("send-btn").classList.remove("hide");
  resize();
}

function uploadFile() {
  canvas.toBlob(function (blob) {
    let xhr = new XMLHttpRequest();
    let form = document.getElementById("taskForm")
    xhr.open('POST', form.action, true);
    xhr.setRequestHeader('x-csrf-token', CSRF_TOKEN);

    let data = new FormData(form);
    data.append('sign', blob, crypto.randomUUID() + '.jpg');
    data.append('signed', true);

    xhr.onload = function (e) {
      document.querySelector("html").innerHTML = xhr.response;
      window.document.dispatchEvent(new Event("DOMContentLoaded", {
        bubbles: true,
        cancelable: true
      }));
      window.history.pushState("", "", '/install');
    };

    xhr.send(data);
  });

}