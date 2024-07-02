package view;

import business.HotelManager;
import core.Helper;
import entity.Hotel;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private JComboBox<Hotel.Star> cmb_hotel_star;
    private JButton btn_hotel_save;
    private JComboBox<Hotel.HostelType> cmb_hostel1;
    private JComboBox<Hotel.FacilityFeature> cmb_facility1;
    private JComboBox<Hotel.HostelType> cmb_hostel2;
    private JComboBox<Hotel.FacilityFeature> cmb_facility2;
    private JComboBox<Hotel.FacilityFeature> cmb_facility3;
    private JTextField fld_hotel_high_season_fnsh;
    private JTextField fld_hotel_high_season_strt;
    private JTextField fld_hotel_open;
    private JTextField fld_hotel_close;

    private HotelManager hotelManager;
    private Hotel hotel;

    // HotelView sınıfının yapıcı methodu
    public HotelView(Hotel hotel) {
        this.hotelManager = new HotelManager(); // HotelManager sınıfı örneği oluşturuldu
        this.hotel = hotel; // Parametre olarak gelen hotel nesnesi atanır

        this.add(container);// 'container' bileşeni eklendi
        this.guiInitilaze(1000, 1000);// Genişlik ve yükseklikle GUI başlatıldı

        // ComboBox'lar için model setlemeleri yapıldı
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

            // Hostel türleri ve tesis özelliklerini setleme
            this.cmb_hostel1.getModel().setSelectedItem(this.hotel.getHostelType().get(0));
            this.cmb_hostel2.getModel().setSelectedItem(this.hotel.getHostelType().get(1));
            this.cmb_facility1.getModel().setSelectedItem(this.hotel.getFacilityFeature().get(0));
            this.cmb_facility2.getModel().setSelectedItem(this.hotel.getFacilityFeature().get(1));
            this.cmb_facility3.getModel().setSelectedItem(this.hotel.getFacilityFeature().get(2));

            // Tarih alanlarını biçimleyerek setleme
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            if (this.hotel.getHigh_season_strt_date() != null) {
                this.fld_hotel_high_season_strt.setText(this.hotel.getHigh_season_strt_date().format(formatter));
            }

            if (this.hotel.getHigh_season_fnsh_date() != null) {
                this.fld_hotel_high_season_fnsh.setText(this.hotel.getHigh_season_fnsh_date().format(formatter));
            }

            if (this.hotel.getHotel_open() != null) {
                this.fld_hotel_open.setText(this.hotel.getHotel_open().format(formatter));
            }

            if (this.hotel.getHotel_close() != null) {
                this.fld_hotel_close.setText(this.hotel.getHotel_close().format(formatter));
            }
        }

        // Kaydet butonunun action listener'ı
        btn_hotel_save.addActionListener(e -> {
            if (checkIsEmpty()) {
                Helper.showMsg("fill");// Eğer alanlar boşsa uyarı göster
            } else {
                try {
                    boolean result;
                    Hotel newHotel = new Hotel();
                    // Hotel özelliklerini setleme
                    newHotel.setName(fld_hotel_name.getText());
                    newHotel.setAdress(fld_hotel_adress.getText());
                    newHotel.setMail(fld_hotel_mail.getText());
                    newHotel.setMpno(fld_hotel_mpno.getText());
                    newHotel.setStar(String.valueOf((Hotel.Star) cmb_hotel_star.getSelectedItem()));

                    try {
                        // Hostel türleri ve tesis özelliklerini setleme
                        ArrayList<Hotel.HostelType> hostelTypes = new ArrayList<>();
                        hostelTypes.add((Hotel.HostelType) cmb_hostel1.getSelectedItem());
                        hostelTypes.add((Hotel.HostelType) cmb_hostel2.getSelectedItem());
                        newHotel.setHostelTypes(hostelTypes);

                        ArrayList<Hotel.FacilityFeature> facilityFeatures = new ArrayList<>();
                        facilityFeatures.add((Hotel.FacilityFeature) cmb_facility1.getSelectedItem());
                        facilityFeatures.add((Hotel.FacilityFeature) cmb_facility2.getSelectedItem());
                        facilityFeatures.add((Hotel.FacilityFeature) cmb_facility3.getSelectedItem());
                        newHotel.setFacilityFeatures(facilityFeatures);
                    } catch (ClassCastException exc) {
                        exc.printStackTrace();
                    }

                    // Tarih alanlarını biçimleyerek setleme
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDate highstartDate = LocalDate.parse(fld_hotel_high_season_strt.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                        LocalDate hihgFinishDate = LocalDate.parse(fld_hotel_high_season_fnsh.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                        LocalDate lowStartDate = LocalDate.parse(fld_hotel_open.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                        LocalDate lowFinishDate = LocalDate.parse(fld_hotel_close.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                        newHotel.setHigh_season_strt_date(highstartDate);
                        newHotel.setHigh_season_fnsh_date(hihgFinishDate);
                        newHotel.setHotel_open(lowStartDate);
                        newHotel.setHotel_close(lowFinishDate);

                    } catch (DateTimeParseException exception) {
                        exception.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Lütfen Tarihleri doğru formatta giriniz (dd/MM/yyyy)");
                        return; // Tarih formatı hatası olduğunda işlemi sonlandırır
                    }

                    // Eğer 'hotel' nesnesi null değilse ve ID'si 0 değilse güncelleme yap, değilse yeni kayıt ekle
                    if (this.hotel != null && this.hotel.getId() != 0) {
                        newHotel.setId(this.hotel.getId());
                        result = this.hotelManager.update(newHotel);
                    } else {
                        result = this.hotelManager.save(newHotel);
                    }
                    if (result) {
                        Helper.showMsg("done");// Başarılı mesajı göster
                        dispose();// Pencereyi kapat
                    } else {
                        Helper.showMsg("error");// Hata mesajı göster
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();// Tarih formatı hatası durumunda hata mesajı ve hata detayı göster
                }
            }
        });
    }

    // Alanların boş olup olmadığını kontrol eden method
    public boolean checkIsEmpty() {
        return Helper.isFieldEmpty(this.fld_hotel_name) ||
                Helper.isFieldEmpty(this.fld_hotel_adress) ||
                Helper.isFieldEmpty(this.fld_hotel_mail) ||
                Helper.isFieldEmpty(this.fld_hotel_mpno) ||
                Helper.isFieldEmpty(this.fld_hotel_high_season_strt) ||
                Helper.isFieldEmpty(this.fld_hotel_high_season_fnsh) ||
                Helper.isFieldEmpty(this.fld_hotel_open) ||
                Helper.isFieldEmpty(this.fld_hotel_close) ||
                this.cmb_hotel_star.getSelectedItem() == null ||
                this.cmb_facility1.getSelectedItem() == null ||
                this.cmb_facility2.getSelectedItem() == null ||
                this.cmb_facility3.getSelectedItem() == null ||
                this.cmb_hostel1.getSelectedItem() == null ||
                this.cmb_hostel2.getSelectedItem() == null;

    }


}
