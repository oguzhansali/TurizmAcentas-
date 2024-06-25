package view.EmployeeView;

import business.HotelManager;
import business.UserManager;
import core.Helper;
import entity.Hotel;
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
    private JTable tbl_facility_feature;
    private UserManager userManager;
    private Hotel hotel;
    private HotelManager hotelManager;
    private DefaultTableModel tmdl_hotel = new DefaultTableModel();
    private DefaultTableModel tmdl_facility_feature = new DefaultTableModel();
    private JPopupMenu hotel_menu;
    private JPopupMenu facility_feature_menu;
    private User user;

    public EmployeeView(User user){
        this.userManager = new UserManager();
        this.hotelManager=new HotelManager();
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
//        this.tbl_facility_feature.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                showPopup(e);
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//                showPopup(e);
//            }
//
//            private void showPopup(MouseEvent e) {
//                if (e.isPopupTrigger()) {
//                    facility_feature_menu.show(e.getComponent(), e.getX(), e.getY());
//                }
//            }
//        });
//
//        this.tbl_facility_feature.setComponentPopupMenu(facility_feature_menu);




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




}
