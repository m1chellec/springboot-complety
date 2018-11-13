package br.com.ractecnologia.springbootessentials.model;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;

@Entity
public class Student {

    private int id;

    private String nome;

    static {
        studentRepository();
    }

    public static List<Student> studentList;


    public Student() {
    }

    public Student(String nome) {
        this.nome = nome;
    }

    public Student(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    private static void studentRepository() {

        studentList = new ArrayList<>(asList(new Student(1, "Juliana"), new Student(2, "Gabriel")));
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public static List<Student> getStudentList() {
        return studentList;
    }

    public static void setStudentList(List<Student> studentList) {
        Student.studentList = studentList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
