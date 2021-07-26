package com.cliente.backend.apirest.backend.api.rest.service;

import com.cliente.backend.apirest.backend.api.rest.entity.Cliente;
import com.cliente.backend.apirest.backend.api.rest.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository cr;

    @Transactional(readOnly = true)
    public List<Cliente> listarClientes(){
        return cr.findAll();
    }

    @Transactional(readOnly = true)
    public Cliente buscarClientePorId(Long id){
        Optional<Cliente> clienteOptional = cr.findById(id);
        return clienteOptional.orElse(null);
    }

    @Transactional
    public Cliente crearCliente(Cliente cliente){
        return cr.save(cliente);
    }
    @Transactional
    public void eliminarCliente(Long id){
        cr.deleteById(id);
    }

    @Transactional
    public void modificarCliente(Long id, String nombre, String apellido, String email){
        cr.modificar(id,nombre,apellido,email);
    }







}
