package entity;

import java.time.LocalDate;

public class Book {
    private int id;
    private int room_id;
    private Room room;
    private String costumerName;
    private String costumerLastname;
    private String identityNum;
    private String mpno;
    private LocalDate strt_date;
    private LocalDate fnsh_date;
    private String bCase;


    public Book() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getCostumerName() {
        return costumerName;
    }

    public void setCostumerName(String costumerName) {
        this.costumerName = costumerName;
    }

    public String getCostumerLastname() {
        return costumerLastname;
    }

    public void setCostumerLastname(String costumerLastname) {
        this.costumerLastname = costumerLastname;
    }

    public String getIdentityNum() {
        return identityNum;
    }

    public void setIdentityNum(String identityNum) {
        this.identityNum = identityNum;
    }

    public String getMpno() {
        return mpno;
    }

    public void setMpno(String mpno) {
        this.mpno = mpno;
    }

    public LocalDate getStrt_date() {
        return strt_date;
    }

    public void setStrt_date(LocalDate strt_date) {
        this.strt_date = strt_date;
    }

    public LocalDate getFnsh_date() {
        return fnsh_date;
    }

    public void setFnsh_date(LocalDate fnsh_date) {
        this.fnsh_date = fnsh_date;
    }

    public String getbCase() {
        return bCase;
    }

    public void setbCase(String bCase) {
        this.bCase = bCase;
    }


    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", room_id=" + room_id +
                ", room=" + room +
                ", costumerName='" + costumerName + '\'' +
                ", costumerLastname='" + costumerLastname + '\'' +
                ", identityNum='" + identityNum + '\'' +
                ", mpno='" + mpno + '\'' +
                ", strt_date=" + strt_date +
                ", fnsh_date=" + fnsh_date +
                '}';
    }
}
