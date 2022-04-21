package co.com.bancolombia.model.Response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespuestaDomain<T> {
	private boolean status;
	private String mensaje;
	private T data;
	
	public static <T> RespuestaDomain<T> ok(T data, String mensaje) {
		return RespuestaDomain.<T>builder()
				.data(data)
				.mensaje(mensaje)
				.status(true)
				.build();
	}

	public static <T> RespuestaDomain<T> error( String mensaje) {
		return RespuestaDomain.<T>builder()
				.data(null)
				.mensaje(mensaje)
				.status(false)
				.build();
	}
	
}
