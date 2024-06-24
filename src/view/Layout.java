package view;

import core.Helper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Layout extends JFrame {
    public void guiInitilaze(int width,int height){
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Turizm Acenta");
        this.setSize(width,height);
        this.setLocation(Helper.getLocationPoint("x",this.getSize()),Helper.getLocationPoint("y",this.getSize()));
        this.setVisible(true);
    }

    //Tabloları oluşturma.
    public void  createTable(DefaultTableModel user, JTable table , Object[] columns, ArrayList<Object[]>rows){
        user.setColumnIdentifiers(columns);
        table.setModel(user);
        table.getTableHeader().setReorderingAllowed(false);
        table.getColumnModel().getColumn(0).setMaxWidth(75);
        table.setEnabled(false);

        DefaultTableModel clearUser = (DefaultTableModel) table.getModel();
        clearUser.setRowCount(0);

        if (rows==null){
            rows= new ArrayList<>();
        }
        for (Object[] row: rows){
            user.addRow(row);
        }
    }
    public int getTableSelectedRow(JTable table, int index){
        return  Integer.parseInt(table.getValueAt(table.getSelectedRow(),index).toString());
    }

    public void tableRowSelected(JTable table){
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selected_row = table.rowAtPoint(e.getPoint());
                table.setRowSelectionInterval(selected_row,selected_row);
            }
        });

    }

}
