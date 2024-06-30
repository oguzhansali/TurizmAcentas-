package dao;

import core.Db;
import entity.Book;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class BookDao {
    private Connection con;
    private final RoomDao roomDao;

    public BookDao(){
        this.con= Db.getInstance();
        this.roomDao=new RoomDao();
    }
    public ArrayList<Book> findAll(){
        return this.selectByQuery("SELECT * FROM public.book ORDER BY book_id ASC");
    }
    public ArrayList<Book> selectByQuery(String query){
        //System.out.println(query);
        ArrayList<Book> books = new ArrayList<>();
        try {
            ResultSet rs = this.con.createStatement().executeQuery(query);
            while (rs.next()){
                books.add(this.match(rs));
            }

        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return books;
    }
    public boolean save(Book book){
        String query = "INSERT INTO public.book "+
                "("+
                "room_id,"+
                "book_costumer_first_name,"+
                "book_costumer_last_name,"+
                "book_costumer_identity_num,"+
                "book_mpno,"+
                "book_booking_strt_date,"+
                "book_booking_fnsh_date,"+
                "book_daily_price,"+
                "book_total_price"+
                ")"+
                " VALUES (?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement pr = con.prepareStatement(query);
            pr.setInt(1,book.getRoom_id());
            pr.setString(2, book.getCostumerName());
            pr.setString(3, book.getCostumerLastname());
            pr.setString(4,book.getIdentityNum());
            pr.setString(5,book.getMpno());
            pr.setDate(6, Date.valueOf(book.getStrt_date()));
            pr.setDate(7,Date.valueOf(book.getFnsh_date()));
            pr.setInt(8,book.getDailyPrice());
            pr.setInt(9,book.getTotalPrice());
            return pr.executeUpdate() !=-1;
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return true;
    }


    public Book match(ResultSet rs) throws SQLException{
        Book book = new Book();
        book.setId(rs.getInt("book_id"));
        book.setRoom_id(rs.getInt("room_id"));
        book.setCostumerName(rs.getString("book_costumer_first_name"));
        book.setCostumerLastname(rs.getString("book_costumer_last_name"));
        book.setIdentityNum(rs.getString("book_costumer_identity_num"));
        book.setMpno(rs.getString("book_mpno"));
        book.setStrt_date(LocalDate.parse(rs.getString("book_booking_strt_date")));
        book.setFnsh_date(LocalDate.parse(rs.getString("book_booking_fnsh_date")));
        book.setDailyPrice(rs.getInt("book_daily_price"));
        book.setTotalPrice(rs.getInt("book_total_price"));
        return book;
    }


}
