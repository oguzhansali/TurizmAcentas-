package view;

import business.HotelManager;
import business.RoomManager;
import core.Helper;
import dao.HotelDao;
import entity.Hotel;
import entity.Room;
import core.ComboItem;

import javax.swing.*;

public class RoomView extends Layout {
    private JPanel container;
    private JLabel lbl_heading;
    private JLabel lbl_roomtype;
    private JComboBox<Room.RoomType> cmb_room_type;
    private JLabel lbl_television;
    private JComboBox<Room.Television> cmb_television;
    private JLabel lbl_minibar;
    private JComboBox<Room.MiniBar> cmb_minibar;
    private JComboBox<Room.GameConsole> cmb_game_console;
    private JLabel lbl_game_console;
    private JComboBox<Room.Safe> cmb_safe;
    private JLabel lbl_safe;
    private JComboBox<Room.Projection> cmb_projection;
    private JLabel lbl_projection;
    private JTextField fld_room_stock;
    private JTextField fld_bed_count;
    private JTextField fld_squaremeter;
    private JLabel lbl_room_stock;
    private JLabel lbl_bed_count;
    private JLabel lbl_squaremeter;
    private JButton btn_save;
    private JComboBox cmb_hotel;
    private JLabel lbl_hotel;
    private JTextField fld_room_adult_price;
    private JTextField fld_room_kid_price;
    private JLabel lbl_adult_price;
    private JLabel lbl_kid_price;
    private Room room;
    private HotelManager hotelManager;
    private RoomManager roomManager;


    // Oda görünümü için yapıcı method
    public RoomView(Room room) {
        this.room = room;
        this.hotelManager = new HotelManager();
        this.roomManager = new RoomManager();
        this.add(container); // 'container' öğesinin başka bir yerde başlatıldığını varsayıyoruz
        this.guiInitilaze(600, 600);// Genişlik ve yükseklikle GUI başlatma

        // Veritabanından otelleri içeren combobox'ı doldurma
        for (Hotel hotel : this.hotelManager.findAll()) {
            this.cmb_hotel.addItem(new ComboItem(hotel.getId(), hotel.getName()));

        }

        // Diğer combobox'ları enum değerleriyle doldurma

        this.cmb_room_type.setModel(new DefaultComboBoxModel<>(Room.RoomType.values()));
        this.cmb_television.setModel(new DefaultComboBoxModel<>(Room.Television.values()));
        this.cmb_minibar.setModel(new DefaultComboBoxModel<>(Room.MiniBar.values()));
        this.cmb_game_console.setModel(new DefaultComboBoxModel<>(Room.GameConsole.values()));
        this.cmb_safe.setModel(new DefaultComboBoxModel<>(Room.Safe.values()));
        this.cmb_projection.setModel(new DefaultComboBoxModel<>(Room.Projection.values()));

        // Var olan bir odayı düzenliyorsak, alanları mevcut verilerle doldur
        if (this.room.getId() != 0) {
            this.fld_bed_count.setText(this.room.getBed_count());
            this.fld_room_stock.setText(this.room.getStock());
            this.fld_squaremeter.setText(this.room.getSquaremeter());
            this.cmb_room_type.getModel().setSelectedItem(this.room.getRoomType());
            this.cmb_television.getModel().setSelectedItem(this.room.getTelevision());
            this.cmb_minibar.getModel().setSelectedItem(this.room.getMiniBar());
            this.cmb_game_console.getModel().setSelectedItem(this.room.getGameConsole());
            this.cmb_safe.getModel().setSelectedItem(this.room.getSafe());
            this.cmb_projection.getModel().setSelectedItem(this.room.getProjection());
            ComboItem defaultRoom = new ComboItem(this.room.getHotel().getId(), this.room.getHotel().getName());
            this.cmb_hotel.getModel().setSelectedItem(defaultRoom);
            this.fld_room_adult_price.setText(this.room.getAdult_price());
            this.fld_room_kid_price.setText(this.room.getKid_price());


        }

        // Kaydet butonunun action listener'ı
        btn_save.addActionListener(e -> {
            // Gerekli alanların boş olup olmadığını kontrol et
            if (Helper.isFieldListEmpty(new JTextField[]{fld_squaremeter, fld_room_stock, fld_bed_count})) {
                Helper.showMsg("fill");
            } else {
                boolean result;

                // Seçilen oteli combobox'dan al
                ComboItem selectedHotel = (ComboItem) cmb_hotel.getSelectedItem();
                this.room.setRoomType((Room.RoomType) cmb_room_type.getSelectedItem());
                this.room.setTelevision((Room.Television) cmb_television.getSelectedItem());
                this.room.setMiniBar((Room.MiniBar) cmb_minibar.getSelectedItem());
                this.room.setRoomType((Room.RoomType) cmb_room_type.getSelectedItem());
                this.room.setGameConsole((Room.GameConsole) cmb_game_console.getSelectedItem());
                this.room.setRoomType((Room.RoomType) cmb_room_type.getSelectedItem());
                this.room.setSafe((Room.Safe) cmb_safe.getSelectedItem());
                this.room.setProjection((Room.Projection) cmb_projection.getSelectedItem());
                this.room.setStock(fld_room_stock.getText());
                this.room.setSquaremeter(fld_squaremeter.getText());
                this.room.setBed_count(fld_bed_count.getText());
                this.room.setHotel_id(selectedHotel.getKey());
                this.room.setAdult_price(fld_room_adult_price.getText());
                this.room.setKid_price(fld_room_kid_price.getText());

                // Oda zaten varsa güncelle, değilse kaydet
                if (this.room.getId() != 0) {
                    result = this.roomManager.update(this.room);
                } else {
                    result = this.roomManager.save(this.room);
                }
                // Sonuca göre uygun mesajı göster
                if (result) {
                    Helper.showMsg("done");
                    dispose();
                } else {
                    Helper.showMsg("error");
                }
            }
        });
    }
}
