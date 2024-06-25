package view.EmployeeView;

import business.HotelManager;
import business.RoomManager;
import business.UserManager;
import core.ComboItem;
import core.Helper;
import entity.Hotel;
import entity.Room;
import entity.User;
import org.postgresql.jdbc2.ArrayAssistant;
import view.HotelView;
import view.Layout;
import view.RoomView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
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
    private JComboBox cmb_room_roomtype;
    private JComboBox cmb_room_television;
    private JComboBox cmb_room_minibar;
    private JComboBox cmb_room_gameconsole;
    private JComboBox cmb_room_safe;
    private JComboBox cmb_room_projection;
    private JButton btn_cancel_room;
    private JButton btn_search_room;
    private JTextField fld_room_bed_count;
    private JComboBox cmb_rooms_hotel;
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
    private static  Object[] col_room={"Oda ID","Otel Name","Yatak Sayısı","Oda Boyutu","Oda Adet","Oda Tipi","Televizyon","Mini Bar","Oyun Konsolu","Kasa","Projeksiyon"};
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
        loadRoomCompanent();




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

        ArrayList<Object[]> roomList = this.roomManager.getForTable(col_room.length,this.roomManager.findAll());
        this.createTable(this.tmdl_room,this.tbl_room,col_room,roomList);
    }
    public void loadRoomTable( ArrayList<Object[]> roomRowListBySearch){
        ArrayList<Object[]> roomList = roomRowListBySearch;
        this.createTable(this.tmdl_room,this.tbl_room,col_room,roomList);
    }

    public void loadRoomCompanent(){
        this.cmb_room_roomtype.setModel(new DefaultComboBoxModel<>(Room.RoomType.values()));
        tableRowSelected(this.tbl_room);
        this.room_menu = new JPopupMenu();
        this.room_menu.add("Yeni").addActionListener(e -> {
            RoomView roomView = new RoomView(new Room());
            roomView.addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosed(WindowEvent e){
                    loadRoomTable();
                }
            });
        });

        this.room_menu.add("Güncelle").addActionListener(e -> {
            int selectModelId = this.getTableSelectedRow(tbl_room, 0);
            RoomView roomView = new RoomView(this.roomManager.getById(selectModelId));
            roomView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadRoomTable();
                }
            });
        });
        this.room_menu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectRoomId = this.getTableSelectedRow(tbl_room, 0);
                if (this.roomManager.delete(selectRoomId)) {
                    Helper.showMsg("done");
                    loadRoomTable();
                } else {
                    Helper.showMsg("error");
                }
            }

        });

        btn_search_room.addActionListener(e -> {
            ComboItem selectedHotel = (ComboItem) this.cmb_rooms_hotel.getSelectedItem();
            int hotelId = 0;
            if (selectedHotel != null) {
                hotelId = selectedHotel.getKey();
            }
            ArrayList<Room> roomList = this.roomManager.searchForTable(
                    hotelId,
                    (Room.RoomType) cmb_room_roomtype.getSelectedItem(),
                    (Room.Television) cmb_room_television.getSelectedItem(),
                    (Room.MiniBar) cmb_room_minibar.getSelectedItem(),
                    (Room.GameConsole) cmb_room_gameconsole.getSelectedItem(),
                    (Room.Safe) cmb_room_safe.getSelectedItem(),
                    (Room.Projection) cmb_room_projection.getSelectedItem(),
                    fld_room_bed_count.getText()
            );
            ArrayList<Object[]> roomRowListBySearch = this.roomManager.getForTable(this.col_room.length, roomList);
            loadRoomTable(roomRowListBySearch);
        });
        btn_cancel_room.addActionListener(e -> {
            this.cmb_room_roomtype.setSelectedItem(null);
            this.cmb_room_television.setSelectedItem(null);
            this.cmb_room_minibar.setSelectedItem(null);
            this.cmb_room_gameconsole.setSelectedItem(null);
            this.cmb_room_safe.setSelectedItem(null);
            this.cmb_room_projection.setSelectedItem(null);
            this.loadRoomTable();

        });



    }




}
