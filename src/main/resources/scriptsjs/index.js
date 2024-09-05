const carouselContainer = document.querySelector('.carousel-container');
const totalItems = document.querySelectorAll('.carousel-item').length;
let currentIndex = 0;
let interval;

// Duplicar los elementos del carrusel para hacer el loop infinito
const firstItem = carouselContainer.firstElementChild.cloneNode(true);
const lastItem = carouselContainer.lastElementChild.cloneNode(true);

carouselContainer.appendChild(firstItem); // Agregar el primer elemento al final
carouselContainer.insertBefore(lastItem, carouselContainer.firstElementChild); // Agregar el último al principio

// Función para avanzar automáticamente
function autoSlide() {
    interval = setInterval(() => {
        showNextImage();
    }, 10000); // Cambiar cada 10 segundos
}

// Función para mostrar la siguiente imagen
function showNextImage() {
    currentIndex++;
    carouselContainer.style.transition = "transform 0.5s ease-in-out";
    carouselContainer.style.transform = `translateX(-${currentIndex * 100}vw)`;

    if (currentIndex === totalItems) {
        setTimeout(() => {
            carouselContainer.style.transition = "none"; // Quitar la transición
            carouselContainer.style.transform = `translateX(-100vw)`; // Volver al primer elemento original
            currentIndex = 1;
        }, 500); // Tiempo de la transición
    }
}

// Función para mostrar la imagen anterior
function showPrevImage() {
    currentIndex--;
    carouselContainer.style.transition = "transform 0.5s ease-in-out";
    carouselContainer.style.transform = `translateX(-${currentIndex * 100}vw)`;

    if (currentIndex === 0) {
        setTimeout(() => {
            carouselContainer.style.transition = "none"; // Quitar la transición
            carouselContainer.style.transform = `translateX(-${totalItems * 100}vw)`; // Volver al último elemento original
            currentIndex = totalItems - 1;
        }, 500); // Tiempo de la transición
    }
}

// Control manual con el mouse (arrastrar)
let startX;
let isDragging = false;

carouselContainer.addEventListener('mousedown', (e) => {
    startX = e.clientX;
    isDragging = true;
    clearInterval(interval); // Detiene el auto-slide mientras se arrastra
});

carouselContainer.addEventListener('mouseup', (e) => {
    if (!isDragging) return;
    const endX = e.clientX;
    if (startX > endX) {
        showNextImage();
    } else {
        showPrevImage();
    }
    isDragging = false;
    autoSlide(); // Reinicia el auto-slide
});

carouselContainer.addEventListener('mouseleave', () => {
    isDragging = false;
});

// Inicializar el auto-slide
autoSlide();
