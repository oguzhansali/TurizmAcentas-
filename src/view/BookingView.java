package view;

import business.BookManager;
import core.Helper;
import entity.Book;
import entity.Hotel;
import entity.Room;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BookingView extends Layout {
    private JPanel container;
    private JLabel lbl_hotel_info;
    private JTextField fld_booking_costumer_name;
    private JTextField fld_booking_costumer_lastname;
    private JTextField fld_booking_identity_num;
    private JTextField fld_booking_mpno;
    private JTextField fld_booking_strt_date;
    private JTextField fld_booking_fnsh_date;
    private JButton btn_booking_save;
    private BookManager bookManager;
    private Room room;
    private Hotel hotel;
    private Book book;

    //Yeni bir rezervasyon ekranı oluşturur
    public BookingView(Room selectedRoom, String strt_date, String fnsh_date) {
        this.room = selectedRoom;
        this.bookManager = new BookManager();

        this.add(container); // Container'ı ekrana ekler
        guiInitilaze(300, 400);// GUI'yi belirtilen boyutlarda başlatır

        lbl_hotel_info.setText("Hotel : " + this.room.getHotel().getName()
        );

        // Başlangıç ve bitiş tarihlerini alanlara yazdırır
        this.fld_booking_strt_date.setText(strt_date);
        this.fld_booking_fnsh_date.setText(fnsh_date);


        // Rezervasyonu kaydetmek için butona tıklanınca yapılacak işlemler
        btn_booking_save.addActionListener(e -> {
            JTextField[] checkFieldList = {
                    this.fld_booking_costumer_name,
                    this.fld_booking_costumer_lastname,
                    this.fld_booking_identity_num,
                    this.fld_booking_mpno,
                    this.fld_booking_strt_date,
                    this.fld_booking_fnsh_date,
            };
            if (Helper.isFieldListEmpty(checkFieldList)) {
                Helper.showMsg("fill"); // Boş alan varsa uyarı gösterir
            } else {
                Book book = new Book();
                book.setbCase("done");// Rezervasyon durumu belirtilir
                book.setRoom_id(this.room.getId());
                book.setCostumerName(this.fld_booking_costumer_name.getText());
                book.setCostumerLastname(this.fld_booking_costumer_lastname.getText());
                book.setStrt_date(LocalDate.parse(fld_booking_strt_date.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                book.setFnsh_date(LocalDate.parse(fld_booking_fnsh_date.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                book.setIdentityNum(this.fld_booking_identity_num.getText());
                book.setMpno(this.fld_booking_mpno.getText());
                // Rezervasyonu kaydeder ve odanın stok durumunu günceller
                if (this.bookManager.save(book)) {
                    this.bookManager.updateRoomStock(this.room);
                    Helper.showMsg("done");// Başarılı mesajı gösterir
                    dispose();// Pencereyi kapatır
                } else {
                    Helper.showMsg("error"); // Hata mesajı gösterir
                }

            }

        });
    }

    //Var olan bir rezervasyonu düzenlemek için ekran oluşturur
    public BookingView(Book book) {
        this.book = book;
        this.bookManager = new BookManager();

        this.add(container);
        guiInitilaze(300, 400);
        this.fld_booking_costumer_name.setText(book.getCostumerName());
        this.fld_booking_costumer_lastname.setText(book.getCostumerLastname());
        this.fld_booking_identity_num.setText(book.getIdentityNum());
        this.fld_booking_mpno.setText(book.getMpno());
        this.fld_booking_strt_date.setText(book.getStrt_date().toString());
        this.fld_booking_fnsh_date.setText(book.getFnsh_date().toString());


        // Var olan rezervasyonu kaydetmek için butona tıklanınca yapılacak işlemler
        btn_booking_save.addActionListener(e -> {
            JTextField[] checkFieldList = {
                    this.fld_booking_costumer_name,
                    this.fld_booking_costumer_lastname,
                    this.fld_booking_identity_num,
                    this.fld_booking_mpno,
                    this.fld_booking_strt_date,
                    this.fld_booking_fnsh_date,
            };
            if (Helper.isFieldListEmpty(checkFieldList)) {
                Helper.showMsg("fill");
            } else {
                book.setbCase("done");
                book.setCostumerName(this.fld_booking_costumer_name.getText());
                book.setCostumerLastname(this.fld_booking_costumer_lastname.getText());
                book.setStrt_date(LocalDate.parse(fld_booking_strt_date.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                book.setFnsh_date(LocalDate.parse(fld_booking_fnsh_date.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                book.setIdentityNum(this.fld_booking_identity_num.getText());
                book.setMpno(this.fld_booking_mpno.getText());

                // Rezervasyonu günceller ve başarılı mesajı gösterir
                if (this.bookManager.update(book)) {
                    Helper.showMsg("done");
                    dispose();
                } else {
                    Helper.showMsg("error");
                }

            }

        });
    }


}
