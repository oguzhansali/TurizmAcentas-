package entity;

public class Room {
    private int id;
    private int hotel_id;
    private String bed_count;
    private String squaremeter;
    private String stock;
    private RoomType roomType;
    private Television television;
    private MiniBar miniBar;
    private GameConsole gameConsole;
    private Safe safe;
    private Projection projection;
    private Hotel hotel;

    public Room() {

    }

    public enum RoomType{
        Single_Oda,
        Double_Oda,
        Junior_Suite_Oda,
        Suite_Oda
    }
    public  enum    Television{
        VAR,
        YOK
    }
    public enum MiniBar{
        VAR,
        YOK
    }
    public enum GameConsole{
        VAR,
        YOK
    }
    public enum Safe{
        VAR,
        YOK
    }

    public enum Projection{
        VAR,
        YOK
    }

    public Room(int id, int hotel_id, String bed_count, String squaremeter,String stock, RoomType roomType, Television television, MiniBar miniBar, GameConsole gameConsole, Safe safe, Projection projection) {
        this.id = id;
        this.hotel_id = hotel_id;
        this.bed_count = bed_count;
        this.squaremeter = squaremeter;
        this.stock = stock;
        this.roomType = roomType;
        this.television = television;
        this.miniBar = miniBar;
        this.gameConsole = gameConsole;
        this.safe = safe;
        this.projection = projection;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getBed_count() {
        return bed_count;
    }

    public void setBed_count(int bed_count) {
        this.bed_count = String.valueOf(bed_count);
    }

    public String getSquaremeter() {
        return squaremeter;
    }

    public void setSquaremeter(String squaremeter) {
        this.squaremeter = squaremeter;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = String.valueOf(stock);
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Television getTelevision() {
        return television;
    }

    public void setTelevision(Television television) {
        this.television = television;
    }

    public MiniBar getMiniBar() {
        return miniBar;
    }

    public void setMiniBar(MiniBar miniBar) {
        this.miniBar = miniBar;
    }

    public GameConsole getGameConsole() {
        return gameConsole;
    }

    public void setGameConsole(GameConsole gameConsole) {
        this.gameConsole = gameConsole;
    }

    public Safe getSafe() {
        return safe;
    }

    public void setSafe(Safe safe) {
        this.safe = safe;
    }

    public Projection getProjection() {
        return projection;
    }

    public void setProjection(Projection projection) {
        this.projection = projection;
    }

    public void setBed_count(String bed_count) {
        this.bed_count = bed_count;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", hotel_id=" + hotel_id +
                ", bed_count=" + bed_count +
                ", squaremeter=" + squaremeter +
                ", stock=" + stock +
                ", roomType=" + roomType +
                ", television=" + television +
                ", miniBar=" + miniBar +
                ", gameConsole=" + gameConsole +
                ", safe=" + safe +
                ", projection=" + projection +
                '}';
    }
}
