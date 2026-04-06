package fr.epita.tests.db;

import java.sql.*;

public class DBTest {

    public static void main(String[] args) throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:h2:mem:test");
        System.out.println(connection.getSchema());

        PreparedStatement preparedStatement = connection.prepareStatement("""
                    CREATE TABLE BIOSTATS (
                        name varchar(255),
                        sex char,
                        age int
                    )                        
                """);
        preparedStatement.execute();

        String insertQuery = """
                INSERT INTO BIOSTATS(name, sex, age) values ('thomas', 'M', 39)
                """;
        PreparedStatement insertStatement = connection.prepareStatement(insertQuery);

        insertStatement.execute();


        String selectQuery = """
                SELECT name,sex,age FROM BIOSTATS
                """;
        PreparedStatement selectStatement = connection.prepareStatement(selectQuery);

        ResultSet resultSet = selectStatement.executeQuery();

        while(resultSet.next()){
            System.out.println(resultSet.getString("name"));
        }

        String updateQuery =
                """ 
                UPDATE BIOSTATS SET age=? WHERE name=?
                """;

        PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
        updateStatement.setInt(1, 40);
        updateStatement.setString(2, "thomas");
        updateStatement.execute();

        resultSet = selectStatement.executeQuery();
        while(resultSet.next()){
            System.out.println(resultSet.getString("name"));
            System.out.println(resultSet.getInt("age"));
        }



        connection.close();
    }
}
