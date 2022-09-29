package com.mitocode.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mitocode.exception.ModeloNotFoundException;
import com.mitocode.model.Medico;
import com.mitocode.model.Pedido;
import com.mitocode.service.IMedicoService;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
	
	Logger logger = LoggerFactory.getLogger(MedicoController.class);

	@Autowired
	private IMedicoService service;
	
	@GetMapping
	public ResponseEntity<List<Medico>> listar() throws Exception {		
		List<Medico> lista = service.listar();
		return new ResponseEntity<List<Medico>>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Medico> listarPorId(@PathVariable("id") Integer id) throws Exception {
		Medico obj = service.listarPorId(id);		
		
		if(obj == null) {
			throw new ModeloNotFoundException("MEDICO CON ID = " + id + " NO ENCONTRADO ");
		}
		return new ResponseEntity<Medico>(obj, HttpStatus.OK);
	}
	
	@GetMapping("/hateoas/{id}")
	public EntityModel<Medico> listarPorIdHateoas(@PathVariable("id") Integer id) throws Exception {
		Medico obj = service.listarPorId(id);		
		
		if(obj.getIdMedico() == null) {
			throw new ModeloNotFoundException("MEDICO CON ID = " + id + " NO ENCONTRADO ");
		}
		//localhost:8080//medicos/{id}
		EntityModel<Medico> recurso = EntityModel.of(obj);
		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).listarPorId(id));
		recurso.add(linkTo.withRel("medico-recurso"));
		return recurso;
	}
	
	/*@PostMapping
	public ResponseEntity<Medico> registrar(@Valid @RequestBody Medico p) {
		Medico obj = service.registrar(p);
		return new ResponseEntity<Medico>(obj, HttpStatus.CREATED); 
	}*/
	
	@PostMapping
	public ResponseEntity<Medico> registrar(@Valid @RequestBody Medico p) throws Exception {
		Medico obj = service.registrar(p);
		
		//localhost:8080/medicos/2
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdMedico()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping
	public ResponseEntity<Medico> modificar(@Valid @RequestBody Medico p) throws Exception {
		Medico obj = service.modificar(p);
		return new ResponseEntity<Medico>(obj, HttpStatus.OK); 
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
		Medico obj = service.listarPorId(id);
		
		if(obj.getIdMedico() == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		
		service.eliminar(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/msg")
	public String mensaje(){
		return service.mensaje();
	}
	
	@PostMapping("/comparaArray")
	public Pedido comparador(@RequestBody Pedido p) {
//		public Map<String, Long> comparador(@RequestBody Pedido p) {
//			Map<String, Long> obj = service.comparaArryList(p);
		return service.comparaArryList(p);
		 
	}

	/**
	 --- FORMATO JSON EN POSTMAN ---
	 POST: http://localhost:8080/medicos/comparaArray
	 
	{
	"listA" : ["POLLO","AGUACATE","TOMATE","CELULAR","MOCHILA","PC","BOCINA","MOCHILA","POLLO","HUEVO","TOMATE"],
	"listB" : ["TOMATE","MOCHILA"]
	}
	--- SALIDA POR CONSOLA DE ECLIPSE ---

	[TOMATE, MOCHILA, MOCHILA, TOMATE]
	{TOMATE=2, MOCHILA=2}
	*/
	
}
