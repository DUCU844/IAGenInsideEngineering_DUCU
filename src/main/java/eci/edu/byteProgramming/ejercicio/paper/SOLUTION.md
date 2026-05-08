# SOLUCION.md — IAGen Inside Engineering

**Estudiante:** DUCU  
**Curso:** CVDS / DOSW  
**Fecha:** 08 de mayo de 2026

---

## Reflexión inicial

Antes de iniciar los ejercicios, mi percepción era que la IA podría resolver los problemas de forma similar independientemente del contexto proporcionado. Al finalizar la actividad, comprobé que la calidad del contexto entregado a la IA tiene un impacto directo y measurable en la precisión de la solución: el Problema #2, con código base, UML y enunciado detallado, permitió identificar errores concretos que en el Problema #1 no habrían sido detectables sin ejecutar el código.

---

## Problema #1: El Videoclub de Don Mario

**Tiempo empleado:** 15 minutos  
**Contexto disponible:** Solo enunciado

---

### Prompts utilizados

**Prompt 1 — Entrega del enunciado completo y solicitud de solución:**

> Problema #1: El Videoclub de Don
> Duración: Máximo 15 minutos
> Don Mario acaba de abrir un videoclub moderno donde los clientes pueden alquilar peliculas fisicas o digitales. El problema es que su sistema anterior era un caos: todos los precios se calculaban igual sin importar el tipo de pelicula o membresia del cliente, y no habia forma de saber que peliculas estaban disponibles en tiempo real.
> Tu Mision
> Ayuda a Don Mario creando un sistema de alquiler que permita:
> 1. Registrar peliculas (fisicas o digitales) con su disponibilidad.
> 2. Que el cliente elija X peliculas para alquilar.
> 3. Calcular el precio total segun su tipo de membresia:
     >    * Basica: precio normal.
>    * Premium: 20% de descuento.
> 4. Mostrar al finalizar un recibo con las peliculas, precio por unidad y total.
     > Peliculas Disponibles
> * [Fisica] Interestellar - $8.000 - Disponible
> * [Fisica] El Padrino - $7.000 - No disponible
> * [Digital] Inception - $5.000 - Disponible
> * [Digital] Matrix - $6.000 - Disponible
    > Caso de Ejemplo
    > Membresia del cliente: Premium Seleccione peliculas (numeros separados por coma): 1,3
>
> --- RECIBO DE ALQUILER ---
> Cliente: Premium
> Peliculas:
>  - Interestellar (Fisica) - $8.000
>  - Inception (Digital) - $5.000
     > Subtotal: $13.000
     > Descuento (20%): $2.600
     > Total a pagar: $10.400
> --------------------------
> ¡Disfrute su pelicula!
>
> Objetivos del Ejercicio
> * Identificar cual o cuales patrones de diseno utilizar.
> * Explicar que principios de SOLID se aplican.
> * Aplicar polimorfismo y encapsulamiento.
> * Colocar evidencia de la ejecucion del ejercicio (ejecucion por consola; no es necesario hacer front).
>
> Este es el ejercicio numero uno, muestrame como se podria solucionar el problema y dame las razones de tus elecciones

**Prompt 2 — Solicitud de implementación completa:**

> Estoy de acuerdo con tu analisis, muestrame como seria la implementacion de esta solución de cero

---

### Patrones de diseño identificados

#### Strategy
Se aplica para el cálculo del precio según la membresía del cliente. La interfaz `EstrategiaDescuento` define el contrato, y cada implementación concreta encapsula su algoritmo de descuento sin que el servicio de alquiler conozca los detalles internos.

```
EstrategiaDescuento (interfaz)
├── MembresiaBasica     → retorna subtotal sin modificación
└── MembresiaPremium    → aplica 20% de descuento
```

**Justificación:** el comportamiento que varía es el cálculo del precio. Strategy encapsula exactamente esa variación y permite agregar nuevas membresías (VIP, Estudiante) sin modificar código existente.

#### Factory Method
Se aplica para la creación de películas. `PeliculaFactory` centraliza la instanciación de `PeliculaFisica` y `PeliculaDigital`, evitando que el código cliente decida directamente qué subclase construir.

```
PeliculaFactory
├── crearFisica(...)   → new PeliculaFisica(...)
└── crearDigital(...)  → new PeliculaDigital(...)
```

---

### Principios SOLID aplicados

