package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Empleado {
    private int idEmpleado;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private String cargo;
    private String password;
    private int idTipoEmpleado;
    private Date fechaContratacion;
    private boolean activo;

    // Constructors
    public Empleado() {}

    public Empleado(String nombre, String apellido, String telefono, String cargo, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.cargo = cargo;
        this.password = password;
        this.activo = true;
    }

    // Getters and Setters
    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public int getIdTipoEmpleado() { return idTipoEmpleado; }
    public void setIdTipoEmpleado(int idTipoEmpleado) { this.idTipoEmpleado = idTipoEmpleado; }

    public Date getFechaContratacion() { return fechaContratacion; }
    public void setFechaContratacion(Date fechaContratacion) { this.fechaContratacion = fechaContratacion; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return nombre + " " + apellido;
    }
}