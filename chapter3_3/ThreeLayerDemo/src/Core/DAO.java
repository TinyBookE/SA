package Core;

import java.sql.ResultSet;

public class DAO {
	private dbConnection conn;
	
	public DAO() {
		conn = new dbConnection();
	}
	
	public DAO(String user, String passwd, String dbname) {
		conn = new dbConnection(user, passwd, dbname);
	}
	
	public ResultSet searchByName(String _username) {
		String query = "select * from [t01_user] where t01_firstname like ? or t01_lastname like ?";
		String[] sqlParameters = new String[2]; 
		sqlParameters[0] = _username;
		sqlParameters[1] = _username;
		return conn.executeSelectQuery(query, sqlParameters);
	}
	
	public ResultSet searchById(String _id) {
		String query = "select * from [t01_id] where t01_id = ?";
		String[] sqlParameters = new String[2]; 
		sqlParameters[0] = _id.substring(0, _id.length()-1);
		return conn.executeSelectQuery(query, sqlParameters);
	}
	
}
