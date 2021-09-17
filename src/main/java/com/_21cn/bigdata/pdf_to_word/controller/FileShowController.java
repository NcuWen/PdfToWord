package com._21cn.bigdata.pdf_to_word.controller;

import com._21cn.bigdata.pdf_to_word.FXApplication;
import com._21cn.bigdata.pdf_to_word.entities.FileRowShow;
import com._21cn.bigdata.pdf_to_word.services.Pdf2word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileShowController {

    @FXML
    private Button startTrans;

    @FXML
    private ChoiceBox outDirectory;

    @FXML
    private ChoiceBox outFileType;

    @FXML
    private Button clear;

    @FXML
    private Button addFile;

    @FXML
    private Pane showFileLayout;

    @FXML
    private TableView<FileRowShow> showFileTable;

    @FXML
    private TableColumn<FileRowShow, String> cName, cPages, cTransMode, cRecovery, cStatus;

    @FXML
    private TableColumn<FileRowShow, Integer> cPageCount;

    /**
     * 初始化
     * @param file 文件
     * @throws IOException IO异常
     */
    public void init(File file) throws IOException {
        FileRowShow fileRowShow = new FileRowShow();
        fileRowShow.setName(file.getName());
        fileRowShow.setPath(file.getAbsolutePath());
        Pdf2word pdf2word = new Pdf2word();
        int pdfPageNumber = pdf2word.getPageNumber(file.getAbsolutePath());
        fileRowShow.setPageCount(pdfPageNumber);
        fileRowShow.setOutEnd(pdfPageNumber);
        System.out.println(fileRowShow);
        List<FileRowShow> fileRowShows = new ArrayList<>();
        fileRowShows.add(fileRowShow);
        int showStatus = showFilesToTable(showFileTable, fileRowShows);
        System.out.println("showStatus: " + showStatus);
        outFileType.getItems().add("docx");
        outDirectory.getItems().add("PDF相同目录");
        outFileType.getSelectionModel().selectFirst();
        outDirectory.getSelectionModel().selectFirst();
//        outFileType.setValue();
    }

    /**
     * 清空表格文件
     * @throws IOException 找不到add-file.fxml时的文件IO异常
     */
    @FXML
    private void clearFiles() throws IOException {
        Stage stageRoot = (Stage) showFileLayout.getScene().getWindow();
        showFileTable.getItems().clear();
        Scene scene2 = switchToAddFileScene(stageRoot, "add-file.fxml", "PDF转WORD", 720, 545);
    }

    /**
     * 增加文件
     */
    @FXML
    private void addFile() throws IOException {
        Stage stageShowFile = (Stage) showFileLayout.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择文件");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        File file = fileChooser.showOpenDialog(stageShowFile);
        System.out.println(file.getAbsolutePath());
        Pdf2word pdf2word = new Pdf2word();
        addFileToTable(file, pdf2word);
    }

    private void addFileToTable(File file, Pdf2word pdf2word) throws IOException {
        FileRowShow fileRowShow = new FileRowShow();
        fileRowShow.setName(file.getName());
        fileRowShow.setPath(file.getAbsolutePath());
        int pdfPageNumber = pdf2word.getPageNumber(file.getAbsolutePath());
        fileRowShow.setPageCount(pdfPageNumber);
        fileRowShow.setOutEnd(pdfPageNumber);
        System.out.println(fileRowShow);
        List<FileRowShow> fileRowShows = showFileTable.getItems();
        fileRowShows.add(fileRowShow);
    }

    /**
     * 展示文件到表格
     * @param tableView 表格控件
     * @param fileRowShows 文件列表
     * @return 状态
     */
    private int showFilesToTable(TableView<FileRowShow> tableView, List<FileRowShow> fileRowShows) {
        int result = 0;
        try {
            ObservableList<FileRowShow> cellData = FXCollections.observableArrayList();
            assert cName != null;
            cName.setCellValueFactory(new PropertyValueFactory<>("name"));
            cPageCount.setCellValueFactory(new PropertyValueFactory<>("pageCount"));
            cPages.setCellValueFactory(new PropertyValueFactory<>("pageRanges"));
            cTransMode.setCellValueFactory(new PropertyValueFactory<>("transMode"));
            cRecovery.setCellValueFactory(new PropertyValueFactory<>("recoveryStr"));
            cStatus.setCellValueFactory(new PropertyValueFactory<>("statusStr"));
            cellData.addAll(fileRowShows);
//            tableView.getColumns().addAll(cName, cPageCount, cPages, cTransMode, cRecovery, cStatus);
            tableView.setItems(cellData);
        }catch (Exception e) {
            e.printStackTrace();
            result = 1;
        }
        return result;
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
    private Scene switchToAddFileScene(Stage stageRoot, String fxmlFile, String title, int width, int height) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FXApplication.class.getResource(fxmlFile));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stageRoot.setTitle(title);
        stageRoot.setScene(scene);
        stageRoot.show();
        return scene;
    }

    /**
     * 开始转换
     */
    @FXML
    private void startTrans() {
        List<FileRowShow> fileRowShows = showFileTable.getItems();
        Pdf2word pdf2word = new Pdf2word();
        for (FileRowShow fileRowShow : fileRowShows) {
            byte status = pdf2word.pdf2Word(fileRowShow);
            fileRowShow.setStatus(status);
        }
        showFileTable.refresh();
    }
}
