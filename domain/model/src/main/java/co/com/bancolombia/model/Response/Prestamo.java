package co.com.bancolombia.model.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Prestamo implements Serializable {
    private String isbn;
    private String identificacionUsuario;
    private String tipoUsuario;
    private String id;
    private String fechaMaximaDevolucion;

}
