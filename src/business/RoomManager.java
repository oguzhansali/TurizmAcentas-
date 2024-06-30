package business;

import core.Helper;
import dao.BookDao;
import dao.RoomDao;
import entity.Book;
import entity.Room;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class RoomManager {
    private final RoomDao roomDao = new RoomDao();
    private final BookDao bookDao=new BookDao();

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
            rowObject[i++] = obj.getHotel().getName();
            rowObject[i++] = obj.getBed_count();
            rowObject[i++] = obj.getSquaremeter();
            rowObject[i++] = obj.getStock();
            rowObject[i++] = obj.getRoomType();
            rowObject[i++] = obj.getTelevision();
            rowObject[i++] = obj.getMiniBar();
            rowObject[i++] = obj.getGameConsole();
            rowObject[i++] = obj.getSafe();
            rowObject[i++] = obj.getProjection();
            rowObject[i++] = obj.getAdult_price();
            rowObject[i++] = obj.getKid_price();
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
        String select = "SELECT * FROM public.room";
        ArrayList<String> whereList=new ArrayList<>();

        if (hotelId!=0){
            whereList.add("room_hotel_id = " + hotelId);
        }
        if (roomType!=null){
            whereList.add("room_room_type='"+ roomType.toString()+"'");
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
    public ArrayList<Room> searchForBooking(String strt_date, String fnsh_date, Room.RoomType roomType, Room.Television television, Room.MiniBar miniBar, Room.GameConsole gameConsole, Room.Safe safe, Room.Projection projection) {
        String query = "SELECT * FROM public.room as r LEFT JOIN public.hotel as h";

        ArrayList<String> where = new ArrayList<>();
        ArrayList<String> joinWhere = new ArrayList<>();
        ArrayList<String> bookOrWhere = new ArrayList<>();

        joinWhere.add("r.room_hotel_id = h.hotel_id");

        // Tarihlerin boş olup olmadığını kontrol edin
        if (strt_date == null || strt_date.isEmpty()) {
            throw new IllegalArgumentException("Başlangıç tarihi boş olamaz");
        }
        if (fnsh_date == null || fnsh_date.isEmpty()) {
            throw new IllegalArgumentException("Bitiş tarihi boş olamaz");
        }

        // Tarih formatını dönüştürme
        strt_date = LocalDate.parse(strt_date, DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString();
        fnsh_date = LocalDate.parse(fnsh_date, DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString();

        if (roomType != null) {
            where.add("r.room_room_type = '" + roomType.toString() + "'");
        }
        if (television != null) {
            where.add("r.room_television = '" + television.toString() + "'");
        }
        if (miniBar != null) {
            where.add("r.room_minibar = '" + miniBar.toString() + "'");
        }
        if (gameConsole != null) {
            where.add("r.room_game_console = '" + gameConsole.toString() + "'");
        }
        if (safe != null) {
            where.add("r.room_safe = '" + safe.toString() + "'");
        }
        if (projection != null) {
            where.add("r.room_projection = '" + projection.toString() + "'");
        }

        String whereStr = String.join(" AND ", where);
        String joinStr = String.join(" AND ", joinWhere);

        if (joinStr.length() > 0) {
            query += " ON " + joinStr;
        }
        if (whereStr.length() > 0) {
            query += " WHERE " + whereStr;
        }

        ArrayList<Room> searchedRoomList = this.roomDao.selectByQuery(query);

        bookOrWhere.add("('" + strt_date + "' BETWEEN book_booking_strt_date AND book_booking_fnsh_date)");
        bookOrWhere.add("('" + fnsh_date + "' BETWEEN book_booking_strt_date AND book_booking_fnsh_date)");
        bookOrWhere.add("(book_booking_strt_date BETWEEN '" + strt_date + "' AND '" + fnsh_date + "')");
        bookOrWhere.add("(book_booking_fnsh_date BETWEEN '" + strt_date + "' AND '" + fnsh_date + "')");

        String bookOrWhereStr = String.join(" OR ", bookOrWhere);
        String bookQuery = "SELECT * FROM public.book WHERE " + bookOrWhereStr;

        ArrayList<Book> bookList = this.bookDao.selectByQuery(bookQuery);
        ArrayList<Integer> busyRoomId = new ArrayList<>();
        for (Book book : bookList) {
            busyRoomId.add(book.getRoom_id());
        }

        // Bu işlem ile busyRoomId listesinin içinde meşgul odaları listeden siler.
        searchedRoomList.removeIf(room -> busyRoomId.contains(room.getId()));

        return searchedRoomList;
    }

}
