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
    private Connection con;
    private HotelDao hotelDao = new HotelDao();

    public RoomDao(){
        this.con= Db.getInstance();
    }

    public Room getById(int id){
        Room obj = null;
        String query = "SELECT * FROM public.room WHERE room_id = ?";
        try {
            PreparedStatement pr =con.prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = this.match(rs);
            }

        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return obj;
    }
    public ArrayList<Room> findAll(){
        return this.selectByQuery("SELECT * FROM public.room ORDER BY room_id ASC");
    }

    public ArrayList<Room> getByListHotelId(int hotelId){
        return this.selectByQuery("SELECT * FROM public.room WHERE room_hotel_id= "+hotelId);
    }
    public ArrayList<Room> selectByQuery(String query){
        ArrayList<Room> roomList = new ArrayList<>();
        try {
            ResultSet rs = this.con.createStatement().executeQuery(query);
            while (rs.next()){
                roomList.add(this.match(rs));
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return roomList;
    }
    public boolean save(Room room){
        String query = "INSERT INTO public.room"+
                "("+
                "room_id, "+
                "room_hotel_id, "+
                "room_bed_count, "+
                "room_squarmeter, "+
                "room_stock, "+
                "room_room_type, "+
                "room_television, "+
                "room_minibar, "+
                "room_game_console, "+
                "room_safe, "+
                "room_projection"+
                ")"+
                " VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pr = con.prepareStatement(query);
            pr.setInt(1,room.getId());
            pr.setInt(2,room.getHotel_id());
            pr.setString(3,room.getBed_count());
            pr.setString(4,room.getSquaremeter());
            pr.setString(5,room.getStock());
            pr.setString(6,room.getRoomType().toString());
            pr.setString(7,room.getTelevision().toString());
            pr.setString(8,room.getMiniBar().toString());
            pr.setString(9,room.getGameConsole().toString());
            pr.setString(10,room.getSafe().toString());
            pr.setString(11,room.getProjection().toString());
            return pr.executeUpdate() !=-1;
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return true;
    }
    public boolean update(Room room){
        String query = "UPDATE public.room SET "+
                "room_hotel_id = ?, "+
                "room_bed_count = ?, "+
                "room_squarmeter = ?, "+
                "room_stock = ?, "+
                "room_room_type = ?, "+
                "room_television = ?, "+
                "room_minibar = ?, "+
                "room_game_console = ?, "+
                "room_safe = ? , "+
                "room_projection= ?"+
                " WHERE room_id = ?";

        try {
            PreparedStatement pr = con.prepareStatement(query);
            pr.setInt(1,room.getHotel_id());
            pr.setString(2,room.getBed_count());
            pr.setString(3,room.getSquaremeter());
            pr.setString(4,room.getStock());
            pr.setString(5,room.getRoomType().toString());
            pr.setString(6,room.getTelevision().toString());
            pr.setString(7,room.getMiniBar().toString());
            pr.setString(8,room.getGameConsole().toString());
            pr.setString(9,room.getSafe().toString());
            pr.setString(10,room.getProjection().toString());
            pr.setInt(11,room.getId());
            return pr.executeUpdate() != -1;
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return true;
    }

    public boolean delete(int room_id){
        String query = "DELETE FROM public.room WHERE room_id = ?";
        try {
            PreparedStatement pr = con.prepareStatement(query);
            pr.setInt(1,room_id);
            return pr.executeUpdate() !=-1;
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return true;
    }

    public Room match(ResultSet rs) throws SQLException{
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
        return room;
    }
}
