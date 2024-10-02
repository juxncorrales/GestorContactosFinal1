package co.edu.uniquindio.gestor.modelo;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ContactoDAO {
    // Lista que actúa como "base de datos" en memoria
    private final List<Contacto> contactos;

    public ContactoDAO() {
        this.contactos = new ArrayList<>(); // Inicializamos la lista vacía
    }

    // Método para crear un nuevo contacto
    public void crearContacto(Contacto contacto) {
        contactos.add(contacto); // Añadimos el contacto a la lista
    }

    // Método para leer todos los contactos
    public List<Contacto> leerContactos() {
        return contactos; // Devolvemos la lista completa de contactos
    }

    // Método para buscar contactos por un criterio (nombre o teléfono)
    public List<Contacto> buscarContacto(String criterio, String valor) {
        return contactos.stream()
                .filter(contacto ->
                        criterio.equalsIgnoreCase("Nombre") ?
                                contacto.getNombre().equalsIgnoreCase(valor) :
                                contacto.getTelefono().equalsIgnoreCase(valor))
                .collect(Collectors.toList()); // Devolvemos los contactos que coincidan con el criterio
    }

    // Método para actualizar un contacto existente
    public void actualizarContacto(Contacto contactoActualizado) {
        Optional<Contacto> contactoExistente = contactos.stream()
                .filter(c -> c.getTelefono().equals(contactoActualizado.getTelefono()))
                .findFirst(); // Buscamos el contacto por teléfono (que debe ser único)

        contactoExistente.ifPresent(contacto -> {
            contacto.setNombre(contactoActualizado.getNombre());
            contacto.setApellido(contactoActualizado.getApellido());
            contacto.setCumpleanos(contactoActualizado.getCumpleanos());
            contacto.setCorreoElectronico(contactoActualizado.getCorreoElectronico());
        }); // Actualizamos los campos del contacto si lo encontramos
    }

    // Método para eliminar un contacto
    public void eliminarContacto(Contacto contacto) {
        contactos.remove(contacto); // Eliminamos el contacto de la lista
    }
}
