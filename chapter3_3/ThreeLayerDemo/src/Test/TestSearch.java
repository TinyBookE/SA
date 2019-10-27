package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Core.dbConnection;

class TestSearch {

	@Test
	void test() {
		dbConnection conn = new dbConnection();
		conn.test("select * from [t01_user] where t01_firstname like 'xian' or t01_lastname like 'xian'");
	}

}
