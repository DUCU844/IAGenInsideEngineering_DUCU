package eci.edu.byteProgramming.ejercicio.paper.Ejercicio1.strategy;

public interface EstrategiaDescuento {
    double aplicarDescuento(double subtotal);
    String getNombreMembresia();
    double getPorcentajeDescuento();
}