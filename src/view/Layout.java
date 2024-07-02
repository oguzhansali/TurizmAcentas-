package view;

import core.Helper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Layout extends JFrame {


    // GUI'nin başlangıç ayarlarını yapılandırma
    public void guiInitilaze(int width, int height) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Pencere kapatıldığında sadece bu pencereyi kapat
        this.setTitle("Turizm Acenta");// Pencere başlığı
        this.setSize(width, height);// Pencere boyutu
        this.setLocation(Helper.getLocationPoint("x", this.getSize()), Helper.getLocationPoint("y", this.getSize())); // Pencere konumu
        this.setVisible(true);// Pencereyi görünür yap
    }

    //Tabloları oluşturma.
    public void createTable(DefaultTableModel user, JTable table, Object[] columns, ArrayList<Object[]> rows) {
        user.setColumnIdentifiers(columns);// Tablo sütun başlıklarını ayarla
        table.setModel(user);// Tablo modelini ayarla
        table.getTableHeader().setReorderingAllowed(false);// Sütunların yeniden sıralanmasını engelle
        table.getColumnModel().getColumn(0).setMaxWidth(75); // İlk sütunun maksimum genişliğini ayarla
        table.setEnabled(false);// Tabloyu düzenlenemez yap

        DefaultTableModel clearUser = (DefaultTableModel) table.getModel();
        clearUser.setRowCount(0);// Tabloyu temizle

        if (rows == null) {
            rows = new ArrayList<>();
        }
        for (Object[] row : rows) {
            user.addRow(row);
        }
    }

    // Seçilen tablo satırının belirli bir indeksteki değerini al
    public int getTableSelectedRow(JTable table, int index) {
        return Integer.parseInt(table.getValueAt(table.getSelectedRow(), index).toString());
    }

    // Tabloda satıra tıklama olayı
    public void tableRowSelected(JTable table) {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selected_row = table.rowAtPoint(e.getPoint());
                table.setRowSelectionInterval(selected_row, selected_row);
            }
        });

    }

}
