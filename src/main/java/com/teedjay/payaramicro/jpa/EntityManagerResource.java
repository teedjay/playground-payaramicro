package com.teedjay.payaramicro.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("jpa")
public class EntityManagerResource {

    @PersistenceContext(unitName = "MyDefaultPersistenceUnit")
    private EntityManager entityManager;

    @GET
    public List<Person> listAllPersons() {
        return entityManager.createNamedQuery("findAllPersons", Person.class).getResultList();
    }

    @GET
    @Path("{id}")
    public Person getPersonById(@PathParam("id") long id) {
        return entityManager.find(Person.class, id);
    }

    @POST
    @Transactional
    public Response addNewPerson(Person person) {
        entityManager.persist(person);
        return Response.ok(person).build();
    }

}
