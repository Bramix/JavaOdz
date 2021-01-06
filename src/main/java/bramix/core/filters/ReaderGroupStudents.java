package bramix.core.filters;

import bramix.core.ReaderStudents;
import bramix.core.Student;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ReaderGroupStudents implements ReaderStudents {
    private final ReaderStudents readerStudents;
    private final String group;

    @Override
    public List<Student> get() {
        return readerStudents.get().stream()
                .filter(item -> item.getGroup().equalsIgnoreCase(group))
                .collect(Collectors.toList());
    }
}
