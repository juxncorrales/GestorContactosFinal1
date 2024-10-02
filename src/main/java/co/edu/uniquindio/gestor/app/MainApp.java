package co.edu.uniquindio.gestor.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static javafx.application.Application.launch;

public class MainApp extends Application {
    @Override

    public void start (Stage stage ) throws Exception {

        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/main_view.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("GestorContactos");
        stage.setResizable(false);
        stage.show();

        }
    public static void main(String[] args) { launch(MainApp.class, args);
    }
    }
