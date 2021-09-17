package com._21cn.bigdata.pdf_to_word.controller;

import com._21cn.bigdata.pdf_to_word.FXApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

public class ApplicationController {

    @FXML
    private VBox rootLayout;

    @FXML
    private Label welcomeText;


    @FXML
    protected void onAddFileClick() throws IOException {
        Stage stageRoot = (Stage) rootLayout.getScene().getWindow();
        Stage stageShowFile = (Stage) rootLayout.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择文件");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        File file = fileChooser.showOpenDialog(stageRoot);
        System.out.println(file.getAbsolutePath());
        Scene scene2 = switchToShowFileScene(stageRoot, "show-file.fxml", "PDF转WORD", 720, 545, file);
    }


    /**
     * 切换Scene
     * @param stageRoot 根Stage
     * @param fxmlFile 需要切换的fxml文件
     * @param title 新的Scene的标题
     * @param width 宽度
     * @param height 高度
     * @return 返回新的Scene
     * @throws IOException 文件找不到异常
     */
    private Scene switchToShowFileScene(Stage stageRoot, String fxmlFile, String title, int width, int height, File file) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FXApplication.class.getResource(fxmlFile));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        FileShowController controller = fxmlLoader.getController();
        controller.init(file);
        stageRoot.setTitle(title);
        stageRoot.setScene(scene);
        stageRoot.show();
        return scene;
    }
}