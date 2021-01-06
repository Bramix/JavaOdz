package bramix.core.writer;

import bramix.core.Student;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.List;

@AllArgsConstructor
@Log4j
public class FileWriter implements Writer {
    private final String path;
    private final List<Student> students;


    public void write() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(path))){
            objectOutputStream.writeObject(students);
        } catch (FileNotFoundException e) {
            log.warn("File " + path + " not found");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Error in writing object to" + path);
        }
    }
}
