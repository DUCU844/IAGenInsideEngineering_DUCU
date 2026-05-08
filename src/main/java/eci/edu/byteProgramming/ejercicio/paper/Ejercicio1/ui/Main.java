package eci.edu.byteProgramming.ejercicio.paper.Ejercicio1.ui;

import eci.edu.byteProgramming.ejercicio.paper.Ejercicio1.factory.PeliculaFactory;
import eci.edu.byteProgramming.ejercicio.paper.Ejercicio1.model.Pelicula;
import eci.edu.byteProgramming.ejercicio.paper.Ejercicio1.service.AlquilerService;
import eci.edu.byteProgramming.ejercicio.paper.Ejercicio1.strategy.EstrategiaDescuento;
import eci.edu.byteProgramming.ejercicio.paper.Ejercicio1.strategy.MembresiaBasica;
import eci.edu.byteProgramming.ejercicio.paper.Ejercicio1.strategy.MembresiaPremium;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        // Catalogo usando Factory Method
        List<Pelicula> catalogo = new ArrayList<>();
        catalogo.add(PeliculaFactory.crearFisica  ("Interestellar", 8000, true));
        catalogo.add(PeliculaFactory.crearFisica  ("El Padrino",    7000, false));
        catalogo.add(PeliculaFactory.crearDigital ("Inception",     5000, true));
        catalogo.add(PeliculaFactory.crearDigital ("Matrix",        6000, true));

        Scanner sc = new Scanner(System.in);
        AlquilerService servicio = new AlquilerService();

        // Mostrar catalogo
        System.out.println("=== VIDEOCLUB DON MARIO ===\n");
        System.out.println("Peliculas disponibles:");
        for (int i = 0; i < catalogo.size(); i++) {
            System.out.printf("  %d. %s%n", i + 1, catalogo.get(i));
        }

        // Seleccion de membresia — aqui se inyecta la estrategia
        System.out.print("\nTipo de membresia (1=Basica / 2=Premium): ");
        int opcion = Integer.parseInt(sc.nextLine().trim());
        EstrategiaDescuento estrategia = (opcion == 2)
                ? new MembresiaPremium()
                : new MembresiaBasica();

        // Seleccion de peliculas
        System.out.print("Seleccione peliculas (numeros separados por coma): ");
        String[] indices = sc.nextLine().split(",");

        List<Pelicula> seleccion = new ArrayList<>();
        for (String idx : indices) {
            int i = Integer.parseInt(idx.trim()) - 1;

            if (i < 0 || i >= catalogo.size()) {
                System.out.println("  [!] Numero " + (i + 1) + " invalido, se omite.");
                continue;
            }

            Pelicula p = catalogo.get(i);
            if (!p.isDisponible()) {
                System.out.println("  [!] \"" + p.getTitulo() + "\" no disponible, se omite.");
                continue;
            }

            seleccion.add(p);
        }

        if (seleccion.isEmpty()) {
            System.out.println("\nNo se seleccionaron peliculas validas. Hasta pronto.");
            sc.close();
            return;
        }

        // Delegamos todo al servicio — Main no sabe como se calcula nada
        servicio.imprimirRecibo(seleccion, estrategia);
        sc.close();
    }
}
