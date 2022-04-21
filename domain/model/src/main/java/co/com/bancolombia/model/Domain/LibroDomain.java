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
public class LibroDomain implements Serializable {
    private String isbn;
    private String nombre;
    private int cantidadTotal;
    private int cantidadDisponible;
}
