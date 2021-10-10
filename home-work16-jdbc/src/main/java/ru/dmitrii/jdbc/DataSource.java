package ru.dmitrii.jdbc;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class DataSource {
    public static Connection connection() throws SQLException {
        final Connection connection = DriverManager.getConnection("jdbc:h2:file:~/test", "sa", "");
        connection.setAutoCommit(true);
        return connection;
    }

    /**
     * Создать базу по схеме
     */
    public static void createDb() {
        String sql;
        try {
            sql = FileUtils.readFileToString(new File(
                    DataSource.class.getResource("/data.sql").getFile()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (PreparedStatement statement = DataSource.connection()
                .prepareStatement(sql)) {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Находит результат вычисления в базе по id
     * @param id int
     * @return Long
     */
    public static Long findFibonacyId(int id) {
        try (PreparedStatement statement = DataSource.connection()
                .prepareStatement("select * from FIBONACHI p where p.id=?")) {
            statement.setInt(1, id);
            statement.execute();

            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                return resultSet.getLong("result");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return (long) -1;
    }

    /**
     * Вставить значение вычисления в базу
      * @param id int
     * @param result long
     */
    public static void createPerson(int id, long result) {
        try (PreparedStatement statement = DataSource.connection()
                .prepareStatement("insert into fibonachi (id, result) values (?, ?)")) {
            statement.setInt(1, id);
            statement.setLong(2, result);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
