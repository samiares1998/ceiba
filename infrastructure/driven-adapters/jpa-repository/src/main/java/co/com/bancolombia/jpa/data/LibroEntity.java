package co.com.bancolombia.jpa.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "libro")
@RequiredArgsConstructor
public class LibroEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "isbn", length = 100, unique = true, nullable = false)
	private String isbn;

	@Column(name = "nombre", length = 100, nullable = false)
	private String nombre;

	@Column(name = "cantidad_total", nullable = false)
	private Integer cantidadTotal;

	@Column(name = "cantidad_disponible", nullable = false)
	private Integer cantidadDisponible;

//	@OneToMany(mappedBy = "libroEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	private Set<PrestamoEntity> prestamoEntities;

}
