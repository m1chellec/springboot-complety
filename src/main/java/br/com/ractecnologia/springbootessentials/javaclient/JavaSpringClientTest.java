package br.com.ractecnologia.springbootessentials.javaclient;

import br.com.ractecnologia.springbootessentials.model.Student;

public class JavaSpringClientTest {

    public static void main(String[] args) {

        Student studentPost = new Student();
        studentPost.setName("John Wick 2");
        studentPost.setEmail("johnwick@gmail.com");
        studentPost.setId(10L);
        JavaClientDAO javaClientDAO = new JavaClientDAO();
        //System.out.println(javaClientDAO.findById(1));
        //  System.out.println(javaClientDAO.findAll());
        //   System.out.println(javaClientDAO.save(studentPost));
       javaClientDAO.update(studentPost);
 // javaClientDAO.delete(10);
    }


}
