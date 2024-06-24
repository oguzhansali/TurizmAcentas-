package entity;

public class HotelHostelType {
    private int id;
    private String hostelType;
    private int otelId;

    public HotelHostelType(String string) {
    }

    public int getOtelId() {
        return otelId;
    }
    public void setOtelId(int otelId) {
        this.otelId = otelId;
    }


    public HotelHostelType(int id,String hostelType){
        this.id=id;
        this.hostelType=hostelType;
    }
    public HotelHostelType(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHostelType() {
        return hostelType;
    }

    public void setHostelType(String hostelType) {
        this.hostelType = hostelType;
    }
}
