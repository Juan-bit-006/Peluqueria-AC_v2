package model;

public class Servicio {
    private int idServicio;
    private String descripcion;
    private double precio;
    private int duracionMinutos;
    private boolean activo;
    private String nombre;



    public Servicio() {
        this.descripcion = descripcion;
        this.precio = precio;
        this.duracionMinutos = duracionMinutos;
        this.activo = true;
        this.nombre = nombre;
    }



    // Getters and Setters
    public int getIdServicio() { return idServicio; }
    public void setIdServicio(int idServicio) { this.idServicio = idServicio; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(int duracionMinutos) { this.duracionMinutos = duracionMinutos; }

    public boolean getActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public String getNombre() {return nombre; }
    public void setNombre(String nombre) {this.nombre = nombre;}


    @Override
    public String toString() {
        return descripcion + " - $" + precio;
    }
}