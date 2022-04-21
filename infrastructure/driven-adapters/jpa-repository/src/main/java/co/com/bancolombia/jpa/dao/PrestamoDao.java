package co.com.bancolombia.jpa.dao;

import co.com.bancolombia.jpa.data.LibroEntity;
import co.com.bancolombia.jpa.data.PrestamoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamoDao extends JpaRepository<PrestamoEntity, Long> {

    PrestamoEntity save(PrestamoEntity prestamo);
    @Query(value = "SELECT * FROM prestamo l WHERE l.id_usuario = ?1",
            nativeQuery = true)
    PrestamoEntity findByUser(Long userID);

}
