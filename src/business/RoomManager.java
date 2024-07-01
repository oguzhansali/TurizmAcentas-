package business;

import core.Helper;
import dao.BookDao;
import dao.RoomDao;
import entity.Book;
import entity.Hotel;
import entity.Room;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

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
    public ArrayList<Room> searchForTable(int hotelId, String hotelName, String address, Date room_strt, Date room_fnsh){
        String select = "SELECT * FROM public.room r";
        String join = " JOIN public.hotel h ON r.room_hotel_id = h.hotel_id";

        ArrayList<String> whereList=new ArrayList<>();

        if (hotelId!=0){
            whereList.add("room_hotel_id = " + hotelId);
        }
        if (hotelName != null && !hotelName.isEmpty()) {
            whereList.add("h.hotel_name ILIKE '%" + hotelName + "%'");
        }
        if (address != null && !address.isEmpty()) {
            whereList.add("h.hotel_adress ILIKE '%" + address + "%'");
        }
        if (room_strt != null && room_fnsh != null) {
            String roomStrtStr=room_strt.toString();
            String room_FnshStr= room_fnsh.toString();


            // Add a condition to filter by year if necessary
            whereList.add("EXTRACT(YEAR FROM '" + room_strt + "') = EXTRACT(YEAR FROM current_date)"); // Example for current year
            whereList.add("('" + room_strt + "' BETWEEN h.hotel_high_season_strt AND h.hotel_high_season_fnsh " +
                    "OR '" + room_fnsh + "' BETWEEN h.hotel_high_season_strt AND h.hotel_high_season_fnsh " +
                    "OR '" + room_strt + "' BETWEEN h.hotel_low_season_strt AND h.hotel_low_season_fnsh " +
                    "OR '" + room_fnsh + "' BETWEEN h.hotel_low_season_strt AND h.hotel_low_season_fnsh " +
                    "OR (h.hotel_high_season_strt BETWEEN '" + room_strt + "' AND '" + room_fnsh + "') " +
                    "OR (h.hotel_high_season_fnsh BETWEEN '" + room_strt + "' AND '" + room_fnsh + "') " +
                    "OR (h.hotel_low_season_strt BETWEEN '" + room_strt + "' AND '" + room_fnsh + "') " +
                    "OR (h.hotel_low_season_fnsh BETWEEN '" + room_strt + "' AND '" + room_fnsh + "'))");

            /*whereList.add("('" + room_strt + "' BETWEEN h.hotel_high_season_strt AND h.hotel_high_season_fnsh " +
                    "OR '" + room_fnsh + "' BETWEEN h.hotel_high_season_strt AND h.hotel_high_season_fnsh " +
                    "OR '" + room_strt + "' BETWEEN h.hotel_low_season_strt AND h.hotel_low_season_fnsh " +
                    "OR '" + room_fnsh + "' BETWEEN h.hotel_low_season_strt AND h.hotel_low_season_fnsh " +
                    "OR (h.hotel_high_season_strt BETWEEN '" + room_strt + "' AND '" + room_fnsh + "') " +
                    "OR (h.hotel_high_season_fnsh BETWEEN '" + room_strt + "' AND '" + room_fnsh + "') " +
                    "OR (h.hotel_low_season_strt BETWEEN '" + room_strt + "' AND '" + room_fnsh + "') " +
                    "OR (h.hotel_low_season_fnsh BETWEEN '" + room_strt + "' AND '" + room_fnsh + "'))");*/
        }

        String whereStr = String.join(" AND ", whereList);
        String query = select + join;
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
