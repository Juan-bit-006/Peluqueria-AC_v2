package model;

import java.sql.Date;

public class Usuario {
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private String cargo;
    private String pass;
    private int idTipoEmpleado;
    private Date fechaContratacion;
    private boolean activo;

    public Usuario(String nombre, String apellido, String telefono, String email, String cargo, String pass, int idTipoEmpleado, Date fechaContratacion, boolean activo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.cargo = cargo;
        this.pass = pass;
        this.idTipoEmpleado = idTipoEmpleado;
        this.fechaContratacion = fechaContratacion;
        this.activo = activo;
    }

    // Getters y setters

    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }
    public String getCargo() { return cargo; }
    public String getPass() { return pass; }
    public int getIdTipoEmpleado() { return idTipoEmpleado; }
    public Date getFechaContratacion() { return fechaContratacion; }
    public boolean isActivo() { return activo; }
}
