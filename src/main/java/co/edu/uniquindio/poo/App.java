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
        biblioteca.consultarLibroPorCodigo(biblioteca);

        // Contar préstamos de un libro por título
        int cantidadPrestamos = biblioteca.contarPrestamosPorLibro("Libro A");
        System.out.println("Cantidad de préstamos de 'Libro A': " + cantidadPrestamos);

        int cantidadPrestamosNombre = biblioteca.contarPrestamosPorNombreLibro("Libro A");
        System.out.println("El libro 'Libro A' ha sido prestado " + cantidadPrestamosNombre + " veces.");

        // Reemplazar libro
        biblioteca.reemplazarLibro(biblioteca, scanner);

        // Consultar préstamo por código
        biblioteca.consultarPrestamoPorCodigo(biblioteca, scanner);

        // Mostrar cantidad de préstamos realizados por cada bibliotecario
        biblioteca.mostrarPrestamosPorBibliotecario();

        // Cerrar el scanner
        scanner.close();


        // Obtener y mostrar el estudiante con más préstamos
        Estudiante topEstudiante = biblioteca.obtenerEstudianteConMasPrestamos();
        System.out.println("Estudiante con más préstamos: " + (topEstudiante != null ? topEstudiante.getNombre() : "No hay estudiantes"));

        // Calcular total recaudado por la empresa
        double totalRecaudado = biblioteca.calcularTotalRecaudado();
        System.out.println("Total recaudado por la empresa: $" + totalRecaudado);
    }

    
    

    

    
    

    
    

    
}
