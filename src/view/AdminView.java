package view;

import business.UserManager;
import core.Helper;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class AdminView extends Layout{
    private JPanel container;
    private JPanel pnl_top;
    private JLabel lbl_welcome;
    private JTabbedPane tab_menu;
    private JPanel pnl_user;
    private JTable tbl_user;
    private JScrollPane scrl_user;
    private JComboBox<User.Role> cmb_user;
    private JButton btn_search;
    private  User user;
    private UserManager userManager;
    private DefaultTableModel tmdl_user = new DefaultTableModel();
    private JPopupMenu user_menu;

    public AdminView(User user){
        this.userManager=new UserManager();
        this.add(container);
        this.guiInitilaze(1000,500);
        this.user=user;

        if (this.user==null){
            dispose();
        }
        this.lbl_welcome.setText("Hoşgeldiniz : " + this.user.getUsername());

        loadUserTable();
        loadUserComponent();


        //Sağ tıklama sorununu bu şekilde çözdüm!!!
        this.tbl_user.addMouseListener(new MouseAdapter() {
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
                    user_menu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        this.tbl_user.setComponentPopupMenu(user_menu);




    }
    public void loadUserTable(){
        Object[] col_user = {"Kullanıcı ID","Kullanıcı Adı","Kullanıcı Şifre","Kullanıcı Pozisyonu"};
        ArrayList<Object[]> userList= this.userManager.getForTable(col_user.length);
        this.createTable(this.tmdl_user,this.tbl_user,col_user,userList);
    }

    public void loadUserComponent(){
        tableRowSelected(this.tbl_user);
        this.user_menu= new JPopupMenu();

        this.user_menu.add("Yeni").addActionListener(e -> {
            AdminView adminView = new AdminView(null);
            adminView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadUserTable();
                }
            });
        });
        this.user_menu.add("Güncelle").addActionListener(e -> {
            int selectUserId = this.getTableSelectedRow(tbl_user,0);
            AdminView adminView = new AdminView(this.userManager.getById(selectUserId));
            adminView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadUserTable();
                }
            });
        });
        this.user_menu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure")){
                int selectedUserId = this.getTableSelectedRow(tbl_user,0);
                if (this.userManager.delete(selectedUserId)){
                    Helper.showMsg("done");
                    loadUserTable();
                }else {
                    Helper.showMsg("error");
                }
            }
        });
    }

    public void loadUserFilter(){
        this.cmb_user.setModel(new DefaultComboBoxModel<>(User.Role.values()));
        this.cmb_user.setSelectedItem(null);

        //Filtreleme işlemi için ActionListener
        this.cmb_user.addActionListener(e -> {
            User.Role selectedRole = (User.Role) cmb_user.getSelectedItem();
            if (selectedRole!=null){
                //ArrayList<Object[]> filteredUserList = this.userManager.getForTable(col_user.length,selectedRole);
            }else {
                loadUserTable();//Tüm kullanıcıları yükler.
            }
        });
    }



}
