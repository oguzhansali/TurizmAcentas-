package dao;

import core.Db;
import entity.Hotel;
import entity.HotelFacilityFeature;
import entity.User;

import java.sql.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

public class HotelDao {
    private Connection con;

    public HotelDao(){
        this.con= Db.getInstance();
    }

    private  HotelFacilityFeatureDAO hotelFacilityFeatureDAO=new HotelFacilityFeatureDAO();

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
        int id = rs.getInt("hotel_id");
        String name = rs.getString("hotel_name");
        String adress = rs.getString("hotel_adress");
        String mail = rs.getString("hotel_mail");
        String mpno = rs.getString("hotel_mpno");
        String hotelStar = rs.getString("hotel_star");
        LocalDate strt_date = LocalDate.parse(rs.getString("hotel_strt_date"));
        LocalDate fnsh_date = LocalDate.parse(rs.getString("hotel_fnsh_date"));

        // Hostel tiplerini ArrayList olarak alabilmek için
        ArrayList<Hotel.HostelType> hostelTypes = new ArrayList<>();
        String hostelTypeString = rs.getString("hotel_hostel_type");
        if (hostelTypeString != null && !hostelTypeString.isEmpty()) {
            String[] hostelTypeStrings = hostelTypeString.split(",");
            for (String type : hostelTypeStrings) {
                try {
                    Hotel.HostelType hostelType = Hotel.HostelType.valueOf(type.trim());
                    hostelTypes.add(hostelType);
                } catch (IllegalArgumentException e) {
                    System.err.println("Geçersiz hostel tipi: " + type);
                    e.printStackTrace();
                    // Alternatif olarak geçersiz bir tip varsa, varsayılan bir tip atayabilirsiniz
                    // Örneğin:
                    // hostelTypes.add(Hotel.HostelType.DEFAULT); // Veya başka bir değer
                }
            }
        }

        // Facility feature'larını ArrayList olarak alabilmek için
        ArrayList<Hotel.FacilityFeature> facilityFeatures = new ArrayList<>();
        String facilityFeatureString = rs.getString("hotel_facility_type");
        if (facilityFeatureString != null && !facilityFeatureString.isEmpty()) {
            String[] facilityFeatureStrings = facilityFeatureString.split(",");
            for (String feature : facilityFeatureStrings) {
                try {
                    Hotel.FacilityFeature facilityFeature = Hotel.FacilityFeature.valueOf(feature.trim());
                    facilityFeatures.add(facilityFeature);
                } catch (IllegalArgumentException e) {
                    System.err.println("Geçersiz tesis özelliği: " + feature);
                    e.printStackTrace();
                    // Alternatif olarak geçersiz bir özellik varsa, varsayılan bir özellik atayabilirsiniz
                    // Örneğin:
                    // facilityFeatures.add(Hotel.FacilityFeature.DEFAULT); // Veya başka bir değer
                }
            }
        }

        return new Hotel(id, name, adress, mail, mpno, Hotel.Star.valueOf(hotelStar), strt_date, fnsh_date, hostelTypes, facilityFeatures);

    }



    public boolean save (Hotel hotel){
        String query = "INSERT INTO public.hotel " +
                "(hotel_name, hotel_adress, hotel_mail, hotel_mpno, hotel_star, " +
                "hotel_strt_date, hotel_fnsh_date, hotel_hostel_type, hotel_facility_type) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pr = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pr.setString(1, hotel.getName());
            pr.setString(2, hotel.getAdress());
            pr.setString(3, hotel.getMail());
            pr.setString(4, hotel.getMpno());
            pr.setString(5, hotel.getStar().toString());
            pr.setDate(6, Date.valueOf(hotel.getStrt_date()));
            pr.setDate(7, Date.valueOf(hotel.getFnsh_date()));

            // Hostel tiplerini virgülle ayırarak String olarak ekliyoruz
            String hostelTypeString = String.join(",", hotel.getHostelType().stream().map(Enum::name).toArray(String[]::new));
            pr.setString(8, hostelTypeString);

            // Facility feature'larını virgülle ayırarak String olarak ekliyoruz
            String facilityFeatureString = String.join(",", hotel.getFacilityFeature().stream().map(Enum::name).toArray(String[]::new));
            pr.setString(9, facilityFeatureString);

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
        return false;

    }

    public boolean update(Hotel hotel){
        String query = "UPDATE public.hotel SET " +
                "hotel_name = ?, " +
                "hotel_adress = ?, " +
                "hotel_mail = ?, " +
                "hotel_mpno = ?, " +
                "hotel_star = ?, " +
                "hotel_strt_date = ?, " +
                "hotel_fnsh_date = ?, " +
                "hotel_hostel_type = ?, " +
                "hotel_facility_type = ? " +
                "WHERE hotel_id = ?";
        try {
            PreparedStatement pr = con.prepareStatement(query);
            pr.setString(1, hotel.getName());
            pr.setString(2, hotel.getAdress());
            pr.setString(3, hotel.getMail());
            pr.setString(4, hotel.getMpno());
            pr.setString(5, hotel.getStar().toString());
            pr.setDate(6, Date.valueOf(hotel.getStrt_date()));
            pr.setDate(7, Date.valueOf(hotel.getFnsh_date()));

            // Hostel tiplerini virgülle ayırarak String olarak ekliyoruz
            String hostelTypeString = String.join(",", hotel.getHostelType().stream().map(Enum::name).toArray(String[]::new));
            pr.setString(8, hostelTypeString);

            // Facility feature'larını virgülle ayırarak String olarak ekliyoruz
            String facilityFeatureString = String.join(",", hotel.getFacilityFeature().stream().map(Enum::name).toArray(String[]::new));
            pr.setString(9, facilityFeatureString);

            pr.setInt(10, hotel.getId());

            return pr.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
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
