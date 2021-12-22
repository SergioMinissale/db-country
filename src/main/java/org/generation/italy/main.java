package org.generation.italy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class main {
	private final static String DB_URL = "jdbc:mysql://localhost:3306/todos";
	private final static String DB_USER = "root";
	private final static String DB_PASSWORD = "RootPassword";

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

			Country sceltaCountry = selectCountryById(con, scan);
			if (sceltaCountry != null) {
				System.out.println("La tua scelta è: " + sceltaCountry.getName());
				
			}

		} catch (SQLException e) {
			System.out.println("OOOPS si è verificato un errore: ");
			System.out.println(e.getMessage());
		}
		scan.close();
	}

	// metodo selectCountryById

	private static Country selectCountryById(Connection con, Scanner scan) throws SQLException {
		Country sceltaCountry = null;
		System.out.println("Select country id: ");
		int countryId = Integer.parseInt(scan.nextLine());

		String query = "select *\r\n" + "from countries c\r\n" + "where c.country_id = ?;";

		try (PreparedStatement psCountry = con.prepareStatement(query)) {
			psCountry.setInt(1, countryId);

			try (ResultSet rsCountry = psCountry.executeQuery()) {
				if (rsCountry.next()) {
					sceltaCountry = new Country(rsCountry.getInt(1), rsCountry.getString(2), rsCountry.getInt(3),
							rsCountry.getDate(4), rsCountry.getString(5), rsCountry.getString(6),
							rsCountry.getInt(7));
				}

			}
		}

		return sceltaCountry;
	}
	// metodo UpdateCountry
	private static void updateCountry(Connection con, Country country) throws SQLException {
		String query2 = "update countries\r\n"
				+ "set country_id = ?,name = ? , area =?,national_day =?,country_code2 =?,country_code3 =?,region_id =?\r\n"
				+ "where country_id =?;";
		try(PreparedStatement psUpdateCountry = con.prepareStatement(query2)) {
			psUpdateCountry.setInt(1, country.getCountryId());
			psUpdateCountry.setString(2, country.getName());
			psUpdateCountry.setInt(3, country.getArea());
			psUpdateCountry.setDate(4, country.getNationalDay());
			psUpdateCountry.setString(5, country.getCountryCode2());
			psUpdateCountry.setString(6, country.getCountryCode3());	
			psUpdateCountry.setInt(7, country.getRegionId());
			int result = psUpdateCountry.executeUpdate();
		}
		
	}

}
