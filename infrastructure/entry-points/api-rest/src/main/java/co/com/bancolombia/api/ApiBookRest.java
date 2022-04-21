package co.com.bancolombia.api;

import co.com.bancolombia.model.Domain.LibroDomain;
import co.com.bancolombia.model.Domain.PrestamoSolicitudDomain;
import co.com.bancolombia.model.Domain.UsuarioDomain;
import co.com.bancolombia.model.Response.RespuestaDomain;
import co.com.bancolombia.usecase.BookUseCase;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/libro", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ApiBookRest {

    private final BookUseCase bookUseCase;

    @PostMapping( "/prestamo" )
    @ResponseBody
    public RespuestaDomain prestamo(@RequestParam Map<String, String> body) {

        PrestamoSolicitudDomain prestamoSolicitudDomain = PrestamoSolicitudDomain.builder()
                .isbn(body.get("isbn"))
                .nombreUsuario(body.get("nombreUsuario"))
                .apellidoUsuario(body.get("apellidoUsuario"))
                .correo(body.get("correo"))
                .documento(body.get("documento"))
                .rol(Integer.parseInt(body.get("rol")))
                .build();
        if(prestamoSolicitudDomain.getIsbn().length()>10){
            return RespuestaDomain.error("ISBN debe ser de solo 10 digitos");
        }
        if(prestamoSolicitudDomain.getIdentificacionUsuario().length()>10){
            return RespuestaDomain.error("identificacion de usuario debe ser de solo 10 digitos");
        }
        return bookUseCase.prestamoLibro(prestamoSolicitudDomain);
    }

    @GetMapping("prestamo/{id}")
    @ResponseBody
    public ResponseEntity<RespuestaDomain> getByIdPrestamo(@PathVariable Long id) {
        return new ResponseEntity<RespuestaDomain>(bookUseCase.getByIdPrestamo(id), HttpStatus.OK);
    }

    @PostMapping( "/guardar" )
    @ResponseBody
    public RespuestaDomain guardar(@RequestParam Map<String, String> body) {

        LibroDomain libro = LibroDomain.builder()
                .isbn(body.get("isbn"))
                .nombre(body.get("nombre"))
                .cantidadTotal(Integer.parseInt(body.get("cantidadTotal")))
                .cantidadDisponible(Integer.parseInt(body.get("cantidadDisponible")))
                .build();
        if(body.isEmpty()||libro.getIsbn().length()>10||libro.getIsbn().isEmpty()){
            return RespuestaDomain.error("ISBN No debe estar vacio");
        }
        return bookUseCase.guardar(libro);
    }

    @PostMapping( "/guardarUsuario" )
    @ResponseBody
    public RespuestaDomain guardarUsuario(@RequestParam Map<String, String> body) {

        UsuarioDomain usuario = UsuarioDomain.builder()
                .correo(body.get("correo"))
                .clave(body.get("clave"))
                .identificacion(body.get("identificacion"))
                .apellido(body.get("apellido"))
                .rol(Integer.parseInt(body.get("rol")))
                .build();
        if(body.isEmpty()||usuario.getIdentificacion().length()>10||usuario.getIdentificacion().isEmpty()){
            return RespuestaDomain.error("Datos incorrectos");
        }
        return bookUseCase.guardarUsuario(usuario);
    }


}
