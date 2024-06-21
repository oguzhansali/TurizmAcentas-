package view;

import business.UserManager;
import core.Helper;
import entity.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddAdminView extends Layout {
    private JPanel container;
    private JLabel lbl_welcome;
    private JLabel lbl_welcome2;
    private JPanel w_top;
    private JTextField fld_username;
    private JPasswordField fld_password;
    private JComboBox<User.Role> cmb_role;
    private JButton btn_save;
    private JLabel lbl_username;
    private JLabel lbl_password;
    private JLabel lbl_user;
    private UserManager userManager;
    private User user;

    public AddAdminView(User user){
        this.userManager=new UserManager();
        this.add(container);
        this.guiInitilaze(300,500);
        this.user=user;

        //JComboBox modeli ayarlanır.
        cmb_role.setModel(new DefaultComboBoxModel<>(User.Role.values()));

        //Alanları mevcut kullanıcı verileri ile doldurur.
        if (user!=null){
            fld_username.setText(user.getUsername());
            fld_password.setText(user.getPassword());
            cmb_role.setSelectedItem(user.getRole());
        }

        btn_save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSave();
            }
        });

    }
    private void onSave(){
        String username = fld_username.getText();
        String password = new String(fld_password.getPassword());
        User.Role role = (User.Role) cmb_role.getSelectedItem();

        if (username.isEmpty()||password.isEmpty()||role==null){
            JOptionPane.showMessageDialog(this,"Tüm alanları doldurulmalıdır.","Hata",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (user==null){
            user=new User();
        }
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);

        if (user.getId() == null){//id'nin null olduğu durum yeni kullanıcıdır.
           if (userManager.add(user)){
               Helper.showMsg("Kullanıcı başarıyla eklendi.");
               dispose();
           }
        }else {
            if (userManager.update(user)){
                Helper.showMsg("Kullanıcı başarıyla güncellendi.");
                dispose();
            }
        }

    }

}
