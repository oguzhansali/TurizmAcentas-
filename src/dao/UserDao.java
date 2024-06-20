package dao;

import core.Db;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao {
    private  final Connection con;

    public UserDao() {
        this.con = Db.getInstance();
    }

    //User tablosundan bütün userlar alınır.
    public ArrayList<User> findAll(){
        ArrayList<User> userList = new ArrayList<>();
        String sql  = "SELECT * FROM public.user";

        try {
            ResultSet rs = this.con.createStatement().executeQuery(sql);
            while(rs.next()){
                userList.add(this.match(rs));

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return userList;
    }
    public User getById(int id){
        User obj = null;
        String query = "SELECT * FROM public.user WHERE user_id = ? ";
        try {
            PreparedStatement pr = con.prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) obj = this.match(rs);
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return obj;
    }

    //Username ve password alınarak kullanıcı girişi sağlanacak.
    public User findByLogin(String username,String password){
        User obj = null;
        String query = "SELECT * FROM public.user WHERE user_name = ? AND user_pass = ?";

        try {
            PreparedStatement pr = con.prepareStatement(query);
            pr.setString(1,username);
            pr.setString(2,password);
            ResultSet rs = pr.executeQuery();

            if (rs.next()){
                obj=this.match(rs);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return obj;

    }

    //Kullanıcıdan alınan veriler Db ile eşleştirildi.
    public User match (ResultSet rs) throws SQLException{
        User obj = new User();
        obj.setId(rs.getInt("user_id"));
        obj.setUsername(rs.getString("user_name"));
        obj.setPassword(rs.getString("user_pass"));
        obj.setRole(User.Role.valueOf(rs.getString("user_role").toUpperCase()));
        return obj;
    }

    public boolean save (User user){
        String query = "INSERT INTO public.user"+
                "( "+
                "user_id,"+
                "user_name,"+
                "user_pass,"+
                "user_role"+
                ")"+
                "VALUES (?,?,?,?)";
        try {
            PreparedStatement pr = con.prepareStatement(query);
            pr.setInt(1,user.getId());
            pr.setString(2,user.getUsername());
            pr.setString(3,user.getPassword());
            pr.setString(4,user.getRole().toString());
            return pr.executeUpdate() !=-1;

        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return true;
    }

    public boolean update(User user){
        String query = "UPDATE public.user SET "+
                "user_name = ? ,"+
                "user_pass = ? ,"+
                "user_role = ? "+
                " WHERE  user_id = ? ";
        try {
            PreparedStatement pr = con.prepareStatement(query);
            pr.setString(1, user.getUsername());
            pr.setString(2, user.getPassword());
            pr.setString(3,user.getRole().toString());
            pr.setInt(4,user.getId());
            return  pr.executeUpdate() != -1;
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return  true;
    }
    public  boolean delete (int user){
        String query = "DELETE FROM public.user WHERE user_id = ?";
        try {
            PreparedStatement pr = con.prepareStatement(query);
            pr.setInt(1, user);
            return pr.executeUpdate() !=-1;
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return true;
    }

}
