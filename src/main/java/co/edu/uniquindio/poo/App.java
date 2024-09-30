package co.edu.uniquindio.poo;

import java.time.LocalDate;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();
        Scanner scanner = new Scanner(System.in);

        // Crear bibliotecarios
        Bibliotecario bibliotecario = new Bibliotecario("Raul", "1234", "raul@uniquindio.edu.co", 311334455, 200000.0, LocalDate.of(2014, 12, 15));
        biblioteca.agregarBibliotecario(bibliotecario);

        // Crear estudiantes
        
        Estudiante estudiante = new Estudiante("Luisa", "112233", "luisa@uniquindio.edu.co", 31552524, true);
        Estudiante estudiante2 = new Estudiante("Miguel", "112244", "miguel@uniquindio.edu.co", 31782834, false);
        Estudiante estudiante3 = new Estudiante("Juan", "112255", "juan@uniquindio.edu.co", 3174874, true);
        biblioteca.agregarEstudiante(estudiante);
        biblioteca.agregarEstudiante(estudiante2);
        biblioteca.agregarEstudiante(estudiante3);

        // Crear libros
        Libro libro1 = new Libro("001", "978-3-16-148410-0", "Autor 1", "Libro A", "Editorial 1", LocalDate.of(2010, 5, 20), 5, 15000.0);
        Libro libro2 = new Libro("002", "978-3-16-148410-1", "Autor 2", "Libro B", "Editorial 2", LocalDate.of(2012, 6, 15), 3, 20000.0);
        biblioteca.agregarLibro(libro1);
        biblioteca.agregarLibro(libro2);

        // Crear préstamo
        Prestamo prestamo1 = new Prestamo("P001", libro1, estudiante, bibliotecario, LocalDate.now());
        biblioteca.crearPrestamo(prestamo1);
        Prestamo prestamo2 = new Prestamo("P002", libro2, estudiante2, bibliotecario, LocalDate.now());
        biblioteca.crearPrestamo(prestamo2);
        Prestamo prestamo3 = new Prestamo("P003", libro2, estudiante3, bibliotecario, LocalDate.now());
        biblioteca.crearPrestamo(prestamo3);

        // Crear detalle de préstamo
        DetallePrestamo detallePrestamo1 = new DetallePrestamo(2, prestamo1, libro1);
        System.out.println("Detalle del préstamo: " + detallePrestamo1);

        // Consultar libro por código
        //consultarLibroPorCodigo(biblioteca);

        // Contar préstamos de un libro por título
        int cantidadPrestamos = biblioteca.contarPrestamosPorLibro("Libro A");
        System.out.println("Cantidad de préstamos de 'Libro A': " + cantidadPrestamos);

        int cantidadPrestamosNombre = biblioteca.contarPrestamosPorNombreLibro("Libro A");
        System.out.println("El libro 'Libro A' ha sido prestado " + cantidadPrestamosNombre + " veces.");

        // Reemplazar libro
        //reemplazarLibro(biblioteca, scanner);

        // Consultar préstamo por código
        //consultarPrestamoPorCodigo(biblioteca, scanner);

        // Mostrar cantidad de préstamos realizados por cada bibliotecario
        //biblioteca.mostrarPrestamosPorBibliotecario();

        // Cerrar el scanner
        scanner.close();


        // Obtener y mostrar el estudiante con más préstamos
        /*Estudiante topEstudiante = biblioteca.obtenerEstudianteConMasPrestamos();
        System.out.println("Estudiante con más préstamos: " + (topEstudiante != null ? topEstudiante.getNombre() : "No hay estudiantes"));

        */

        // Calcular total recaudado por la empresa
        /*double totalRecaudado = biblioteca.calcularTotalRecaudado();
        System.out.println("Total recaudado por la empresa: $" + totalRecaudado);
        */

        for (Bibliotecario bibliotecario : biblioteca.getBibliotecarios()) {
            double pago = biblioteca.calcularPagoBibliotecario(bibliotecario);
            System.out.println("Pago a " + bibliotecario.getNombre() + ": $" + pago);
        }
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

    /**
     * Método para reemplazar un libro en la biblioteca dado su código.
     * @param biblioteca La instancia de la Biblioteca.
     * @param scanner El Scanner para la entrada del usuario.
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
     * @param scanner El Scanner para la entrada del usuario.
     * @return El nuevo libro creado.
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
     * Método para consultar un préstamo por su código.
     * @param biblioteca La instancia de la Biblioteca.
     * @param scanner El Scanner para la entrada del usuario.
     */
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

    
}
