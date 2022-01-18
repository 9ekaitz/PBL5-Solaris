var canvas;
var ctx;
window.addEventListener("DOMContentLoaded", function () {
  canvas = document.querySelector('#sign');

  ctx = canvas.getContext('2d');
  resize(); // Resizes the canvas once the window loads
  document.addEventListener('mousedown', startPainting);
  document.addEventListener('mouseup', stopPainting);
  document.addEventListener('mousemove', sketch);
  window.addEventListener('resize', resize);
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