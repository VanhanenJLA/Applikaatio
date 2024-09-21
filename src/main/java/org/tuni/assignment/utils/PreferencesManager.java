package org.tuni.assignment.utils;

import com.google.gson.GsonBuilder;
import org.tuni.assignment.models.Preferences;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;


public class PreferencesManager {
    private static final String PREFERENCES_FILE = "preferences.json";
    private Preferences preferences;


    private PreferencesManager() {
        loadPreferences();
    }

    public static PreferencesManager getInstance() {
        return InstanceHolder.instance;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public void savePreferences(Preferences preferences) {
        this.preferences = preferences;
        var gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();
        try (var writer = new FileWriter(PREFERENCES_FILE)) {
            gson.toJson(preferences, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPreferences() {
        var gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        try (var reader = new FileReader(PREFERENCES_FILE)) {
            this.preferences = gson.fromJson(reader, Preferences.class);
        } catch (Exception e) { // Catch all exceptions
            // Log the exception for debugging
            e.printStackTrace();

            // Use default preferences
            this.preferences = new Preferences();
        }
    }

    private static final class InstanceHolder {
        private static final PreferencesManager instance = new PreferencesManager();
    }

}