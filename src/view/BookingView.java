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
    private JTextField fld_booking_adult_count;
    private JTextField fld_booking_kid_count;
    private JButton btn_booking_save;
    private BookManager bookManager;
    private Room room;
    private Hotel hotel;

    public BookingView(Room selectedRoom,String strt_date,String fnsh_date){
        this.room=selectedRoom;
        this.bookManager=new BookManager();

        this.add(container);
        guiInitilaze(300,400);

        lbl_hotel_info.setText("Hotel : "+this.room.getHotel().getName()+ " / "+
                this.hotel.getHostelType()
        );

        this.fld_booking_strt_date.setText(strt_date);
        this.fld_booking_fnsh_date.setText(fnsh_date);


        btn_booking_save.addActionListener(e -> {
            JTextField[] checkFieldList = {
                    this.fld_booking_costumer_name,
                    this.fld_booking_costumer_lastname,
                    this.fld_booking_identity_num,
                    this.fld_booking_mpno,
                    this.fld_booking_strt_date,
                    this.fld_booking_fnsh_date,
                    this.fld_booking_adult_count,
                    this.fld_booking_kid_count
            };
            if (Helper.isFieldListEmpty(checkFieldList)){
                Helper.showMsg("fill");
            }else{
                Book book = new Book();
                book.setbCase("done");
                book.setRoom_id(this.room.getId());
                book.setCostumerName(this.fld_booking_costumer_name.getText());
                book.setCostumerLastname(this.fld_booking_costumer_lastname.getText());
                book.setStrt_date(LocalDate.parse(strt_date, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                book.setFnsh_date(LocalDate.parse(fnsh_date,DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                book.setIdentityNum(this.fld_booking_identity_num.getText());
                book.setMpno(this.fld_booking_mpno.getText());
                book.setAdultCount(this.fld_booking_adult_count.getText());
                book.setKidCount(this.fld_booking_kid_count.getText());

                if (this.bookManager.save(book)){
                    Helper.showMsg("done");
                    dispose();
                }else {
                    Helper.showMsg("error");
                }
            }

        });
    }

}
