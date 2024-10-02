package co.edu.uniquindio.gestor.modelo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Contacto {
    private String nombre;
    private String apellido;
    private String telefono;
    private String cumpleanos;
    private String correoElectronico;
}
