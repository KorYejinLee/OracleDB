package oracledb;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Insert_To_Ipone_TBL_Run {

	public static void main(String[] args) throws IOException {
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
			connect = DriverManager.getConnection(connectionURL,"gmarket","ipone"); // try/catch문을 사용해야 한다 // 싱글톤 // 직접 대입해도 된다
			final String insert_sql = """
					INSERT INTO ipone_info (INFOID, NAME, PRICE_ORIGINAL, DISCOUNT, PRICE_SELLER)\s
					VALUES(?, ?, ?, ?, ?)
					"""; // ? 엘비스 연산자 파라메터 // customer() 순서는 뒤바뀌어도 됨 // 순서는 중요하지 않다, 하지만 이름은 맞춰줘야 함 // 자바 sql문 세미콜론을 붙이면 오류가 남 // 확장성
			PreparedStatement preparedStatement = null;
			while(true) {
				String iphone_str = bufferedReader.readLine();
				if(iphone_str == null) break; // 다 읽은 경우
				String[] iphone_arr = iphone_str.split(",");
				if(iphone_arr[0] == "") {
					continue;
				} else {
					preparedStatement = connect.prepareStatement(insert_sql); // prepare			
					preparedStatement.setInt(1, Integer.parseInt(iphone_arr[0])); // prepared // index 1서부터 시작한다
					preparedStatement.setString(2, iphone_arr[1]);
					preparedStatement.setInt(3, Integer.parseInt(iphone_arr[2]));
					preparedStatement.setInt(4, Integer.parseInt(iphone_arr[3]));
					preparedStatement.setInt(5, Integer.parseInt(iphone_arr[4]));
					final int row = preparedStatement.executeUpdate();
					System.out.println("저장된 index : " + row);
				}
			}
			preparedStatement.close();
			bufferedReader.close(); // 이것만 해줘도 다 닫아준다.
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.printf("%s\r\n", e.getMessage()); // 프로그램이 죽지 않고 에러 메시지를 전달한다
		} catch (SQLException e) {
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
