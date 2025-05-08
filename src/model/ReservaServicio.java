package model;

import java.sql.*;

public class ReservaServicio {
    private int idReservaServicio;
    private int idReserva;
    private int idServicio;
    private int idEmpleado;
    private Time horaInicio;
    private Time horaFin;
    private double precioAplicado;

    // Extra fields for presentation (not in DB)
    private String nombreCliente;
    private String apellidoCliente;
    private String telefonoCliente;
    private String descripcionServicio;

    // Constructors
    public ReservaServicio() {}

    public ReservaServicio(int idReserva, int idServicio, int idEmpleado, Time horaInicio, Time horaFin, double precioAplicado) {
        this.idReserva = idReserva;
        this.idServicio = idServicio;
        this.idEmpleado = idEmpleado;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.precioAplicado = precioAplicado;
    }

    // Getters and Setters
    public int getIdReservaServicio() { return idReservaServicio; }
    public void setIdReservaServicio(int idReservaServicio) { this.idReservaServicio = idReservaServicio; }

    public int getIdReserva() { return idReserva; }
    public void setIdReserva(int idReserva) { this.idReserva = idReserva; }

    public int getIdServicio() { return idServicio; }
    public void setIdServicio(int idServicio) { this.idServicio = idServicio; }

    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }

    public Time getHoraInicio() { return horaInicio; }
    public void setHoraInicio(Time horaInicio) { this.horaInicio = horaInicio; }

    public Time getHoraFin() { return horaFin; }
    public void setHoraFin(Time horaFin) { this.horaFin = horaFin; }

    public double getPrecioAplicado() { return precioAplicado; }
    public void setPrecioAplicado(double precioAplicado) { this.precioAplicado = precioAplicado; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getApellidoCliente() { return apellidoCliente; }
    public void setApellidoCliente(String apellidoCliente) { this.apellidoCliente = apellidoCliente; }

    public String getTelefonoCliente() { return telefonoCliente; }
    public void setTelefonoCliente(String telefonoCliente) { this.telefonoCliente = telefonoCliente; }

    public String getDescripcionServicio() { return descripcionServicio; }
    public void setDescripcionServicio(String descripcionServicio) { this.descripcionServicio = descripcionServicio; }

    @Override
    public String toString() {
        return nombreCliente + " " + apellidoCliente + " - " + descripcionServicio + " - " + horaInicio;
    }
}