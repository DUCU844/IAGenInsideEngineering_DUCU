package eci.edu.byteProgramming.ejercicio.paper.Ejercicio1.model;

public abstract class Pelicula {
    protected String titulo;
    protected double precio;
    protected boolean disponible;

    public Pelicula(String titulo, double precio, boolean disponible) {
        this.titulo = titulo;
        this.precio = precio;
        this.disponible = disponible;
    }

    // Polimorfismo: cada subclase define su tipo
    public abstract String getTipo();

    public String getTitulo()     { return titulo; }
    public double getPrecio()     { return precio; }
    public boolean isDisponible() { return disponible; }

    @Override
    public String toString() {
        String estado = disponible ? "Disponible" : "No disponible";
        return String.format("[%s] %s - $%.0f - %s", getTipo(), titulo, precio, estado);
    }
}