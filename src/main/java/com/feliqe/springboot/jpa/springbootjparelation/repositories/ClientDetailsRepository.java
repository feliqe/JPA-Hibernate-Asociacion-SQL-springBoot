package com.feliqe.springboot.jpa.springbootjparelation.repositories;

import org.springframework.data.repository.CrudRepository;

import com.feliqe.springboot.jpa.springbootjparelation.entities.ClientDetails;

public interface ClientDetailsRepository extends CrudRepository<ClientDetails, Long> {

}
