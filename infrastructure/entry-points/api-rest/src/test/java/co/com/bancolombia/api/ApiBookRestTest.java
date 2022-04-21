package co.com.bancolombia.api;

import co.com.bancolombia.model.Gateway.IBookProviderGateway;
import co.com.bancolombia.model.Gateway.IUserProviderGateway;
import co.com.bancolombia.model.Response.Prestamo;
import co.com.bancolombia.model.Response.RespuestaDomain;
import co.com.bancolombia.usecase.BookUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.core.Is.is;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ContextConfiguration(classes = {
        ApiBookRest.class,
        BookUseCase.class,
        IBookProviderGateway.class
})
@AutoConfigureMockMvc
@WebMvcTest(ApiBookRest.class)
public class ApiBookRestTest {

    public static final int USUARIO_AFILIADO = 1;
    public static final int USUARIO_EMPLEADO = 2;
    public static final int USUARIO_INVITADO = 3;
    public static final int USUARIO_DESCONOCIDO = 5;
    @MockBean
    ApiBookRest ApiBookRest;
    @MockBean
    IBookProviderGateway iBookProviderGateway;
    @Autowired
    private MockMvc mvc;
    @InjectMocks
    private ObjectMapper objectMapper;

    @Test
    public void prestamoLibroUsuarioAfiliadoDeberiaAlmacenarCorrectamenteYCalcularFechaDeDevolucion() throws Exception {
        Map<String, String> body = new HashMap<String, String>();
        body.put("isbn", "ABC");
        body.put("documento", "12145636");
        body.put("tipoUsuario", String.valueOf(USUARIO_AFILIADO));

        MvcResult resultadoLibroPrestado = mvc.perform(
                MockMvcRequestBuilders.post("/api/libro/prestamo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andDo(print())
                //.andExpect(status().isOk())
                .andReturn();

        System.out.println("prueva"+resultadoLibroPrestado);

    }
    @Test
    public void prestamoLibroUsuarioEmpleadoDeberiaAlmacenarCorrectamenteYCalcularFechaDeDevolucion() throws Exception {
        Map<String, String> body = new HashMap<String, String>();
        body.put("isbn", "ABC");
        body.put("documento", "12145636");


        mvc.perform(MockMvcRequestBuilders
                .get("/api/libro/prestamo")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
               // .andExpect(jsonPath("$.fechaMaximaDevolucion", is(fechaPrestamo.format(formato))))
                .andExpect(jsonPath("$.isbn").exists());



    }

  /*  @Test
    public void prestamoLibroUsuarioInvitadoDeberiaAlmacenarCorrectamenteYCalcularFechaDeDevolucion() throws Exception {
        Map<String, String> body = new HashMap<String, String>();
        body.put("isbn", "ABC");
        body.put("documento", "12145636");
        body.put("tipoUsuario", String.valueOf(USUARIO_INVITADO));

        MvcResult resultadoLibroPrestado = mvc.perform(MockMvcRequestBuilders
                .post("/api/libro/prestamo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.fechaMaximaDevolucion").exists())
                .andReturn();

        Prestamo resultadoPrestamo = objectMapper.readValue(resultadoLibroPrestado.getResponse().getContentAsString(), Prestamo.class);

        LocalDate fechaPrestamo = LocalDate.now();
        fechaPrestamo = addDaysSkippingWeekends(fechaPrestamo, 7);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");


        mvc.perform(MockMvcRequestBuilders
                .get("/api/libro/prestamo" + resultadoPrestamo.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.fechaMaximaDevolucion", is(fechaPrestamo.format(formato))))
                .andExpect(jsonPath("$.isbn", is("EQWQW8545")))
                .andExpect(jsonPath("$.identificacionUsuario", is("74851254")))
                .andExpect(jsonPath("$.tipoUsuario", is(USUARIO_INVITADO)));


    }

    @Test
    public void usuarioInvitadoTratandoDePrestarUnSegundoLibroDeberiaRetornarExcepcion() throws Exception {
        Map<String, String> body = new HashMap<String, String>();
        body.put("isbn", "ABC");
        body.put("documento", "12145636");
        body.put("tipoUsuario", String.valueOf(USUARIO_INVITADO));

        mvc.perform(MockMvcRequestBuilders
                .post("/api/libro/prestamo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.fechaMaximaDevolucion").exists());

        mvc.perform(MockMvcRequestBuilders
                .post("/api/libro/prestamo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.mensaje", is("El usuario con identificación 1111111111 ya tiene un libro prestado por lo cual no se le puede realizar otro préstamo")));

    }

    @Test
    public void usuarioNoInvitadoTratandoDePrestarUnSegundoLibroDeberiaPrestarloCorrectamente() throws Exception {
        Map<String, String> body = new HashMap<String, String>();
        body.put("isbn", "ABC");
        body.put("documento", "12145636");
        body.put("tipoUsuario", String.valueOf(USUARIO_AFILIADO));

        mvc.perform(MockMvcRequestBuilders
                .post("/api/libro/prestamo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.fechaMaximaDevolucion").exists());

        mvc.perform(MockMvcRequestBuilders
                .post("/api/libro/prestamo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.fechaMaximaDevolucion").exists());

    }

    @Test
    public void prestamoConTipoDeUsuarioNoPermitidoDeberiaRetornarExcepcion() throws Exception {
        Map<String, String> body = new HashMap<String, String>();
        body.put("isbn", "ABC");
        body.put("documento", "12145636");
        body.put("tipoUsuario", String.valueOf(USUARIO_DESCONOCIDO));

        mvc.perform(MockMvcRequestBuilders
                .post("/api/libro/prestamo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.mensaje", is("Tipo de usuario no permitido en la biblioteca")));
    }



    public static LocalDate addDaysSkippingWeekends(LocalDate date, int days) {
        LocalDate result = date;
        int addedDays = 0;
        while (addedDays < days) {
            result = result.plusDays(1);
            if (!(result.getDayOfWeek() == DayOfWeek.SATURDAY || result.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                ++addedDays;
            }
        }
        return result;
    }

   */
}