package dao;

import core.Db;
import entity.Book;
import entity.Room;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class BookDao {
    private Connection con;// Veritabanı bağlantısı
    private final RoomDao roomDao;// Oda DAO sınıfı bağlantısı

    public BookDao() {
        this.con = Db.getInstance(); // Veritabanı bağlantısını Db sınıfından al
        this.roomDao = new RoomDao(); // Oda DAO sınıfını oluştur
    }

    // Tüm rezervasyonları getirir
    public ArrayList<Book> findAll() {
        return this.selectByQuery("SELECT * FROM public.book ORDER BY book_id ASC");
    }


    // Verilen sorguya göre rezervasyonları getirir
    public ArrayList<Book> selectByQuery(String query) {
        ArrayList<Book> books = new ArrayList<>();
        try {
            ResultSet rs = this.con.createStatement().executeQuery(query);
            while (rs.next()) {
                books.add(this.match(rs));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return books;
    }

    // Yeni rezervasyon ekler
    public boolean save(Book book) {
        String query = "INSERT INTO public.book " +
                "(" +
                "room_id," +
                "book_costumer_first_name," +
                "book_costumer_last_name," +
                "book_costumer_identity_num," +
                "book_mpno," +
                "book_booking_strt_date," +
                "book_booking_fnsh_date" +
                ")" +
                " VALUES (?,?,?,?,?,?,?)";

        try {
            PreparedStatement pr = con.prepareStatement(query);
            pr.setInt(1, book.getRoom_id());
            pr.setString(2, book.getCostumerName());
            pr.setString(3, book.getCostumerLastname());
            pr.setString(4, book.getIdentityNum());
            pr.setString(5, book.getMpno());
            pr.setDate(6, Date.valueOf(book.getStrt_date()));
            pr.setDate(7, Date.valueOf(book.getFnsh_date()));
            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    // Rezervasyonu günceller
    public boolean update(Book book) {
        String query = "UPDATE public.book SET " +
                "book_costumer_first_name = ? ," +
                "book_costumer_last_name = ? ," +
                "book_costumer_identity_num = ? ," +
                "book_mpno = ? ," +
                "book_booking_strt_date = ? ," +
                "book_booking_fnsh_date = ? " +
                "WHERE book_id = ?";
        try {
            PreparedStatement pr = con.prepareStatement(query);
            //pr.setInt(1,book.getRoom_id());
            pr.setString(1, book.getCostumerName());
            pr.setString(2, book.getCostumerLastname());
            pr.setString(3, book.getIdentityNum());
            pr.setString(4, book.getMpno());
            pr.setDate(5, Date.valueOf(book.getStrt_date()));
            pr.setDate(6, Date.valueOf(book.getFnsh_date()));
            pr.setInt(7, book.getId());
            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    // Rezervasyonu siler
    public boolean delete(int bookId) {
        String query = "DELETE FROM public.book WHERE book_id = ? ";
        try {
            PreparedStatement pr = con.prepareStatement(query);
            pr.setInt(1, bookId);
            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    // ResultSet'teki verileri Book nesnesine eşleştirir
    public Book match(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("book_id"));
        book.setRoom_id(rs.getInt("room_id"));
        book.setCostumerName(rs.getString("book_costumer_first_name"));
        book.setCostumerLastname(rs.getString("book_costumer_last_name"));
        book.setIdentityNum(rs.getString("book_costumer_identity_num"));
        book.setMpno(rs.getString("book_mpno"));
        book.setStrt_date(LocalDate.parse(rs.getString("book_booking_strt_date")));
        book.setFnsh_date(LocalDate.parse(rs.getString("book_booking_fnsh_date")));
        return book;
    }

    // Oda stokunu günceller (azaltır)
    public boolean updateRoomStock(Room room) {
        String query = "UPDATE public.room SET " +
                "room_stock = TO_CHAR(TO_NUMBER(room_stock,'99999')-1,'99999') " +
                "WHERE room_id = ?";
        try {
            PreparedStatement pr = con.prepareStatement(query);
            pr.setInt(1, room.getId());
            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    // Oda stokunu artırır
    public boolean increaseRoomStock(int room_id) {
        String query = "UPDATE public.room SET " +
                "room_stock = TO_CHAR(TO_NUMBER(room_stock,'99999')+1,'99999') " +
                "WHERE room_id = ?";
        try {
            PreparedStatement pr = con.prepareStatement(query);
            pr.setInt(1, room_id);
            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    // ID'ye göre rezervasyon getirir
    public Book getById(int id) {
        Book obj = null;
        String query = "SELECT * FROM public.book WHERE book_id = ?";
        try {
            PreparedStatement pr = con.prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = this.match(rs);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }
}
