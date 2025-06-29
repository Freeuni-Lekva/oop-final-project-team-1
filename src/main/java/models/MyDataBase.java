package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDataBase {
    private static Connection dbConnection;
    static{
        try {
                dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz_system","root","Luka2004#");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static Connection getConnection(){
            return dbConnection;
    }

    public static void close(){
        try{
            dbConnection.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}
