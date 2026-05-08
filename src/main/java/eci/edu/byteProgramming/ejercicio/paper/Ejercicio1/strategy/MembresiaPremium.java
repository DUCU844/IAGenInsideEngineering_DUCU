package eci.edu.byteProgramming.ejercicio.paper.Ejercicio1.strategy;

public class MembresiaPremium implements EstrategiaDescuento {
    private static final double DESCUENTO = 0.20;

    @Override
    public double aplicarDescuento(double subtotal) {
        return subtotal * (1 - DESCUENTO);
    }

    @Override
    public String getNombreMembresia() { return "Premium"; }

    @Override
    public double getPorcentajeDescuento() { return DESCUENTO * 100; }
}
