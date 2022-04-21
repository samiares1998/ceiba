package co.com.bancolombia.usecase;

import co.com.bancolombia.model.Domain.LibroDomain;
import co.com.bancolombia.model.Domain.PrestamoSolicitudDomain;
import co.com.bancolombia.model.Domain.UsuarioDomain;
import co.com.bancolombia.model.Gateway.IBookProviderGateway;
import co.com.bancolombia.model.Response.RespuestaDomain;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookUseCase {
    private final IBookProviderGateway iBookProviderGateway;

    public RespuestaDomain prestamoLibro(PrestamoSolicitudDomain prestamoSolicitudDomain) {
        return iBookProviderGateway.prestamoLibro(prestamoSolicitudDomain);
    }

    public RespuestaDomain getByIdPrestamo(Long id) {
        return iBookProviderGateway.getByIdPrestamo(id);
    }

    public RespuestaDomain guardar(LibroDomain libro) {
        return iBookProviderGateway.guardar(libro);
    }

    public RespuestaDomain guardarUsuario(UsuarioDomain usuario) {
        return iBookProviderGateway.guardarUsuario(usuario);
    }
}
