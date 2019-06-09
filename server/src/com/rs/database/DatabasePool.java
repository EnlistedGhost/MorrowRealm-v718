package com.rs.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ArrayBlockingQueue;

public class DatabasePool {
	
	public static boolean enableDatabase = true;
	
	public static String[] forumsdb = 	{ "ip_address", "db_user", "db_pass", "database_name" };
	public static String[] votedb = 	{ "ip_address", "db_user", "db_pass", "database_name" };
	public static String[] hsdb = 		{ "ip_address", "db_user", "db_pass", "database_name" };
	public static String[] donatedb = 	{ "ip_address", "db_user", "db_pass", "database_name" };
	
	public static Connection con;
	public static Statement stm;
    public static boolean connected;
    
    public static boolean connect(String[] args) {
        try {
        	if (con != null) {
        		if (con.isValid(3)) {
        			return true;
        		} else {
        			destroy();
        			return false;
        		}
        	}
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:mysql://" + args[0] + ":3306/" + args[3], args[1], args[2]);
            stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            connected = true;
            return true;
        }  catch(Exception e) {
            connected = false;
            e.printStackTrace();
            return false;
        }
    }
    
    public static void destroy() {
        try {
        	stm.close();
        	con.close();
        	stm = null;
        	con = null;
            connected = false;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static int executeUpdate(String query) {
        try {
            int results = stm.executeUpdate(query);
            return results;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }
    
    public static ResultSet executeQuery(String query) {
        try {
            ResultSet results = stm.executeQuery(query);
            return results;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public static int getUid(String username) {
		try {
			ResultSet rs = executeQuery("SELECT * FROM xf_user WHERE username='"+username+"' LIMIT 1");
			while (rs.next()) 
				return rs.getInt("user_id");
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return 0;
	}
	
}
