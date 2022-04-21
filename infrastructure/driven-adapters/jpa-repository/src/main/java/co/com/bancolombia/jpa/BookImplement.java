package co.com.bancolombia.jpa;

import co.com.bancolombia.jpa.dao.LibroDao;
import co.com.bancolombia.jpa.dao.PrestamoDao;
import co.com.bancolombia.jpa.dao.UsuarioDao;
import co.com.bancolombia.jpa.data.LibroEntity;
import co.com.bancolombia.jpa.data.PrestamoEntity;
import co.com.bancolombia.jpa.data.UsuarioEntity;
import co.com.bancolombia.model.Domain.LibroDomain;
import co.com.bancolombia.model.Domain.PrestamoSolicitudDomain;
import co.com.bancolombia.model.Domain.UsuarioDomain;
import co.com.bancolombia.model.Gateway.IBookProviderGateway;
import co.com.bancolombia.model.Response.Prestamo;
import co.com.bancolombia.model.Response.RespuestaDomain;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@AllArgsConstructor
@Service
public class BookImplement implements IBookProviderGateway {

    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private LibroDao libroDao;
    @Autowired
    private PrestamoDao prestamoDao;

    @Override
    public RespuestaDomain findByIsbn(String isbn) {
        return null;
    }

    @Override
    public RespuestaDomain findAll() {
        return null;
    }

    @Override
    public RespuestaDomain findAllPrestamo() {
        return null;
    }

    @Override
    public RespuestaDomain prestamoLibro(PrestamoSolicitudDomain prestamoSolicitudDomain) {
        UsuarioEntity user = usuarioDao.findByIdentificacion(prestamoSolicitudDomain.getDocumento());
        if(Objects.isNull(user)){
            return RespuestaDomain.error("No se encotnro el usuario " + prestamoSolicitudDomain.getNombreUsuario());
        }
        LibroEntity libro = libroDao.findByIsbn(prestamoSolicitudDomain.getIsbn());
        if(Objects.isNull(libro)){
            return RespuestaDomain.error("No se encontro el libro con ISBN " + prestamoSolicitudDomain.getIsbn());
        }
        PrestamoEntity prestamoUser = prestamoDao.findByUser(user.getId());
        if(!Objects.isNull(prestamoUser)){
            return RespuestaDomain.error("El usuario con identificación +"+prestamoSolicitudDomain.getDocumento()+"ya tiene un libro prestado por lo cual no se le puede realizar otro préstamo");
        }
        if(prestamoSolicitudDomain.getRol()>3 || prestamoSolicitudDomain.getRol()<1){
            return RespuestaDomain.error("Tipo de usuario no permitido en la biblioteca");
        }

        Calendar c = Calendar.getInstance();
        int dias=0;
        int num_dias_afectar=7;
        if(user.getRol().equals(1)){
            num_dias_afectar=10;
        }else if(user.getRol().equals(2)){
            num_dias_afectar=8;
        }

        if(Integer.parseInt(user.getRol())>3){

            return RespuestaDomain.error("Tipo de usuario no permitido en la biblioteca");

        }


        Date fecha_fin = new Date();

        while (dias<=num_dias_afectar)
        {

            if (c.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && c.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
            {

                fecha_fin = c.getTime();
                dias++;
            }
            c.add(Calendar.DATE, 1);
        }


        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String date = simpleDateFormat.format(fecha_fin);

        LocalDate lt = LocalDate.now();

        PrestamoEntity prestamo = new PrestamoEntity();
        prestamo.setUsuarioEntity(user);
        prestamo.setLibroEntity(libro);
        prestamo.setObservaciones("");
        prestamo.setFechaPrestamo(lt);
        prestamo.setFechaEntrega(lt);
        prestamo.setFechaEntregado(LocalDate.parse(date));

        PrestamoEntity prestamoExit= prestamoDao.save(prestamo);

        if(Objects.isNull(prestamoExit)){
            return RespuestaDomain.error("Error creando el prestamo");
        }
        Prestamo response = Prestamo.builder()
                .id(prestamoExit.getId().toString())
                .fechaMaximaDevolucion(date)
                .identificacionUsuario(user.getIdentificacion())
                .isbn(libro.getIsbn())
                .tipoUsuario(user.getRol()).build();

        return RespuestaDomain.ok(response,"exitosa");
    }

    @Override
    public RespuestaDomain getByIdPrestamo(Long id) {
        PrestamoEntity prestamoExit= prestamoDao.getById(id);
        if(Objects.isNull(prestamoExit)){
            return RespuestaDomain.error("No se encontro el prestado con ese id");
        }
        UsuarioEntity user= usuarioDao.getById(prestamoExit.getUsuarioEntity().getId());
        LibroEntity libro = libroDao.getById(prestamoExit.getLibroEntity().getId());
        Prestamo response = Prestamo.builder()
                .id(prestamoExit.getId().toString())
                .fechaMaximaDevolucion(String.valueOf(prestamoExit.getFechaEntrega()))
                .identificacionUsuario(user.getIdentificacion())
                .isbn(libro.getIsbn())
                .tipoUsuario(user.getRol()).build();

        return RespuestaDomain.ok(response,"exitosa");
    }

    @Override
    public RespuestaDomain guardar(LibroDomain libro) {

        if(!Objects.isNull(libroDao.findByIsbn(libro.getIsbn()))){
            return RespuestaDomain.error("Ya existe un libro con ese ISBN");
        }
        ModelMapper mapper = new ModelMapper();
        LibroEntity libroResponse = mapper.map(libro,LibroEntity.class);
        return RespuestaDomain.ok(libroResponse,"exitosa");
    }

    @Override
    public RespuestaDomain guardarUsuario(UsuarioDomain usuario) {
        if(!Objects.isNull(usuarioDao.findByIdentificacion(usuario.getIdentificacion()))){
            return RespuestaDomain.error("Ya existe un usuario con esa identificacion");
        }
        usuario.getClave();//utilizar un metodo de encriptacion
        ModelMapper mapper = new ModelMapper();
        UsuarioEntity response = mapper.map(usuario,UsuarioEntity.class);
        return RespuestaDomain.ok(response,"exitosa");
    }
}
