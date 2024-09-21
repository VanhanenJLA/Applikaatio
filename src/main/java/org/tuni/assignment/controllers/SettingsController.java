package org.tuni.assignment.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tuni.assignment.models.Preferences;
import org.tuni.assignment.utils.PreferencesManager;

public class SettingsController {
    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TextField cityTextField;

    private Preferences preferences;
    private PreferencesManager preferencesManager;

    @FXML
    public void initialize() {
        preferencesManager = PreferencesManager.getInstance();
        preferences = preferencesManager.getPreferences();

        startDatePicker.setValue(preferences.getStartDate());
        endDatePicker.setValue(preferences.getEndDate());
        cityTextField.setText(preferences.getCity());
    }

    @FXML
    private void savePreferences() {
        preferences.setStartDate(startDatePicker.getValue());
        preferences.setEndDate(endDatePicker.getValue());
        preferences.setCity(cityTextField.getText());

        preferencesManager.savePreferences(preferences);

        closeWindow();
    }

    @FXML
    private void closeWindow() {
        var stage = (Stage) cityTextField
                .getScene()
                .getWindow();
        stage.close();
    }
}
