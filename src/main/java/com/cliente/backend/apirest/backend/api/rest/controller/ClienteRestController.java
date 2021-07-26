package com.cliente.backend.apirest.backend.api.rest.controller;


import com.cliente.backend.apirest.backend.api.rest.entity.Cliente;
import com.cliente.backend.apirest.backend.api.rest.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/cliente")
public class ClienteRestController {

    @Autowired
    private ClienteService cs;

    @GetMapping("/listar")
    public List<Cliente> listarCliente(){
        return cs.listarClientes();
    }
    @GetMapping("/buscarid/{id}")
    public Cliente buscarClienteId(@PathVariable Long id){
        return cs.buscarClientePorId(id);
    }
    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente crearCliente(@RequestBody Cliente cliente){

        return cs.crearCliente(cliente);
    }
   /* @PutMapping("/clientes/{id}")
    public void modificarCliente(@PathVariable Long id, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String email){
        cs.modificarCliente(id,nombre,apellido,email);
    }*/
    @PutMapping("/modificar/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente modificarCliente(@RequestBody Cliente cliente, @PathVariable Long id){
        Cliente clienteActual = cs.buscarClientePorId(id);
        clienteActual.setNombre(cliente.getNombre());
        clienteActual.setApellido(cliente.getApellido());
        clienteActual.setEmail(cliente.getEmail());

        return cs.crearCliente(clienteActual);
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarCliente(@PathVariable Long id){
        cs.eliminarCliente(id);
    }


}
