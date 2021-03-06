package br.com.ractecnologia.springbootessentials.repository;

import br.com.ractecnologia.springbootessentials.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface StudentRepository extends PagingAndSortingRepository<Student,Long> {


    List<Student> findByNameIgnoreCaseContaining(String name);
}
