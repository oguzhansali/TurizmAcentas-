package dao;

import core.Db;
import entity.Hotel;

import java.sql.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        Hotel newHotel = new Hotel();
        newHotel.setId(rs.getInt("hotel_id"));
        newHotel.setName(rs.getString("hotel_name"));
        newHotel.setAdress(rs.getString("hotel_adress"));
        newHotel.setMail(rs.getString("hotel_mail"));
        newHotel.setMpno(rs.getString("hotel_mpno"));
        newHotel.setStar(rs.getString("hotel_star"));
        newHotel.setHigh_season_strt_date(LocalDate.parse(rs.getString("hotel_high_season_strt")));
        newHotel.setHigh_season_fnsh_date(LocalDate.parse(rs.getString("hotel_high_season_fnsh")));
        newHotel.setLow_season_strt_date(LocalDate.parse(rs.getString("hotel_low_season_strt")));
        newHotel.setLow_season_fnsh_date(LocalDate.parse(rs.getString("hotel_low_season_fnsh")));

        newHotel.setHostelTypes(Hotel.HostelType.valueOf(rs.getString("hotel_hostel_type")));
        newHotel.setFacilityFeatures(Hotel.FacilityFeature.valueOf(rs.getString("hotel_facility_type")));




        /*String hostelTypeStr = rs.getString("hotel_hostel_type");
        String[] strHostelType = hostelTypeStr.split(",");

        List<Hotel.HostelType> hostelTypeList = new ArrayList<>();
        for (String type : strHostelType) {
            *//*newHotel.setHostelTypes();*//*

            //hostelTypeList.add((Hotel.HostelType.valueOf(type.trim())));

            *//*try {
                Hotel.HostelType hostelType = Hotel.HostelType.valueOf(type.trim().toUpperCase());
                hostelTypeList.add(hostelType);
            } catch (IllegalArgumentException e) {
                System.out.println("Geçersiz hostel tipi: " + type);
            }*//*

        }
        System.out.println(hostelTypeList);

        // Hostel tiplerini ArrayList olarak alabilmek için
        *//*String[] hostelTypeStrings = rs.getString("hotel_hostel_type").split(",");
        for (String type : hostelTypeStrings) {
            if (!type.isEmpty()) {
                newHotel.addHostelType(Hotel.HostelType.valueOf(type.trim()));
            }
        }

        // Facility feature'larını ArrayList olarak alabilmek için
        String[] facilityFeatureStrings = rs.getString("hotel_facility_type").split(",");
        for (String feature : facilityFeatureStrings) {
            if (!feature.isEmpty()) {
                newHotel.addFacilityFeature(Hotel.FacilityFeature.valueOf(feature.trim()));
            }
        }*/



        return newHotel;
    }



    public boolean save (Hotel hotel){
        String query = "INSERT INTO public.hotel " +
                "(hotel_name, hotel_adress, hotel_mail, hotel_mpno, hotel_star, hotel_high_season_strt, hotel_high_season_fnsh, hotel_hostel_type, hotel_facility_type, hotel_low_season_strt, hotel_low_season_fnsh) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
        try {
            PreparedStatement pr = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pr.setString(1, hotel.getName());
            pr.setString(2, hotel.getAdress());
            pr.setString(3, hotel.getMail());
            pr.setString(4, hotel.getMpno());
            pr.setString(5, hotel.getStar().toString());

            /*pr.setDate(6, Date.valueOf(hotel.getHigh_season_strt_date()));
            pr.setDate(7, Date.valueOf(hotel.getHigh_season_fnsh_date()));*/

            // Tarihlerin null olup olmadığını kontrol et
            if (hotel.getHigh_season_strt_date() != null) {
                pr.setDate(6, Date.valueOf(hotel.getHigh_season_strt_date()));
            } else {
                pr.setNull(6, Types.DATE);
            }

            if (hotel.getHigh_season_fnsh_date() != null) {
                pr.setDate(7, Date.valueOf(hotel.getHigh_season_fnsh_date()));
            } else {
                pr.setNull(7, Types.DATE);
            }

            pr.setString(8,hotel.getHostelType().toString());
            pr.setString(9,hotel.getFacilityFeature().toString());

           /* pr.setDate(10, Date.valueOf(hotel.getLow_season_strt_date()));
            pr.setDate(11, Date.valueOf(hotel.getLow_season_fnsh_date()));*/


            if (hotel.getLow_season_strt_date() != null) {
                pr.setDate(10, Date.valueOf(hotel.getLow_season_strt_date()));
            } else {
                pr.setNull(10, Types.DATE);
            }

            if (hotel.getLow_season_fnsh_date() != null) {
                pr.setDate(11, Date.valueOf(hotel.getLow_season_fnsh_date()));
            } else {
                pr.setNull(11, Types.DATE);
            }


            /*// Hostel tiplerini virgülle ayırarak String olarak ekliyoruz
            String hostelTypeString = String.join(",", hotel.getHostelType().toString());
            pr.setString(8, hostelTypeString);

            // Facility feature'larını virgülle ayırarak String olarak ekliyoruz
            String facilityFeatureString = String.join(",", hotel.getFacilityFeature().toString());
            pr.setString(9, facilityFeatureString);*/






            int rowCount = pr.executeUpdate();
            if (rowCount > 0) {
                ResultSet generatedKeys = pr.getGeneratedKeys();
                if (generatedKeys.next()) {
                    hotel.setId(generatedKeys.getInt(1));
                }
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;

    }

    public boolean update(Hotel hotel){
        String query = "UPDATE public.hotel SET " +
                "hotel_name = ?, " +
                "hotel_adress = ?, " +
                "hotel_mail = ?, " +
                "hotel_mpno = ?, " +
                "hotel_star = ?, " +
                "hotel_high_season_strt = ?, " +
                "hotel_high_season_fnsh = ?, " +
                "hotel_hostel_type = ?, " +
                "hotel_facility_type = ?, " +
                "hotel_low_season_strt= ?,"+
                "hotel_low_season_fnsh= ?"+
                "WHERE hotel_id = ?";
        try {
            PreparedStatement pr = con.prepareStatement(query);
            pr.setString(1, hotel.getName());
            pr.setString(2, hotel.getAdress());
            pr.setString(3, hotel.getMail());
            pr.setString(4, hotel.getMpno());
            pr.setString(5, hotel.getStar().toString());
            pr.setDate(6, Date.valueOf(hotel.getHigh_season_strt_date()));
            pr.setDate(7, Date.valueOf(hotel.getHigh_season_fnsh_date()));

            pr.setString(8,hotel.getHostelType().toString());
            pr.setString(9,hotel.getFacilityFeature().toString());

            pr.setDate(10, Date.valueOf(hotel.getLow_season_strt_date()));
            pr.setDate(11, Date.valueOf(hotel.getLow_season_fnsh_date()));

            /*
            // Hostel tiplerini virgülle ayırarak String olarak ekliyoruz
            String hostelTypeString = String.join(",", hotel.getHostelTypeNames());
            pr.setString(8, hostelTypeString);

            // Facility feature'larını virgülle ayırarak String olarak ekliyoruz
            String facilityFeatureString = String.join(",",hotel.getFacilityFeatureNames());
            pr.setString(9, facilityFeatureString);
            */

            pr.setInt(10, hotel.getId());

            return pr.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
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
        return true;
    }
    public List<String> getAllHotelNames() throws SQLException {
        List<String> hotelNames = new ArrayList<>();
        String query = "SELECT hotel_name FROM public.hotel"; // hotel tablosundan otel adlarını alacak sorgu

        try (PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                hotelNames.add(rs.getString("hotel_name"));
            }
        }

        return hotelNames;
    }
}
