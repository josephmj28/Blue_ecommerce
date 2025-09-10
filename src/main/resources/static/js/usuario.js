document.addEventListener('DOMContentLoaded', () => {
    const canvas = document.getElementById('hero-background');
    const ctx = canvas.getContext('2d');
    const dogImage = document.querySelector('.hero-dog-image');
    const keywords = document.querySelectorAll('.keyword');

    let width, height;
    let particles = [];
    const particleCount = 100;

    function resizeCanvas() {
        width = canvas.width = window.innerWidth;
        height = canvas.height = window.innerHeight;
    }

    class Particle {
        constructor() {
            this.x = Math.random() * width;
            this.y = Math.random() * height;
            this.vx = (Math.random() - 0.5) * 0.5;
            this.vy = (Math.random() - 0.5) * 0.5;
            this.radius = Math.random() * 1.5 + 1;
            this.alpha = 0;
            this.fadeIn = true;
        }

        update() {
            this.x += this.vx;
            this.y += this.vy;

            if (this.x < 0 || this.x > width) this.vx *= -1;
            if (this.y < 0 || this.y > height) this.vy *= -1;

            if (this.fadeIn) {
                this.alpha += 0.005;
                if (this.alpha > 0.5) {
                    this.fadeIn = false;
                }
            }
        }

        draw() {
            ctx.beginPath();
            ctx.arc(this.x, this.y, this.radius, 0, Math.PI * 2);
            ctx.fillStyle = `rgba(0, 123, 255, ${this.alpha})`;
            ctx.fill();
        }
    }

    function createParticles() {
        particles = [];
        for (let i = 0; i < particleCount; i++) {
            particles.push(new Particle());
        }
    }

    function animate() {
        ctx.clearRect(0, 0, width, height);
        particles.forEach(p => {
            p.update();
            p.draw();
        });
        requestAnimationFrame(animate);
    }

    resizeCanvas();
    createParticles();
    animate();

    window.addEventListener('resize', resizeCanvas);

    // Animación de la imagen del perro
    const dogBaseScale = 1;
    let dogHoverScale = 1;

    keywords.forEach(keyword => {
        keyword.addEventListener('mouseenter', () => {
            dogHoverScale = 1.05;
            dogImage.style.transform = `scale(${dogHoverScale})`;
        });

        keyword.addEventListener('mouseleave', () => {
            dogHoverScale = 1;
            dogImage.style.transform = `scale(${dogBaseScale})`;
        });
    });

    // Pequeño efecto de "respiración" para el perro
    setInterval(() => {
        if (dogHoverScale === 1) {
            dogImage.style.transform = `scale(${dogBaseScale + Math.sin(Date.now() / 1000) * 0.005})`;
        }
    }, 16);
});