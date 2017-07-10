
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

import db_utility.Db_utility;

public class Sqlplus_utility {
	
	public static void main(String []args) throws ClassNotFoundException
	{
		Scanner scn = new Scanner(System.in);
		String usrname,passwrd,sql,prompt1,qname;
		ArrayList<String> array=new ArrayList<String>();
		System.out.println("sqlplus>");
		System.out.println("Please Enter your Username :" );
		usrname = scn.nextLine();
		System.out.println("Please Enter your password :" );
		passwrd= scn.nextLine();
		
		if(usrname.equals("ora1") && passwrd.equals("ora1"))
		{
			System.out.println("Prompt a sql statement to execute : ");
			sql = scn.nextLine();
			System.out.println("Prompt '1' to print in console '2' to print in file "
					+ "'3' to add in database '4' to delete records "
					+"5 to add named queries :");
			prompt1 = scn.nextLine();
			if(prompt1.equals("1"))
			{
				array=Db_utility.select_record(sql);
				if(array.size()>11)
				{
					write_file(array);			
				}
				else
				{
					for (int i = 0; i < array.size(); i++) {
						System.out.println(array.get(i));
					}
				}	
				
			}
			else if(prompt1.equals("2"))
			{
				array=Db_utility.select_record(sql);
				write_file(array);
			}
			else if(prompt1.equals("3"))
			{
				System.out.println("Please Enter your Username :" );
				usrname = scn.nextLine();
				System.out.println("Please Enter your password :" );
				passwrd= scn.nextLine();
				if(usrname.equals("ora1") && passwrd.equals("ora1"))
				{
					
				}
			}
			else if(prompt1.equals("4"))
			{
				System.out.println("Please Enter your Username to delete records:" );
				usrname = scn.nextLine();
				System.out.println("Please Enter your password to delete records:" );
				passwrd= scn.nextLine();
				if(usrname.equals("ora1") && passwrd.equals("ora1"))
				{
					System.out.println("Enter Full Name of the record you want to delete");
					String name=scn.nextLine();
					String predel_sql="Select count(*) from customers where fullname = '"+name+"'";
					int affect_count= Db_utility.row_count(predel_sql);
					System.out.println("If you delete this record it will affect "+ affect_count+" rows in database. Do you want to continue(yes or no)? ");
					String response= scn.nextLine();
					if(response.equals("yes"))
					{
						String del_sql="delete from customers where fullname = '"+name+"'";
						Db_utility.delete_record(del_sql);
						System.out.println("Deletion is done");
					}
					else
					{
						System.out.println("Thanks for your response");
					}
				}
			}
			else if(prompt1.equals("5"))
			{
				ArrayList<String>listqueries=new ArrayList<String>();
				System.out.println("Prompt a sql statement to execute : ");
				sql = scn.nextLine();
				System.out.println("Give it a name");
				qname = scn.nextLine();
				listqueries.add(qname);
				save_named_queries(sql,qname);
				System.out.println("Pick a query name to execute");
				for(int i=0;i<listqueries.size();i++)
				{
					System.out.println(listqueries.get(i));
				}
				String q_ex_name= scn.nextLine();
				execute_named_queries(q_ex_name);
			}
		}
		
	}
	
	public static void write_file(ArrayList<String> sd)
	{
		int num=0;
		if(sd.size()>11)
		{
			num=11;
		}
		else
		{
			num=sd.size();
		}
		PrintWriter writer=null;
		try {
			writer = new PrintWriter(new File("sql_info.txt"));
			for (int i = 0; i < num; i++) {
				writer.println(sd.get(i));
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
		writer.close();
		System.out.println("Information is saved in a file named 'sql_info'");
	}
	
	/*named queries*/
	
	public static void execute_named_queries(String qname)
	{
		String sql="";
		ArrayList<String> array=new ArrayList<String>();
		try {
			Scanner sc = new Scanner(new File(qname+".txt"));
			while(sc.hasNextLine())
			{
				sql=sc.nextLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		array=Db_utility.select_record(sql);
		for (int i = 0; i < array.size(); i++) {
			System.out.println(array.get(i));
		}
	}
	
	/*execute named queries*/
	
	public static void save_named_queries(String query, String qname)
	{
		PrintWriter writer=null;
		try {
			writer = new PrintWriter(new File(qname+".txt"));
			writer.println(qname);
			writer.println(query);		
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
		writer.close();
		System.out.println("Information is saved in a file named "+ qname);
	}

}
