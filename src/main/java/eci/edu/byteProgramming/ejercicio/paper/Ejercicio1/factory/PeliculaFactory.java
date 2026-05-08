package eci.edu.byteProgramming.ejercicio.paper.Ejercicio1.factory;


import eci.edu.byteProgramming.ejercicio.paper.Ejercicio1.model.Pelicula;
import eci.edu.byteProgramming.ejercicio.paper.Ejercicio1.model.PeliculaDigital;
import eci.edu.byteProgramming.ejercicio.paper.Ejercicio1.model.PeliculaFisica;

public class PeliculaFactory {

    public static Pelicula crearFisica(String titulo, double precio, boolean disponible) {
        return new PeliculaFisica(titulo, precio, disponible);
    }

    public static Pelicula crearDigital(String titulo, double precio, boolean disponible) {
        return new PeliculaDigital(titulo, precio, disponible);
    }
}
