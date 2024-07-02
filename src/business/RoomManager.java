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
    private final BookDao bookDao = new BookDao();

    // ID'ye göre oda getiren metot.
    public Room getById(int id) {
        return this.roomDao.getById(id);
    }

    // ID'ye göre oda getiren metot.
    public ArrayList<Room> findAll() {
        return this.roomDao.findAll();
    }

    // Tablo gösterimi için odaları hazırlayan metot.
    public ArrayList<Object[]> getForTable(int size, ArrayList<Room> roomList) {
        ArrayList<Object[]> roomObjList = new ArrayList<>();
        for (Room obj : roomList) {
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

    // Oda kaydetme metodu.
    public boolean save(Room room) {
        if (room.getId() > 0 && this.getById(room.getId()) != null) {
            Helper.showMsg("error");
            return false;
        }
        return this.roomDao.save(room);
    }

    // Oda güncelleme metodu.
    public boolean update(Room room) {
        if (this.getById(room.getId()) == null) {
            Helper.showMsg(room.getId() + " ID kayıtlı oda bulunamadı.");
            return false;
        }
        return this.roomDao.update(room);
    }

    // Oda silme metodu.
    public boolean delete(int id) {
        if (this.getById(id) == null) {
            Helper.showMsg(id + " ID kayıtlı oda bulunamadı. ");
            return false;
        }
        return this.roomDao.delete(id);
    }

    // Otel ID'sine göre odaları getiren metot.
    public ArrayList<Room> getByListHotelId(int hotelId) {
        return this.roomDao.getByListHotelId(hotelId);
    }

    // Oda arama metodu.
    public ArrayList<Room> searchForTable(int hotelId, String hotelName, String address, String strt_date, String fnsh_date) {
        String query = "SELECT * FROM public.room AS r LEFT JOIN public.hotel AS h ON r.room_hotel_id = h.hotel_id";

        ArrayList<String> where = new ArrayList<>();

        if (hotelId != 0) {
            where.add("r.room_hotel_id = " + hotelId);
        }
        if (hotelName != null && !hotelName.isEmpty()) {
            where.add("h.hotel_name ILIKE '%" + hotelName + "%'");
        }
        if (address != null && !address.isEmpty()) {
            where.add("h.hotel_adress ILIKE '%" + address + "%'");
        }

        // Tarihlerin otel açılış ve kapanış tarihleri arasında olup olmadığını kontrol et
        if (strt_date != null && !strt_date.isEmpty()) {
            where.add("'" + strt_date + "' BETWEEN h.hotel_open AND h.hotel_close");
        }
        if (fnsh_date != null && !fnsh_date.isEmpty()) {
            where.add("'" + fnsh_date + "' BETWEEN h.hotel_open AND h.hotel_close");
        }

        String whereStr = String.join(" AND ", where);

        if (!whereStr.isEmpty()) {
            query += " WHERE " + whereStr;
        }

        ArrayList<Room> searchedRoomList = this.roomDao.selectByQuery(query);

        return searchedRoomList;
    }


    // Rezervasyon yapılacak odaları arayan metot.
    public ArrayList<Room> searchForBooking(String strt_date, String fnsh_date) {
        String query = "SELECT * FROM public.room as r JOIN public.hotel as h";

        ArrayList<String> where = new ArrayList<>();
        ArrayList<String> joinWhere = new ArrayList<>();
        ArrayList<String> bookOrWhere = new ArrayList<>();

        joinWhere.add("r.room_hotel_id = h.hotel_id");


        // Tarihlerin boş olup olmadığını kontrol edin
        if (!strt_date.isEmpty() && !fnsh_date.isEmpty()) {
            strt_date = LocalDate.parse(strt_date, DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString();
            fnsh_date = LocalDate.parse(fnsh_date, DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString();
            where.add("('" + strt_date + "' BETWEEN hotel_open AND hotel_close)");
            where.add("('" + fnsh_date + "' BETWEEN hotel_open AND hotel_close)");
        }

        where.add(" TO_NUMBER(room_stock,'999999')>0");

        String whereStr = String.join(" AND ", where);
        String joinStr = String.join(" AND ", joinWhere);

        if (joinStr.length() > 0) {
            query += " ON " + joinStr;
        }
        if (whereStr.length() > 0) {
            query += " WHERE " + whereStr;
        }

        ArrayList<Room> searchedRoomList = this.roomDao.selectByQuery(query);


        return searchedRoomList;
    }

}
