const toggleButton = document.getElementById('toggleButton');

document.addEventListener('DOMContentLoaded', function () {
  const collapsibleContainer = document.getElementById('collapsibleContainer');
  const imageContainer = document.getElementById('imageContainer');
  imageContainer.style.display = 'none';
  const imagesFolder = 'images/';

  // List of image file names (you can fetch this dynamically or hardcode it)
  const imageFileNames = ['AoPS.png', 'balloon.png', 'dots.png', 'plane.png', 'sky.png'];

  // Function to create thumbnail elements
  function createThumbnailElement(fileName) {
    const thumbnailElement = document.createElement('div');
    thumbnailElement.classList.add('imageItem');

    const imageElement = document.createElement('img');
    imageElement.src = imagesFolder + fileName;

    const fileNameElement = document.createElement('p');
    fileNameElement.textContent = fileName;

    thumbnailElement.appendChild(imageElement);
    thumbnailElement.appendChild(fileNameElement);

    return thumbnailElement;
  }

  imageFileNames.forEach(function (fileName) {
    const thumbnailElement = createThumbnailElement(fileName);
    imageContainer.appendChild(thumbnailElement);
  });

  collapsibleContainer.addEventListener('click', function () {
    imageContainer.style.display = (imageContainer.style.display === 'none') ? 'flex' : 'none';
    collapsibleContainer.classList.toggle('expanded');
    toggleButton.textContent = collapsibleContainer.classList.contains('expanded') ? '◄' : '►';
    focusToPIXI();
  });
});

function focusToPIXI() {
    const pixiIframe = parent.document.getElementById('pixi-iframe');
    if (pixiIframe) {
        pixiIframe.focus();
    }
}