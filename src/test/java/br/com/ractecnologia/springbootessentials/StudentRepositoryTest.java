package br.com.ractecnologia.springbootessentials;

import br.com.ractecnologia.springbootessentials.model.Student;
import br.com.ractecnologia.springbootessentials.repository.StudentRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createShouldPersistData() {
        Student student = new Student("Willian", "willian@gmail.com");
        this.studentRepository.save(student);
        assertThat(student.getId()).isNotNull();
        assertThat(student.getName()).isEqualTo("Willian");
        assertThat(student.getEmail()).isEqualTo("willian@gmail.com");
    }

    @Test
    public void deleteShouldPersistData() {
        Student student = new Student("Willian", "willian@gmail.com");
        this.studentRepository.save(student);
        studentRepository.delete(student);
        assertThat(studentRepository.findOne(student.getId())).isNull();
    }

    @Test
    public void updateShouldPersistData() {
        Student student = new Student("Willian", "willian@gmail.com");
        this.studentRepository.save(student);
        student.setName("Willian2");
        student.setEmail("willian2@gmail.com");
        this.studentRepository.save(student);
        student = studentRepository.findOne(student.getId());
        assertThat(student.getName()).isEqualTo("Willian2");
        assertThat(student.getEmail()).isEqualTo("willian2@gmail.com");
    }

    @Test
    public void findByNameIgnoreCaseContainingShouldIgnoreCase() {
        Student student = new Student("Willian", "willian@gmail.com");
        Student student2 = new Student("Willian2", "willian@gmail.com");
        this.studentRepository.save(student);
        this.studentRepository.save(student2);
        List<Student> studentList = studentRepository.findByNameIgnoreCaseContaining("willian");
        assertThat(studentList.size()).isEqualTo(2);
    }

    @Test
    public void createWhenNameIsNullShouldThrowConstraintValidationException() {
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("O campo do estudante é obrigatório");
        this.studentRepository.save(new Student());
    }

    @Test
    public void createWhenEmailIsNullShouldThrowConstraintValidationException() {
        thrown.expect(ConstraintViolationException.class);
        Student student = new Student();
        student.setName("Layla");
        this.studentRepository.save(student);


    }

}
