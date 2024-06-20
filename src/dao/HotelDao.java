package dao;

import core.Db;
import entity.Hotel;

import java.net.http.HttpClient;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HotelDao {
    private final Connection con;


    public HotelDao() {
        this.con = Db.getInstance();
    }

    public ArrayList<Hotel> findAll(){
        ArrayList<Hotel> hotelList = new ArrayList<>();
        String sql = "SELECT * FROM public.otel ORDER BY otel_id ASC";
        try {
            ResultSet rs = this.con.createStatement().executeQuery(sql);
            while (rs.next()){
                hotelList.add(this.match(rs));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return hotelList;
    }

    public  Hotel match (ResultSet rs) throws SQLException{
        Hotel obj = new Hotel();
        obj.setId(rs.getInt("otel_id"));
        obj.setName(rs.getString("otel_name"));
        return obj;
    }
}
