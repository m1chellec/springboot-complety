package br.com.ractecnologia.springbootessentials.javaclient;

import br.com.ractecnologia.springbootessentials.model.PageableResponse;
import br.com.ractecnologia.springbootessentials.model.Student;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class JavaSpringClientTest {

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri("http://localhost:8080/v1/protected/students")
                .basicAuthorization("oda", "devdojo").build();
        Student student = restTemplate.getForObject("/{id}", Student.class, 2);
        ResponseEntity<Student> forEntity = restTemplate.getForEntity("/{id}", Student.class, 2);
        System.out.println(student);
        System.out.println(forEntity);
//        Student[] students = restTemplate.getForObject("/", Student[].class);
//        System.out.println(Arrays.toString(students));

        ResponseEntity<PageableResponse<Student>> exchange = restTemplate.exchange("/?sort=id,desc&sort=name,asc", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Student>>() {
                });
        System.out.println(exchange);


    }
}
