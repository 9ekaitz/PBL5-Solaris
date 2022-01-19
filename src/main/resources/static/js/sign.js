const CSRF_TOKEN = document.querySelector("meta[name='_csrf']").content;
const CSRF_HEADER = document.querySelector("meta[name='_csrf_header']").content;

var canvas_container;
var task_container;
var canvas;
var ctx;

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
      return
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
    xhr.open('POST', '/install/2/save', true);
    xhr.setRequestHeader('x-csrf-token', CSRF_TOKEN)

    let formData = new FormData(document.getElementById("taskForm"));
    formData.append('sign', blob, crypto.randomUUID()+'.jpg');

    // action after uploading happens
    xhr.onload = function (e) {
      xhr.getResponseHeader
      window.location.replace("http://www.w3schools.com");
    };

    xhr.send(formData);
  }, 'image/jpeg', 0.95);
}