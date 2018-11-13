package br.com.ractecnologia.springbootessentials.controller;


import br.com.ractecnologia.springbootessentials.error.CustomErrorType;
import br.com.ractecnologia.springbootessentials.model.Student;
import br.com.ractecnologia.springbootessentials.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("students")
public class StudentController {


    private DateUtil dateUtil;

    @Autowired
    public StudentController(DateUtil dateUtil) {
        this.dateUtil = dateUtil;
    }

    @GetMapping
    public ResponseEntity<?> listAll() {

        return new ResponseEntity<>(Student.studentList, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") int id) {
        Student student = new Student();
        student.setId(id);
        int index = Student.studentList.indexOf(student);

        if (index == -1) {
            return new ResponseEntity<>(new CustomErrorType("Students not found"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(Student.studentList.get(index), HttpStatus.ACCEPTED);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Student student) {
        Student.studentList.add(student);
        return new ResponseEntity<>(student, HttpStatus.OK);


    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody Student student) {
        Student.studentList.remove(student);
        return new ResponseEntity<>(student, HttpStatus.OK);


    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Student student) {
        Student.studentList.remove(student);
        Student.studentList.add(student);
        return new ResponseEntity<>(student, HttpStatus.OK);


    }


}
