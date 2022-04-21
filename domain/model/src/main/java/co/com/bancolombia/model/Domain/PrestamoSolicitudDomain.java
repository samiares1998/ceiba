package co.com.bancolombia.model.Domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class PrestamoSolicitudDomain implements Serializable {
    private String isbn;
    private String nombreUsuario;
    private String apellidoUsuario;
    private String identificacionUsuario;
    private String correo;
    private String documento;
    private int rol;
}
