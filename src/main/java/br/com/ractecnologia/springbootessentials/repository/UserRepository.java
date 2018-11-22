package br.com.ractecnologia.springbootessentials.repository;

import br.com.ractecnologia.springbootessentials.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User,Long> {

    User findByUsername(String username);
}