| Principio | Cómo se evidencia |
|-----------|-------------------|
| **SRP** | `Pelicula` solo representa datos. `AlquilerService` solo coordina la lógica de negocio. `Main` solo gestiona la interacción con el usuario. |
| **OCP** | Para agregar una membresía "VIP" se crea una nueva clase que implementa `EstrategiaDescuento`. No se toca ninguna clase existente. |
| **LSP** | `PeliculaFisica` y `PeliculaDigital` son intercambiables en cualquier lugar donde se espera un `Pelicula`. |
| **DIP** | `AlquilerService` depende de la abstracción `EstrategiaDescuento`, nunca de `MembresiaBasica` ni `MembresiaPremium` directamente. |

---

### Polimorfismo y encapsulamiento

- **Polimorfismo:** `Pelicula` declara `getTipo()` como método abstracto. Cada subclase lo implementa devolviendo su tipo. El servicio llama `p.getTipo()` sin saber qué subclase es.
- **Encapsulamiento:** Los atributos de `Pelicula` son `protected`. El porcentaje de descuento en `MembresiaPremium` está encapsulado como constante privada `DESCUENTO = 0.20`. Ningún componente externo necesita conocer ese valor.

---

### Estructura del proyecto

```
src/
├── model/
│   ├── Pelicula.java              (abstracta)
│   ├── PeliculaFisica.java
│   └── PeliculaDigital.java
├── strategy/
│   ├── EstrategiaDescuento.java   (interfaz)
│   ├── MembresiaBasica.java
│   └── MembresiaPremium.java
├── factory/
│   └── PeliculaFactory.java
├── service/
│   └── AlquilerService.java
└── ui/
    └── Main.java
```

---

### Evidencia de ejecución

**Caso 1 — Membresía Premium, películas 1 y 3 (caso del enunciado):**

```
=== VIDEOCLUB DON MARIO ===

Peliculas disponibles:
  1. [Fisica] Interestellar - $8000 - Disponible
  2. [Fisica] El Padrino - $7000 - No disponible
  3. [Digital] Inception - $5000 - Disponible
  4. [Digital] Matrix - $6000 - Disponible

Tipo de membresia (1=Basica / 2=Premium): 2
Seleccione peliculas (numeros separados por coma): 1,3

--- RECIBO DE ALQUILER ---
Cliente: Premium
Peliculas:
  - Interestellar (Fisica) - $8000
  - Inception (Digital) - $5000
Subtotal: $13000
Descuento (20%): $2600
Total a pagar: $10400
--------------------------
¡Disfrute su pelicula!
```

**Caso 2 — Membresía Básica, película no disponible incluida:**

```
Tipo de membresia (1=Basica / 2=Premium): 1
Seleccione peliculas (numeros separados por coma): 2,4
  [!] "El Padrino" no disponible, se omite.

--- RECIBO DE ALQUILER ---
Cliente: Basica
Peliculas:
  - Matrix (Digital) - $6000
Subtotal: $6000
Total a pagar: $6000
--------------------------
¡Disfrute su pelicula!
```

**Caso 3 — Selección con solo película no disponible:**

```
Tipo de membresia (1=Basica / 2=Premium): 1
Seleccione peliculas (numeros separados por coma): 2
  [!] "El Padrino" no disponible, se omite.

No se seleccionaron peliculas validas. Hasta pronto.
```

---

## Problema #2: Tienda Virtual

**Tiempo empleado:** 22 minutos  
**Contexto disponible:** Enunciado + código base incompleto + diagramas UML

---

### Prompts utilizados

**Prompt 1 — Entrega del enunciado:**

> Problema #2: Tienda Virtual
> Duración: Máximo 25 minutos
> Descripción del Problema
> Una tienda virtual necesita implementar un sistema de pagos que soporte múltiples métodos de pago:
> * Tarjeta de crédito
> * PayPal
> * Criptomonedas
    > Cada método tiene su propio proceso de validación y ejecución. El sistema debe:
> 1. Crear objetos de pago y sus validadores correspondientes
> 2. No exponer los detalles internos a la lógica principal de compras
> 3. Notificar automáticamente a otros componentes cuando se procesa un pago exitoso:
     >    * 📦 Módulo de inventario: descontar del stock
>    * 📄 Módulo de facturación: generar factura
>    * 📧 Módulo de notificaciones: enviar correo al cliente
       > Requisitos Técnicos
       > La solución debe ser flexible para:
> * ✅ Soportar nuevos métodos de pago sin modificar la lógica existente
> * ✅ Permitir que nuevos módulos reaccionen a eventos de pago sin cambiar el core
    > Pistas de patrones:
