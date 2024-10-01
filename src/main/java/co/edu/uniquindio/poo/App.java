package co.edu.uniquindio.poo;




import java.time.LocalDate;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();
        Scanner scanner = new Scanner(System.in);

        /**
         * Crear bibliotecario
         */
        Bibliotecario bibliotecario = new Bibliotecario("Raul", "1234", "raul@uniquindio.edu.co", 311334455, 200000.0,
                LocalDate.of(2014, 12, 15));
        biblioteca.agregarBibliotecario(bibliotecario);

        /**
         * Crear Estudiante
         */

        Estudiante estudiante1 = new Estudiante("Luisa", "112233", "luisa@uniquindio.edu.co", 31552524, true);
        Estudiante estudiante2 = new Estudiante("Miguel", "112244", "miguel@uniquindio.edu.co", 31782834, false);
        Estudiante estudiante3 = new Estudiante("Juan", "112255", "juan@uniquindio.edu.co", 3174874, true);
        biblioteca.agregarEstudiante(estudiante1);
        biblioteca.agregarEstudiante(estudiante2);
        biblioteca.agregarEstudiante(estudiante3);

        /**
         * Crear libros
         */
        Libro libro1 = new Libro("001", "1b2c3d", "Autor 1", "Libro A", "Editorial 1",
                LocalDate.of(2010, 5, 20), 5, 15000.0);
        Libro libro2 = new Libro("002", "1a2b3c", "Autor 2", "Libro B", "Editorial 2",
                LocalDate.of(2012, 6, 15), 3, 20000.0);
        biblioteca.agregarLibro(libro1);
        biblioteca.agregarLibro(libro2);

        /**
         * Crear prestamos
         */
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


        int opcion = 0;
        while (opcion != 9) {
            // Menú interactivo
            System.out.println("\nMenú Biblioteca");
            System.out.println("1. Reemplazar libro");
            System.out.println("2. Consultar libro por código");
            System.out.println("3. Consultar préstamos de un libro por título");
            System.out.println("4. Consultar préstamo por código");
            System.out.println("5. Mostrar préstamos realizados por cada bibliotecario");
            System.out.println("6. Mostrar estudiante con más préstamos");
            System.out.println("7. Calcular total recaudado");
            System.out.println("8. Calcular pago a bibliotecario");
            System.out.println("9. Salir");
            System.out.print("Selecciona una opción: ");
            
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); 
            } else {
                System.out.println("Opción no válida. Intenta nuevamente.");
                scanner.nextLine(); 
                continue;
            }

            switch (opcion) {
                case 1:
                    /**
                     * reemplazar libro
                     */
                    Biblioteca.reemplazarLibro(biblioteca, scanner);
                    break;
                case 2:
                    /**
                     * consultar libro por codigo
                     */
                    Biblioteca.consultarLibroPorCodigo(biblioteca);
                    break;
                case 3:
                    /**
                     * consultar  prestamos de libro por titulo
                     */
                    System.out.print("Ingresa el título del libro: ");
                    String titulo = scanner.nextLine();
                    int cantidadPrestamos = biblioteca.contarPrestamosPorLibro(titulo);
                    System.out.println("Cantidad de préstamos del libro '" + titulo + "': " + cantidadPrestamos);
                    break;
                case 4:
                    /**
                     * consultar prestamos por codigo
                     */
                    Biblioteca.consultarPrestamoPorCodigo(biblioteca, scanner);
                    break;
                case 5:
                    /**
                     * mostrar prestamos realizados por cada bibliotecario
                     */
                    biblioteca.mostrarPrestamosPorBibliotecario();
                    break;
                case 6:
                    /**
                     * mostrar estudiante con mas prestamos
                     */
                    Estudiante topEstudiante = biblioteca.obtenerEstudianteConMasPrestamos();
                    System.out.println("Estudiante con más préstamos: "
                            + (topEstudiante != null ? topEstudiante.getNombre() : "No hay estudiantes"));
                    break;
                case 7:
                    /**
                     * calcular total recaudado por la empresa
                     */
                    double totalRecaudado = biblioteca.calcularTotalRecaudado();
                    System.out.println("Total recaudado por la empresa: $" + totalRecaudado);
                    break;
                case 8:
                    /**
                     * calcular pago de bibliotecario
                     */
                    for (Bibliotecario b : biblioteca.getBibliotecarios()) {
                        double pago = biblioteca.calcularPagoBibliotecario(b);
                        System.out.println("Pago a " + b.getNombre() + ": $" + pago);
                    }
                    break;
                case 9:
                    /**
                     * salir del programa 
                     */
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Intenta nuevamente.");
            }
        }

        /**
         * cerrar el scanner
         */
        scanner.close();
    }

}
