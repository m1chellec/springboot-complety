package br.com.ractecnologia.springbootessentials.controller;


import br.com.ractecnologia.springbootessentials.error.CustomErrorType;
import br.com.ractecnologia.springbootessentials.error.ResourceNotFoundException;
import br.com.ractecnologia.springbootessentials.model.Student;
import br.com.ractecnologia.springbootessentials.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("students")
public class StudentController {


    private final StudentRepository studentDao;

    @Autowired
    public StudentController(StudentRepository studentDao) {
        this.studentDao = studentDao;
    }

    @GetMapping
    public ResponseEntity<?> listAll() {

        return new ResponseEntity<>(studentDao.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") Long id) {
        verifyIfStudentsExists(id);
        Optional<Student> studentOptional = studentDao.findById(id);
        return new ResponseEntity<>(studentOptional.get(), HttpStatus.ACCEPTED);

    }

    @GetMapping(path = "/findByName/{name}")
    public ResponseEntity<?> findStudentsByName(@PathVariable String name) {
        return new ResponseEntity<>(studentDao.findByNameIgnoreCaseContaining(name), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Student student) {
        Student student1 = studentDao.save(student);
        return new ResponseEntity<>(student1, HttpStatus.CREATED);

    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody Student student) {
        verifyIfStudentsExists(student.getId());
        studentDao.delete(student);
        return new ResponseEntity<>(student, HttpStatus.NO_CONTENT);


    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Student student) {
        verifyIfStudentsExists(student.getId());
        studentDao.save(student);
        return new ResponseEntity<>(student, HttpStatus.NO_CONTENT);


    }

    private void verifyIfStudentsExists(Long id) {

        if (!studentDao.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Student not found for id: " + id);

        }

    }


}
