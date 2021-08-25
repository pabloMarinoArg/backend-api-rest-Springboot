package com.cliente.backend.apirest.backend.api.rest.controller;


import com.cliente.backend.apirest.backend.api.rest.entity.Cliente;
import com.cliente.backend.apirest.backend.api.rest.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
    @GetMapping("/listar/page/{page}")
    public Page<Cliente> listarCliente(@PathVariable Integer page){
        return cs.listarClientesPage(PageRequest.of(page, 4));
    }


    @GetMapping("/buscarid/{id}")
    public ResponseEntity<?> buscarClienteId(@PathVariable Long id){
        Cliente cliente = null;
        Map<String,Object> response = new HashMap<>();
        try {
            cliente = cs.buscarClientePorId(id);
        }catch (DataAccessException e){
            response.put("mensaje","Error al consultar la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(cliente == null){
            response.put("mensaje","El cliente ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<Cliente>(cliente,HttpStatus.OK);
        }

    }


    @PostMapping("/crear")
    public ResponseEntity<?> crearCliente(@RequestBody Cliente cliente){
        Cliente clienteNuevo = null;
        Map<String,Object> response = new HashMap<>();
        try {
            clienteNuevo = cs.crearCliente(cliente);

        }catch (DataAccessException e){
            response.put("mensaje","Error al ingresar cliente a la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","El cliente ha sido creado con exito");
        response.put("cliente",clienteNuevo);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }
   /* @PutMapping("/clientes/{id}")
    public void modificarCliente(@PathVariable Long id, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String email){
        cs.modificarCliente(id,nombre,apellido,email);
    }*/


    @PutMapping("/modificar/{id}")
    public ResponseEntity<?> modificarCliente(@RequestBody Cliente cliente, @PathVariable Long id){
        Cliente clienteActual = cs.buscarClientePorId(id);
        Cliente clientemodificado = null;
        Map<String,Object> response = new HashMap<>();

        try {
            clienteActual.setNombre(cliente.getNombre());
            clienteActual.setApellido(cliente.getApellido());
            clienteActual.setEmail(cliente.getEmail());
            clienteActual.setCreateAt(cliente.getCreateAt());
            clientemodificado = cs.crearCliente(clienteActual);
        }catch (DataAccessException e){
            response.put("mensaje","Error al modificar el cliente en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(clienteActual == null){
            response.put("mensaje","El cliente ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
        }



        response.put("mensaje","El cliente se modifico con exito");
        response.put("cliente",clientemodificado);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarCliente(@PathVariable Long id){
        Map<String,Object> response = new HashMap<>();


        try {
            cs.eliminarCliente(id);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al eliminar cliente de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","El cliente se ha eliminado con exito");
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

    }


}
