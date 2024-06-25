package view.EmployeeView;

import business.HotelManager;
import business.RoomManager;
import business.UserManager;
import core.Helper;
import entity.Hotel;
import entity.Room;
import entity.User;
import view.HotelView;
import view.Layout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class EmployeeView extends Layout {
    private JPanel container;
    private JPanel pnl_top;
    private JLabel lbl_welcome;
    private JTabbedPane tab_menu;
    private JPanel pnl_hotel;
    private JScrollPane scrl_hotel;
    private JTable tbl_hotel;
    private JPanel pnl_room;
    private JTable tbl_room;
    private JScrollPane scrl_room;
    private UserManager userManager;
    private Hotel hotel;
    private HotelManager hotelManager;
    private DefaultTableModel tmdl_hotel = new DefaultTableModel();
    private DefaultTableModel tmdl_room = new DefaultTableModel();
    private JPopupMenu hotel_menu;
    private JPopupMenu room_menu;
    private User user;
    private Room room;
    private RoomManager roomManager;

    public EmployeeView(User user){
        this.userManager = new UserManager();
        this.hotelManager=new HotelManager();
        this.roomManager=new RoomManager();

        this.add(container);
        this.guiInitilaze(1500,1000);
        this.user=user;

        if (this.user==null){
            dispose();
        }
        this.lbl_welcome.setText("Hoşgeldiniz : " + this.user.getUsername());

        //Hotel Tab Menu.
        loadHotelTable();
        loadHotelComponent();

        //Room Menu
        loadRoomTable();




        //Sağ tıklama sorununu bu şekilde çözdüm!!!
        this.tbl_hotel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                showPopup(e);
            }

            private void showPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    hotel_menu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        this.tbl_hotel.setComponentPopupMenu(hotel_menu);
        this.scrl_hotel.setComponentPopupMenu(hotel_menu);



        //Sağ tıklama sorununu bu şekilde çözdüm!!!
        this.tbl_room.addMouseListener(new MouseAdapter() {
            @Override
           public void mousePressed(MouseEvent e) {
               showPopup(e);
           }

            @Override
            public void mouseReleased(MouseEvent e) {
                showPopup(e);
           }

            private void showPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    room_menu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        this.tbl_room.setComponentPopupMenu(room_menu);
        this.scrl_room.setComponentPopupMenu(room_menu);




    }
    public void loadHotelTable(){
        Object[] col_hotel = {"Otel ID",
                "Otel Adı",
                "Otel Adresi",
                "Otel Maili",
                "Telefon Numarası",
                "Yıldız",
                "Sezon Başlangıç",
                "Sezon Bitiş",
                "Tesis Özelliği",
                "Pansiyon Türü"};
        ArrayList<Object[]> hotelList= this.hotelManager.getForTable(col_hotel.length);

        this.createTable(this.tmdl_hotel,this.tbl_hotel,col_hotel,hotelList);
        // Tesis özellikleri ve pansiyon türlerini JComboBox'lardan alıp hotelList'e ekleyin

    }

    public void loadHotelComponent(){
        tableRowSelected(this.tbl_hotel);
        this.hotel_menu= new JPopupMenu();
        //HotelView OLUŞTURULARAK y9eni otel oluşturlacak.
        this.hotel_menu.add("Yeni").addActionListener(e -> {
            HotelView hotelView = new HotelView(null);
            hotelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadHotelTable();
                }
            });
            hotelView.setVisible(true);
        });

        this.hotel_menu.add("Güncelle").addActionListener(e -> {
            int selectHotelId = this.getTableSelectedRow(tbl_hotel,0);

            Hotel hotel=this.hotelManager.getById(selectHotelId);
            HotelView hotelView =new HotelView(hotel);
            hotelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadHotelTable();
                }
            });
            hotelView.setVisible(true);
        });

        this.hotel_menu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure")){
                int selectedHotelId = this.getTableSelectedRow(tbl_hotel,0);
                if (this.hotelManager.delete(selectedHotelId)){
                    Helper.showMsg("done");
                    loadHotelTable();
                }else {
                    Helper.showMsg("error");
                }
            }
        });
    }
    public void loadRoomTable(){
        Object[] col_room={"Oda ID","Otel ID","Yatak Sayısı","Oda Boyutu","Oda Adet","Oda Tipi","Televizyon","Mini Bar","Oyun Konsolu","Kasa","Projeksiyon"};
        ArrayList<Object[]> roomList = this.roomManager.getForTable(col_room.length,this.roomManager.findAll());
        this.createTable(this.tmdl_room,this.tbl_room,col_room,roomList);
    }




}
