package bramix.design.controllers;

import bramix.core.filters.ReaderNameStudents;
import bramix.core.writer.FileWriter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import bramix.CONSTANTS;
import bramix.Main;
import bramix.core.FileReaderStudents;
import bramix.core.ReaderStudents;
import bramix.core.Student;
import bramix.core.filters.ReaderCourseStudents;
import bramix.core.filters.ReaderGroupStudents;
import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Log4j
public class StartController implements Initializable {

    @FXML
    private TableView table;
    @FXML
    private TableColumn<Student, String> columnName;
    @FXML
    private TableColumn<Student, String>  columnSurname;
    @FXML
    private TableColumn<Student, String>  columnCourse;
    @FXML
    private TableColumn<Student, String>  columnGroup;
    @FXML
    private TableColumn<Student, String>  columnFacultet;
    @FXML
    private TableColumn<Student, String> columnMarks;
    @FXML
    private TableColumn<Student, String> columnPublicWork;
    @FXML
    private TableColumn<Student, Boolean> columnHasScholarship;

    private ReaderStudents readerStudents = null;

    public TextField textSearch;

    public void clickAdd(MouseEvent mouseEvent) throws IOException {
        ((Stage)textSearch.getScene().getWindow()).close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(Main.class.getResource("/fxml/add.fxml"));
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void clickSearch(MouseEvent mouseEvent) {
        String text = textSearch.getText();
        if (text.isEmpty()) {
            readerStudents = new FileReaderStudents(CONSTANTS.DATABASE);
        }
        String[] commands = text.split(";");
        for (String command: commands) {
            String[] buf = command.split(":");
            switch (buf[0]) {
                case "group": readerStudents = new ReaderGroupStudents(readerStudents, buf[1]); break;
                case "course": readerStudents = new ReaderCourseStudents(readerStudents, buf[1]); break;
                case "surname": readerStudents = new ReaderNameStudents(readerStudents, buf[1]); break;
            }
        }
        show();
    }

    public void clickDelete(MouseEvent mouseEvent) {
        final int focusedIndex = table.getSelectionModel().getFocusedIndex();
        List<Student> buf = new FileReaderStudents(CONSTANTS.DATABASE).get();
        buf.remove(focusedIndex);
        new FileWriter(CONSTANTS.DATABASE, buf).write();
        show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        readerStudents = new FileReaderStudents(CONSTANTS.DATABASE);
        columnName.setCellValueFactory(new PropertyValueFactory("name"));
        columnSurname.setCellValueFactory(new PropertyValueFactory("surname"));
        columnCourse.setCellValueFactory(new PropertyValueFactory("course"));
        columnGroup.setCellValueFactory(new PropertyValueFactory("group"));
        columnFacultet.setCellValueFactory(new PropertyValueFactory("baseBenefit"));
        columnMarks.setCellValueFactory(new PropertyValueFactory("marks"));
        columnPublicWork.setCellValueFactory(new PropertyValueFactory("publicWorkMark"));
        columnHasScholarship.setCellValueFactory(new PropertyValueFactory("bonus"));
        show();
    }

    private void show() {
        table.setItems(FXCollections.observableArrayList(readerStudents.get()));
    }
}
