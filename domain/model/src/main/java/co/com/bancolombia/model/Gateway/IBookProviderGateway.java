package co.com.bancolombia.model.Gateway;

import co.com.bancolombia.model.Domain.LibroDomain;
import co.com.bancolombia.model.Domain.PrestamoSolicitudDomain;
import co.com.bancolombia.model.Domain.UsuarioDomain;
import co.com.bancolombia.model.Response.RespuestaDomain;

public interface IBookProviderGateway {


    RespuestaDomain findByIsbn(String isbn);

    RespuestaDomain findAll();

    RespuestaDomain findAllPrestamo();

    RespuestaDomain prestamoLibro(PrestamoSolicitudDomain prestamoSolicitudDomain);

    RespuestaDomain getByIdPrestamo(Long id);

    RespuestaDomain guardar(LibroDomain libro);

    RespuestaDomain guardarUsuario(UsuarioDomain usuario);
}
