/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *
 * @author foet1
 */
public class Database {
    private final String DB_url = "jdbc:mysql://localhost";
    private String user;
    private String password;
    private int port;
    public String getUser() {
        return user;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

 

 
   
    
    public Database(String user, String password, int port) {
        this.user = user;
        this.password = password;
        this.port = port;
    }
    
    public boolean checkClass(){
       try {
        Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        return false;
        }
       return true;
    }
    
    public void createDB(){
      if(checkClass()){
        try{
            Connection conn = DriverManager.getConnection(DB_url +":" + port +"/",user,password);
            Statement st = (Statement) conn.createStatement();
            String sql = "CREATE DATABASE IF NOT EXISTS user;";
            st.executeUpdate(sql);
            System.out.println("Create database successfully.");
        } catch(SQLException e){
            System.out.println("Fail to create database.");
        } 
      }  else{
          System.out.println("Class for MySQL not found");
      }
    }
    
    public void createTable(){
        if(checkClass()){
           try{
            Connection conn = DriverManager.getConnection(DB_url +":" + port +"/" + "user",user,password);
            Statement st = (Statement) conn.createStatement();
            String sql_1 = "USE user;";
            String sql_2 = "CREATE TABLE IF NOT EXISTS userInfor" +
                    "( id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "firstName VARCHAR(255), " +
                    "lastName VARCHAR(255), " +
                    "username VARCHAR(255) UNIQUE, " +
                    "password NVARCHAR(255), " +
                    "email NVARCHAR(255) UNIQUE);";
            conn.setAutoCommit(false);
            st.addBatch(sql_1);
            st.addBatch(sql_2);
            st.executeBatch();
            conn.commit();
               System.out.println("Create table successfully.");
           } catch(SQLException e){
               System.out.println("Fail to create table.");
           }
        } else{
            System.out.println("Class for MySQL not found");
        }
    }
 

    public boolean checkUsernameAndEmail(String username, String email){
         if(checkClass()){
            try{
        Connection conn = DriverManager.getConnection(DB_url +":" + port +"/" + "user",user,password);
        Statement statement = (Statement) conn.createStatement();
        String query1 = "SELECT username,email FROM user WHERE username = \'" + username + "\' OR email = \'" + email + "\'";
        ResultSet rs = statement.executeQuery(query1);
        return rs.next();
            }catch(SQLException e){
                System.out.println("Fail to search data.");
                return false;
            }
        }else{
             System.out.println("Class for MySQL not found");
             return false;
        }
    }
    

    public void Add_data(String firstName, String lastName, String username, String passwordUser, String email) {
        if(checkClass()){
            try{
        Connection conn = DriverManager.getConnection(DB_url +":" + port +"/" + "user",user,password);
        Statement statement = (Statement) conn.createStatement();
        String query = "INSERT INTO userInfor(firstName,lastName,username,password,email) VALUES (\'" +  firstName + "\',\'" + lastName + "\',\'" + username + "\',\'" + passwordUser + "\',\'" + email + "\')";
        statement.executeUpdate(query);
            }catch(SQLException e){
                System.out.println(e);
                System.out.println("Fail to add data to table.");
            }
        } else{
             System.out.println("Class for MySQL not found");
        }
    }
    
    public boolean Search_data(String username, String passwordUser){
        if(checkClass()){
            try{
        Connection conn = DriverManager.getConnection(DB_url +":" + port +"/" + "user",user,password);
        Statement statement = (Statement) conn.createStatement();
        String query1 = "SELECT username,password FROM userInfor WHERE username = \'" + username + "\' AND password = \'" + passwordUser + "\'";
        ResultSet rs = statement.executeQuery(query1);
        return rs.next();
            }catch(SQLException e){
                System.out.println(e);
                System.out.println("Fail to search data.");
                return false;
            }
        }else{
             System.out.println("Class for MySQL not found");
             return false;
        }
    }
}