> * Se requiere un mecanismo para crear familias de objetos relacionados (pago + validador)
> * Se requiere un mecanismo para notificar automáticamente a múltiples observadores de eventos
>
> Ahora te comparto el ejercicio #2

**Prompt 2 — Entrega del código base:**

> [archivo adjunto: IAGenInsideEngineering_DUCU.zip]
>
> Te comparto el codigo que se me entrego para el ejercicio

**Prompt 3 — Solicitud de pruebas unitarias:**

> Dame una clase de pruebas para probar el correcto funcionamiento del ejercicio

**Prompt 4 — Solicitud del documento de solución con prompts reales:**

> Quiero que coloques los promts completos como te los entrege para que hagan analisis correcto de como te hago preguntas y te solicito informacion
>
> Dame una clase de pruebas para probar el correcto funcionamiento del ejercicio
>
> dame le -md

---

### Patrones de diseño identificados

#### Abstract Factory
La interfaz `PaymentFactory` actúa como fábrica abstracta. Cada implementación concreta crea su familia de objeto `PaymentMethod` sin exponer los detalles de construcción a `ECIPayment`. Esto cumple el requisito: "crear familias de objetos relacionados sin exponer los detalles internos".

```
PaymentFactory (interfaz)
├── CreditCardPaymentFactory  → crea CreditCardFactory (PaymentMethod)
├── PaypalPaymentFactory      → crea PaypalFactory (PaymentMethod)
└── CryptoPaymentFactory      → crea CryptoFactory (PaymentMethod)
```

#### Observer
`ECIPayment` es el sujeto observable. Mantiene una lista de `PaymentObserver` y los notifica en `processPayment()`. `PaymentEventObserver` es el observador concreto que coordina los tres módulos post-pago.

```
ECIPayment (sujeto)
└── notifica a → PaymentObserver (interfaz)
                 └── PaymentEventObserver
                     ├── Inventory.discountProduct(...)
                     ├── Facturation.generateInvoice(...)
                     └── Notification.sendConfirmationEmail(...)
```

---

### Pregunta 1: ¿Son los patrones adecuados?

Sí, con una observación. El enunciado menciona "crear familias de objetos relacionados (pago + validador)". En el código entregado el validador (`ValidatePayment`) está integrado como interfaz dentro de `PaymentMethod` — no es un objeto separado creado por la fábrica. Para cumplir literalmente Abstract Factory en su forma completa habría que separar el validador como producto independiente. Sin embargo, para el alcance del ejercicio la implementación es funcional y justificable: la validación es parte del comportamiento del método de pago, no un objeto externo.

---

### Pregunta 2: ¿Qué clases e interfaces faltan?

La pieza crítica ausente era la interfaz `PaymentFactory`:

```java
public interface PaymentFactory {
    PaymentMethod createPaymentMethod(double amount, String customerId, String description);
}
```

Sin esta interfaz, `ECIPayment.processPayment(PaymentFactory factory, ...)` no compila porque el tipo `PaymentFactory` no existe en el proyecto.

Adicionalmente faltaban las fábricas concretas `CreditCardPaymentFactory`, `PaypalPaymentFactory` y `CryptoPaymentFactory`. Las clases originales (`CreditCardFactory`, `PaypalFactory`, `CryptoFactory`) extendían `PaymentMethod` directamente — eran el producto, no la fábrica. Se crearon fábricas concretas separadas que implementan `PaymentFactory` y delegan la construcción a los `PaymentMethod` existentes.

---

### Pregunta 3: ¿El diagrama de contexto es suficiente?

El diagrama provisto muestra las relaciones entre componentes y es útil como punto de partida. Sin embargo, presenta dos omisiones relevantes:

1. **No muestra la interfaz `PaymentFactory`**, pieza central del patrón Abstract Factory. Solo muestra las clases concretas extendiendo `PaymentMethod`, lo que lleva a confundir fábrica con producto.

2. **No distingue entre fábrica y producto** en las clases `CreditCardFactory`, `PaypalFactory` y `CryptoFactory`. El diseño correcto requiere fábricas separadas que no aparecen en el diagrama.

**Cambio sugerido:** agregar la interfaz `PaymentFactory` con las tres implementaciones concretas y mostrar la dependencia de `ECIPayment` hacia la interfaz, no hacia las clases concretas.

---

### Pregunta 4: Errores del código que impiden compilar

