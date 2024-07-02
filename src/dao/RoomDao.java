package dao;

import core.Db;
import entity.Hotel;
import entity.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomDao {
    private Connection con;// Veritabanı bağlantısı
    private HotelDao hotelDao = new HotelDao();// HotelDao nesnesi

    public RoomDao() {
        this.con = Db.getInstance(); // Veritabanı bağlantısını Db sınıfından al
        this.hotelDao = new HotelDao(); // HotelDao nesnesi oluştur
    }

    // ID'ye göre oda getirir
    public Room getById(int id) {
        Room obj = null;
        String query = "SELECT * FROM public.room WHERE room_id = ?";
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

    // Tüm odaları getirir
    public ArrayList<Room> findAll() {
        return this.selectByQuery("SELECT * FROM public.room ORDER BY room_id ASC");
    }

    // Belirli bir otel ID'sine ait odaları getirir
    public ArrayList<Room> getByListHotelId(int hotelId) {
        return this.selectByQuery("SELECT * FROM public.room WHERE room_hotel_id= " + hotelId);
    }

    // Verilen sorguya göre odaları getirir
    public ArrayList<Room> selectByQuery(String query) {
        ArrayList<Room> roomList = new ArrayList<>();
        try {
            ResultSet rs = this.con.createStatement().executeQuery(query);
            while (rs.next()) {
                roomList.add(this.match(rs));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return roomList;
    }

    // Yeni oda ekler
    public boolean save(Room room) {
        String query = "INSERT INTO public.room" +
                "(" +
                //"room_id, "+
                "room_hotel_id, " +
                "room_bed_count, " +
                "room_squarmeter, " +
                "room_stock, " +
                "room_room_type, " +
                "room_television, " +
                "room_minibar, " +
                "room_game_console, " +
                "room_safe, " +
                "room_projection," +
                "room_adult_price," +
                "room_kid_price" +
                ")" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pr = con.prepareStatement(query);
            //pr.setInt(1,room.getId());
            pr.setInt(1, room.getHotel_id());
            pr.setString(2, room.getBed_count());
            pr.setString(3, room.getSquaremeter());
            pr.setString(4, room.getStock());
            pr.setString(5, room.getRoomType().toString());
            pr.setString(6, room.getTelevision().toString());
            pr.setString(7, room.getMiniBar().toString());
            pr.setString(8, room.getGameConsole().toString());
            pr.setString(9, room.getSafe().toString());
            pr.setString(10, room.getProjection().toString());
            pr.setString(11, room.getAdult_price());
            pr.setString(12, room.getKid_price());
            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    // Odayı günceller
    public boolean update(Room room) {
        String query = "UPDATE public.room SET " +
                "room_hotel_id = ?, " +
                "room_bed_count = ?, " +
                "room_squarmeter = ?, " +
                "room_stock = ?, " +
                "room_room_type = ?, " +
                "room_television = ?, " +
                "room_minibar = ?, " +
                "room_game_console = ?, " +
                "room_safe = ? , " +
                "room_projection= ?," +
                "room_adult_price= ?, " +
                "room_kid_price= ? " +
                " WHERE room_id = ?";

        try {
            PreparedStatement pr = con.prepareStatement(query);
            pr.setInt(1, room.getHotel_id());
            pr.setString(2, room.getBed_count());
            pr.setString(3, room.getSquaremeter());
            pr.setString(4, room.getStock());
            pr.setString(5, room.getRoomType().toString());
            pr.setString(6, room.getTelevision().toString());
            pr.setString(7, room.getMiniBar().toString());
            pr.setString(8, room.getGameConsole().toString());
            pr.setString(9, room.getSafe().toString());
            pr.setString(10, room.getProjection().toString());
            pr.setString(11, room.getAdult_price());
            pr.setString(12, room.getKid_price());
            pr.setInt(13, room.getId());
            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    // Odayı siler
    public boolean delete(int room_id) {
        String query = "DELETE FROM public.room WHERE room_id = ?";
        try {
            PreparedStatement pr = con.prepareStatement(query);
            pr.setInt(1, room_id);
            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    // Belirli bir otel ID'sine ait odaları siler
    public boolean deleteRoomsByHotelId(int hotelId) {
        String query = "DELETE FROM public.room WHERE room_hotel_id = ?";
        try {
            PreparedStatement pr = con.prepareStatement(query);
            pr.setInt(1, hotelId);
            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    // ResultSet'teki verileri Room nesnesine eşleştirir
    public Room match(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setId(rs.getInt("room_id"));
        room.setHotel(this.hotelDao.getById(rs.getInt("room_hotel_id")));
        room.setBed_count(rs.getInt("room_bed_count"));
        room.setSquaremeter(String.valueOf(rs.getInt("room_squarmeter")));
        room.setStock(rs.getString("room_stock"));
        room.setRoomType(Room.RoomType.valueOf(rs.getString("room_room_type")));
        room.setTelevision(Room.Television.valueOf(rs.getString("room_television")));
        room.setMiniBar(Room.MiniBar.valueOf(rs.getString("room_minibar")));
        room.setGameConsole(Room.GameConsole.valueOf(rs.getString("room_game_console")));
        room.setSafe(Room.Safe.valueOf(rs.getString("room_safe")));
        room.setProjection(Room.Projection.valueOf(rs.getString("room_projection")));
        room.setAdult_price(rs.getString("room_adult_price"));
        room.setKid_price(rs.getString("room_kid_price"));
        return room;
    }
}
