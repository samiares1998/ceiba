package co.com.bancolombia.jpa.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@ToString
@EqualsAndHashCode
@Getter
@Table(name = "usuario")
public class UsuarioEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "correo", length = 100, unique = true)
	private String correo;

	@Column(name = "clave", length = 100)
	private String clave;

	@Column(name = "rol", length = 1)
	private String rol;

	@Column(name = "identificacion", length = 100)
	private String identificacion;

	@Column(name = "nombre", length = 100)
	private String nombre;

	@Column(name = "apellido", length = 100)
	private String apellido;


//	@OneToMany(mappedBy = "usuarioEntityCliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	private Set<PrestamoEntity> usuarioClientes;
//
//	@OneToMany(mappedBy = "usuarioEntityBiblioteca", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	private Set<PrestamoEntity> usuarioBibliotecas;

}
