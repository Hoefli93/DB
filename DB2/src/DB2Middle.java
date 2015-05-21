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
			Class.forName("com.mysql.jdbc.Driver"); // Datenbanktreiber für JDBC
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
	 * // Tabelle anzeigen String sql ="SELECT RaumNr,Sitzplätze FROM Raum";
	 * ResultSet result = query.executeQuery(sql);
	 * 
	 * // Ergebnisstabelle durchforsten 
	 * 
	 * while (result.next()) { 
	 * String RaumNr =result.getString("RaumNr"); 
	 * String Sitzplätze =result.getString("Sitzplätze");
	 * 
	 * String info = RaumNr+ ", " + Sitzplätze ;
	 * 
	 * System.out.println(info); } return;}} catch (SQLException e) {
	 * e.printStackTrace(); } }
	 */

	// Raum

	public static boolean createRoom(int seats) {
		 try {
			 con = getConnection();
		 
		    if(con != null){
		      
		        // Insert-Statement erzeugen (Fragezeichen werden später ersetzt).
		        String sql = "INSERT INTO raum(raumnr,sitzplätze)"+"VALUES(?,?)";
		        PreparedStatement preparedStatement = con.prepareStatement(sql);
		        String lastroom = "SELECT raumnr FROM raum";
		        
		        // Erstes Fragezeichen durch "firstName" Parameter ersetzen
     preparedStatement.setInt(1,7);
      //Zweites Fragezeichen durch "lastName" Parameter ersetzen
     preparedStatement.setInt(2, seats);
     // SQL ausführen.
     preparedStatement.executeUpdate();
     
		        // Es wird der letzte Datensatz abgefragt
		        
     ResultSet result = preparedStatement.executeQuery(lastroom);
     result.getInt(lastroom);
     
     
     
		         return true;
		      }} catch (SQLException e) {
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
				String sql = "SELECT raumnr,sitzplätze FROM raum";
				ResultSet result = query.executeQuery(sql);
				return result;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ResultSet getRoomById(int id) {

		return null;
	}

	public boolean editRoom(int id, int seats) {

		return true;
	}

	public boolean deleteRoomById(int id) {
		return false;

	}

	// Vorlesung

	public boolean createLecture(String name, int roomnr, int professornr,
			int coursenr) {
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

	public boolean createProfessor(String name, String firstname, String email,
			String tel) {
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

	public boolean createStudent(int id, String name, String firstname,
			String email) {

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

	public boolean createCourse(String name) {
		return false;

	}

	// Fach

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