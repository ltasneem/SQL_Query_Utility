package db_utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Db_utility {
	
	private static Connection con = null;
	private static Statement stmt = null;
	private static ResultSet rs = null;
	
	public static ArrayList<String> select_record(String s)
	{
		ArrayList<String> arr= new ArrayList<String>();
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
                        //con = DriverManager.getConnection("jdbc:oracle:thin:sys as sysdba/oracle@localhost:1521:orcl");
                        con = DriverManager.getConnection("jdbc:oracle:thin:ora1/ora1@localhost:1521:orcl");
			stmt = con.createStatement();
			rs = stmt.executeQuery(s);
			ResultSetMetaData metadata = rs.getMetaData();
			int numberOfColumns = metadata.getColumnCount();
			String st="";
			String columnnames="";
			int first =0;
			//System.out.println(numberOfColumns);
			while(rs.next()){
				int i=1;
				while(i <= numberOfColumns) {
					int g=i;
		            st+=rs.getString(i++)+ "      ";
		            columnnames+=metadata.getColumnName(g)+ "       ";
		        }
				if(first==0)
				{
					arr.add(columnnames);
					arr.add(st);
				}
				else
					arr.add(st);
				first++;
				
			}
			}catch (SQLException e) {
				e.printStackTrace();
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return arr;
	}
	
	public static void insert_record(String s)
	{
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
                        //con = DriverManager.getConnection("jdbc:oracle:thin:sys as sysdba/oracle@localhost:1521:orcl");
                        con = DriverManager.getConnection("jdbc:oracle:thin:ora1/ora1@localhost:1521:orcl");
			stmt = con.createStatement();
			rs = stmt.executeQuery(s);
		PreparedStatement pstmt = con.prepareStatement(s);
		//stmt.setString(1, user.getName());
		
		}
		catch (SQLException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
		try {
			rs.close();
			stmt.close();
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		}
	}
	
	public static int column_count(String s)
	{
		int numberOfColumns=0;
		try{
			rs = stmt.executeQuery(s);
			ResultSetMetaData rsmd = rs.getMetaData();
			 numberOfColumns = rsmd.getColumnCount();
		}
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
		try {
			rs.close();
			stmt.close();
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		}
		return numberOfColumns;
	}
}
