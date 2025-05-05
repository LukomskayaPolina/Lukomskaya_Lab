package storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLiteStorage {

    private Connection connection;

    // Подключение к базе данных SQLite
    public SQLiteStorage(String dbPath) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:D:\\3 курс\\Средства и технологии анализа\\Lukomskaya_Lab\\Lab10\\messages.db");

        // Создаём таблицу, если её ещё нет
        String createTableSQL = "CREATE TABLE IF NOT EXISTS kafka_messages (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id TEXT, " +
                "action TEXT, " +
                "timestamp TEXT)";
        try (PreparedStatement stmt = connection.prepareStatement(createTableSQL)) {
            stmt.execute();
        }
    }

    // Метод для сохранения сообщений
    public void saveMessage(String userId, String action, String timestamp) {
        String sql = "INSERT INTO kafka_messages (user_id, action, timestamp) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userId);
            stmt.setString(2, action);
            stmt.setString(3, timestamp);
            stmt.executeUpdate();
            System.out.println("Сообщение успешно сохранено в SQLite: " + userId + ", " + action + ", " + timestamp);
        } catch (SQLException e) {
            System.err.println("Ошибка при сохранении сообщения: " + e.getMessage());
        }
    }

    // Метод для отображения всех сообщений из базы данных
    public void displayMessages() {
        String sql = "SELECT * FROM kafka_messages";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("Содержимое базы данных:");
            System.out.println("ID | User ID | Action     | Timestamp");
            System.out.println("-------------------------------------");
            while (rs.next()) {
                int id = rs.getInt("id");
                String userId = rs.getString("user_id");
                String action = rs.getString("action");
                String timestamp = rs.getString("timestamp");

                System.out.printf("%d | %s | %s | %s%n", id, userId, action, timestamp);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при отображении сообщений: " + e.getMessage());
        }
    }

    // Закрытие соединения с базой данных
    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при закрытии соединения с SQLite: " + e.getMessage());
        }
    }
}
