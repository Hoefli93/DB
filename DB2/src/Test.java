import java.sql.ResultSet;
import java.sql.SQLException;


public class Test {

	public static void main(String[] args) throws SQLException {
		ResultSet as = DB2Middle.getAllRooms();
		
		
		while ( as.next() )
			System.out.printf( "%s, %s, %s%n", as.getString(1), as.getString(2));
	}

}
