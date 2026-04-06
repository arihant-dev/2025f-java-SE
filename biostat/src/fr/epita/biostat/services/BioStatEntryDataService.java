package fr.epita.biostat.services;

import fr.epita.biostat.datamodel.BioStatEntry;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BioStatEntryDataService {

    // TODO: make this class a singleton
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

    // Create a configuration service - a singleton service
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:mem:test;DB_ClOSE_DELAY=-1");
    }

    public List<BioStatEntry> search(BioStatEntry qbe) throws SQLException {
        List<BioStatEntry> results = new ArrayList<>();
        try(Connection connection = getConnection()) {
            String selectQuery = """
                    SELECT id,name,sex,age FROM BIOSTATS
                    WHERE 
                        ((? is null) or (name LIKE ?))
                    or 
                        (? is null or sex = ?)
                    or 
                        (? is null or age = ?)
                    """;
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);

            selectStatement.setString(1, qbe.getName());
            selectStatement.setString(2, qbe.getName() == null ? null: "%" +qbe.getName() + "%");
            selectStatement.setString(3, qbe.getSex());
            selectStatement.setString(4, qbe.getSex());
            selectStatement.setObject(5, qbe.getAge());
            selectStatement.setObject(6, qbe.getAge());

            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String sex = resultSet.getString("sex");
                int age = resultSet.getInt("age");
                BioStatEntry entry = new BioStatEntry(name, sex, age, null, null);
                entry.setId(resultSet.getInt("id"));
                results.add(entry);
            }
        }
        return results;
    }

    public void create(BioStatEntry entry) throws SQLException {

        try (Connection connection = getConnection()) {
            String insertQuery = """
                    
                       INSERT INTO BIOSTATS(id,name, sex, age) values (?, ?, ?, ?)
                    
                    """;
            PreparedStatement insertStatement = connection.prepareStatement(
                    insertQuery);
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
        try (Connection connection = getConnection()) {
            String deleteQuery = """
                    
                   DELETE FROM BIOSTATS WHERE id=?
                    
                    """;
            PreparedStatement insertStatement = connection.prepareStatement(
                    deleteQuery);

            insertStatement.setInt(1, entry.getId());
            insertStatement.execute();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }


}
