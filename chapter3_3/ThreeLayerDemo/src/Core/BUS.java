package Core;

import java.sql.ResultSet;

public class BUS {
	private DAO _userDAO;
	
	public BUS() {
		_userDAO = new DAO();
	}
	
	public BUS(String user, String passwd, String dbname) {
		// TODO Auto-generated constructor stub
		_userDAO = new DAO(user, passwd, dbname);
	}	
	
	public UserVO getUserEmailByName(String name) {
		UserVO userVO = new UserVO();
		ResultSet rs = _userDAO.searchByName(name);
		try {
			if(rs.wasNull()) {
				return userVO;
			}
			while (rs.next()) {
				userVO.idUser = Integer.valueOf(rs.getString("t01_id"));
				userVO.firstname = rs.getString("t01_firstname");
				userVO.lastname = rs.getString("t01_lastname");
				userVO.email = rs.getString("t01_email");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return userVO;
	}
	
	public UserVO getUserEmailById(String id) {
		UserVO userVO = new UserVO();
		ResultSet rs = _userDAO.searchById(id);
		try {
			if(rs.wasNull()) {
				return userVO;
			}
			while (rs.next()) {
				userVO.idUser = Integer.valueOf(rs.getString("t01_id"));
				userVO.firstname = rs.getString("t01_firstname");
				userVO.lastname = rs.getString("t01_lastname");
				userVO.email = rs.getString("t01_email");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return userVO;
	}
	
	
}
