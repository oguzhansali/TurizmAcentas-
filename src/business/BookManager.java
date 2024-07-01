package business;

import dao.BookDao;
import entity.Book;

public class BookManager {
    private final BookDao bookDao;
    public BookManager(){
        this.bookDao=new BookDao();
    }

    public boolean save(Book book){
        return this.bookDao.save(book);
    }

    public boolean update(Book book){
        return this.bookDao.update(book);
    }
    public boolean delete(int id){
        return this.bookDao.delete(id);
    }

}
