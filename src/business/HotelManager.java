package business;

import core.Helper;
import dao.HotelDao;
import entity.Hotel;
import entity.User;

import java.util.ArrayList;

public class HotelManager {
    private HotelDao hotelDao= new HotelDao();

    public Hotel getById(int id){
        return this.hotelDao.getById(id);
    }
    public ArrayList<Hotel> findAll(){
        return this.hotelDao.findAll();
    }

    public ArrayList<Object[]> getForTable(int size){
        ArrayList<Object[]> hotelObjList = new ArrayList<>();
        ArrayList<Hotel> hotelList = this.findAll();
        for (Hotel obj: hotelList){
            int i=0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getName();
            rowObject[i++] = obj.getAdress();
            rowObject[i++] = obj.getMail();
            rowObject[i++] = obj.getMpno();
            rowObject[i++] = obj.getStar();
            rowObject[i++] = obj.getStrt_date();
            rowObject[i++] = obj.getFnsh_date();
            hotelObjList.add(rowObject);
        }
        return hotelObjList;

    }

    public boolean save(Hotel hotel){
        if (hotel.getId()>0 && this.getById(hotel.getId())!=null){
            Helper.showMsg("error");
            return false;
        }
        return this.hotelDao.save(hotel);
    }
    public boolean update(Hotel hotel){
        if (this.getById(hotel.getId())==null){
            Helper.showMsg(hotel.getId()+ " ID kayıtlı user bulunamadı.");
            return false;
        }
        return this.hotelDao.update(hotel);
    }
    public boolean delete(int id){
        if (this.getById(id)==null){
            Helper.showMsg(id+ " ID kayıtlı user bulunamadı");
            return false;
        }
        return this.hotelDao.delete(id);
    }

    public boolean add(Hotel hotel) {
        return this.save(hotel);
    }

}
