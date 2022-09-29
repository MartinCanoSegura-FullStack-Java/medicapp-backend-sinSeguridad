package com.mitocode.service;

import java.util.List;

import com.mitocode.dto.ConsultaListaExamenDTO;
import com.mitocode.dto.ConsultaResumenDTO;
import com.mitocode.dto.FiltroConsultaDTO;
import com.mitocode.model.Consulta;
import com.mitocode.model.Pedido;

public interface IConsultaService extends ICRUD<Consulta, Integer> {
	
	/** Junto con la anotacion @JsonIgnore en la propiedad consulta en DetalleConsulta 
	 *  Se usa para Hacer la incersion de detalle-consulta 
	 */
	Consulta registrarTransaccional(ConsultaListaExamenDTO dto) throws Exception;
	
	List<Consulta> buscar(FiltroConsultaDTO filtro);
	List<Consulta> buscarFecha(FiltroConsultaDTO filtro);
	List<ConsultaResumenDTO> listarResumen();
	byte[] generarReporte();
	
	String mensaje();
	Pedido comparaArryList(Pedido p);

}
