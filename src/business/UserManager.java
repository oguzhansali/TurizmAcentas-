package business;

import core.Helper;
import dao.UserDao;
import entity.User;

import java.util.ArrayList;

public class UserManager {

    private UserDao userDao = new UserDao();

    // Kullanıcı adı ve şifre ile giriş yapmayı sağlayan metot.
    // Veritabanında kullanıcı bulunursa ilgili User nesnesi döner.
    public User findByLogin(String username, String password) {

        return this.userDao.findByLogin(username, password);
    }

    // ID'ye göre kullanıcı getiren metot.
    public User getById(int id) {
        return this.userDao.getById(id);
    }

    // Tüm kullanıcıları getiren metot.
    public ArrayList<User> findAll() {
        return this.userDao.findAll();
    }

    // Tablo gösterimi için kullanıcıları hazırlayan metot.
    public ArrayList<Object[]> getForTable(int size) {
        ArrayList<Object[]> userObjList = new ArrayList<>();
        ArrayList<User> userList = this.findAll();
        for (User obj : userList) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getUsername();
            rowObject[i++] = obj.getPassword();
            rowObject[i++] = obj.getRole();
            userObjList.add(rowObject);
        }
        return userObjList;

    }

    // Belirli bir roldeki kullanıcıları tablo gösterimi için hazırlayan metot.
    public ArrayList<Object[]> getForTable(int size, User.Role role) {
        ArrayList<Object[]> userObjList = new ArrayList<>();
        ArrayList<User> userList = this.findAll();
        for (User obj : userList) {
            if (obj.getRole() == role) {
                int i = 0;
                Object[] rowObject = new Object[size];
                rowObject[i++] = obj.getId();
                rowObject[i++] = obj.getUsername();
                rowObject[i++] = obj.getPassword();
                rowObject[i++] = obj.getRole();
                userObjList.add(rowObject);
            }
        }
        return userObjList;
    }

    // Kullanıcı kaydetme metodu.
    public boolean save(User user) {
        if (user.getId() != null && this.getById(user.getId()) != null) {
            Helper.showMsg("error");
            return false;
        }
        return this.userDao.save(user);
    }

    // Kullanıcı güncelleme metodu.
    public boolean update(User user) {
        if (this.getById(user.getId()) == null) {
            Helper.showMsg(user.getId() + " ID kayıtlı user bulunamadı.");
            return false;
        }
        return this.userDao.update(user);
    }

    // Kullanıcı silme metodu.
    public boolean delete(int id) {
        if (this.getById(id) == null) {
            Helper.showMsg(id + " ID kayıtlı user bulunamadı");
            return false;
        }
        return this.userDao.delete(id);
    }

    // Kullanıcı ekleme metodu.
    public boolean add(User user) {
        return this.save(user);
    }

}
