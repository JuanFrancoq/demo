package co.edu.uniquindio.poo;

import java.util.Collection;
import java.util.LinkedList;

public class Estudiante extends Persona {

    private boolean estado;
    private Collection<Prestamo> prestamos;

    public Estudiante(String nombre, String cedula, String correo, int telefono, boolean estado) {
        super(nombre, cedula, correo, telefono);
        this.estado = estado;
        prestamos = new LinkedList<>();
    }

    /**
     * getters y setters
     * @return
     */

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Collection<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(Collection<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }

    @Override
    public String toString() {
        return "Estudiante [estado=" + estado + ", prestamos=" + prestamos + "]";
    }

}
