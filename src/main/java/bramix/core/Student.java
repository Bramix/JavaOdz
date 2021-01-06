package bramix.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@AllArgsConstructor
@Getter
public class Student implements Serializable {
    private final String name;
    private final String surname;
    private final String course;
    private final String group;
    private final BigDecimal baseBenefit;
    private final String marks;
    private final Integer publicWorkMark;
    private final BigDecimal bonus ;


    public Student(String name, String surname, String course, String group, BigDecimal baseBenefit, String marks, Integer publicWorkMark) {
        this.name = name;
        this.surname = surname;
        this.course = course;
        this.group = group;
        this.baseBenefit = baseBenefit;
        this.marks = marks;
        this.publicWorkMark = publicWorkMark;
        this.bonus = calculateBonus();
    }

    private BigDecimal calculateBonus() {
        List<Integer> marks = Stream.of(this.marks.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        boolean hasAllFive = marks.stream()
                .allMatch(mark -> mark.equals(5));

        if (hasAllFive && publicWorkMark == 1) {
            return this.baseBenefit.multiply(BigDecimal.valueOf(1.5));
        }
        if (hasAllFive && publicWorkMark == -1) {
            return this.baseBenefit.multiply(BigDecimal.valueOf(1.25));
        }

        if (marks.stream().allMatch(mark -> mark > 3)) {
            return this.baseBenefit;
        }

        return BigDecimal.ZERO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(name, student.name) &&
                Objects.equals(surname, student.surname) &&
                Objects.equals(course, student.course) &&
                Objects.equals(group, student.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, course, group);
    }
}
