package business;

import dao.BookDao;
import entity.Book;
import entity.Room;

import java.util.ArrayList;

public class BookManager {
    private final BookDao bookDao;

    public BookManager() {
        this.bookDao = new BookDao();
    }

    // Kitap kaydetme işlemi
    public boolean save(Book book) {
        return this.bookDao.save(book);
    }

    // Kitap güncelleme işlemi
    public boolean update(Book book) {
        return this.bookDao.update(book);
    }

    // Kitap silme işlemi
    public boolean delete(int id) {
        return this.bookDao.delete(id);
    }

    // Oda stokunu güncelleme işlemi
    public boolean updateRoomStock(Room room) {
        return bookDao.updateRoomStock(room);
    }

    public boolean increaseRoomStock(int room_id) {
        return bookDao.increaseRoomStock(room_id);
    }

    // Tablo için veri alımı
    public ArrayList<Object[]> getForTable(int size, ArrayList<Book> reservedList) {
        ArrayList<Object[]> roomObjList = new ArrayList<>();
        for (Book obj : reservedList) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getRoom_id();
            rowObject[i++] = obj.getCostumerName();
            rowObject[i++] = obj.getCostumerLastname();
            rowObject[i++] = obj.getIdentityNum();
            rowObject[i++] = obj.getMpno();
            rowObject[i++] = obj.getStrt_date();
            rowObject[i++] = obj.getFnsh_date();
            roomObjList.add(rowObject);
        }
        return roomObjList;
    }

    // Tüm rezervasyonları getirme işlemi
    public ArrayList<Book> findAll() {
        return this.bookDao.findAll();
    }

    // Belirli bir ID'ye sahip rezervasyonu getirme işlemi
    public Book getById(int id) {
        return this.bookDao.getById(id);
    }
}
