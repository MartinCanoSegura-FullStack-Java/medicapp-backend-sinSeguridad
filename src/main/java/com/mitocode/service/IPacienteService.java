package com.mitocode.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mitocode.model.Paciente;
import com.mitocode.model.Pedido;

public interface IPacienteService extends ICRUD<Paciente, Integer> {
	
	Page<Paciente> listarPageable(Pageable pageable);
	
	String mensaje();
	Pedido comparaArryList(Pedido p);

}
