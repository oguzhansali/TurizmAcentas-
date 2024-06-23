package entity;

public class HotelFacilityFeature {
    private  int id;
    private  String facilityType;

    private int otelId;

    public int getOtelId() {
        return otelId;
    }

    public void setOtelId(int otelId) {
        this.otelId = otelId;
    }

    public HotelFacilityFeature(int id, String name) {
        this.id = id;
        this.facilityType = name;
    }
    public HotelFacilityFeature(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }
}
