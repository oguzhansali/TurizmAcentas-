package entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Hotel {

    private int id;
    private String name;
    private String adress;
    private String mail;
    private String mpno;
    private Star star;
    private LocalDate strt_date;
    private LocalDate fnsh_date;
    private ArrayList<HostelType> hostelType;
    private ArrayList<FacilityFeature> facilityFeatures;

    public Hotel(int id, String name, String adress, String mail, String mpno, Star star, LocalDate strtDate, LocalDate fnshDate, ArrayList<HostelType> hostelTypes, ArrayList<FacilityFeature> facilityFeatures) {
    }

    public void setHostelTypes(ArrayList<Hotel.HostelType> hostelTypes) {
    }

    public void setFacilityFeatures(ArrayList<FacilityFeature> facilityFeatures) {
    }


    public enum Star{
        BIR_YILDIZLI,
        IKI_YILDIZLI,
        UC_YILDIZLI,
        DORT_YILDIZLI,
        BES_YILDIZLI
    }
    public enum HostelType{
        ULTRA_HER_SEY_DAHIL,
        HER_SEY_DAHIL,
        ODA_KAHVALTI,
        TAM_PANSIYON,
        YARIM_PANSIYON,
        SADECE_YATAK,
        ALKOL_HARIÇ_FULL_CREDIT

    }
    public  enum FacilityFeature{
        UCRETSIZ_OTOPARK,
        UCRETSIZ_WIFI,
        YUZME_HAVUZU,
        FITNES_CENTER,
        HOTEL_CONCIERGE,
        SPA,
        TUM_GUN_ODA_SERVISI

    }

    public Hotel(int id, String name, String adress, String mail, String mpno, Star star, LocalDate strt_date, LocalDate fnsh_date,FacilityFeature facilityFeature,HostelType hostelType) {
        this.id = id;
        this.name = name;
        this.adress = adress;
        this.mail = mail;
        this.mpno = mpno;
        this.star = star;
        this.strt_date = strt_date;
        this.fnsh_date = fnsh_date;
        this.facilityFeatures=new ArrayList<>();
        this.hostelType=new ArrayList<>();
    }

    public Hotel() {
        this.hostelType = new ArrayList<>();
        this.facilityFeatures=new ArrayList<>();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMpno() {
        return mpno;
    }

    public void setMpno(String mpno) {
        this.mpno = mpno;
    }

    public Star getStar() {
        return star;
    }

    public void setStar(String star) {
        try {
            this.star = Star.valueOf(star);
        }catch (IllegalArgumentException e){
            System.err.println("Geçersiz star değeri :" + star);
            e.printStackTrace();
        }

    }

    public LocalDate getStrt_date() {
        return  strt_date;
    }

    public void setStrt_date(LocalDate strt_date)  {
        this.strt_date= strt_date;
    }

    public LocalDate getFnsh_date() {
        return fnsh_date;
    }

    public void setFnsh_date(LocalDate fnsh_date) {
        this.fnsh_date = fnsh_date;
    }

    public ArrayList<HostelType> getHostelType() {
        return hostelType;
    }

    public void setHostelType(ArrayList<HostelType> hostelType) {
        this.hostelType = hostelType;
    }

    public ArrayList<FacilityFeature> getFacilityFeature() {
        return facilityFeatures;
    }

    public void setFacilityFeature(ArrayList<FacilityFeature> facilityFeature) {
        this.facilityFeatures = facilityFeature;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", adress='" + adress + '\'' +
                ", mail='" + mail + '\'' +
                ", mpno='" + mpno + '\'' +
                ", star=" + star +
                ", strt_date=" + strt_date +
                ", fnsh_date=" + fnsh_date +
                ", hostelType=" + hostelType +
                ", facilityFeature=" + facilityFeatures  +
                '}';
    }
}
