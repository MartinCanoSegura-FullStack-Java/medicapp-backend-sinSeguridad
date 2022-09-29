package com.mitocode.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mitocode.model.Paciente;
import com.mitocode.model.Pedido;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.IPacienteRepo;
import com.mitocode.service.IPacienteService;

@Service
public class PacienteServiceImpl extends CRUDImpl<Paciente, Integer> implements IPacienteService {

	@Autowired
	private IPacienteRepo repo;
	
	@Override
	protected IGenericRepo<Paciente, Integer> getRepo(){
		return repo;
	}
	
	@Override
	public Page<Paciente> listarPageable(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Override
	public String mensaje() {
		return "Este es el Mensaje Recuperado";
	}

	@Override
	public Pedido comparaArryList(Pedido P) {
//		public Map<String, Long> comparaArryList(Pedido P) {
		List<String> lista1 = P.getListA();
		List<String> lista2 = P.getListB();
		List<String> listaR = new ArrayList<String>();
		
//		lista1.forEach(System.out::println); System.out.println("-------------------");
//		lista2.forEach(System.out::println); System.out.println("-------------------");
		
		List<String> list3 = lista1.stream().filter(p -> lista2.contains(p)).collect(Collectors.toList());
//			System.out.println(list3);
		
		Map<String, Long> lista4 = list3.stream().collect(Collectors.groupingBy(p -> p, Collectors.counting()));
//		System.out.println(lista4);
		
		for(Map.Entry<String, Long> entry : lista4.entrySet() ) {  
			 listaR.add(entry.getKey() + " = " + entry.getValue());  
		}
		P.setListA(listaR);	
		P.setListB(null);
		return P;
	}

	

}
