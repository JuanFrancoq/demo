package co.edu.uniquindio.poo;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Biblioteca {
    private ArrayList<Bibliotecario> bibliotecarios = new ArrayList<>();
    private ArrayList<Estudiante> estudiantes = new ArrayList<>();
    private ArrayList<Libro> libros = new ArrayList<>();
    private ArrayList<Prestamo> prestamos = new ArrayList<>();

    public List<Bibliotecario> getBibliotecarios() {
        return bibliotecarios;
    }

    // Método para agregar bibliotecarios
    public void agregarBibliotecario(Bibliotecario bibliotecario) {
        bibliotecarios.add(bibliotecario);
    }

    // Método para agregar estudiantes
    public void agregarEstudiante(Estudiante estudiante) {
        estudiantes.add(estudiante);
    }

    // Método para agregar libros
    public void agregarLibro(Libro libro) {
        libros.add(libro);
    }

    // Método para crear préstamos
    public void crearPrestamo(Prestamo prestamo) {
        prestamos.add(prestamo);
        for (DetallePrestamo detalle : prestamo.getDetalles()) {
            detalle.getLibro().disminuirUnidades(); 
        }
    }

    // Método para buscar libro por código
    public Libro buscarLibroPorCodigo(String codigoLibro) {
        for (Libro libro : libros) {
            if (libro.getCodigo().equals(codigoLibro)) {
                return libro;
            }
        }
        return null;
    }

    /**
     * Método para consultar los datos de un libro dado su código.
     * @param codigoLibro El código del libro a consultar.
     * @return El objeto Libro si se encuentra, null en caso contrario.
     */
    public Libro consultarDatosLibroPorCodigo(String codigoLibro) {
        return buscarLibroPorCodigo(codigoLibro);
    }

    /**
     * Método para contar la cantidad de préstamos de un libro por su título.
     * @param titulo El título del libro.
     * @return La cantidad de préstamos.
     */
    public int contarPrestamosPorLibro(String titulo) {
        int contador = 0;
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getLibro().getTitulo().equalsIgnoreCase(titulo)) {
                contador++;
            }
        }
        return contador;
    }

    /**
     * Método para reemplazar un libro por otro dado su código.
     * @param codigo El código del libro a reemplazar.
     * @param nuevoLibro El nuevo objeto Libro que lo reemplazará.
     */
    public boolean reemplazarLibro(String codigoLibroAntiguo, Libro nuevoLibro) {
        for (int i = 0; i < libros.size(); i++) {
            if (libros.get(i).getCodigo().equals(codigoLibroAntiguo)) {
                libros.set(i, nuevoLibro); 
                return true; 
            }
        }
        return false;
    }

    // Método para adicionar un libro al préstamo
    public boolean adicionarLibroAPrestamo(String codigoPrestamo, Libro libro) {
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getCodigo().equals(codigoPrestamo)) {
                if (libro.getUnidadesDisponibles() > 0) {
                    prestamo.getDetalles().add(new DetallePrestamo(1, prestamo, libro));
                    libro.disminuirUnidades();
                    System.out.println("Libro '" + libro.getTitulo() + "' añadido al préstamo.");
                    return true;
                } else {
                    System.out.println("No hay unidades disponibles para el libro: " + libro.getTitulo());
                }
            }
        }
        return false; // Préstamo no encontrado
    }

    // Método para entregar un préstamo
    public void entregarPrestamo(String codigoPrestamo, LocalDate fechaEntrega) {
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getCodigo().equals(codigoPrestamo)) {
                long diasPrestamo = java.time.temporal.ChronoUnit.DAYS.between(prestamo.getFechaPrestamo(), fechaEntrega);
                double costoPrestamo = calcularCostoPrestamo(prestamo, diasPrestamo);
                System.out.println("Costo del préstamo: $" + costoPrestamo);
                
                // Actualizar unidades del libro y estado
                for (DetallePrestamo detalle : prestamo.getDetalles()) {
                    detalle.getLibro().setUnidadesDisponibles(
                        detalle.getLibro().getUnidadesDisponibles() + detalle.getCantidad()
                    );
                }
                
                // Actualizar estado del préstamo
                System.out.println("Préstamo entregado con éxito.");
                return;
            }
        }
        System.out.println("Préstamo no encontrado.");
    }

    // Método para calcular el costo del préstamo
    private double calcularCostoPrestamo(Prestamo prestamo, long diasPrestamo) {
        double costoPorDia = 1000; // Define el costo por día
        return diasPrestamo * costoPorDia;
    }

    // Método para consultar datos de un préstamo
    public Prestamo consultarDatosPrestamo(String codigoPrestamo) {
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getCodigo().equals(codigoPrestamo)) {
                return prestamo;
            }
        }
        return null; // No encontrado
    }

    // Método para mostrar la cantidad de préstamos realizados por cada bibliotecario
    public void mostrarPrestamosPorBibliotecario() {
        for (Bibliotecario bibliotecario : bibliotecarios) {
            int contador = 0;
            for (Prestamo prestamo : prestamos) {
                if (prestamo.getBibliotecario().equals(bibliotecario)) {
                    contador++;
                }
            }
            System.out.println("Bibliotecario: " + bibliotecario.getNombre() + " - Préstamos realizados: " + contador);
        }
    }
    public int contarPrestamosPorNombreLibro(String nombreLibro) {
        int contador = 0;
        
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getLibro().getTitulo().equalsIgnoreCase(nombreLibro)) {
                contador++;
            }
        }
        
        return contador;
    }
    // Método para consultar un préstamo por código
    public Prestamo consultarDatosPrestamoPorCodigo(String codigo) {
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getCodigo().equals(codigo)) {
                return prestamo;
            }
        }
        return null; // Retorna null si no se encuentra el préstamo
    }
    // Mostrar los datos del estudiante con más préstamos (sin importar el libro)
    public Estudiante obtenerEstudianteConMasPrestamos() {
        return estudiantes.stream()
                .max(Comparator.comparingInt(est -> contarPrestamosPorEstudiante(est)))
                .orElse(null);  // Retorna null si no hay estudiantes
    }

    private int contarPrestamosPorEstudiante(Estudiante estudiante) {
        return (int) prestamos.stream()
                .filter(prestamo -> prestamo.getEstudiante().equals(estudiante))
                .count();
    }
    // Total de dinero recaudado por la empresa
    public double calcularTotalRecaudado() {
        double totalRecaudado = 0;
    
        for (Prestamo prestamo : prestamos) {
            totalRecaudado += prestamo.calcularSubtotal();
        }
    
        return totalRecaudado;
    }
    public double calcularPagoBibliotecario(Bibliotecario bibliotecario) {
    double totalPrestamosBibliotecario = 0;

    // Calcular el total de los préstamos realizados por el bibliotecario
    for (Prestamo prestamo : prestamos) {
        if (prestamo.getBibliotecario().equals(bibliotecario)) {
            totalPrestamosBibliotecario += prestamo.calcularSubtotal();
        }
    }

    double porcentajeBase = totalPrestamosBibliotecario * 0.20;  // 20% del valor de cada préstamo

    // Calcular años de antigüedad usando la fecha de ingreso
    LocalDate fechaActual = LocalDate.now();
    LocalDate fechaIngreso = bibliotecario.getFechaIngreso();
    int aniosAntiguedad = Period.between(fechaIngreso, fechaActual).getYears(); // Calcular años de antigüedad
    
    double bonificacion = porcentajeBase * 0.02 * aniosAntiguedad;  // Bonificación del 2% por año de antigüedad
    return porcentajeBase + bonificacion;
}
}
