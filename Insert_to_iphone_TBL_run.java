package oracle_db_insert;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Insert_to_iphone_TBL_run {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Connection connect = null;
		String content = null;

		final InputStream inputStream = new FileInputStream("./Iphone_table.csv");
		final Reader reader = new InputStreamReader(inputStream);
		final BufferedReader bufferedReader = new BufferedReader(reader);
		
		String[] dataes = content.split("\r\n");
		final String connectionURL = """
				jdbc:oracle:thin:@192.168.0.13:1521/xe 
				"""; // 여러개 문자열
		try {
			Class.forName("oracle.jdbc.OracleDriver"); // byte code
			connect = DriverManager.getConnection(connectionURL,"gmarket","ipone"); 
			final String insert_sql = """
					INSERT INTO ipone_info (INFOID, NAME, PRICE_ORIGINAL, DISCOUNT, PRICE_SELLER)\s
					VALUES(?, ?, ?, ?, ?)
					"""; 
			PreparedStatement preparedStatement = null;
			while(true) {
				String iphone_str = bufferedReader.readLine();
				if(iphone_str == null) break; 
				String[] iphone_arr = iphone_str.split(",");
				if(iphone_arr[0] == "") {
					continue;
				} else {
					preparedStatement = connect.prepareStatement(insert_sql);		
					preparedStatement.setInt(1, Integer.parseInt(iphone_arr[0])); 
					preparedStatement.setString(2, iphone_arr[1]);
					preparedStatement.setInt(3, Integer.parseInt(iphone_arr[2]));
					preparedStatement.setInt(4, Integer.parseInt(iphone_arr[3]));
					preparedStatement.setInt(5, Integer.parseInt(iphone_arr[4]));
					final int row = preparedStatement.executeUpdate();
					System.out.println("저장된 index : " + row);
				}
			}
			preparedStatement.close();
			bufferedReader.close(); 
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.printf("%s\r\n", e.getMessage()); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connect.close(); // 연결 오류
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
