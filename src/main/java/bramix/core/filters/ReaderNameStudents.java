package bramix.core.filters;

import bramix.core.ReaderStudents;
import bramix.core.Student;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ReaderNameStudents implements ReaderStudents {
    private final ReaderStudents readerStudents;
    private final String surname;

    @Override
    public List<Student> get() {
        return readerStudents.get().stream()
                .filter(item -> item.getSurname().equalsIgnoreCase(surname))
                .collect(Collectors.toList());
    }
}
