package com.example.demo.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Cliente;
import com.example.demo.repositories.ClienteRepository;
import com.example.demo.services.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@GetMapping
	public List<Cliente> getCliente() {
		return clienteService.getCliente();
	}

	@GetMapping("/nome/{nome}")
	public List<Cliente> getClienteByNome(@PathVariable String nome) {
		return clienteService.getClienteByNome(nome);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
		if (clienteService.getClienteById(id) != null) {
			return ResponseEntity.ok(clienteService.getClienteById(id));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Cliente saveCliente(@Valid @RequestBody Cliente cliente) {
		return clienteService.saveCliente(cliente);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Cliente> updateCliente(@Valid @PathVariable Long id, @RequestBody Cliente cliente) {
		if (clienteService.getClienteById(id) != null) {
			cliente.setId(clienteService.getClienteById(id).getId());
			clienteService.saveCliente(cliente);
			return ResponseEntity.ok(cliente);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.PROCESSING)
	public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
		Cliente cliente = new Cliente();
		if (clienteService.getClienteById(id) != null) {
			cliente.setId(clienteService.getClienteById(id).getId());
			clienteService.deleteCliente(id);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}

	}
}
