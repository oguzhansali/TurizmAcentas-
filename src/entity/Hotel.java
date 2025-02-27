package entity;

import org.postgresql.jdbc2.ArrayAssistant;

import java.time.LocalDate;
import java.util.ArrayList;

public class Hotel {
    private int id;
    private String name;
    private String adress;
    private String mail;
    private String mpno;
    private Star star;
    private LocalDate high_season_strt_date;
    private LocalDate high_season_fnsh_date;
    private LocalDate hotel_open;
    private LocalDate hotel_close;
    private ArrayList<HostelType> hostelTypes = new ArrayList<>();
    private ArrayList<FacilityFeature> facilityFeatures = new ArrayList<>();


    //Otel yıldızı için enum tanımı.
    public enum Star {
        BIR_YILDIZLI,
        IKI_YILDIZLI,
        UC_YILDIZLI,
        DORT_YILDIZLI,
        BES_YILDIZLI
    }

    //Pansiyon Tipi için enum tanımı
    public enum HostelType {
        ULTRA_HER_SEY_DAHIL,
        HER_SEY_DAHIL,
        ODA_KAHVALTI,
        TAM_PANSIYON,
        YARIM_PANSIYON,
        SADECE_YATAK,
        ALKOL_HARIÇ_FULL_CREDIT
    }

    //Tesis Özellikleri için enum tanımı
    public enum FacilityFeature {
        UCRETSIZ_OTOPARK,
        UCRETSIZ_WIFI,
        YUZME_HAVUZU,
        FITNES_CENTER,
        HOTEL_CONCIERGE,
        SPA,
        TUM_GUN_ODA_SERVISI
    }

    //Constructor
    public Hotel(int id, String name, String adress, String mail, String mpno, Star star, LocalDate high_season_strt_date, LocalDate high_season_fnsh_date, LocalDate hotel_open, LocalDate hotel_close, ArrayList<HostelType> hostelTypes, ArrayList<FacilityFeature> facilityFeatures) {
        this.id = id;
        this.name = name;
        this.adress = adress;
        this.mail = mail;
        this.mpno = mpno;
        this.star = star;
        this.high_season_strt_date = high_season_strt_date;
        this.high_season_fnsh_date = high_season_fnsh_date;
        this.hotel_open = hotel_open;
        this.hotel_close = hotel_close;
        this.facilityFeatures = facilityFeatures;
        this.hostelTypes = hostelTypes;
    }


    public Hotel() {

    }

    //Hotel için getter ve setter
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
        this.star = Star.valueOf(star);

    }

    public LocalDate getHigh_season_strt_date() {
        return high_season_strt_date;
    }

    public void setHigh_season_strt_date(LocalDate high_season_strt_date) {
        this.high_season_strt_date = high_season_strt_date;
    }

    public LocalDate getHigh_season_fnsh_date() {
        return high_season_fnsh_date;
    }

    public void setHigh_season_fnsh_date(LocalDate high_season_fnsh_date) {
        this.high_season_fnsh_date = high_season_fnsh_date;
    }

    public ArrayList<HostelType> getHostelType() {
        return hostelTypes;
    }

    public ArrayList<FacilityFeature> getFacilityFeature() {
        return facilityFeatures;
    }


    public void setStar(Star star) {
        this.star = star;
    }

    public void setHostelTypes(ArrayList<HostelType> hostelTypes) {
        this.hostelTypes = hostelTypes;
    }

    public void setFacilityFeatures(ArrayList<FacilityFeature> facilityFeatures) {
        this.facilityFeatures = facilityFeatures;
    }


    public void addHostelType(HostelType type) {
        this.hostelTypes.add(type);
    }

    public void addFacilityFeature(FacilityFeature feature) {
        this.facilityFeatures.add(feature);
    }

    public LocalDate getHotel_open() {
        return hotel_open;
    }

    public void setHotel_open(LocalDate hotel_open) {
        this.hotel_open = hotel_open;
    }

    public LocalDate getHotel_close() {
        return hotel_close;
    }

    public void setHotel_close(LocalDate hotel_close) {
        this.hotel_close = hotel_close;
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
                ", high_season_strt_date=" + high_season_strt_date +
                ", high_season_fnsh_date=" + high_season_fnsh_date +
                ", hotel_open=" + hotel_open +
                ", hotel_close=" + hotel_close +
                ", hostelTypes=" + hostelTypes +
                ", facilityFeatures=" + facilityFeatures +
                '}';
    }
}
