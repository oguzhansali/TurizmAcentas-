package entity;

import java.time.LocalDate;
import java.util.ArrayList;

public class Hotel {
    private int id;
    private String name;
    private String adress;
    private String mail;
    private String mpno;
    private Star star;
    private LocalDate strt_date;
    private LocalDate fnsh_date;
    private HostelType hostelTypes;
    private FacilityFeature facilityFeatures;


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


    public Hotel(int id, String name, String adress, String mail, String mpno, Star star, LocalDate strt_date, LocalDate fnsh_date,HostelType hostelTypes,FacilityFeature facilityFeatures) {
        this.id = id;
        this.name = name;
        this.adress = adress;
        this.mail = mail;
        this.mpno = mpno;
        this.star = star;
        this.strt_date = strt_date;
        this.fnsh_date = fnsh_date;
        this.facilityFeatures=facilityFeatures;
        this.hostelTypes=hostelTypes;
    }



    public Hotel() {

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
        this.star= Star.valueOf(star);

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

    public HostelType  getHostelType() {
        return hostelTypes;
    }

    public FacilityFeature getFacilityFeature() {
        return facilityFeatures;
    }


    public void setStar(Star star) {
        this.star = star;
    }

    public void setHostelTypes(HostelType hostelTypes) {
        this.hostelTypes = hostelTypes;
    }

    public void setFacilityFeatures(FacilityFeature facilityFeatures) {
        this.facilityFeatures = facilityFeatures;
    }

    /*
    public void addHostelType(HostelType type){
        this.hostelTypes.add(type);
    }
    public void addFacilityFeature(FacilityFeature feature){
        this.facilityFeatures.add(feature);
    }

     */


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
                ", hostelType=" + hostelTypes +
                ", facilityFeature=" + facilityFeatures  +
                '}';
    }
}
