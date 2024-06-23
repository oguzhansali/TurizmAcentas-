package view;

import business.HotelManager;
import core.Helper;
import entity.Hotel;
import entity.HotelFacilityFeature;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;

public class HotelView extends Layout {
    private JPanel container;
    private JLabel lbl_hotel;
    private JTextField fld_hotel_name;
    private JTextField fld_hotel_adress;
    private JTextField fld_hotel_mail;
    private JTextField fld_hotel_mpno;
    private JComboBox<Hotel.Star > cmb_hotel_star;
    private JTextField fld_hotel_season_strt;
    private JTextField fld_hotel_season_fnsh;
    private JButton btn_hotel_save;
    private JComboBox cmb_hostel1;
    private JComboBox cmb_facility1;
    private JComboBox cmb_hostel2;
    private JComboBox cmb_facility2;
    private JComboBox cmb_facility3;
    private JComboBox cmb_hostel3;
    private JComboBox cmb_facility4;
    private JComboBox cmb_facility5;
    private JComboBox cmb_facility6;
    private JComboBox cmb_facility7;
    private JComboBox cmb_hostel4;
    private JComboBox cmb_hostel5;
    private JComboBox cmb_hostel6;
    private JComboBox cmb_hostel7;

    private HotelManager hotelManager;

    public HotelView(Hotel hotel){
        this.hotelManager= new HotelManager();
        this.add(container);
        this.guiInitilaze(500,1000);

        if (hotel!=null){
            this.fld_hotel_name.setText(hotel.getName());
        }

        btn_hotel_save.addActionListener(e -> {
            if (this.checkIsEmpty()){
                Helper.showMsg("fill");
            }else {
                try{
                   boolean result;
                    Hotel newHotel = new Hotel();
                    newHotel.setName(fld_hotel_name.getText());
                    newHotel.setAdress(fld_hotel_adress.getText());
                    newHotel.setMail(fld_hotel_mail.getText());
                    newHotel.setMpno(fld_hotel_mpno.getText());
                    newHotel.setStar(cmb_hotel_star.toString());
                    newHotel.setStrt_date(fld_hotel_season_strt.getText());
                    newHotel.setFnsh_date(fld_hotel_season_fnsh.getText());


                    ArrayList<HotelFacilityFeature> hotelFacilityFeatures= new ArrayList<>();

                    HotelFacilityFeature hotelFacilityFeature1=new HotelFacilityFeature();
                    hotelFacilityFeature1.setFacilityType(cmb_facility1.toString());
                    hotelFacilityFeatures.add(hotelFacilityFeature1);
                    HotelFacilityFeature hotelFacilityFeature2=new HotelFacilityFeature();
                    hotelFacilityFeature2.setFacilityType(cmb_facility2.toString());
                    hotelFacilityFeatures.add(hotelFacilityFeature2);
                    HotelFacilityFeature hotelFacilityFeature3=new HotelFacilityFeature();
                    hotelFacilityFeature3.setFacilityType(cmb_facility3.toString());
                    hotelFacilityFeatures.add(hotelFacilityFeature3);
                    HotelFacilityFeature hotelFacilityFeature4=new HotelFacilityFeature();
                    hotelFacilityFeature4.setFacilityType(cmb_facility4.toString());
                    hotelFacilityFeatures.add(hotelFacilityFeature4);
                    HotelFacilityFeature hotelFacilityFeature5=new HotelFacilityFeature();
                    hotelFacilityFeature5.setFacilityType(cmb_facility5.toString());
                    hotelFacilityFeatures.add(hotelFacilityFeature5);
                    HotelFacilityFeature hotelFacilityFeature6=new HotelFacilityFeature();
                    hotelFacilityFeature6.setFacilityType(cmb_facility6.toString());
                    hotelFacilityFeatures.add(hotelFacilityFeature6);
                    HotelFacilityFeature hotelFacilityFeature7=new HotelFacilityFeature();
                    hotelFacilityFeature7.setFacilityType(cmb_facility7.toString());
                    hotelFacilityFeatures.add(hotelFacilityFeature7);

                    hotelManager.saveHotelAndFacilityFeature(newHotel,hotelFacilityFeatures);}
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

     public boolean checkIsEmpty(){
         return  Helper.isFieldEmpty(this.fld_hotel_name)||
                 Helper.isFieldEmpty(this.fld_hotel_adress)||
                 Helper.isFieldEmpty(this.fld_hotel_mail)||
                 Helper.isFieldEmpty(this.fld_hotel_mpno)||
                 Helper.isFieldEmpty(this.fld_hotel_season_strt)||
                 Helper.isFieldEmpty(this.fld_hotel_season_fnsh);
     }



}
