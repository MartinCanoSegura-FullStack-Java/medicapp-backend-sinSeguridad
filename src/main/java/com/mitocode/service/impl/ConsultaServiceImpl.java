package com.mitocode.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.dto.ConsultaListaExamenDTO;
import com.mitocode.dto.ConsultaResumenDTO;
import com.mitocode.dto.FiltroConsultaDTO;
import com.mitocode.model.Consulta;
import com.mitocode.model.Pedido;
import com.mitocode.repo.IConsultaExamenRepo;
import com.mitocode.repo.IConsultaRepo;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.service.IConsultaService;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ConsultaServiceImpl extends CRUDImpl<Consulta, Integer> implements IConsultaService {

	@Autowired
	private IConsultaRepo repo;
	
	@Autowired
	private IConsultaExamenRepo ceRepo;
	
	@Override
	protected IGenericRepo<Consulta, Integer> getRepo() {
		return repo;
	}
	
	
	//	@Override
//	public Consulta registrarTransaccional(ConsultaListaExamenDTO dto) throws Exception {
//		
//		dto.getConsulta().getDetalleConsulta().forEach(det -> det.setConsulta(dto.getConsulta()));
//		repo.save(dto.getConsulta());
//		dto.getLstExamen().forEach(ex -> ceRepo.registrar(dto.getConsulta().getIdConsulta(), ex.getIdExamen()));
//		return dto.getConsulta();
//	}
	
	@Transactional
	@Override
	public Consulta registrarTransaccional(ConsultaListaExamenDTO dto) throws Exception {
		dto.getConsulta().getDetalleConsulta().forEach(det -> det.setConsulta(dto.getConsulta()));
		repo.save(dto.getConsulta());
		dto.getLstExamen().forEach(ex -> ceRepo.registrar(dto.getConsulta().getIdConsulta(), ex.getIdExamen()));
		return dto.getConsulta();
	}

	

	

	

	@Override
	public List<Consulta> buscar(FiltroConsultaDTO filtro) {
		return repo.buscar(filtro.getDni(), filtro.getNombreCompleto());
	}

	@Override
	public List<Consulta> buscarFecha(FiltroConsultaDTO filtro) {
		return repo.buscarFecha(filtro.getFechaConsulta(), filtro.getFechaConsulta().plusDays(1)); 
	}

	@Override
	public List<ConsultaResumenDTO> listarResumen() {
		List<ConsultaResumenDTO> consultas = new ArrayList<>();
		repo.listarResumen().forEach(x -> {
			ConsultaResumenDTO cr = new ConsultaResumenDTO();
			cr.setCantidad(Integer.parseInt(String.valueOf(x[0])));
			cr.setFecha(String.valueOf(x[1]));
			consultas.add(cr);
		});
		return consultas;
	}

	
	@Override
	public byte[] generarReporte() {
		byte[] data = null;

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("txt_titulo", "Consultas por Fecha");

		try {
			File file = new ClassPathResource("/reports/consultas.jasper").getFile();
			JasperPrint print = JasperFillManager.fillReport(file.getPath(), parametros,
					new JRBeanCollectionDataSource(this.listarResumen()));
			data = JasperExportManager.exportReportToPdf(print);
			// mitocode jasperreports | excel, pdf, ppt, word, csv
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
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
