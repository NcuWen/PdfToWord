package com._21cn.bigdata.pdf_to_word;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FXApplication extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FXApplication.class.getResource("add-file.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 720, 470);
        stage.setTitle("PDF转WORD");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}