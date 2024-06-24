package business;

import core.Helper;
import dao.HotelDao;
import dao.HotelFacilityFeatureDAO;
import dao.HotelHostelTypeDAO;
import entity.Hotel;
import entity.HotelFacilityFeature;
import entity.HotelHostelType;
import entity.User;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class HotelManager {
    private HotelDao hotelDao= new HotelDao();
    private HotelFacilityFeatureDAO hotelFacilityFeatureDAO=new HotelFacilityFeatureDAO();
    private HotelHostelTypeDAO hotelHostelTypeDAO = new HotelHostelTypeDAO();


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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            rowObject[i++] = obj.getStrt_date();
            rowObject[i++] = obj.getFnsh_date();
            rowObject[i++] = obj.getFacilityFeature();
            rowObject[i++] = obj.getHostelType();
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
