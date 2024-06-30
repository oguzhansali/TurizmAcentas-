package view.EmployeeView;

import business.BookManager;
import business.HotelManager;
import business.RoomManager;
import business.UserManager;
import core.ComboItem;
import core.Helper;
import entity.Book;
import entity.Hotel;
import entity.Room;
import entity.User;
import view.BookingView;
import view.HotelView;
import view.Layout;
import view.RoomView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
    private JComboBox<Room.RoomType> cmb_room_roomtype;
    private JComboBox<Room.Television> cmb_room_television;
    private JComboBox<Room.MiniBar> cmb_room_minibar;
    private JComboBox<Room.GameConsole> cmb_room_gameconsole;
    private JComboBox<Room.Safe> cmb_room_safe;
    private JComboBox<Room.Projection> cmb_room_projection;
    private JButton btn_cancel_room;
    private JButton btn_search_room;
    private JTextField fld_room_bed_count;
    private JComboBox<String> cmb_rooms_hotel;
    private JPanel pnl_booking;
    private JTable tbl_booking;
    private JScrollPane scrl_booking;
    private JFormattedTextField fld_strt_date;
    private JFormattedTextField fld_fnsh_date;
    private JComboBox<Room.RoomType> cmb_booking_room_type;
    private JComboBox<Room.Television> cmb_booking_room_tv;
    private JComboBox<Room.MiniBar> cmb_booking_room_minibar;
    private JComboBox<Room.GameConsole> cmb_booking_room_gameconsole;
    private JComboBox<Room.Safe> cmb_booking_room_safe;
    private JComboBox<Room.Projection> cmb_booking_room_projection;
    private JButton btn_booking_search;
    private JButton btn_cncl_booking;
    private UserManager userManager;
    private Hotel hotel;
    private HotelManager hotelManager;
    private DefaultTableModel tmdl_hotel = new DefaultTableModel();
    private DefaultTableModel tmdl_room = new DefaultTableModel();
    private DefaultTableModel tmdl_booking=new DefaultTableModel();
    private JPopupMenu hotel_menu;
    private JPopupMenu room_menu;
    private JPopupMenu booking_menu;
    private User user;
    private Room room;
    private Book book;
    private BookManager bookManager;
    private RoomManager roomManager;
    private Object[] col_hotel;
    private Object [] col_room;

    public EmployeeView(User user) {
        this.userManager = new UserManager();
        this.hotelManager = new HotelManager();
        this.roomManager = new RoomManager();
        this.bookManager=new BookManager();

        this.add(container);
        this.guiInitilaze(1500, 1000);
        this.user = user;

        if (this.user == null) {
            dispose();
        }
        this.lbl_welcome.setText("Hoşgeldiniz : " + this.user.getUsername());//Kullanıcı girişinde arayüzde giirş yapan kullanıcı bilgisi gözlemlenecek.

        this.col_room = new Object[] {"Oda ID", "Otel Name", "Yatak Sayısı", "Oda Boyutu", "Oda Adet", "Oda Tipi", "Televizyon", "Mini Bar", "Oyun Konsolu", "Kasa", "Projeksiyon","Gecelik Yetişkin Ücret","Gecelik Çocuk Ücret"};


        //Hotel Tab Menu.
        loadHotelTable();
        loadHotelComponent();

        //Room Menu
        loadRoomTable();
        loadRoomCompanent();
        loadRoomFilter();

        //Booking Menu
        loadBookingTable(null);
        loadBookingComponent();
        loadBookingFilter();


        // Hotel  ekleme arayüz ekranında mouse işlemleri yapılabilir hale getirildi.
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


        // Oda ekleme arayüz ekranında mouse işlemleri yapılabilir hale getirildi.
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


        // Rezervasyon Arayüz ekranında mouse işlemleri yapılabilir hale getirildi.
        this.tbl_booking.addMouseListener(new MouseAdapter() {
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
                    booking_menu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        this.tbl_booking.setComponentPopupMenu(booking_menu);
        this.scrl_booking.setComponentPopupMenu(booking_menu);



    }

    //Otel verilerinin geleceği tablo oluşturuldu.
    public void loadHotelTable() {
        Object[] col_hotel = {"Otel ID", "Otel Adı", "Otel Adresi", "Otel Maili", "Telefon Numarası", "Yıldız", " Yüksek Sezon Başlangıç", " Yüksek Sezon Bitiş","Alçak Sezon Başlangıç","Alcaçk Sezon Bitiş" ,"Tesis Özelliği", "Pansiyon Türü"};
        ArrayList<Object[]> hotelList = this.hotelManager.getForTable(col_hotel.length);
        this.createTable(this.tmdl_hotel, this.tbl_hotel, col_hotel, hotelList);
        // Tesis özellikleri ve pansiyon türlerini JComboBox'lardan alıp hotelList'e ekleyin

    }

    //Otel işlemlerinin gerçekleştirilmesi için componentler gerçekleştirildi.
    public void loadHotelComponent() {
        tableRowSelected(this.tbl_hotel);
        this.hotel_menu = new JPopupMenu();
        //HotelView OLUŞTURULARAK y9eni otel oluşturlacak.
        this.hotel_menu.add("Yeni").addActionListener(e -> {
            HotelView hotelView = new HotelView(null);
            hotelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadHotelTable();
                    loadRoomTable();
                }
            });
            hotelView.setVisible(true);
        });

        this.hotel_menu.add("Güncelle").addActionListener(e -> {
            int selectHotelId = this.getTableSelectedRow(tbl_hotel, 0);
            Hotel hotel = this.hotelManager.getById(selectHotelId);
            HotelView hotelView = new HotelView(hotel);
            hotelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadHotelTable();
                    loadRoomTable();
                }
            });
            hotelView.setVisible(true);
        });

        this.hotel_menu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectedHotelId = this.getTableSelectedRow(tbl_hotel, 0);
                if (this.hotelManager.delete(selectedHotelId)) {
                    Helper.showMsg("done");
                    loadHotelTable();
                    loadRoomTable();
                } else {
                    Helper.showMsg("error");
                }
            }
        });
    }
    //Static tanımlanmış col_room ile room tablosu oluşturuldu.
    public void loadRoomTable() {
        Object[] col_room = {"Oda ID", "Otel Name", "Yatak Sayısı", "Oda Boyutu", "Oda Adet", "Oda Tipi", "Televizyon", "Mini Bar", "Oyun Konsolu", "Kasa", "Projeksiyon","Gecelik Yetişkin Ücret","Gecelik Çocuk Ücret"};
        ArrayList<Object[]> roomList = this.roomManager.getForTable(col_room.length, this.roomManager.findAll());
        this.createTable(this.tmdl_room, this.tbl_room, col_room, roomList);
    }

    //Oda arama işleminde verileri filtrelendiğinde oluşturulacak tablo.
    public void loadRoomTable(ArrayList<Object[]> roomRowListBySearch) {
        Object[] col_room = {"Oda ID", "Otel Name", "Yatak Sayısı", "Oda Boyutu", "Oda Adet", "Oda Tipi", "Televizyon", "Mini Bar", "Oyun Konsolu", "Kasa", "Projeksiyon","Gecelik Yetişkin Ücret","Gecelik Çocuk Ücret"};
        ArrayList<Object[]> roomList = roomRowListBySearch;
        this.createTable(this.tmdl_room, this.tbl_room, col_room, roomList);
    }

    //ComboBoxları setleme işlemi ve sıfırlama işlemi yapıldı.
    public void loadRoomFilter(){
        List<String> hotelNames = this.hotelManager.getAllHotelNames();
        if (hotelNames != null && !hotelNames.isEmpty()) {
            this.cmb_rooms_hotel.setModel(new DefaultComboBoxModel<>(hotelNames.toArray(new String[0])));
            this.cmb_rooms_hotel.setSelectedItem(null);
        } else {
            // Otel adları boş veya null ise JComboBox'ı boşaltabilir veya bir mesaj gösterebilirsiniz.
            this.cmb_rooms_hotel.setModel(new DefaultComboBoxModel<>());
            this.cmb_rooms_hotel.setSelectedItem(null);
        }

        // Diğer ComboBox'lar için standart yükleme işlemlerini gerçekleştirin
        this.cmb_room_television.setModel(new DefaultComboBoxModel<>(Room.Television.values()));
        this.cmb_room_television.setSelectedItem(null);
        this.cmb_room_roomtype.setModel(new DefaultComboBoxModel<>(Room.RoomType.values()));
        this.cmb_room_roomtype.setSelectedItem(null);
        this.cmb_room_minibar.setModel(new DefaultComboBoxModel<>(Room.MiniBar.values()));
        this.cmb_room_minibar.setSelectedItem(null);
        this.cmb_room_gameconsole.setModel(new DefaultComboBoxModel<>(Room.GameConsole.values()));
        this.cmb_room_gameconsole.setSelectedItem(null);
        this.cmb_room_safe.setModel(new DefaultComboBoxModel<>(Room.Safe.values()));
        this.cmb_room_safe.setSelectedItem(null);
        this.cmb_room_projection.setModel(new DefaultComboBoxModel<>(Room.Projection.values()));
        this.cmb_room_projection.setSelectedItem(null);
        this.fld_room_bed_count.setText("");
    }

    public void loadRoomCompanent() {
        tableRowSelected(this.tbl_room);
        this.room_menu = new JPopupMenu();
        this.room_menu.add("Yeni").addActionListener(e -> {
            RoomView roomView = new RoomView(new Room());
            roomView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadRoomTable();
                    loadBookingTable(null);
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
                    loadBookingTable(null);
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
            Room.RoomType roomType = (Room.RoomType) cmb_room_roomtype.getSelectedItem();
            Room.Television television = (Room.Television) cmb_room_television.getSelectedItem();
            Room.MiniBar miniBar = (Room.MiniBar) cmb_room_minibar.getSelectedItem();
            Room.GameConsole gameConsole = (Room.GameConsole) cmb_room_gameconsole.getSelectedItem();
            Room.Safe safe = (Room.Safe) cmb_room_safe.getSelectedItem();
            Room.Projection projection = (Room.Projection) cmb_room_projection.getSelectedItem();
            String bedCount = fld_room_bed_count.getText();


            ArrayList<Room> roomList = this.roomManager.searchForTable(
                    hotelId,
                    roomType,
                    television,
                    miniBar,
                    gameConsole,
                    safe,
                    projection,
                    bedCount

            );
            ArrayList<Object[]> roomRowListBySearch = this.roomManager.getForTable(this.col_room.length, roomList);
            loadRoomTable(roomRowListBySearch);
        });
        btn_cancel_room.addActionListener(e -> {
            loadRoomFilter();
        });


    }

    private void loadBookingTable(ArrayList<Object[]> roomList) {
        Object[] col_booking_List = {"ID","Oda ID","Müşteri Adı","Müşteri Soyadı","TC. NO.","Telefon No.","Başlangıç Tarihi","Bitiş Tarihi","Oda günlük Ücret","Rezervasyon Toplam Tutar"};
        createTable(this.tmdl_booking,this.tbl_booking,col_booking_List,roomList);
    }
    public void  loadBookingFilter(){
        this.cmb_booking_room_type.setModel(new DefaultComboBoxModel<>(Room.RoomType.values()));
        this.cmb_booking_room_type.setSelectedItem(null);
        this.cmb_booking_room_tv.setModel(new DefaultComboBoxModel<>(Room.Television.values()));
        this.cmb_booking_room_tv.setSelectedItem(null);
        this.cmb_booking_room_minibar.setModel(new DefaultComboBoxModel<>(Room.MiniBar.values()));
        this.cmb_booking_room_minibar.setSelectedItem(null);
        this.cmb_booking_room_gameconsole.setModel(new DefaultComboBoxModel<>(Room.GameConsole.values()));
        this.cmb_booking_room_gameconsole.setSelectedItem(null);
        this.cmb_booking_room_safe.setModel(new DefaultComboBoxModel<>(Room.Safe.values()));
        this.cmb_booking_room_safe.setSelectedItem(null);
        this.cmb_booking_room_projection.setModel(new DefaultComboBoxModel<>(Room.Projection.values()));
        this.cmb_booking_room_projection.setSelectedItem(null);
    }

    private void loadBookingComponent(){
        tableRowSelected(this.tbl_booking);
        this.booking_menu=new JPopupMenu();
        this.booking_menu.add("Rezervasyon Yap").addActionListener(e -> {
            int selectRoomId=this.getTableSelectedRow(this.tbl_booking,0);
            BookingView bookingView = new BookingView(
                    this.roomManager.getById(selectRoomId),
                    this.fld_strt_date.getText(),
                    this.fld_fnsh_date.getText()
            );

            bookingView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBookingTable(null);
                    loadBookingFilter();
                }
            });
        });
        btn_booking_search.addActionListener(e -> {
            ArrayList<Room> roomList = this.roomManager.searchForBooking(
                    fld_strt_date.getText(),
                    fld_fnsh_date.getText(),
                    (Room.RoomType) cmb_booking_room_type.getSelectedItem(),
                    (Room.Television) cmb_booking_room_tv.getSelectedItem(),
                    (Room.MiniBar) cmb_booking_room_minibar.getSelectedItem(),
                    (Room.GameConsole)cmb_booking_room_gameconsole.getSelectedItem(),
                    (Room.Safe)cmb_booking_room_safe.getSelectedItem(),
                    (Room.Projection)cmb_booking_room_projection.getSelectedItem()
            );
            ArrayList<Object[]> roomBookingRow= this.roomManager.getForTable(this.col_room.length,roomList);
            loadBookingTable(roomBookingRow);

        });
        btn_cncl_booking.addActionListener(e -> {
            loadBookingFilter();

        });

    }








}
