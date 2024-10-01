package co.edu.uniquindio.poo;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Biblioteca {
    private ArrayList<Bibliotecario> bibliotecarios = new ArrayList<>();
    private ArrayList<Estudiante> estudiantes = new ArrayList<>();
    private ArrayList<Libro> libros = new ArrayList<>();
    private ArrayList<Prestamo> prestamos = new ArrayList<>();

    public List<Bibliotecario> getBibliotecarios() {
        return bibliotecarios;
    }

    /**
     * Método para agregar bibliotecario
     * @param bibliotecario
     */
    public void agregarBibliotecario(Bibliotecario bibliotecario) {
        bibliotecarios.add(bibliotecario);
    }

    /**
     * Método para agregar estudiante
     * @param estudiante
     */
    public void agregarEstudiante(Estudiante estudiante) {
        estudiantes.add(estudiante);
    }

    /**
     * Método para agregar libros
     * @param libro
     */
    public void agregarLibro(Libro libro) {
        libros.add(libro);
    }

    /**
     * Método para crear préstamo
     * @param prestamo
     */
    public void crearPrestamo(Prestamo prestamo) {
        prestamos.add(prestamo);
        for (DetallePrestamo detalle : prestamo.getDetalles()) {
            detalle.getLibro().disminuirUnidades(); 
        }
    }

    /**
     * Método para buscar libro por código
     * @param codigoLibro
     * @return
     */
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
     * @param codigoLibro 
     * @return 
     */
    public Libro consultarDatosLibroPorCodigo(String codigoLibro) {
        return buscarLibroPorCodigo(codigoLibro);
    }

    /**
     * Método para contar la cantidad de préstamos de un libro por su título.
     * @param titulo 
     * @return 
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
     * @param codigo 
     * @param nuevoLibro 
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

    /**
     * Método para adicionar un libro al préstamo
     * @param codigoPrestamo
     * @param libro
     * @return
     */
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
        return false;
    }

    /**
     * Método para entregar un préstamo
     * @param codigoPrestamo
     * @param fechaEntrega
     */
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

    /**
     * Método para calcular el costo del préstamo
     * @param prestamo
     * @param diasPrestamo
     * @return
     */
    private double calcularCostoPrestamo(Prestamo prestamo, long diasPrestamo) {
        double costoPorDia = 1000; // Define el costo por día
        return diasPrestamo * costoPorDia;
    }

    /**
     * Método para mostrar la cantidad de préstamos realizados por cada bibliotecario
     */
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
    /**
     * Método para contar prestamos por nombre del libro 
     * @param nombreLibro
     * @return
     */
    public int contarPrestamosPorNombreLibro(String nombreLibro) {
        int contador = 0;
        
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getLibro().getTitulo().equalsIgnoreCase(nombreLibro)) {
                contador++;
            }
        }
        
        return contador;
    }
    /**
     * Método para consultar un préstamo por código
     * @param codigo
     * @return
     */
    public Prestamo consultarDatosPrestamoPorCodigo(String codigo) {
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getCodigo().equals(codigo)) {
                return prestamo;
            }
        }
        return null;
    }
    /**
     * Mostrar los datos del estudiante con más préstamos 
     * @return
     */
    public Estudiante obtenerEstudianteConMasPrestamos() {
        return estudiantes.stream()
                .max(Comparator.comparingInt(est -> contarPrestamosPorEstudiante(est)))
                .orElse(null);  // Retorna null si no hay estudiantes
    }
    /**
     * contar libros por estudiante
     * @param estudiante
     * @return
     */
    public int contarPrestamosPorEstudiante(Estudiante estudiante) {
        return (int) prestamos.stream()
                .filter(prestamo -> prestamo.getEstudiante().equals(estudiante))
                .count();
    }
    /**
     * Total de dinero recaudado por la empresa
     * @return
     */
    public double calcularTotalRecaudado() {
        double totalRecaudado = 0;
    
        for (Prestamo prestamo : prestamos) {
            totalRecaudado += prestamo.calcularSubtotal();
        }
        return totalRecaudado;
    }
    public double calcularPagoBibliotecario(Bibliotecario bibliotecario) {
    double totalPrestamosBibliotecario = 0;
    for (Prestamo prestamo : prestamos) {
        if (prestamo.getBibliotecario().equals(bibliotecario)) {
            totalPrestamosBibliotecario += prestamo.calcularSubtotal();
        }
    }

    double porcentajeBase = totalPrestamosBibliotecario * 0.20; 

    /**
     * // Calcular años de antigüedad usando la fecha de ingreso
     */
    LocalDate fechaActual = LocalDate.now();
    LocalDate fechaIngreso = bibliotecario.getFechaIngreso();
    int aniosAntiguedad = Period.between(fechaIngreso, fechaActual).getYears(); // Calcular años de antigüedad
    
    double bonificacion = porcentajeBase * 0.02 * aniosAntiguedad;  // Bonificación del 2% por año de antigüedad
    return porcentajeBase + bonificacion;
}   
    public static void consultarPrestamoPorCodigo(Biblioteca biblioteca, Scanner scanner) {
        System.out.print("Ingrese el código del préstamo que desea consultar: ");
        String codigoPrestamo = scanner.nextLine();

        Prestamo prestamoConsultado = biblioteca.consultarDatosPrestamoPorCodigo(codigoPrestamo);

        if (prestamoConsultado != null) {
            System.out.println("\n--- Detalles del Préstamo ---");
            System.out.println("Código: " + prestamoConsultado.getCodigo());
            System.out.println("Libro: " + prestamoConsultado.getLibro().getTitulo());
            System.out.println("Estudiante: " + prestamoConsultado.getEstudiante().getNombre());
            System.out.println("Bibliotecario: " + prestamoConsultado.getBibliotecario().getNombre());
            System.out.println("Fecha de Préstamo: " + prestamoConsultado.getFechaPrestamo());
            System.out.println("-----------------------------\n");
        } else {
            System.out.println("No se encontró un préstamo con el código proporcionado.\n");
        }
    }
    /**
     * Método para reemplazar un libro en la biblioteca dado su código.
     * @param biblioteca
     * @param scanner
     */
    public static void reemplazarLibro(Biblioteca biblioteca, Scanner scanner) {
        System.out.print("Ingrese el código del libro a reemplazar: ");
        String codigoLibro = scanner.nextLine();

        Libro libroNuevo = crearLibro(scanner);
        boolean reemplazado = biblioteca.reemplazarLibro(codigoLibro, libroNuevo);

        if (reemplazado) {
            System.out.println("El libro con código " + codigoLibro + " ha sido reemplazado con éxito.");
        } else {
            System.out.println("No se encontró un libro con el código " + codigoLibro + ".");
        }
    }
    /**
     * Método para crear un nuevo libro a partir de la entrada del usuario.
     * @param scanner 
     * @return 
     */
    private static Libro crearLibro(Scanner scanner) {
        System.out.print("Ingrese el código del nuevo libro: ");
        String codigo = scanner.nextLine();
        System.out.print("Ingrese el ISBN del nuevo libro: ");
        String isbn = scanner.nextLine();
        System.out.print("Ingrese el autor del nuevo libro: ");
        String autor = scanner.nextLine();
        System.out.print("Ingrese el título del nuevo libro: ");
        String titulo = scanner.nextLine();
        System.out.print("Ingrese la editorial del nuevo libro: ");
        String editorial = scanner.nextLine();
        System.out.print("Ingrese la fecha de publicación (YYYY-MM-DD): ");
        LocalDate fechaPublicacion = LocalDate.parse(scanner.nextLine());
        System.out.print("Ingrese las unidades disponibles: ");
        int unidadesDisponibles = Integer.parseInt(scanner.nextLine());
        System.out.print("Ingrese el valor del libro: ");
        double valor = Double.parseDouble(scanner.nextLine());

        return new Libro(codigo, isbn, autor, titulo, editorial, fechaPublicacion, unidadesDisponibles, valor);
    }

    /**
     * Método para consultar y mostrar los datos de un libro dado su código.
     * @param biblioteca La instancia de la Biblioteca.
     */

    public static void consultarLibroPorCodigo(Biblioteca biblioteca) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el código del libro que desea consultar: ");
        String codigoLibro = scanner.nextLine();

        Libro libroConsultado = biblioteca.consultarDatosLibroPorCodigo(codigoLibro);

        if (libroConsultado != null) {
            System.out.println("\n--- Detalles del Libro ---");
            System.out.println("Código: " + libroConsultado.getCodigo());
            System.out.println("ISBN: " + libroConsultado.getIsbn());
            System.out.println("Autor: " + libroConsultado.getAutor());
            System.out.println("Título: " + libroConsultado.getTitulo());
            System.out.println("Editorial: " + libroConsultado.getEditorial());
            System.out.println("Fecha de Publicación: " + libroConsultado.getFechaPublicacion());
            System.out.println("Unidades Disponibles: " + libroConsultado.getUnidadesDisponibles());
            System.out.println("Valor: $" + libroConsultado.getValor());
            System.out.println("--------------------------\n");
        } else {
            System.out.println("No se encontró un libro con el código proporcionado.\n");
        }
    }
    
}
