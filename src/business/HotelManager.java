package business;

import core.Helper;
import dao.HotelDao;
import dao.RoomDao;
import entity.Hotel;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HotelManager {
    private HotelDao hotelDao = new HotelDao();
    private RoomDao roomDao = new RoomDao();

    // ID'ye göre otel getiren metot.
    public Hotel getById(int id) {
        return this.hotelDao.getById(id);
    }

    // Tüm otelleri getiren metot.
    public ArrayList<Hotel> findAll() {
        return this.hotelDao.findAll();
    }


    // Tablo gösterimi için otelleri hazırlayan metot.
    public ArrayList<Object[]> getForTable(int size) {
        ArrayList<Object[]> hotelObjList = new ArrayList<>();
        ArrayList<Hotel> hotelList = this.findAll();
        for (Hotel obj : hotelList) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getName();
            rowObject[i++] = obj.getAdress();
            rowObject[i++] = obj.getMail();
            rowObject[i++] = obj.getMpno();
            rowObject[i++] = obj.getStar();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            rowObject[i++] = obj.getHigh_season_strt_date();
            rowObject[i++] = obj.getHigh_season_fnsh_date();
            rowObject[i++] = obj.getHotel_open();
            rowObject[i++] = obj.getHotel_close();
            rowObject[i++] = obj.getFacilityFeature();
            rowObject[i++] = obj.getHostelType();
            hotelObjList.add(rowObject);
        }
        return hotelObjList;

    }

    // Otel kaydetme metodu.
    public boolean save(Hotel hotel) {
        if (hotel.getId() > 0 && this.getById(hotel.getId()) != null) {
            Helper.showMsg("error");
            return false;
        }
        return this.hotelDao.save(hotel);
    }

    // Otel güncelleme metodu.
    public boolean update(Hotel hotel) {
        if (this.getById(hotel.getId()) == null) {
            Helper.showMsg(hotel.getId() + " ID kayıtlı hotel bulunamadı.");
            return false;
        }
        return this.hotelDao.update(hotel);
    }

    // Otel silme metodu.
    public boolean delete(int id) {
        if (this.getById(id) == null) {
            Helper.showMsg(id + " ID kayıtlı hotel bulunamadı");
            return false;
        }
        // Otele ait odaları da sil.
        this.roomDao.deleteRoomsByHotelId(id);
        return this.hotelDao.delete(id);
    }

    // Otel ekleme metodu.
    public boolean add(Hotel hotel) {
        return this.save(hotel);
    }


}
