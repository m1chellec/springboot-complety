package br.com.ractecnologia.springbootessentials.controller;


import br.com.ractecnologia.springbootessentials.error.ResourceNotFoundException;
import br.com.ractecnologia.springbootessentials.model.Student;
import br.com.ractecnologia.springbootessentials.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1")
public class StudentController {


    private final StudentRepository studentDao;

    @Autowired
    public StudentController(StudentRepository studentDao) {
        this.studentDao = studentDao;
    }

    @GetMapping(path = "/protected/students")
    public ResponseEntity<?> listAll(Pageable pageable) {
        return new ResponseEntity<>(studentDao.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(path = "/protected/students/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") Long id) {
        verifyIfStudentsExists(id);
        Student student = studentDao.findOne(id);
        return new ResponseEntity<>(student, HttpStatus.ACCEPTED);

    }

    @GetMapping(path = "/protected/students/findByName/{name}")
    public ResponseEntity<?> findStudentsByName(@PathVariable String name) {
        return new ResponseEntity<>(studentDao.findByNameIgnoreCaseContaining(name), HttpStatus.OK);

    }

    @PostMapping(path = "/admin/students")
    public ResponseEntity<?> save(@Valid @RequestBody Student student) {
        Student student1 = studentDao.save(student);
        return new ResponseEntity<>(student1, HttpStatus.CREATED);

    }

    @DeleteMapping(path = "/admin/students/{id}")
   // @PreAuthorize("hasRole('ADMIN') ")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        verifyIfStudentsExists(id);
        studentDao.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "/admin/students")
    public ResponseEntity<?> update(@RequestBody Student student) {
        verifyIfStudentsExists(student.getId());
        studentDao.save(student);
        return new ResponseEntity<>(student, HttpStatus.NO_CONTENT);
    }

    private void verifyIfStudentsExists(Long id) {

        if (studentDao.findOne(id) == null) {
            throw new ResourceNotFoundException("Student not found for id: " + id);

        }

    }


}
