package eci.edu.byteProgramming.ejercicio.paper.Ejercicio1.strategy;

public class MembresiaBasica implements EstrategiaDescuento {

    @Override
    public double aplicarDescuento(double subtotal) {
        return subtotal; // precio normal, sin descuento
    }

    @Override
    public String getNombreMembresia() { return "Basica"; }

    @Override
    public double getPorcentajeDescuento() { return 0.0; }
}
