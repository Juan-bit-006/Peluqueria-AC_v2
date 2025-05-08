package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Reserva {
    private int idReserva;
    private int idCliente;
    private Date fecha;
    private int idEstado;
    private String comentarios;
    private Timestamp fechaCreacion;
    private List<ReservaServicio> servicios;

    // Constructors
    public Reserva() {
        this.servicios = new ArrayList<>();
    }

    public Reserva(int idCliente, Date fecha) {
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.idEstado = 1; // Por defecto: Pendiente
        this.servicios = new ArrayList<>();
    }

    // Getters and Setters
    public int getIdReserva() { return idReserva; }
    public void setIdReserva(int idReserva) { this.idReserva = idReserva; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public int getIdEstado() { return idEstado; }
    public void setIdEstado(int idEstado) { this.idEstado = idEstado; }

    public String getComentarios() { return comentarios; }
    public void setComentarios(String comentarios) { this.comentarios = comentarios; }

    public Timestamp getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Timestamp fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public List<ReservaServicio> getServicios() { return servicios; }
    public void setServicios(List<ReservaServicio> servicios) { this.servicios = servicios; }

    public void addServicio(ReservaServicio servicio) {
        this.servicios.add(servicio);
    }
}