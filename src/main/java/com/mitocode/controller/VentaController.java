package com.mitocode.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.mitocode.dto.FiltroConsultaDTO;
import com.mitocode.dto.ResumenVentaDTO;
import com.mitocode.dto.VentaDTO;
import com.mitocode.exception.ModeloNotFoundException;
import com.mitocode.model.Venta;
import com.mitocode.model.VentaComida;
import com.mitocode.service.IVentaService;

@RestController
@RequestMapping("/ventas")
public class VentaController {

	@Autowired
	private IVentaService service;
	
	@GetMapping
	public ResponseEntity<List<Venta>> listar(){
		List<Venta> lista = service.listar();
		return new ResponseEntity<List<Venta>>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Venta> listarPorId(@PathVariable("id") Integer id){
		Venta obj = service.listarPorId(id);
		if(obj.getIdVenta() == null) {
			throw new ModeloNotFoundException("ID NO EXISTE: " + id);
		}
		return new ResponseEntity<Venta>(obj, HttpStatus.OK);
	}
	
	/*@PostMapping
	public ResponseEntity<Venta> registrar(@RequestBody Venta obj) {
		Venta objeto = service.registrar(obj);
		return new ResponseEntity<Venta>(objeto, HttpStatus.CREATED);
	}*/
	
	/*@PostMapping
	public ResponseEntity<Object> registrar(@Valid @RequestBody Venta obj) {
		Venta pel = service.registrar(obj);
		
		// localhost:8080/ventas/2
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pel.getIdVenta()).toUri();
		return ResponseEntity.created(location).build();
	}*/
	
	@PostMapping
	public ResponseEntity<Object> registrar(@Valid @RequestBody VentaDTO obj) {
		Venta pel = service.registrarTransaccional(obj);
		
		// localhost:8080/ventas/2
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pel.getIdVenta()).toUri();
		return ResponseEntity.created(location).build();
	}

	
	@PutMapping
	public ResponseEntity<Venta> modificar(@Valid @RequestBody Venta obj) {
		Venta objeto = service.modificar(obj);
		return new ResponseEntity<Venta>(objeto, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> elminar(@PathVariable("id") Integer id){
		Venta obj = service.listarPorId(id);
		if(obj.getIdVenta() == null) {
			throw new ModeloNotFoundException("ID NO EXISTE: " + id);
		}
		service.eliminar(id);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/generarReporte", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE, consumes = "application/json")
	public ResponseEntity<byte[]> generarReporte(@RequestBody VentaDTO venta) {
		byte[] data = null;
		data = service.generarReporte(venta);
		return new ResponseEntity<byte[]>(data, HttpStatus.OK);
	}
	
	@PostMapping(value = "/buscar", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Venta>> buscar(@RequestBody FiltroConsultaDTO filtro) {
		List<Venta> ventas = new ArrayList<>();

		if (filtro != null) {
			if (filtro.getFechaConsulta() != null) {
				ventas = service.buscarfecha(filtro);
			} else {
				ventas = service.buscar(filtro);
			}
		}
		return new ResponseEntity<List<Venta>>(ventas, HttpStatus.OK);
	}
	
	@GetMapping(value = "/buscar/comidas/{idVenta}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<VentaComida>> listar(@PathVariable("idVenta") Integer idVenta) {
		List<VentaComida> ventaComidas = new ArrayList<>();
		ventaComidas = service.listarComidasPorVenta(idVenta);
		return new ResponseEntity<List<VentaComida>>(ventaComidas, HttpStatus.OK);
	}
	
	@GetMapping(value = "/listarResumen", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResumenVentaDTO>> listarResumen() {
		List<ResumenVentaDTO> ventas = new ArrayList<>();
		ventas = service.listarResumen();
		return new ResponseEntity<List<ResumenVentaDTO>>(ventas, HttpStatus.OK);
	}
}
