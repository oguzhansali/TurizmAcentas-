package view;

import business.HotelManager;
import core.ComboItem;
import core.Helper;
import entity.Hotel;
import entity.HotelFacilityFeature;
import entity.HotelHostelType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
    private JComboBox<Hotel.HostelType> cmb_hostel1;
    private JComboBox<Hotel.FacilityFeature> cmb_facility1;
    private JComboBox<Hotel.HostelType> cmb_hostel2;
    private JComboBox<Hotel.FacilityFeature> cmb_facility2;
    private JComboBox<Hotel.FacilityFeature> cmb_facility3;
    private HotelManager hotelManager;
    private Hotel hotel;

    public HotelView(Hotel hotel){
        this.hotelManager= new HotelManager();
        this.hotel=hotel;

        this.add(container);
        this.guiInitilaze(1000,1000);

        this.cmb_hotel_star.setModel(new DefaultComboBoxModel<>(Hotel.Star.values()));
        this.cmb_facility1.setModel(new DefaultComboBoxModel<>(Hotel.FacilityFeature.values()));
        this.cmb_facility2.setModel(new DefaultComboBoxModel<>(Hotel.FacilityFeature.values()));
        this.cmb_facility3.setModel(new DefaultComboBoxModel<>(Hotel.FacilityFeature.values()));
        this.cmb_hostel1.setModel(new DefaultComboBoxModel<>(Hotel.HostelType.values()));
        this.cmb_hostel2.setModel(new DefaultComboBoxModel<>(Hotel.HostelType.values()));


        //Hotel Nesnesi null değilse alanları doldurur.
        if (hotel != null) {
            this.fld_hotel_name.setText(this.hotel.getName());
            this.fld_hotel_adress.setText(this.hotel.getAdress());
            this.fld_hotel_mail.setText(this.hotel.getMail());
            this.fld_hotel_mpno.setText(this.hotel.getMpno());
            this.cmb_hotel_star.getModel().setSelectedItem(this.hotel.getStar());
            this.cmb_hostel1.getModel().setSelectedItem(this.hotel.getHostelType());
            this.cmb_hostel2.getModel().setSelectedItem(this.hotel.getHostelType());
            this.cmb_facility1.getModel().setSelectedItem(this.hotel.getFacilityFeature());
            this.cmb_facility2.getModel().setSelectedItem(this.hotel.getFacilityFeature());
            this.cmb_facility3.getModel().setSelectedItem(this.hotel.getFacilityFeature());

            // Tarihleri set etme.
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            this.fld_hotel_season_strt.setText(this.hotel.getStrt_date().format(formatter));
            this.fld_hotel_season_fnsh.setText(this.hotel.getFnsh_date().format(formatter));

        }


        btn_hotel_save.addActionListener(e -> {
            if (checkIsEmpty()) {
                Helper.showMsg("fill");
            } else {
                try {
                    boolean result;
                    Hotel newHotel = new Hotel();
                    newHotel.setName(fld_hotel_name.getText());
                    newHotel.setAdress(fld_hotel_adress.getText());
                    newHotel.setMail(fld_hotel_mail.getText());
                    newHotel.setMpno(fld_hotel_mpno.getText());
                    newHotel.setStar(String.valueOf((Hotel.Star) cmb_hotel_star.getSelectedItem()));

                    // Set hostel types
                    ArrayList<Hotel.HostelType> hostelTypes = new ArrayList<>();
                    hostelTypes.add((Hotel.HostelType) cmb_hostel1.getSelectedItem());
                    hostelTypes.add((Hotel.HostelType) cmb_hostel2.getSelectedItem());
                    newHotel.setHostelTypes(hostelTypes);

                    // Set facility features
                    ArrayList<Hotel.FacilityFeature> facilityFeatures = new ArrayList<>();
                    facilityFeatures.add((Hotel.FacilityFeature) cmb_facility1.getSelectedItem());
                    facilityFeatures.add((Hotel.FacilityFeature) cmb_facility2.getSelectedItem());
                    facilityFeatures.add((Hotel.FacilityFeature) cmb_facility3.getSelectedItem());
                    newHotel.setFacilityFeatures(facilityFeatures);


                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        LocalDate startDate = LocalDate.parse(fld_hotel_season_strt.getText(), formatter);
                        LocalDate finishDate = LocalDate.parse(fld_hotel_season_fnsh.getText(), formatter);
                        newHotel.setStrt_date(startDate);
                        newHotel.setFnsh_date(finishDate);
                    } catch (DateTimeParseException exception) {
                        exception.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Lütfen Tarihleri doğru formatta giriniz (dd-MM-yyyy)");
                        return; // Tarih formatı hatası olduğunda işlemi sonlandırır
                    }

                    if (this.hotel != null && this.hotel.getId() != 0) {
                        newHotel.setId(this.hotel.getId());
                        result = this.hotelManager.update(newHotel);
                    } else {
                        result = this.hotelManager.save(newHotel);
                    }
                    if (result) {
                        Helper.showMsg("done");
                        dispose();
                    } else {
                        Helper.showMsg("error");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
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
                 Helper.isFieldEmpty(this.fld_hotel_season_fnsh)||
                 this.cmb_hotel_star.getSelectedItem() ==null||
                 this.cmb_facility1.getSelectedItem()==null||
                 this.cmb_facility2.getSelectedItem()==null||
                 this.cmb_facility3.getSelectedItem()==null||
                 this.cmb_hostel1.getSelectedItem()==null||
                 this.cmb_hostel2.getSelectedItem()==null;


     }



}
