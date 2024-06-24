package dao;

import core.Db;
import entity.HotelFacilityFeature;
import entity.HotelHostelType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HotelHostelTypeDAO {
    private Connection con;

    public HotelHostelTypeDAO(){
        this.con= Db.getInstance();
    }
    public ArrayList<HotelHostelType> findAll(){
        ArrayList<HotelHostelType> hotelHostelTypesList = new ArrayList<>();
        String sql = "SELECT * FROM public.hotel_hostel_type ORDER BY hostel_id ASC";
        try {
            ResultSet rs = this.con.createStatement().executeQuery(sql);
            while (rs.next()){
                hotelHostelTypesList.add(this.match(rs));
            }


        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return hotelHostelTypesList;
    }
    public boolean save (HotelHostelType hotelHostelType){
        String query = "INSERT INTO public.hotel_hostel_type (hostel_type,hotel_id) VALUES (?,?)";
        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setString(1, hotelHostelType.getHostelType());
            pr.setInt(2,hotelHostelType.getOtelId());

            return pr.executeUpdate() !=-1;
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return true;
    }
    public boolean update(HotelHostelType hotelHostelType){
        String query = "UPDATE public.hotel_hostel_type SET hostel_type= ? WHERE hostel_id= ?";
        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setString(1,hotelHostelType.getHostelType());
            pr.setInt(2,hotelHostelType.getId());
            return pr.executeUpdate() !=0;
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return true;
    }
    public boolean delete(int id){
        String query = "DELETE FROM public.hotel_hostel_type WHERE hostel_id = ?";
        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setInt(1,id);
            return pr.executeUpdate() !=-1;
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return true;
    }
    public HotelHostelType getById(int id){
        HotelHostelType obj = null;
        String query ="SELECT * FROM public.hotel_hostel_type WHERE hostel_id = ?";
        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj=this.match(rs);
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return obj;
    }


    public HotelHostelType match (ResultSet rs) throws SQLException {
        HotelHostelType obj= new HotelHostelType();
        obj.setId(rs.getInt("hostel_id"));
        obj.setHostelType(rs.getString("hostel_type"));
        return obj;
    }
}
