package eci.edu.byteProgramming.ejercicio.paper.util;

// Interfaz del Abstract Factory
// Cada implementación concreta sabe cómo construir su propio PaymentMethod
public interface PaymentFactory {
    PaymentMethod createPaymentMethod(double amount, String customerId, String description);
}
