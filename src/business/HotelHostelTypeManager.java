package business;

import core.Helper;
import dao.HotelHostelTypeDAO;
import entity.HotelFacilityFeature;
import entity.HotelHostelType;

import java.util.ArrayList;

public class HotelHostelTypeManager {
    private HotelHostelTypeDAO hotelHostelTypeDAO;
    private HotelManager hotelManager;

    public HotelHostelTypeManager(HotelHostelTypeDAO hotelHostelTypeDAO, HotelManager hotelManager) {
        this.hotelHostelTypeDAO = hotelHostelTypeDAO;
        this.hotelManager = hotelManager;
    }

    public ArrayList<HotelHostelType> findAll(){return this.hotelHostelTypeDAO.findAll();
    }
    public ArrayList<Object[]> getForTable(int size){
        ArrayList<Object[]> hotelHostelTypeRowList = new ArrayList<>();
        for (HotelHostelType hotelHostelType: this.findAll()){
            Object[] rowObject = new Object[size];
            int i = 0;
            rowObject[i++] = hotelHostelType.getId();
            rowObject[i++]=hotelHostelType.getHostelType();
            hotelHostelTypeRowList.add(rowObject);
        }
        return hotelHostelTypeRowList;
    }
    public HotelHostelType getById(int id){
        return this.hotelHostelTypeDAO.getById(id);
    }
    public boolean save(HotelHostelType hotelHostelType){
        if (hotelHostelType.getId()!=0){
            Helper.showMsg("error");
        }
        return this.hotelHostelTypeDAO.save(hotelHostelType);
    }
    public boolean update(HotelHostelType hotelHostelType){
        if (this.getById(hotelHostelType.getId())==null){
            Helper.showMsg("notFound");
        }
        return  this.hotelHostelTypeDAO.update(hotelHostelType);
    }
    public boolean delete (int id) {
        if (this.getById(id) == null) {
            Helper.showMsg(id + " ID kayıtlı tesis özelliği bulunamadı.");
            return false;
        }
        return true;
    }
}
