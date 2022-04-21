package co.com.bancolombia.model.Gateway;

import co.com.bancolombia.model.Response.RespuestaDomain;


public interface IUserProviderGateway {

    RespuestaDomain findByCorreo(String correo);

    RespuestaDomain findByIdentidad(String identidad);

    RespuestaDomain findAll();


}
