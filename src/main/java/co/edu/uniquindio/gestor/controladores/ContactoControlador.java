package co.edu.uniquindio.gestor.controladores;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import co.edu.uniquindio.gestor.modelo.Contacto;
import co.edu.uniquindio.gestor.modelo.ContactoService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ContactoControlador {
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtCumpleanos;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TableView<Contacto> tablaContactos;
    @FXML
    private TableColumn<Contacto, String> nombreColumna;
    @FXML
    private TableColumn<Contacto, String> telefonoColumna;
    @FXML
    private TableColumn<Contacto, String> correoColumna;
    @FXML
    private TableColumn<Contacto, String> cumpleanosColumna;

    private ContactoService contactoService;

    public void initialize() {
        contactoService = new ContactoService();
        nombreColumna.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre()));
        telefonoColumna.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTelefono()));
        correoColumna.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCorreoElectronico()));
        cumpleanosColumna.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCumpleanos()));
        cargarContactos();
    }

    @FXML
    private void agregarContacto() {
        // Lógica para agregar contacto
        Contacto contacto = Contacto.builder()
                .nombre(txtNombre.getText())
                .apellido(txtApellido.getText())
                .telefono(txtTelefono.getText())
                .cumpleanos(txtCumpleanos.getText())
                .correoElectronico(txtCorreo.getText())
                .build();

        boolean agregado = contactoService.agregarContacto(contacto);
        if (agregado) {
            cargarContactos();
            mostrarAlerta("Contacto agregado correctamente", Alert.AlertType.INFORMATION);
            limpiarCampos();
        } else {
            mostrarAlerta("El contacto ya existe", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void editarContacto() {
        Contacto contactoSeleccionado = tablaContactos.getSelectionModel().getSelectedItem();

        if (contactoSeleccionado != null) {
            // Validar entradas
            if (txtNombre.getText().isEmpty() || txtApellido.getText().isEmpty() || txtTelefono.getText().isEmpty() ||
                    txtCumpleanos.getText().isEmpty() || txtCorreo.getText().isEmpty()) {
                mostrarAlerta("Por favor, completa todos los campos", Alert.AlertType.ERROR);
                return;
            }

            try {
                // Actualizar los valores del contacto seleccionado
                contactoSeleccionado.setNombre(txtNombre.getText());
                contactoSeleccionado.setApellido(txtApellido.getText());
                contactoSeleccionado.setTelefono(txtTelefono.getText());

                // Si txtCumpleanos es una fecha, convertir a LocalDate (ejemplo)
                LocalDate cumpleanos = LocalDate.parse(txtCumpleanos.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                contactoSeleccionado.setCumpleanos(cumpleanos.toString());

                contactoSeleccionado.setCorreoElectronico(txtCorreo.getText());

                // Actualizar el contacto en el servicio
                contactoService.actualizarContacto(contactoSeleccionado);

                // Recargar la tabla de contactos
                cargarContactos();

                // Mostrar alerta de éxito
                mostrarAlerta("Contacto actualizado correctamente", Alert.AlertType.INFORMATION);
            } catch (DateTimeParseException e) {
                mostrarAlerta("Formato de fecha incorrecto. Usa el formato 'yyyy-MM-dd'", Alert.AlertType.ERROR);
            } catch (Exception e) {
                mostrarAlerta("Ocurrió un error al actualizar el contacto", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Selecciona un contacto para editar", Alert.AlertType.ERROR);
        }
    }


    @FXML
    private void eliminarContacto() {
        Contacto contactoSeleccionado = tablaContactos.getSelectionModel().getSelectedItem();
        if (contactoSeleccionado != null) {
            contactoService.eliminarContacto(contactoSeleccionado);
            cargarContactos();
            mostrarAlerta("Contacto eliminado correctamente", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Selecciona un contacto para eliminar", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void buscarContacto() {
        String nombre = txtNombre.getText();
        List<Contacto> resultadoBusqueda = contactoService.buscarContacto("Nombre", nombre);
        tablaContactos.getItems().setAll(resultadoBusqueda);
    }

    private void cargarContactos() {
        List<Contacto> contactos = contactoService.obtenerContactos();
        tablaContactos.getItems().setAll(contactos);
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtApellido.clear();
        txtTelefono.clear();
        txtCumpleanos.clear();
        txtCorreo.clear();
    }

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
