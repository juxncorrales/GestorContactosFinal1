package co.edu.uniquindio.gestor.modelo;


import java.util.List;

public class ContactoService {
    private final ContactoDAO contactoDAO;

    public ContactoService() {
        this.contactoDAO = new ContactoDAO(); // Inicializamos el DAO
    }

    // Crear contacto con validación de duplicados
    public boolean agregarContacto(Contacto contacto) {
        List<Contacto> contactosExistentes = contactoDAO.leerContactos();
        boolean contactoDuplicado = contactosExistentes.stream()
                .anyMatch(c -> c.getTelefono().equals(contacto.getTelefono()) ||
                        c.getCorreoElectronico().equals(contacto.getCorreoElectronico()));

        if (!contactoDuplicado) {
            contactoDAO.crearContacto(contacto);
            return true; // Contacto agregado
        }
        return false; // Contacto duplicado
    }

    // Leer todos los contactos
    public List<Contacto> obtenerContactos() {
        return contactoDAO.leerContactos();
    }

    // Actualizar un contacto existente
    public void actualizarContacto(Contacto contactoActualizado) {
        contactoDAO.actualizarContacto(contactoActualizado);
    }

    // Eliminar un contacto
    public void eliminarContacto(Contacto contacto) {
        contactoDAO.eliminarContacto(contacto);
    }

    // Buscar contactos por nombre o teléfono
    public List<Contacto> buscarContacto(String criterio, String valor) {
        return contactoDAO.buscarContacto(criterio, valor);
    }
}