| # | Archivo | Error | Descripción |
|---|---------|-------|-------------|
| 1 | `PaymentEventObserver.java` | Import incorrecto | Importa `javax.management.Notification` (clase JMX del JDK) en lugar de `eci.edu.byteProgramming.ejercicio.paper.util.Notification`. Los métodos `sendConfirmationEmail` y `sendFailureNotification` no son reconocidos. |
| 2 | Todo el proyecto | Tipo inexistente | `ECIPayment.processPayment()` recibe un parámetro de tipo `PaymentFactory`, pero esa interfaz no está definida en ningún archivo del proyecto. Cualquier clase que use `ECIPayment` falla en compilación. |
| 3 | `pom.xml` | Versión SNAPSHOT inaccesible | El parent POM usa `spring-boot-starter-parent:4.0.0-SNAPSHOT`, versión inexistente en repositorios públicos. Corregido a `3.2.5` estable. |
| 4 | `CreditCardFactory.java` | NullPointerException en constructor | `determineCardType()` llama `cardNumber.startsWith()` sin verificar null. Si se pasa `null` como número de tarjeta, el constructor lanza `NullPointerException` antes de que `validateCardNumber()` pueda retornar `false`. Detectado por prueba unitaria. |

---

### Pregunta 5: Correcciones implementadas

**Corrección 1 — Import en `PaymentEventObserver.java`:**

```java
// ANTES (incorrecto)
import javax.management.Notification;

// DESPUÉS
// Se eliminó el import. Notification está en el mismo paquete,
// no requiere import explícito.
```

**Corrección 2 — Interfaz `PaymentFactory` creada:**

```java
public interface PaymentFactory {
    PaymentMethod createPaymentMethod(double amount, String customerId, String description);
}
```

**Corrección 3 — Fábricas concretas agregadas (ejemplo):**

```java
public class CreditCardPaymentFactory implements PaymentFactory {
    private final String number, name, expirationDate, cvv, address;

    public CreditCardPaymentFactory(String number, String name,
                                    String expirationDate, String cvv, String address) { ... }

    @Override
    public PaymentMethod createPaymentMethod(double amount, String customerId, String description) {
        return new CreditCardFactory(amount, customerId, description,
                number, name, expirationDate, cvv, address);
    }
}
```

**Corrección 4 — Guard null en `CreditCardFactory.determineCardType()`:**

```java
// ANTES
private String determineCardType(String cardNumber) {
    if (cardNumber.startsWith("4")) return "VISA"; // NullPointerException si null
    ...
}

// DESPUÉS
private String determineCardType(String cardNumber) {
    if (cardNumber == null) return "UNKNOWN";       // guard agregado
    if (cardNumber.startsWith("4")) return "VISA";
    ...
}
```

---

### Pregunta 6: Pruebas unitarias — evidencia de ejecución

**Resultado:** 46 pruebas ejecutadas, 46 exitosas, 0 fallidas.

```
Test run finished after 9542 ms
[  46 tests found     ]
[   0 tests skipped   ]
[  46 tests started   ]
[   0 tests aborted   ]
[  46 tests successful]
[   0 tests failed    ]
```

**Cobertura por grupo:**

| # | Grupo de pruebas | Pruebas |
|---|------------------|---------|
| 1 | Validación Tarjeta de Crédito | 7 |
| 2 | Validación PayPal | 5 |
| 3 | Validación Criptomoneda | 5 |
| 4 | Transiciones de PaymentStatus | 5 |
| 5 | Abstract Factory — tipos creados | 6 |
| 6 | Observer — notificaciones | 6 |
| 7 | Módulo Inventory | 6 |
| 8 | Módulo Facturation | 4 |
| 9 | Entidad Product | 1 |
| | **Total** | **46** |

La prueba `nullCardNumberFailsValidation` del grupo 1 fue la que reveló el bug en `determineCardType()`. Sin la suite de pruebas, este error habría pasado desapercibido en el análisis estático del código.

---

### Estructura final del proyecto (Ejercicio 2)

```
util/
├── PaymentFactory.java              ← NUEVA (interfaz Abstract Factory)
├── PaymentMethod.java               ← base abstracta (existente)
├── ValidatePayment.java             ← interfaz de validación (existente)
├── PaymentStatus.java               ← enum (existente)
│
├── CreditCardFactory.java           ← PaymentMethod concreto (corregido: null guard)
├── PaypalFactory.java               ← PaymentMethod concreto (existente)
├── CryptoFactory.java               ← PaymentMethod concreto (existente)
│
├── CreditCardPaymentFactory.java    ← NUEVA (implementa PaymentFactory)
├── PaypalPaymentFactory.java        ← NUEVA (implementa PaymentFactory)
├── CryptoPaymentFactory.java        ← NUEVA (implementa PaymentFactory)
│
├── ECIPayment.java                  ← sujeto Observer (existente)
├── PaymentObserver.java             ← interfaz Observer (existente)
├── PaymentEventObserver.java        ← observador concreto (corregido: import)
│
├── Inventory.java                   ← módulo inventario (existente)
├── Facturation.java                 ← módulo facturación (existente)
├── Notification.java                ← módulo notificaciones (existente)
├── Product.java                     ← entidad producto (existente)
│
└── PaymentMain.java                 ← NUEVA (clase de demostración)
```

