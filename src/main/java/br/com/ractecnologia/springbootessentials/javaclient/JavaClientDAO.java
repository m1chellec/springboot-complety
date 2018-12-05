package br.com.ractecnologia.springbootessentials.javaclient;

import br.com.ractecnologia.springbootessentials.model.PageableResponse;
import br.com.ractecnologia.springbootessentials.model.Student;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class JavaClientDAO {

    private RestTemplate restTemplate = new RestTemplateBuilder()
            .rootUri("http://localhost:8080/v1/protected/students")
            .basicAuthorization("oda", "devdojo").build();

    private RestTemplate restTemplateAdmin = new RestTemplateBuilder()
            .rootUri("http://localhost:8080/v1/admin/students")
            .basicAuthorization("oda", "devdojo").build();

    public Student findById(long id) {
        return restTemplate.getForObject("/{id}", Student.class, id);
    }


    public List<Student> findAll(){

        ResponseEntity<PageableResponse<Student>> exchange = restTemplate.exchange("/?sort=id,desc&sort=name,asc", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Student>>() {
                });
        return exchange.getBody().getContent();
    }

    public Student save(Student student){
        ResponseEntity<Student> exchangePost = restTemplateAdmin.exchange("/", HttpMethod.POST,
                new HttpEntity<>(student, createJsonHeader()), Student.class);
        return exchangePost.getBody();



    }

    private static HttpHeaders createJsonHeader() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
