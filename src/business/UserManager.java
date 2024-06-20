package business;

import core.Helper;
import dao.UserDao;
import entity.User;

import java.util.ArrayList;

public class UserManager {

    private UserDao userDao = new UserDao();

    //Alınan parametreler findByLogin metodunda çağrılırlar ve veri tabanında user bulunursa bir User nesnesi döner.
    public User findByLogin(String username,String password){

        return this.userDao.findByLogin(username,password);
    }
    public User getById(int id){
        return this.userDao.getById(id);
    }
    public ArrayList<User> findAll(){
        return this.userDao.findAll();
    }

    public ArrayList<Object[]> getForTable(int size){
        ArrayList<Object[]> userObjList = new ArrayList<>();
        ArrayList<User> userList = this.findAll();
        for (User obj: userList){
            int i=0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getUsername();
            rowObject[i++] = obj.getPassword();
            rowObject[i++] = obj.getRole();
            userObjList.add(rowObject);
        }
        return userObjList;

    }
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

    public boolean save(User user){
        if (this.getById(user.getId())!=null){
            Helper.showMsg("error");
            return false;
        }
        return this.userDao.save(user);
    }
    public boolean update(User user){
        if (this.getById(user.getId())==null){
            Helper.showMsg(user.getId()+" ID kayıtlı user bulunamadı.");
            return false;
        }
        return this.userDao.update(user);
    }
    public boolean delete(int id){
        if (this.getById(id)==null){
            Helper.showMsg(id+ " ID kayıtlı user bulunamadı");
            return false;
        }
        return this.userDao.delete(id);
    }

}
