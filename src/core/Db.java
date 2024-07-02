package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {
    private static Db instance = null;
    private Connection connection = null;
    private final String DB_URL = "jdbc:postgresql://localhost:5432/turizmacente";// Veritabanı URL'si
    private final String DB_USERNAME = "postgres"; // Veritabanı kullanıcı adı
    private final String DB_PASS = "40427034004";// Veritabanı şifresi

    private Db() {
        try {
            this.connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASS);// Veritabanı bağlantısı oluşturuluyor
        } catch (SQLException e) {
            System.out.println(e.getMessage());// Bağlantı hatası durumunda hata mesajı yazdırılıyor
        }
    }

    // Bağlantıyı geri döndüren metod
    public Connection getConnection() {
        return connection;
    }

    // Singleton deseni ile veritabanı bağlantısını yöneten metod
    public static Connection getInstance() {
        try {
            if (instance == null || instance.getConnection().isClosed()) {
                instance = new Db();
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return instance.getConnection();
    }
}
