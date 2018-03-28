package com.teedjay.payaramicro.database;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Path("database")
public class DefaultDatabaseResource {

    @Resource
    private DataSource defaultDataSource;

    @GET
    public String assertDefaultDataSourcePresent() {
        return (defaultDataSource == null) ? "No Default DataSource" : "Default DataSource is present";
    }

    @GET
    @Path("{id}")
    public String getNameOfPersonInDatabase(@PathParam("id") long id) throws SQLException {
        try (
            Connection connection = defaultDataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select name from person where id = " + id);
        ) {
            if (!resultSet.next()) return "Person with id " + id + " was not found in database";
            return resultSet.getString(1);
        }
    }

}
