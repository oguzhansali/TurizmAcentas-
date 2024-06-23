package dao;

import core.Db;
import entity.HotelFacilityFeature;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HotelFacilityFeatureDAO {
    private final Connection con;

    public HotelFacilityFeatureDAO(){
        this.con = Db.getInstance();

    }
    public ArrayList<HotelFacilityFeature> findAll(){
        ArrayList<HotelFacilityFeature> hotelFacilityFeaturesList = new ArrayList<>();
        String sql = "SELECT * FROM public.hotel_facility_feature ORDER BY facility_id ASC";
        try {
            ResultSet rs = this.con.createStatement().executeQuery(sql);
            while (rs.next()){
                hotelFacilityFeaturesList.add(this.match(rs));
            }


        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return hotelFacilityFeaturesList;
    }
    public boolean save (HotelFacilityFeature hotelFacilityFeature){
        String query = "INSERT INTO public.hotel_facility_feature (facility_type,otel_id) VALUES (?,?)";
        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setString(1, hotelFacilityFeature.getFacilityType());
            pr.setInt(2,hotelFacilityFeature.getOtelId());

            return pr.executeUpdate() !=-1;
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return true;
    }
    public boolean update(HotelFacilityFeature hotelFacilityFeature){
        String query = "UPDATE public.hotel_facility_feature SET facility_type= ? WHERE facility_id= ?";
        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setString(1,hotelFacilityFeature.getFacilityType());
            pr.setInt(2,hotelFacilityFeature.getId());
            return pr.executeUpdate() !=-1;
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return true;
    }
    public boolean delete(int id){
        String query = "DELETE FROM public.hotel_facility_feature WHERE facility_id = ?";
        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setInt(1,id);
            return pr.executeUpdate() !=-1;
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return true;
    }
    public HotelFacilityFeature getById(int id){
        HotelFacilityFeature obj = null;
        String query ="SELECT * FROM public.hotel_facility_feature WHERE facility_id = ?";
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


    public HotelFacilityFeature match (ResultSet rs) throws SQLException{
        HotelFacilityFeature obj= new HotelFacilityFeature();
        obj.setId(rs.getInt("facility_id"));
        obj.setFacilityType(rs.getString("facility_type"));
        return obj;
    }

}
