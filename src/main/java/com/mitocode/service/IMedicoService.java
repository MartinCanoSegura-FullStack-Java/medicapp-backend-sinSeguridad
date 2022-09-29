package com.mitocode.service;

import com.mitocode.model.Medico;
import com.mitocode.model.Pedido;

public interface IMedicoService extends ICRUD<Medico, Integer> {
	
	String mensaje();
	Pedido comparaArryList(Pedido p);

}
