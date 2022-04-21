package co.com.bancolombia.jpa.dao;

import co.com.bancolombia.jpa.data.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioDao extends JpaRepository<UsuarioEntity, Long> {

    @Query(value = "SELECT * FROM usuario u WHERE u.identificacion = ?1",
            nativeQuery = true)
    UsuarioEntity findByIdentificacion(String identificacion);

    UsuarioEntity findByCorreo(String correo);


}
