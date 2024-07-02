package core;

import javax.swing.*;
import java.awt.*;

public class Helper {

    // Belirtilen tip ve boyut için pencere konumunu hesaplar
    public static int getLocationPoint(String type, Dimension size) {
        return switch (type) {
            case "x" -> (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
            case "y" -> (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
            default -> 0;
        };
    }

    // Mesaj gösterir
    public static void showMsg(String str) {
        String msg;
        String title;
        switch (str) {
            case "fill":
                msg = "Lütfen tüm alanları doldurunuz!";
                title = "Hata!";
                break;
            case "done":
                msg = "İşlem Başarılı.";
                title = "Sonuç!";
                break;
            case "notFound":
                msg = "Kayıt Bulunamadı.";
                title = "Bulunamadı!";
                break;
            case "error":
                msg = "Hatalı işlem yaptınız";
                title = "Hata!";
                break;
            case "fillDate":
                msg = "Lütfen Arama Yapmak İstediğiz Tarih Aralığını Giriniz.";
                title = "Hata!";
                break;
            default:
                msg = str;
                title = "Mesaj!";
                break;
        }
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    // Kullanıcıya onaylatma mesajı gösterir
    public static boolean confirm(String str) {
        String msg;
        if (str.equals("sure")) {
            msg = "Bu işlemi yapmak istediğine emin misin ?";
        } else {
            msg = str;
        }
        return JOptionPane.showConfirmDialog(null, msg, "Emin misin?", JOptionPane.YES_NO_OPTION) == 0;
    }

    // JTextFiled boş mu diye kontrol eder
    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().trim().isEmpty();
    }

    // JFormattedTextField boş mu diye kontrol eder
    public static boolean isFieldEmpty(JFormattedTextField field) {
        return field.getText().trim().isEmpty();
    }

    // JTextField dizisi içindeki alanların boş olup olmadığını kontrol eder
    public static boolean isFieldListEmpty(JTextField[] fieldList) {
        for (JTextField field : fieldList) {
            if (isFieldEmpty(field)) {
                return true;
            }
        }
        return false;
    }


}
