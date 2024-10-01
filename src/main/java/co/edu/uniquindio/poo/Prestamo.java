package co.edu.uniquindio.poo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Prestamo {

    private String codigo;
    private Libro libro; // Asociar un libro con el préstamo
    private Estudiante estudiante;
    private Bibliotecario bibliotecario;
    private LocalDate fechaPrestamo;
    private LocalDate fechaEntrega;
    private double costoPrestamo;
    private List<DetallePrestamo> detalles;

    // Constructor
    public Prestamo(String codigo, Libro libro, Estudiante estudiante, Bibliotecario bibliotecario, LocalDate fechaPrestamo) {
        this.codigo = codigo;
        this.libro = libro;
        this.estudiante = estudiante;
        this.bibliotecario = bibliotecario;
        this.fechaPrestamo = fechaPrestamo;
        this.costoPrestamo = calcularCosto(); // Método que calcularía el costo del préstamo
        this.detalles = new ArrayList<>();
    }

    // Método para entregar el préstamo y calcular el costo
    public void entregarPrestamo(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
        this.costoPrestamo = calcularCosto();
        this.libro.incrementarUnidades(); // Devuelve la unidad del libro al inventario
    }

    // Método para calcular el costo del préstamo en función de los días de préstamo
    private double calcularCosto() {
        if (fechaEntrega != null && fechaPrestamo != null) {
            long diasPrestamo = java.time.temporal.ChronoUnit.DAYS.between(fechaPrestamo, fechaEntrega);
            return diasPrestamo * 1000; // Suponiendo un costo de 1000 por día
        }
        return 0;
    }
    public double calcularSubtotal() {
        double subtotal = 0;

        for (DetallePrestamo detalle : detalles) {
            subtotal += detalle.calcularSubtotal(); // Sumar el subtotal de cada detalle
        }

        return subtotal;
    }
    public void agregarDetalle(DetallePrestamo detallePrestamo) {
        this.detalles.add(detallePrestamo); // Agrega el detalle a la lista
        System.out.println("Detalle añadido: " + detallePrestamo.getLibro().getTitulo() + " - Cantidad: " + detallePrestamo.getCantidad());
    }
    


    /**
     * Getters y Setters
     * @return
     */
    public String getCodigo() {
        return codigo;
    }
    public List<DetallePrestamo> getDetalles() {
        return detalles;
    }

    public Libro getLibro() {
        return libro; // Asegúrate de tener este método
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public Bibliotecario getBibliotecario() {
        return bibliotecario;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public double getCostoPrestamo() {
        return costoPrestamo;
    }

    @Override
    public String toString() {
        return "Prestamo [codigo=" + codigo + ", libro=" + libro.getTitulo() + ", estudiante=" + estudiante.getNombre() 
               + ", bibliotecario=" + bibliotecario.getNombre() + ", fechaPrestamo=" + fechaPrestamo 
               + ", fechaEntrega=" + fechaEntrega + ", costoPrestamo=" + costoPrestamo + "]";
    }
}
