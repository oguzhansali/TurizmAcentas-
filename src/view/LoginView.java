package view;

import business.UserManager;
import core.Helper;
import entity.User;
import view.EmployeeView.EmployeeView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends Layout {
    private JPanel container;
    private JLabel lbl_welcome;
    private JLabel lbl_welcome2;
    private JTextField fld_username;
    private JLabel lbl_username;
    private JLabel lbl_password;
    private JButton btn_login;
    private JPanel w_buttom;
    private JPasswordField fld_pass;
    private JPanel w_top;
    private final UserManager userManager;


    // LoginView sınıfının yapıcı methodu
    public LoginView() {
        this.userManager = new UserManager();// UserManager sınıfı örneği oluşturuldu
        this.add(container); // 'container' bileşeni eklendi
        this.guiInitilaze(400, 400);// Genişlik ve yükseklikle GUI başlatıldı

        // Giriş butonunun action listener'ı
        btn_login.addActionListener(e -> {
            JTextField[] checkFildList = {this.fld_username, this.fld_pass};
            if (Helper.isFieldListEmpty(checkFildList)) {
                Helper.showMsg("fill"); // Eğer kullanıcı adı veya şifre alanları boşsa uyarı göster
            } else {
                // Kullanıcıyı kullanıcı adı ve şifresine göre bul
                User loginUser = this.userManager.findByLogin(this.fld_username.getText(), this.fld_pass.getText());

                if (loginUser == null) {
                    Helper.showMsg("notFound");// Kullanıcı bulunamazsa uyarı göster
                } else {
                    // Kullanıcı bulunduysa rolüne göre ilgili görünümü aç
                    if (loginUser.getRole() == User.Role.ADMİN) {
                        AdminView adminView = new AdminView(loginUser);
                    } else if (loginUser.getRole() == User.Role.EMPLOYEE) {
                        EmployeeView employeeView = new EmployeeView(loginUser);
                    }
                    dispose();// LoginView penceresini kapat
                }
            }

        });
    }


}
