package com.mitocode.service;

import com.mitocode.model.Examen;
import com.mitocode.model.Pedido;

public interface IExamenService extends ICRUD<Examen, Integer> {
	
	String mensaje();
	Pedido comparaArryList(Pedido p);

}
