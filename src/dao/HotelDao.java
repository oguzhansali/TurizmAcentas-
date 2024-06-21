package dao;

import core.Db;
import entity.Hotel;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class HotelDao {
    private Connection con;

    public HotelDao(){
        this.con= Db.getInstance();
    }

    public ArrayList<Hotel> findAll(){
        ArrayList<Hotel> hotelList = new ArrayList<>();
        String sql  = "SELECT * FROM public.hotel";

        try {
            ResultSet rs = this.con.createStatement().executeQuery(sql);
            while(rs.next()){
                hotelList.add(this.match(rs));

            }
        }catch (SQLException e){
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return hotelList;
    }
    public Hotel getById(int id){
        Hotel obj = null;
        String query = "SELECT * FROM public.hotel WHERE hotel_id = ? ";
        try {
            PreparedStatement pr = con.prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) obj = this.match(rs);
        }catch (SQLException | ParseException throwables){
            throwables.printStackTrace();
        }
        return obj;
    }

    //Kullanıcıdan alınan veriler Db ile eşleştirildi.
    public Hotel match (ResultSet rs) throws SQLException, ParseException {
        Hotel obj = new Hotel();
        obj.setId(rs.getInt("hotel_id"));
        obj.setName(rs.getString("hotel_name"));
        obj.setAdress(rs.getString("hotel_adress"));
        obj.setMail(rs.getString("hotel_mail"));
        obj.setMpno(rs.getString("hotel_mpno"));
        obj.setStar(rs.getString("hotel_star"));
        obj.setStrt_date(String.valueOf(rs.getDate("hotel_strt_date")));
        obj.setFnsh_date(String.valueOf(rs.getDate("hotel_fnsh_date")));
        return obj;
    }

    public boolean save (Hotel hotel){
        String query = "INSERT INTO public.hotel"+
                "( "+
                "hotel_name,"+
                "hotel_adress,"+
                "hotel_mail,"+
                "hotel_mpno,"+
                "hotel_star,"+
                "hotel_strt_date,"+
                "hotel_fnsh_date"+
                ")"+
                "VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement pr = con.prepareStatement(query);
            pr.setString(1,hotel.getName());
            pr.setString(2,hotel.getAdress());
            pr.setString(3,hotel.getMail());
            pr.setString(4,hotel.getMpno());
            pr.setString(5,hotel.getStar());
            pr.setDate(6,hotel.getStrt_date());
            pr.setDate(7,hotel.getFnsh_date());
            int rowCount=  pr.executeUpdate();
            if (rowCount>0){
                ResultSet generatedKeys = pr.getGeneratedKeys();
                if (generatedKeys.next()){
                    hotel.setId(generatedKeys.getInt(1));
                }
                return true;
            }

        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean update(Hotel hotel){
        String query = "UPDATE public.hotel SET "+
                "hotel_name = ? ,"+
                "hotel_adress= ? ,"+
                "hotel_mail = ? ,"+
                "hotel_mpno = ? ,"+
                "hotel_star = ? ,"+
                "hotel_strt_date = ? ,"+
                "hotel_fnsh_date = ? "+
                " WHERE  hotel_id = ? ";
        try {
            PreparedStatement pr = con.prepareStatement(query);
            pr.setString(1,hotel.getName());
            pr.setString(2,hotel.getAdress());
            pr.setString(3,hotel.getMail());
            pr.setString(4,hotel.getMpno());
            pr.setString(5,hotel.getStar());
            pr.setDate(6,hotel.getStrt_date());
            pr.setDate(7,hotel.getFnsh_date());
            pr.setInt(8,hotel.getId());
            return  pr.executeUpdate() >0;
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return  false;
    }
    public  boolean delete (int hotel){
        String query = "DELETE FROM public.hotel WHERE hotel_id = ?";
        try {
            PreparedStatement pr = con.prepareStatement(query);
            pr.setInt(1, hotel);
            return pr.executeUpdate() > 0;
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return false;
    }
}
