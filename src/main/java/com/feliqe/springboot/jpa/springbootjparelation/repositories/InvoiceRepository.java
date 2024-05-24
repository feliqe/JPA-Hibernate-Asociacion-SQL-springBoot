package com.feliqe.springboot.jpa.springbootjparelation.repositories;

import org.springframework.data.repository.CrudRepository;

import com.feliqe.springboot.jpa.springbootjparelation.entities.Invoice;

//para poder relaizar las acciones de CRUD de SQL
public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

}
