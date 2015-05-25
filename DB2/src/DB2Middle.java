import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB2Middle {
	private static Connection con;
	private static String dbHost = "codd.2clever4you.net"; // Hostname
	private static String dbPort = "3306"; // Port -- Standard: 3306
	private static String dbName = "db212"; // Datenbankname
	private static String dbUser = "db212"; // Datenbankuser
	private static String dbPass = "arparili"; // Datenbankpasswort
	private static Statement query;

	private DB2Middle() {
		try {
			Class.forName("com.mysql.jdbc.Driver"); // Datenbanktreiber f�r JDBC
													// Schnittstellen laden.

			// Verbindung zur JDBC-Datenbank herstellen.
			con = DriverManager.getConnection("jdbc:mysql://" + dbHost + ":"
					+ dbPort + "/" + dbName + "?" + "user=" + dbUser + "&"
					+ "password=" + dbPass);
		} catch (ClassNotFoundException e) {
			System.out.println("Treiber nicht gefunden");
		} catch (SQLException e) {
			System.out.println("Verbindung nicht moglich");
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
	}

	private static Connection getConnection() throws SQLException {
		if (con == null)
			new DB2Middle();
		return con;
	}

	/*
	 * public static void printNameList() { try { con = getConnection();
	 * 
	 * if(con != null){ // Abfrage-Statement erzeugen. Statement query;
	 * 
	 * query = con.createStatement();
	 * 
	 * // Tabelle anzeigen String sql ="SELECT RaumNr,Sitzpl�tze FROM Raum";
	 * ResultSet result = query.executeQuery(sql);
	 * 
	 * // Ergebnisstabelle durchforsten
	 * 
	 * while (result.next()) { String RaumNr =result.getString("RaumNr"); String
	 * Sitzpl�tze =result.getString("Sitzpl�tze");
	 * 
	 * String info = RaumNr+ ", " + Sitzpl�tze ;
	 * 
	 * System.out.println(info); } return;}} catch (SQLException e) {
	 * e.printStackTrace(); } }
	 */

	// Raum

	public static boolean createRoom(int seats) {
		try {
			con = getConnection();

			if (con != null) {

				query = con.createStatement();

				String sqllast = "SELECT * FROM raum ORDER BY raumnr DESC LIMIT 1";
				ResultSet result = query.executeQuery(sqllast);
				String sql = "INSERT INTO raum(raumnr,sitzpl�tze) VALUES(?,?)";
				PreparedStatement preparedStatement = con.prepareStatement(sql);
				
				while (result.next()) {
					int id = result.getInt(1) + 1;
					preparedStatement.setInt(1, id);
					preparedStatement.setInt(2, seats);
					preparedStatement.executeUpdate();
					return true;}
				preparedStatement.setInt(1, 1);
				preparedStatement.setInt(2, seats);
				preparedStatement.executeUpdate();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	public static ResultSet getAllRooms() {

		try {
			con = getConnection();

			if (con != null) {

				// Abfrage-Statement erzeugen.
				query = con.createStatement();
				// Tabelle anzeigen
				String sql = "SELECT * FROM raum";
				ResultSet result = query.executeQuery(sql);
				while (result.next() == true)
					System.out.println(result.getInt(1) + ","
							+ result.getString(2));
				return result;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ResultSet getRoomById(int id) {

		try {
			con = getConnection();

			if (con != null) {
				// Abfrage-Statement erzeugen.

				query = con.createStatement();
				// Tabelle anzeigen
				String string = String.valueOf(id);
				String sql = "SELECT raumnr,sitzpl�tze FROM raum where raumnr="
						+ string;

				ResultSet result = query.executeQuery(sql);
				result.next();

				return result;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean editRoom(int id, int seats) {
		try {
			con = getConnection();

			if (con != null) {

				ResultSet rs = getRoomById(id);
				String sql = "SELECT * FROM raum WHERE raumnr = ?"
						+ rs.getString(1);
				// PreparedStatement erzeugen.

				PreparedStatement preparedStatement = con.prepareStatement(sql);

				// Ergebnistabelle erzeugen und abholen.
				String updateSql = "UPDATE raum "
						+ "SET raumnr = ?, sitzpl�tze = ? "
						+ "WHERE raumnr = ?";
				PreparedStatement preparedUpdateStatement = con
						.prepareStatement(updateSql);
				// Erstes Fragezeichen durch "id" Parameter ersetzen
				preparedUpdateStatement.setInt(1, id);
				// Zweites Fragezeichen durch "seats" Parameter ersetzen
				preparedUpdateStatement.setInt(2, seats);
				// Drittes Fragezeichen durch "id" Parameter ersetzen
				preparedUpdateStatement.setInt(3, id);

				preparedUpdateStatement.executeUpdate();

				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean deleteRoomById(int id) {
		try {
			con = getConnection();

			if (con != null) {

				ResultSet rs = getRoomById(id);
				// Abfrage-Statement erzeugen.
				query = con.createStatement();
				// Tabelle anzeigen
				String sql = "DELETE FROM raum WHERE raumnr=" + rs.getString(1);
				query.executeUpdate(sql);
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// Vorlesung

	public static boolean createLecture(String name, int roomnr, int professornr,int coursenr) {
		try {
			con = getConnection();

			if (con != null) {

				query = con.createStatement();
				
		String sqllast = "SELECT *  FROM vorlesung ORDER BY vorlesungsnr DESC LIMIT 1";
		ResultSet result = query.executeQuery(sqllast);
		String sql = "INSERT INTO vorlesung(vorlesungsnr,name,raumnr,dozentnr,fachnr) VALUES(?,?,?,?,?)";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		
		while (result.next()) {
			int id = result.getInt(1) + 1;
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, name);
			preparedStatement.setInt(3, roomnr);
			preparedStatement.setInt(4, professornr);
			preparedStatement.setInt(5, coursenr);
			preparedStatement.executeUpdate();
			
			return true;}
		preparedStatement.setInt(1, 1);
		preparedStatement.setString(2, name);
		preparedStatement.setInt(3, roomnr);
		preparedStatement.setInt(4, professornr);
		preparedStatement.setInt(5, coursenr);
		preparedStatement.executeUpdate();
		return true;
			}} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return false;

	}
	

	public ResultSet getAllLectures() {
		return null;

	}

	public ResultSet getLectureById(int id) {
		return null;

	}

	public boolean editLecture(int id, String name, int roomnr,
			int professornr, int coursenr) {
		return false;

	}

	public boolean deleteLectureById(int id) {
		return false;

	}

	// Professor

	public static boolean createProfessor(String name, String firstname, String email,String tel) {
		try {
			con = getConnection();

			if (con != null) {

				query = con.createStatement();

				String sqllast = "SELECT *  FROM dozent ORDER BY dozentnr DESC LIMIT 1";
				ResultSet result = query.executeQuery(sqllast);
				String sql = "INSERT INTO dozent(dozentnr,email,telefonnr,vorname,name) VALUES(?,?,?,?,?)";
				PreparedStatement preparedStatement = con.prepareStatement(sql);
				
				while (result.next()) {
					int id = result.getInt(1) + 1;
					preparedStatement.setInt(1, id);
					preparedStatement.setString(2, email);
					preparedStatement.setString(3, tel);
					preparedStatement.setString(4, firstname);
					preparedStatement.setString(5, name);
					preparedStatement.executeUpdate();
					return true;
					}
				preparedStatement.setInt(1, 1);
				preparedStatement.setString(2, email);
				preparedStatement.setString(3, tel);
				preparedStatement.setString(4, firstname);
				preparedStatement.setString(5, name);
				preparedStatement.executeUpdate();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public ResultSet getAllProfessors() {
		return null;

	}

	public ResultSet getProfessorById(int id) {
		return null;

	}

	public boolean editProfessor(int id, String name, String firstname,
			String email, String tel) {
		return false;

	}

	public boolean deleteProfessorById(int id) {
		return false;

	}

	// Student

	public static boolean createStudent(int id, String name, String firstname,String email) {
		try {
			con = getConnection();

			if (con != null) {

				query = con.createStatement();

				String sqllast = "SELECT *  FROM student ORDER BY matrikelnr DESC LIMIT 1";
				ResultSet result = query.executeQuery(sqllast);
				String sql = "INSERT INTO student(matrikelnr,vorname,name,email) VALUES(?,?,?,?)";
				PreparedStatement preparedStatement = con.prepareStatement(sql);
				
				while (result.next()) {
					preparedStatement.setInt(1, id);
					preparedStatement.setString(2, firstname);
					preparedStatement.setString(3, name);
					preparedStatement.setString(4, email);
					preparedStatement.executeUpdate();
					return true;}
				preparedStatement.setInt(1, id);
				preparedStatement.setString(2, firstname);
				preparedStatement.setString(3, name);
				preparedStatement.setString(4, email);
				preparedStatement.executeUpdate();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	public ResultSet getAllStudents() {
		return null;

	}

	public ResultSet getStudentById(int id) {
		return null;

	}

	public boolean editStudent(int id, String name, String firstname,
			String email) {
		return false;

	}

	public boolean deleteStudentById(int id) {
		return false;

	}

	// Fach
	public static boolean createCourse(String name) {
		try {
			con = getConnection();

			if (con != null) {

				query = con.createStatement();

				String sqllast = "SELECT *  FROM fach ORDER BY fachnr DESC LIMIT 1";
				ResultSet result = query.executeQuery(sqllast);
				String sql = "INSERT INTO fach(fachnr,name) VALUES(?,?)";
				PreparedStatement preparedStatement = con.prepareStatement(sql);
				
				while (result.next()) {
					int id = result.getInt(1) + 1;
					preparedStatement.setInt(1, id);
					preparedStatement.setString(2, name);
					preparedStatement.executeUpdate();
					return true;}
				preparedStatement.setInt(1, 1);
				preparedStatement.setString(2, name);
				preparedStatement.executeUpdate();
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	public ResultSet getAllCourses() {
		return null;

	}

	public ResultSet getCourseById(int id) {
		return null;

	}

	public boolean editCourse(int id, String name) {
		return false;

	}

	public boolean deleteCourseById(int id) {
		return false;

	}
}