package com.feliqe.springboot.jpa.springbootjparelation.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
//indicamos el nombre de la tabla en la base de datos
@Table(name = "clients")
public class Client {

    //generamos el campo de id e indicamos que es primary key y que es auto incrementable
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastname;

    //muchas a uno eje: un cliente a muchas direcciones
    // CascadeType.ALL - es para indicar que se apliquen todos los metodos de SCRUM
    // orphanRemoval - es para los cmapos no queden en null y se borren
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    //relaicon con el modelo de address
    //como estan dentro del mismo package no es necesario inidcar la ruta
    //se creara un tabla intermedia con la relaccion de los id
    //indicamos la llave foreana del id del cliente se muestre en la tabla de address
    // @JoinColumn(name = "client_id")

    //para crear una tabla con los id de la la relacion FOREINGKEY
    //1er se crear el nombre de la relacion
    //2do se identifican las llaves de cada tabla
    //3ro se identifica la llave del FOREINGKEY
    @JoinTable(
        name = "tbl_clientes_to_direcciones",
        joinColumns = @JoinColumn(name = "id_cliente"),
        inverseJoinColumns = @JoinColumn(name = "id_direcciones"),
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_direcciones"}))
    private Set<Address> addresses;

    //relacion inverda birideccional
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "client")
    private Set<Invoice> invoices;

    //clase padre
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "client")
    private ClientDetails clientDetails;

    public Client() {
        //indicamos la inicializacion de la LIST
        addresses = new HashSet<>();
        invoices = new HashSet<>();
    }

    public Client(String name, String lastname) {
        //this() - indicamos que inicialize con el list y despues con las siguientes metodos
        this();
        this.name = name;
        this.lastname = lastname;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }

    //metodo de crear para invoices y client
    //para encadenar se definie como Client y se retorna el campo this
    public Client addInvoices(Invoice invoice) {
        invoices.add(invoice);
        invoice.setClient(this);
        return this;
    }

    public void removeInvoice(Invoice invoice) {
        this.getInvoices().remove(invoice);
        invoice.setClient(null);
    }

    public ClientDetails getClientDetails() {
        return clientDetails;
    }

    public void setClientDetails(ClientDetails clientDetails) {
        this.clientDetails = clientDetails;
        //creamos la relacion inversa
        clientDetails.setClient(this);
    }

    //relaccion inversa de eliminar
    public void removeClientDetails(ClientDetails clientDetails) {
        clientDetails.setClient(null);
        this.clientDetails = null;
    }

    @Override
    public String toString() {
        return "{id=" + id +
                ", name=" + name +
                ", lastname=" + lastname +
                ", invoices=" + invoices +
                ", addresses=" + addresses +
                ", clientDetails=" + clientDetails +
        "}";
    }
}
