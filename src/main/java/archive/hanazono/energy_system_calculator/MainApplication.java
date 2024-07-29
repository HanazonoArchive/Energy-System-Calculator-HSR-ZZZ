package archive.hanazono.energy_system_calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

public class MainApplication extends Application {

    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage; // Declare stage as a class-level variable

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage; // Initialize the class-level stage variable

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("MainInterface.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 320);
        stage.setTitle("Energy System Calculator (by HanazonoArchive v1.3)");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        MainController controller = fxmlLoader.getController();
        controller.setStage(stage); // Pass the Stage reference

        URL resourceUrl = getClass().getResource("/archive/hanazono/energy_system_calculator/Image/icon.png");
        if (resourceUrl != null) {
            ImageView logoicons = new ImageView(resourceUrl.toExternalForm());
            stage.getIcons().add(logoicons.getImage());
        }

        scene.setOnMousePressed(this::handleMousePressed);
        scene.setOnMouseDragged(this::handleMouseDragged);
    }

    // Handle the mouse press event to initialize the dragging
    private void handleMousePressed(MouseEvent event) {
        xOffset = event.getScreenX() - stage.getX();
        yOffset = event.getScreenY() - stage.getY();
    }

    // Handle the mouse drag event to move the stage
    private void handleMouseDragged(MouseEvent event) {
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
