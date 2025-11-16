package org.example.Epoli.models;
import jakarta.persistence.*;



    @Entity
    @Table(name = "productos")

    public class Producto {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        private String nombre;
        private String descripcion;
        private String imagen;
        private Double precio;
        private String ubicacion;


        @ManyToOne
        private Usuario usuario;

        @ManyToOne
        @JoinColumn(name = "emprendimientoId")
        private Emprendimiento emprendimiento;

        public Producto() {
        }

        public Producto(Integer id, String nombre, String descripcion, String imagen, Double precio, String ubicacion, Usuario usuario) {
            this.id = id;
            this.nombre = nombre;
            this.descripcion = descripcion;
            this.imagen = imagen;
            this.precio = precio;
            this.ubicacion = ubicacion;

        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }


        public Double getPrecio() {
            return precio;
        }

        public void setPrecio(Double precio) {
            this.precio = precio;
        }

        public String getUbicacion() {
            return ubicacion;
        }

        public void setUbicacion(String ubicacion) {
            this.ubicacion = ubicacion;
        }

        public String getImagen() {
            return imagen;
        }

        public void setImagen(String imagen) {
            this.imagen = imagen;
        }



        public Usuario getUsuario() {
            return usuario;
        }

        public void setUsuario(Usuario usuario) {
            this.usuario = usuario;
        }


        @Override
        public String toString() {
            return "producto{" +
                    "id=" + id +
                    ", nombre='" + nombre + '\'' +
                    ", description='" + descripcion + '\'' +
                    ", precio=" + precio +
                    ", Ubicacion=" + ubicacion +
                    '}';
        }
    }

