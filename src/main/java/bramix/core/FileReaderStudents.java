package bramix.core;

import lombok.extern.log4j.Log4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

@Log4j
public class FileReaderStudents implements ReaderStudents {

    private final String path;

    public FileReaderStudents(String path) {
        this.path = path;
    }

    @Override
    public List<Student> get() {
        List<Student> students = new ArrayList<>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(path))){
            students = (List<Student>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            log.error("Error in getting object from" + path);
        }
        return students;
    }
}
