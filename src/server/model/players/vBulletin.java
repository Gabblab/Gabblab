/**
 * vBulletin class
 * @author Mad Turnip
 */
package server.model.players;
import java.sql.*;

public class vBulletin implements Runnable {
	
	
	private Connection connection = null;
	private Statement statement = null;
	private static Thread thread = null;
	
	public enum Type {
		myBB,
		SMF,
		IPB,
		vBulletin,
		phpBB,
	}

	private String[] tableNames = new String[6];
	private void setTables(){
		if(forum == Type.myBB){
			tableNames = new String[] {"mybb_users","username","password","salt","usergroupid",};
		} else if(forum == Type.SMF){
			tableNames = new String[] {" smf_members","memberName","passwd","passwordSalt","ID_GROUP",};
		} else if(forum == Type.IPB){
			tableNames = new String[] {"ipb_members","members_display_name","members_pass_hash","members_pass_salt","member_group_id",};
		} else if(forum == Type.vBulletin){//vbulletin
			tableNames = new String[] {"user","username","password","salt","usergroupid",};
		} else if(forum == Type.phpBB){//phpBB
			tableNames = new String[] {"users","username","user_password","user_password","group_id",};
		}
	}
	
	public vBulletin(String url,String database,String username,String password,Type t){
		this.hostAddress = "jdbc:mysql://"+url+"/"+database;
		this.username = username;
		this.password = password;
		this.forum = t;
		try {
			//connect();
			thread = new Thread(this);
			thread.start();
		} catch(Exception e){
			connection = null;
			e.printStackTrace();
		}
	}
	
	private final String hostAddress;
	private final String username;
	private final String password;
	private final Type forum;
	
	private void connect(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch(Exception e2){
			System.out.println("Cannot find mySql Driver.");
			return;
		}
		try {
			connection = DriverManager.getConnection(hostAddress, username,password);
			statement = connection.createStatement();
		} catch(Exception e){
			System.out.println("Connetion rejected, Wrong username or password, or ip is banned, or host is down.");
			connection = null;
			e.printStackTrace();
		}
	}
	private void ping(){
		try{
			ResultSet results = null;
			String query = "SELECT * FROM "+tableNames[0]+" WHERE "+tableNames[2]+" LIKE 'null312'";
			results = statement.executeQuery(query);
		} catch(Exception e){
			connection = null;
			connect();
			e.printStackTrace();
		}
	}
	
	public void run(){
		boolean allowRun = true;
		while(allowRun){
			try {
				if(connection == null) {
					setTables();
					connect();
				} else {
					ping();
				}
				thread.sleep(10000);
			} catch(Exception e){
			}
		}
	}
	/**
	 * returns 2 integers, the return code and the usergroup of the player
	 */
	
	public int[] checkUser(String name,String password){
		int i = 0;
		int[] returnCodes = {0,0};//return code for client, group id

		try{
			ResultSet results = null;
			String query = "SELECT * FROM "+tableNames[0]+" WHERE "+tableNames[1]+" LIKE '"+name+"'";
			try {
			if(statement == null)
				statement = connection.createStatement();
			} catch(Exception e5){
				statement = null;
				connection = null;
				connect();
				statement = connection.createStatement();
			}
			results = statement.executeQuery(query);
			if(results.next()){
				String salt = results.getString(tableNames[3]);
				String pass = results.getString(tableNames[2]);
				int group = results.getInt(tableNames[4]);
				returnCodes[1] = group;
				String pass2 = "";
				if(forum == Type.myBB){
					pass2 = MD5.MD5(MD5.MD5(salt)+MD5.MD5(password));
				} else if(forum == Type.vBulletin){
					pass2 = MD5.MD5(password);
					pass2 = MD5.MD5(pass2+salt);
				} else if(forum == Type.SMF){
					pass2 = MD5.SHA((name.toLowerCase())+password);
				} else if(forum == Type.phpBB){
					pass2 = MD5.MD5(password);
				} else if(forum == Type.IPB){
					pass2 = MD5.MD5(MD5.MD5(salt)+MD5.MD5(password));
				}
				if(pass.equals(pass2)){
					returnCodes[0] = 2;
					return returnCodes;
				} else {
					returnCodes[0] = 3;
					return returnCodes;
				}
			} else {
				//no user exists
				returnCodes[0] = 12;
				return returnCodes;
			}
		} catch(Exception e){
			statement = null;
			returnCodes[0] = 8;
			return returnCodes;
		}
	}
}