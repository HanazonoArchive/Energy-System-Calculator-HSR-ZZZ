package archive.hanazono.energy_system_calculator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainController {

    @FXML
    private Pane BackgroundPane;

    @FXML
    private Pane CloseButton;

    @FXML
    private Label CurrentDateLB;

    @FXML
    private Label CurrentTimeLB;

    @FXML
    private TextField EnergyLeftTF;

    @FXML
    private Label EnergyMinLB;

    @FXML
    private TextField EnergyMinsTF;

    @FXML
    private HBox ExecuteButton;

    @FXML
    private Label FullInLB;

    @FXML
    private AnchorPane MainAnchorPane;

    @FXML
    private Label MaxEnergyLB;

    @FXML
    private TextField MaxEnergyTF;

    @FXML
    private Label WillTakeLB;

    @FXML
    private Label YouHaveLB;

    private Stage stage;

    @FXML
    private Label StatusLB;

    // Method to set the stage reference
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Method to add a listener to TextField to accept only numbers
    private void makeTextFieldNumeric(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    @FXML
    void initialize() {
        // Make the TextFields numeric only
        makeTextFieldNumeric(EnergyLeftTF);
        makeTextFieldNumeric(EnergyMinsTF);
        makeTextFieldNumeric(MaxEnergyTF);
    }

    @FXML
    void HandlesClicked(MouseEvent event) {
        if (event.getSource() == CloseButton){
            stage.close();
        } else if (event.getSource() == ExecuteButton){
            // Maximum points
            int MAX_ENERGY = Integer.parseInt(MaxEnergyTF.getText());
            // Minutes per point
            int MINUTES_PER_ENERGY = Integer.parseInt(EnergyMinsTF.getText());
            // Current Energy
            int CURRENT_ENERGY = Integer.parseInt(EnergyLeftTF.getText());

            if (CURRENT_ENERGY < 0 || CURRENT_ENERGY > MAX_ENERGY) {
                StatusLB.setText("Status: ERROR!");
            } else {
                // Calculate the remaining points needed to reach 240
                int remainingEnergy = MAX_ENERGY - CURRENT_ENERGY;

                // Calculate the time in minutes to reach the maximum points
                int totalMinutes = remainingEnergy * MINUTES_PER_ENERGY;

                // Get the current date and time
                LocalDateTime now = LocalDateTime.now();
                LocalDate today = now.toLocalDate();

                // Add the total minutes to the current time to get the target time
                LocalDateTime targetTime = now.plusMinutes(totalMinutes);

                // Format the date and time
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
                String formattedTargetTime = targetTime.format(timeFormatter);
                String formattedTime = now.format(timeFormatter);
                String formattedCurrentDate = today.format(dateFormatter);

                // Calculate hours, minutes, and seconds
                Duration duration = Duration.ofMinutes(totalMinutes);
                long hours = duration.toHours();
                long minutes = duration.toMinutes() % 60;
                long seconds = duration.getSeconds() % 60;

                FullInLB.setText(formattedTargetTime);
                YouHaveLB.setText(String.valueOf(CURRENT_ENERGY));
                MaxEnergyLB.setText(String.valueOf(MAX_ENERGY));
                EnergyMinLB.setText(String.valueOf(MINUTES_PER_ENERGY));
                WillTakeLB.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
                CurrentDateLB.setText(formattedCurrentDate);
                CurrentTimeLB.setText(formattedTime);

                StatusLB.setText("Status: Success!");
            }
        }
    }
}
