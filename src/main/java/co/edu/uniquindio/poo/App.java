package co.edu.uniquindio.poo;

import java.time.LocalDate;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();
        Scanner scanner = new Scanner(System.in);

        // Crear bibliotecarios
        Bibliotecario bibliotecario = new Bibliotecario("Raul", "1234", "raul@uniquindio.edu.co", 311334455, 200000.0,
                LocalDate.of(2014, 12, 15));
        biblioteca.agregarBibliotecario(bibliotecario);

        // Crear estudiantes

        Estudiante estudiante1 = new Estudiante("Luisa", "112233", "luisa@uniquindio.edu.co", 31552524, true);
        Estudiante estudiante2 = new Estudiante("Miguel", "112244", "miguel@uniquindio.edu.co", 31782834, false);
        Estudiante estudiante3 = new Estudiante("Juan", "112255", "juan@uniquindio.edu.co", 3174874, true);
        biblioteca.agregarEstudiante(estudiante1);
        biblioteca.agregarEstudiante(estudiante2);
        biblioteca.agregarEstudiante(estudiante3);

        // Crear libros
        Libro libro1 = new Libro("001", "978-3-16-148410-0", "Autor 1", "Libro A", "Editorial 1",
                LocalDate.of(2010, 5, 20), 5, 15000.0);
        Libro libro2 = new Libro("002", "978-3-16-148410-1", "Autor 2", "Libro B", "Editorial 2",
                LocalDate.of(2012, 6, 15), 3, 20000.0);
        biblioteca.agregarLibro(libro1);
        biblioteca.agregarLibro(libro2);

        // Crear préstamo
        Prestamo prestamo1 = new Prestamo("P001", libro1, estudiante1, bibliotecario, LocalDate.now());
        DetallePrestamo detallePrestamo1 = new DetallePrestamo(2, prestamo1, libro1);
        prestamo1.agregarDetalle(detallePrestamo1); // Agregar detalle al préstamo
        biblioteca.crearPrestamo(prestamo1);
        System.out.println("Detalle del préstamo1: " + detallePrestamo1);
        System.out.println("Subtotal del préstamo1: " + prestamo1.calcularSubtotal());
        
        Prestamo prestamo2 = new Prestamo("P002", libro2, estudiante2, bibliotecario, LocalDate.now());
        DetallePrestamo detallePrestamo2 = new DetallePrestamo(1, prestamo2, libro2);
        prestamo2.agregarDetalle(detallePrestamo2); // Agregar detalle al préstamo
        biblioteca.crearPrestamo(prestamo2);
        System.out.println("Detalle del préstamo2: " + detallePrestamo2);
        System.out.println("Subtotal del préstamo2: " + prestamo2.calcularSubtotal());
        
        Prestamo prestamo3 = new Prestamo("P003", libro2, estudiante3, bibliotecario, LocalDate.now());
        DetallePrestamo detallePrestamo3 = new DetallePrestamo(3, prestamo3, libro2);
        prestamo3.agregarDetalle(detallePrestamo3); // Agregar detalle al préstamo
        biblioteca.crearPrestamo(prestamo3);
        System.out.println("Detalle del préstamo3: " + detallePrestamo3);
        System.out.println("Subtotal del préstamo3: " + prestamo3.calcularSubtotal());


        // Consultar libro por código
        Biblioteca.consultarLibroPorCodigo(biblioteca);

        // Contar préstamos de un libro por título
        int cantidadPrestamos = biblioteca.contarPrestamosPorLibro("Libro A");
        System.out.println("Cantidad de préstamos de 'Libro A': " + cantidadPrestamos);

        int cantidadPrestamosNombre = biblioteca.contarPrestamosPorNombreLibro("Libro A");
        System.out.println("El libro 'Libro A' ha sido prestado " + cantidadPrestamosNombre + " veces.");

        // Reemplazar libro
        Biblioteca.reemplazarLibro(biblioteca, scanner);

        // Consultar préstamo por código
        Biblioteca.consultarPrestamoPorCodigo(biblioteca, scanner);

        // Mostrar cantidad de préstamos realizados por cada bibliotecario
        biblioteca.mostrarPrestamosPorBibliotecario();

        biblioteca.obtenerEstudianteConMasPrestamos();

        biblioteca.calcularTotalRecaudado();

        biblioteca.calcularPagoBibliotecario(bibliotecario);

        // Cerrar el scanner
        scanner.close();

        // Obtener y mostrar el estudiante con más préstamos
        Estudiante topEstudiante = biblioteca.obtenerEstudianteConMasPrestamos();
        System.out.println("Estudiante con más préstamos: "
                + (topEstudiante != null ? topEstudiante.getNombre() : "No hay estudiantes"));

        // Calcular total recaudado por la empresa
        double totalRecaudado = biblioteca.calcularTotalRecaudado();
        System.out.println("Total recaudado por la empresa: $" + totalRecaudado);

        // Calcular y mostrar pagos a los bibliotecarios
        for (Bibliotecario b : biblioteca.getBibliotecarios()) {
            double pago = biblioteca.calcularPagoBibliotecario(b);
            System.out.println("Pago a " + b.getNombre() + ": $" + pago);
        }
    }

}
