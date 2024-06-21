package entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Hotel {
    private int id;
    private String name;
    private String adress;
    private String mail;
    private String mpno;
    private String star;
    private Date strt_date;
    private Date fnsh_date;

    public Hotel(int id, String name, String adress, String mail, String mpno, String star, Date strt_date, Date fnsh_date) {
        this.id = id;
        this.name = name;
        this.adress = adress;
        this.mail = mail;
        this.mpno = mpno;
        this.star = star;
        this.strt_date = strt_date;
        this.fnsh_date = fnsh_date;
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

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public java.sql.Date getStrt_date() {
        return (java.sql.Date) strt_date;
    }

    public void setStrt_date(String strt_date) throws ParseException {
        this.strt_date= converToDate(strt_date);
    }

    public java.sql.Date getFnsh_date() {
        return (java.sql.Date) fnsh_date;
    }

    public void setFnsh_date(String fnsh_date) throws ParseException {
        this.fnsh_date = converToDate(fnsh_date);
    }

    private Date converToDate(String dateString) throws  ParseException{
        SimpleDateFormat format  = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date parsed = format.parse(dateString);
        return new Date(parsed.getTime());
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", adress='" + adress + '\'' +
                ", mail='" + mail + '\'' +
                ", mpno='" + mpno + '\'' +
                ", star='" + star + '\'' +
                ", strt_date=" + strt_date +
                ", fnsh_date=" + fnsh_date +
                '}';
    }
}
