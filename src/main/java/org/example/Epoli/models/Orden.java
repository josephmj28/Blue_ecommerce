package org.example.Epoli.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "ordenes")
    public class Orden {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        private String numero;
        private Date fechaCreacion;
        private Date fechaRecibida;
        private Double total;

        @ManyToOne
        private Usuario usuario;

        @OneToOne(mappedBy = "orden")
        private Detalleorden detalleOrden;

        public Orden() {
        }

        public Orden(Integer id, String numero, Date fechaCreacion, Date fechaRecibida, Double total) {
            this.id = id;
            this.numero = numero;
            this.fechaCreacion = fechaCreacion;
            this.fechaRecibida = fechaRecibida;
            this.total = total;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getNumero() {
            return numero;
        }

        public void setNumero(String numero) {
            this.numero = numero;
        }

        public Date getFechaCreacion() {
            return fechaCreacion;
        }

        public void setFechaCreacion(Date fechaCreacion) {
            this.fechaCreacion = fechaCreacion;
        }

        public Date getFechaRecibida() {
            return fechaRecibida;
        }

        public void setFechaRecibida(Date fechaRecibida) {
            this.fechaRecibida = fechaRecibida;
        }

        public Double getTotal() {
            return total;
        }

        public void setTotal(Double total) {
            this.total = total;
        }

        public Usuario getUsuario() {
            return usuario;
        }

        public void setUsuario(Usuario usuario) {
            this.usuario = usuario;
        }

        public Detalleorden getDetalleOrden() {
            return detalleOrden;
        }

        public void setDetalleorden(Detalleorden detalleOrden) {
            this.detalleOrden = detalleOrden;
        }

        @Override
        public String toString() {
            return "Orden{" +
                    "id=" + id +
                    ", numero='" + numero + '\'' +
                    ", fechaCreacion=" + fechaCreacion +
                    ", fechaRecibida=" + fechaRecibida +
                    ", total=" + total +
                    '}';
        }
    }



