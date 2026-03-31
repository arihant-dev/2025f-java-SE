package fr.epita.biostat.services;

import fr.epita.biostat.datamodel.BioStatEntry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class BioStatEntryDataService {


    public BioStatEntryDataService() throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                        CREATE TABLE BIOSTATS (
                            id int,
                            name varchar(255),
                            sex char,
                            age int
                        )                        
                    """);
            preparedStatement.execute();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            throw sqle;
        }

    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:mem:test;DB_ClOSE_DELAY=-1");
    }

    public List<BioStatEntry> search(BioStatEntry qbe) throws SQLException {
        return null;
    }

    public void create(BioStatEntry entry) throws SQLException {

        try (Connection connection = getConnection()) {
            String insertQuery = """
                    
                       INSERT INTO BIOSTATS(id,name, sex, age) values (?, ?, ?, ?)
                    
                    """;
            PreparedStatement insertStatement = connection.prepareStatement(
                    insertQuery);
            //TODO set parameters
            insertStatement.setInt(1, entry.getId());
            insertStatement.setString(2, entry.getName());
            insertStatement.setString(3, entry.getSex());
            insertStatement.setInt(4, entry.getAge());
            insertStatement.execute();
        }

    }

    public void update(BioStatEntry entry) throws SQLException {
        try (Connection connection = getConnection()) {
            String updateQuery = """
                    
                   UPDATE BIOSTATS SET age=?, name=?, sex=? WHERE id=?
                    
                    """;
            PreparedStatement insertStatement = connection.prepareStatement(
                    updateQuery);

            insertStatement.setInt(1, entry.getAge());
            insertStatement.setString(2, entry.getName());
            insertStatement.setString(3, entry.getSex());
            insertStatement.setInt(4, entry.getId());
            insertStatement.execute();
        }
    }

    public void delete(BioStatEntry entry) {

    }


}
