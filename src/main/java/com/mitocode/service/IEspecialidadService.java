package com.mitocode.service;

import com.mitocode.model.Especialidad;
import com.mitocode.model.Pedido;

public interface IEspecialidadService extends ICRUD<Especialidad, Integer> {
	
	String mensaje();
	Pedido comparaArryList(Pedido p);

}
