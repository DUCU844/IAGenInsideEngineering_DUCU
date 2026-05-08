package eci.edu.byteProgramming.ejercicio.paper.Ejercicio1.service;

import eci.edu.byteProgramming.ejercicio.paper.Ejercicio1.model.Pelicula;
import eci.edu.byteProgramming.ejercicio.paper.Ejercicio1.strategy.EstrategiaDescuento;

import java.util.List;

public class AlquilerService {

    public double calcularSubtotal(List<Pelicula> peliculas) {
        return peliculas.stream()
                .mapToDouble(Pelicula::getPrecio)
                .sum();
    }

    public double calcularTotal(List<Pelicula> peliculas, EstrategiaDescuento estrategia) {
        return estrategia.aplicarDescuento(calcularSubtotal(peliculas));
    }

    public void imprimirRecibo(List<Pelicula> peliculas, EstrategiaDescuento estrategia) {
        double subtotal  = calcularSubtotal(peliculas);
        double total     = calcularTotal(peliculas, estrategia);
        double descuento = subtotal - total;

        System.out.println("\n--- RECIBO DE ALQUILER ---");
        System.out.println("Cliente: " + estrategia.getNombreMembresia());
        System.out.println("Peliculas:");

        for (Pelicula p : peliculas) {
            System.out.printf("  - %s (%s) - $%.0f%n",
                    p.getTitulo(), p.getTipo(), p.getPrecio());
        }

        System.out.printf("Subtotal: $%.0f%n", subtotal);

        if (descuento > 0) {
            System.out.printf("Descuento (%.0f%%): $%.0f%n",
                    estrategia.getPorcentajeDescuento(), descuento);
        }

        System.out.printf("Total a pagar: $%.0f%n", total);
        System.out.println("--------------------------");
        System.out.println("¡Disfrute su pelicula!");
    }
}