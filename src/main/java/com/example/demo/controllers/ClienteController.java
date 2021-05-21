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

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteRepository ClienteRepository;

	@GetMapping
	public List<Cliente> getCliente() {
		return ClienteRepository.findAll();
	}

	@GetMapping("/nome/{nome}")
	public List<Cliente> getClienteByNome(@PathVariable String nome) {
		return ClienteRepository.findByNomeContaining(nome);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
		if (ClienteRepository.findById(id).isPresent()) {
			return ResponseEntity.ok(ClienteRepository.findById(id).get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Cliente seveCliente(@Valid @RequestBody Cliente cliente) {
		return ClienteRepository.save(cliente);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Cliente> updateCliente(@Valid @PathVariable Long id , @RequestBody Cliente cliente) {
		if (ClienteRepository.findById(id).isPresent()) {
			cliente.setId(ClienteRepository.findById(id).get().getId());
			ClienteRepository.save(cliente);
			return ResponseEntity.ok(cliente);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.PROCESSING)
	public ResponseEntity<Void> updateCliente(@PathVariable Long id) {
		Cliente cliente = new Cliente();
		if (ClienteRepository.findById(id).isPresent()) {
			cliente.setId(ClienteRepository.findById(id).get().getId());
			ClienteRepository.delete(cliente);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}

	}
}
