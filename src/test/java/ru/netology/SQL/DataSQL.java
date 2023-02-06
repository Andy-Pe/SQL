package ru.netology.SQL;

import lombok.SneakyThrows;
import lombok.val;
import ru.netology.data.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSQL {
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass"
        );
    }

    @SneakyThrows
    public static void createUser(User.UserData user) {

        val dataSQL = "INSERT INTO users(id, login, password) VALUES (?, ?, ?);";

        try (
                val conn = connect();
                val dataStmt = conn.prepareStatement(dataSQL);
        ) {
            dataStmt.setString(1, user.getId());
            dataStmt.setString(2, user.getLogin());
            dataStmt.setString(3, user.getEncryptedPassword());
            dataStmt.executeUpdate();
        }
    }

    @SneakyThrows
    public static void cleanDefaultData() {
        val deleteCards = "DELETE FROM cards WHERE number = '5559 0000 0000 0002' OR number = '5559 0000 0000 0001';";
        val deleteUsers = "DELETE FROM users WHERE login = 'petya' OR login = 'vasya';";
        try (
                val conn = connect();
                val dataStmt = conn.createStatement();
        ) {
            dataStmt.executeUpdate(deleteCards);
            dataStmt.executeUpdate(deleteUsers);
        }
    }

    @SneakyThrows
    public static String getVerificationCode(String id) {
        val selectCode = "SELECT code FROM auth_codes WHERE user_id = '" + id + "';";
        Thread.sleep(500);

        try (
                val conn = connect();
                val dataStmt = conn.createStatement()
        ) {
            try (val rs = dataStmt.executeQuery(selectCode)) {
                if (rs.next()) {
                    return rs.getString(1);
                }
            }
        }
        return "Error";
    }

    @SneakyThrows
    public static String getUserStatus(String id) {
        val selectCode = "SELECT status FROM users WHERE id = '" + id + "';";
        Thread.sleep(500);

        try (
                val conn = connect();
                val dataStmt = conn.createStatement()
        ) {
            try (val rs = dataStmt.executeQuery(selectCode)) {
                if (rs.next()) {
                    return rs.getString(1);
                }
            }
        }
        return "Error";
    }
}
