module org.tuni.assignment {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.net.http;
    requires com.google.gson;

    exports org.tuni.assignment;
    exports org.tuni.assignment.models;
    exports org.tuni.assignment.services;
    exports org.tuni.assignment.utils;
    exports org.tuni.assignment.controllers;

    opens org.tuni.assignment.models to javafx.fxml, com.google.gson;
    opens org.tuni.assignment to javafx.fxml;
    opens org.tuni.assignment.services to javafx.fxml;
    opens org.tuni.assignment.utils to javafx.fxml;
    opens org.tuni.assignment.controllers to javafx.fxml;
}