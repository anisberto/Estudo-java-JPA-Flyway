package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entities.Cliente;
import com.example.demo.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Transactional
	public List<Cliente> getCliente() {
		return clienteRepository.findAll();
	}

	@Transactional
	public List<Cliente> getClienteByNome(String nome) {
		return clienteRepository.findByNomeContaining(nome);
	}

	@Transactional
	public Cliente getClienteById(Long id) {
		return clienteRepository.findById(id).get();
	}

	@Transactional
	public Cliente saveCliente(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	@Transactional
	public Cliente updateCliente(Long id, Cliente cliente) {
		cliente.setId(clienteRepository.findById(id).get().getId());
		clienteRepository.save(cliente);
		return cliente;
	}

	@Transactional
	public void deleteCliente(Long id) {
		Cliente cliente = new Cliente();
		cliente.setId(clienteRepository.findById(id).get().getId());
		clienteRepository.delete(cliente);
	}
}
