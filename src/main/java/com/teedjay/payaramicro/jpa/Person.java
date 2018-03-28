package com.teedjay.payaramicro.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "findAllPersons", query = "SELECT p FROM Person p")
public class Person {

    @Id
    @Column
    public long id;

    @Column
    public String name;

}
