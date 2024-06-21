package view.EmployeeView;

import business.HotelManager;
import core.Helper;
import entity.Hotel;
import entity.User;
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
    private JPanel pnl_facility_feature;
    private JScrollPane scrl_facility_feature;
    private JTable tbl_facility_feature;

    private Hotel hotel;
    private HotelManager hotelManager;
    private DefaultTableModel tmdl_hotel = new DefaultTableModel();
    private DefaultTableModel tmdl_facility_feature = new DefaultTableModel();
    private JPopupMenu hotel_menu;
    private JPopupMenu facility_feature_menu;
    private User user;

    public EmployeeView(User user){
        this.hotel_menu= new ;
        this.add(container);
        this.guiInitilaze(1000,500);
        this.user=user;

        if (this.hotel==null){
            dispose();
        }
        this.lbl_welcome.setText("Hoşgeldiniz : " + this.user.getUsername());



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



        //Sağ tıklama sorununu bu şekilde çözdüm!!!
        this.tbl_facility_feature.addMouseListener(new MouseAdapter() {
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
                    facility_feature_menu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        this.tbl_facility_feature.setComponentPopupMenu(facility_feature_menu);




    }
    public void loadHotelTable(){
        Object[] col_hotel = {"Otel ID","Otel Adı","Otel Adresi","Otel Maili","Telefon Numarası","Yıldız","Sezon Başlangıç","Sezon Bitiş"};
        ArrayList<Object[]> hotelList= this.hotelManager.getForTable(col_hotel.length);
        this.createTable(this.tmdl_hotel,this.tbl_hotel,col_hotel,hotelList);
    }

    public void loadHotelComponent(){
        tableRowSelected(this.tbl_hotel);
        this.hotel_menu= new JPopupMenu();
        //HotelView OLUŞTURULARAK y9eni otel oluşturlacak.
        this.hotel_menu.add("Yeni").addActionListener(e -> {
            EmployeeView employeeView = new EmployeeView(null);
            employeeView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadHotelTable();
                }
            });
            employeeView.setVisible(true);
        });

        this.hotel_menu.add("Güncelle").addActionListener(e -> {
            int selectHotelId = this.getTableSelectedRow(tbl_hotel,0);
            Hotel selectedHotel =this.hotelManager.getById(selectHotelId);
            EmployeeView employeeView = new EmployeeView(selectedHotel);
            employeeView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadHotelTable();
                }
            });
            employeeView.setVisible(true);
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
