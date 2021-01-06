package bramix.design.controllers;

import bramix.CONSTANTS;
import bramix.Main;
import bramix.core.FileReaderStudents;
import bramix.core.Student;
import bramix.core.writer.FileWriter;
import bramix.design.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Log4j
public class AddController {

    public TextField textFieldName;
    public TextField textFieldSurname;
    public TextField textFieldCourse;
    public TextField textFieldGroup;
    public TextField textFieldBaseBenefit;
    public TextField textFieldMarks;
    public TextField textFieldPublicMark;

    boolean check(String name, String text) {

        if (text.isEmpty()) {
            log.error("Empty field " + name);
            new Message("Error", "Empty field", "Please add info " + name).show();
            return false;
        }

        boolean res = true;

        switch (name) {
            case "baseBenefit":
            case "publicWorkMark":
                res = text.matches("[0-9]*");
                if (!res) new Message("Error", "baseBenefit and publicWorkMark should contain only numbers", null).show();
                break;
            case "marks":
                String[] split = text.split(",");
                res = split.length == 5 &&
                        Stream.of(split)
                        .allMatch(string -> string.matches("^[0-5]+$"));
                if (!res) new Message("Error", "Incorrect format of marks", null).show();
                break;
        }

        return res;
    }

    public void clickAdd(ActionEvent actionEvent) throws IOException {
        boolean status ;
        String name = textFieldName.getText();
        status = check("name", name);
        String surname = textFieldSurname.getText();
        status = status && check("surname", surname);

        String course = textFieldCourse.getText();
        status = status && check("course", course);
        String group = textFieldGroup.getText();
        status = status && check("group", group);

        status = status && check("baseBenefit", textFieldBaseBenefit.getText());
        BigDecimal baseBenefit = BigDecimal.valueOf(Double.parseDouble(textFieldBaseBenefit.getText()));

        status = status && check("publicWorkMark", textFieldPublicMark.getText());
        Integer publicWorkMark = Integer.parseInt(textFieldPublicMark.getText());

        status = status && check("marks", textFieldMarks.getText());
        String marks = textFieldMarks.getText();
        if (status) {
            List<Student> buf = new FileReaderStudents(CONSTANTS.DATABASE).get();
            buf.add(new Student(name, surname , course, group, baseBenefit, marks, publicWorkMark));
            new FileWriter(CONSTANTS.DATABASE, buf).write();
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(Main.class.getResource("/fxml/start.fxml"));
            primaryStage.setResizable(false);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            ((Stage) textFieldName.getScene().getWindow()).close();
        }
    }
}
