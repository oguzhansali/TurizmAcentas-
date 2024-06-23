package business;

import core.Helper;
import dao.HotelFacilityFeatureDAO;
import entity.HotelFacilityFeature;

import java.util.ArrayList;

public class HotelFacilityFeatureManager {
    private final HotelFacilityFeatureDAO hotelFacilityFeatureDAO;
    private final HotelManager hotelManager;

    public HotelFacilityFeatureManager(){
        this.hotelFacilityFeatureDAO=new HotelFacilityFeatureDAO();
        this.hotelManager=new HotelManager();
    }
    public ArrayList<HotelFacilityFeature> findAll(){return this.hotelFacilityFeatureDAO.findAll();
    }

    public ArrayList<Object[]> getForTable(int size){
        ArrayList<Object[]> hotelFcltryFtreRowList = new ArrayList<>();
        for (HotelFacilityFeature hotelFacilityFeature: this.findAll()){
            Object[] rowObject = new Object[size];
            int i = 0;
            rowObject[i++] = hotelFacilityFeature.getId();
            rowObject[i++]=hotelFacilityFeature.getFacilityType();
            hotelFcltryFtreRowList.add(rowObject);
        }
        return hotelFcltryFtreRowList;
    }
    public HotelFacilityFeature getById(int id){
        return this.hotelFacilityFeatureDAO.getById(id);
    }
    public boolean save(HotelFacilityFeature hotelFacilityFeature){
        if (hotelFacilityFeature.getId()!=0){
            Helper.showMsg("error");
        }
        return this.hotelFacilityFeatureDAO.save(hotelFacilityFeature);
    }
    public boolean update(HotelFacilityFeature hotelFacilityFeature){
        if (this.getById(hotelFacilityFeature.getId())==null){
            Helper.showMsg("notFound");
        }
        return  this.hotelFacilityFeatureDAO.update(hotelFacilityFeature);
    }
    public boolean delete (int id) {
        if (this.getById(id) == null) {
            Helper.showMsg(id + " ID kayıtlı tesis özelliği bulunamadı.");
            return false;
        }
        return true;
    }

}
