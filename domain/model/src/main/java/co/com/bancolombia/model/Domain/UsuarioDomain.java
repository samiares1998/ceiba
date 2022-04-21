package co.com.bancolombia.model.Domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UsuarioDomain {
    private String correo;
    private String clave;
    private String identificacion;
    private String nombre;
    private String apellido;
    private int rol;
}
