package com.feliqe.springboot.jpa.springbootjparelation.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.feliqe.springboot.jpa.springbootjparelation.entities.Client;

//para poder relaizar las acciones de CRUD de SQL
public interface ClientRepository extends CrudRepository<Client, Long> {

    @Query("select c from Client c left join fetch c.addresses where c.id = ?1")
    Optional<Client> findOneWithAddress(Long id);

    @Query("select c from Client c left join fetch c.invoices where c.id = ?1")
    Optional<Client> findOneWithInvoices(Long id);

    //busca toda la informacion del ambas tablas
    @Query("select c from Client c left join fetch c.invoices left join fetch c.addresses left join fetch c.clientDetails where c.id = ?1")
    Optional<Client> findOne(Long id);
}
