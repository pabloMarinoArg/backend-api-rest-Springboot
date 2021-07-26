package com.cliente.backend.apirest.backend.api.rest.repository;

import com.cliente.backend.apirest.backend.api.rest.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long> {

    @Modifying
    @Query("UPDATE Cliente c SET c.nombre = :nombre, c.apellido = :apellido, c.email = :email WHERE c.id = :id")
    void modificar(@Param("id") Long id, @Param("nombre") String nombre,@Param("apellido") String apellido,@Param("email") String email);


}