---

### Evidencia de ejecución

**Caso 1 — Tarjeta de crédito válida:**

```
=== CASO 1: Tarjeta de Crédito (válida) ===
🚀 ECI Payments: Starting payment process...
Customer: Juan Pérez (juan@email.com)
Amount: $1200.0
----------------------------------------
Processing Credit Card payment...
Contacting bank for card: **** **** **** 1111
Payment authorized by bank
Payment processed successfully!

Payment Observer: Processing successful payment events...
✅ Inventory: Discounted 1 units of Gaming Laptop — Remaining stock: 4
Facturation: Invoice INV-1001 | Subtotal: $1200.00 | Tax: $228.00 | Total: $1428.00 COP
Notification: Confirmation email sent to juan@email.com
All post-payment processes completed successfully!
```

**Caso 2 — PayPal válido:**

```
=== CASO 2: PayPal (válido) ===
Processing PayPal payment...
PayPal payment authorized for: maria@paypal.com
Payment processed successfully!
✅ Inventory: Discounted 1 units of Smartphone — Remaining stock: 9
Invoice INV-1002 generated. Total: $952.00 COP
Confirmation email sent to maria@email.com
```

**Caso 3 — Crypto con saldo suficiente:**

```
=== CASO 3: Criptomoneda (saldo suficiente) ===
Processing Cryptocurrency payment...
Transaction broadcasted to blockchain
Blockchain hash: 0x32e60ef6
Payment processed successfully!
✅ Inventory: Discounted 1 units of Java Programming Book — Remaining stock: 19
Invoice INV-1003 generated. Total: $54.73 COP
Confirmation email sent to carlos@email.com
```

**Caso 4 — Crypto con saldo insuficiente (pago fallido):**

```
=== CASO 4: Criptomoneda (saldo insuficiente) ===
Processing Cryptocurrency payment...
Crypto validation failed!
Payment failed!

Payment Observer: Processing failed payment events...
Notification: Failure notification sent to ana@email.com
Failed payment processes completed.
```

---

## Conclusiones de la actividad

### Comparación entre los dos enfoques

| Aspecto | Problema #1 (solo enunciado) | Problema #2 (contexto enriquecido) |
|---------|------------------------------|-------------------------------------|
| Tiempo empleado | 15 minutos | 19 minutos |
| Prompts necesarios | 2 | 4 |
| Precisión de la solución | Alta — diseño libre | Alta — condicionada al código existente |
| Detección de errores | No aplica (código propio) | Crítica: 4 errores identificados |
| Calidad del prompt | Requirió especificar patrones explícitamente | El código base guió el análisis |
| Errores revelados por pruebas | 0 | 1 (NullPointerException en CreditCardFactory) |

### Aprendizajes clave

**La IA es más precisa con más contexto.** En el Problema #2, el código base permitió identificar errores concretos que en el Problema #1 no habrían sido detectables sin ejecutar el código. El contexto enriquecido reduce la ambigüedad del prompt y eleva la calidad de la respuesta.

**Los prompts completos importan.** La diferencia entre el Problema #1 (2 prompts, enunciado solamente) y el Problema #2 (4 prompts, enunciado + código + pruebas + documentación) demuestra que proporcionar todo el contexto disponible desde el principio produce respuestas más ajustadas y reduce iteraciones correctivas.

**Las pruebas unitarias revelan lo que el análisis estático no ve.** El bug en `CreditCardFactory.determineCardType()` pasó el análisis de código manual pero fue detectado inmediatamente por la prueba `nullCardNumberFailsValidation`. La IA no reemplaza la validación — la complementa.

**El desarrollador sigue siendo responsable.** La IA identificó que `CreditCardFactory` mezclaba responsabilidades de fábrica y producto, pero la decisión de separar en fábricas independientes requirió comprensión del patrón Abstract Factory. La IA amplifica el conocimiento existente, no lo reemplaza.

---

*Repositorio: fork de IAGenInsideEngineering — DOSW Testigos de Jehová*