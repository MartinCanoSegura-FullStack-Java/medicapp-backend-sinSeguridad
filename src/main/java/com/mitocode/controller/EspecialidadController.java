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
import com.mitocode.model.Especialidad;
import com.mitocode.model.Pedido;
import com.mitocode.service.IEspecialidadService;

@RestController
@RequestMapping("/especialidades")
public class EspecialidadController {
	
	Logger logger = LoggerFactory.getLogger(EspecialidadController.class);

	@Autowired
	private IEspecialidadService service;
	
	@GetMapping
	public ResponseEntity<List<Especialidad>> listar() throws Exception {		
		List<Especialidad> lista = service.listar();
		return new ResponseEntity<List<Especialidad>>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Especialidad> listarPorId(@PathVariable("id") Integer id) throws Exception {
		Especialidad obj = service.listarPorId(id);		
		
		if(obj == null) {
			throw new ModeloNotFoundException("PACIENTE CON ID = " + id + " NO ENCONTRADO ");
		}
		return new ResponseEntity<Especialidad>(obj, HttpStatus.OK);
	}
	
	@GetMapping("/hateoas/{id}")
	public EntityModel<Especialidad> listarPorIdHateoas(@PathVariable("id") Integer id) throws Exception {
		Especialidad obj = service.listarPorId(id);		
		
		if(obj.getIdEspecialidad() == null) {
			throw new ModeloNotFoundException("PACIENTE CON ID = " + id + " NO ENCONTRADO ");
		}
		//localhost:8080//Especialidads/{id}
		EntityModel<Especialidad> recurso = EntityModel.of(obj);
		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).listarPorId(id));
		recurso.add(linkTo.withRel("Especialidad-recurso"));
		return recurso;
	}
	
	/*@PostMapping
	public ResponseEntity<Especialidad> registrar(@Valid @RequestBody Especialidad p) {
		Especialidad obj = service.registrar(p);
		return new ResponseEntity<Especialidad>(obj, HttpStatus.CREATED); 
	}*/
	
	@PostMapping
	public ResponseEntity<Especialidad> registrar(@Valid @RequestBody Especialidad p) throws Exception {
		Especialidad obj = service.registrar(p);
		
		//localhost:8080/Especialidads/2
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdEspecialidad()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping
	public ResponseEntity<Especialidad> modificar(@Valid @RequestBody Especialidad p) throws Exception {
		Especialidad obj = service.modificar(p);
		return new ResponseEntity<Especialidad>(obj, HttpStatus.OK); 
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
		Especialidad obj = service.listarPorId(id);
		
		if(obj.getIdEspecialidad() == null) {
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
	 POST: http://localhost:8080/Especialidades/comparaArray
	 
	{
	"listA" : ["POLLO","AGUACATE","TOMATE","CELULAR","MOCHILA","PC","BOCINA","MOCHILA","POLLO","HUEVO","TOMATE"],
	"listB" : ["TOMATE","MOCHILA"]
	}
	--- SALIDA POR CONSOLA DE ECLIPSE ---

	[TOMATE, MOCHILA, MOCHILA, TOMATE]
	{TOMATE=2, MOCHILA=2}
	*/
	
}
