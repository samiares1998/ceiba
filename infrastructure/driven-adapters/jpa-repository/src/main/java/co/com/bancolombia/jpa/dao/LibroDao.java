package co.com.bancolombia.jpa.dao;

import co.com.bancolombia.jpa.data.LibroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroDao extends JpaRepository<LibroEntity, Long> {

    LibroEntity findByIsbn(String isbn);


}