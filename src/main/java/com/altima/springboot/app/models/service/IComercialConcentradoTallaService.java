package com.altima.springboot.app.models.service;

import java.math.BigInteger;
import java.util.List;

import com.altima.springboot.app.dto.TallasPivoteDto;
import com.altima.springboot.app.models.entity.ComercialConcentradoTalla;

public interface IComercialConcentradoTallaService {

	List<Object[]> findPrendaCliente(Long idpedido);

	List<ComercialConcentradoTalla> findAll();

	void save(ComercialConcentradoTalla comercialconcentradotalla);

	void delete(Long id);

	ComercialConcentradoTalla findOne(Long id);

	List<Object[]> findTallasPrendaEspecificacion(Long idpedido, Long idempleado, Long idprenda);

	List<ComercialConcentradoTalla> findDuplicates(String Empleado, String Largo, String PrendaCliente, String Talla,
			String Pulgadas, String especificacion, String pedido);

	List<ComercialConcentradoTalla> findDuplicates(String Empleado, String PrendaCliente, String especificacion,
			String pedido);

	List<Object[]> findPrenda(Long idpedido, Long idempleado);

	void deleteprenda(Long id_empleado, Long id_prenda);

	void deleteall(Long id_empleado);

	String genpivot(List<String> list);

	List<Object[]> findPrendaTalla2(String genpivot, Long idpedido);

	List<Object[]> findPrendaTalla3(Long idpedido);

	List<Object[]> findPrendasEmpleado(Long idempleado, Long idpedido);

	List<Object[]> findPrendapivote(Long idpedido, Long idempleado);

	List<Object[]> findPrendasEspecificaciones(Long idempleado, Long idpedido, Long idprenda);

	List<Object[]> findPrendasEspecificaciones2(Long idempleado, Long idpedido, Long idprenda);

	void updateall(Long talla, Long largo, Long idempleado, Long idpedido, Long idprenda);

	void updateall(Long largo, Long idempleado, Long idpedido, Long idprenda);

	void updateall1(Long talla, Long idempleado, Long idpedido, Long idprenda);

	List<Object[]> findPrendaTalla2(String genpivot, Long idpedido, Long idspf);

	Object findPrendaTalla3(Long idpedido, Long parseLong);

	Long findSPF(Long idpedido);

	BigInteger findByEmployeeClothesAndOrder(Long id_empleado_pedido, Long id_prenda_cliente,
			Long id_pedido);

	void updateall(Long idempleado, Long idpedido, Long idprenda);

	List<TallasPivoteDto> findPivoteByidPedido(Long idPedido);

	List<TallasPivoteDto> findPivoteByIdEmpleado(Long idEmpleado);
}
