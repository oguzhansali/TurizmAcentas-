package business;

import core.Helper;
import dao.RoomDao;
import entity.Room;

import java.util.ArrayList;

public class RoomManager {
    private final RoomDao roomDao = new RoomDao();

    public Room getById(int id){
        return this.roomDao.getById(id);
    }
    public ArrayList<Room> findAll(){
        return this.roomDao.findAll();
    }
    public ArrayList<Object[]> getForTable(int size,ArrayList<Room> roomList){
        ArrayList<Object[]> roomObjList = new ArrayList<>();
        for (Room obj : roomList){
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getHotel_id();
            rowObject[i++] = obj.getBed_count();
            rowObject[i++] = obj.getSquaremeter();
            rowObject[i++] = obj.getStock();
            rowObject[i++] = obj.getRoomType();
            rowObject[i++] = obj.getTelevision();
            rowObject[i++] = obj.getMiniBar();
            rowObject[i++] = obj.getGameConsole();
            rowObject[i++] = obj.getSafe();
            rowObject[i++] = obj.getProjection();
            roomObjList.add(rowObject);
        }
        return roomObjList;
    }
    public boolean save(Room room){
        if (room.getId()>0 && this.getById(room.getId())!=null){
            Helper.showMsg("error");
            return false;
        }
        return this.roomDao.save(room);
    }
    public boolean update(Room room){
        if (this.getById(room.getId())==null){
            Helper.showMsg(room.getId()+" ID kayıtlı oda bulunamadı.");
            return false;
        }
        return this.roomDao.update(room);
    }
    public boolean delete(int id){
        if (this.getById(id) ==null){
            Helper.showMsg(id+" ID kayıtlı oda bulunamadı. ");
            return false;
        }
        return this.roomDao.delete(id);
    }
    public ArrayList<Room> getByListHotelId(int hotelId){
        return this.roomDao.getByListHotelId(hotelId);
    }
    public ArrayList<Room> searchForTable(int hotelId, Room.RoomType roomType, Room.Television television, Room.MiniBar miniBar, Room.GameConsole gameConsole, Room.Safe safe, Room.Projection projection, String text){
        String select = "SELET * FROM public.room";
        ArrayList<String> whereList=new ArrayList<>();

        if (hotelId!=0){
            whereList.add("room_hotel_id = " + hotelId);
        }
        if (roomType!=null){
            whereList.add("room_room_type=' "+ roomType.toString()+"'");
        }
        if (television!=null){
            whereList.add("room_television=' "+television.toString()+"'");
        }
        if (miniBar!=null){
            whereList.add("room_minibar=' "+miniBar.toString()+"'");
        }
        if (gameConsole!=null){
            whereList.add("room_game_console=' "+gameConsole.toString()+"'");
        }
        if (safe!=null){
            whereList.add("room_safe=' "+safe.toString()+"'");
        }
        if (projection!=null){
            whereList.add("room_projection=' "+projection.toString()+"'");
        }
        String whereStr = String.join(" AND ", whereList);
        String query = select;
        if (whereStr.length()>0){
            query+=" WHERE " + whereStr;
        }
        return  this.roomDao.selectByQuery(query);
    }
}
