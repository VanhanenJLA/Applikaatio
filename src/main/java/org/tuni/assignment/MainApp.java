package org.tuni.assignment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        var loader = new FXMLLoader(getClass().getResource("/views/main-window.fxml"));
        var scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Electricity Prices and Wind Speeds");
        primaryStage.show();
    }
}
