package it.polito.tdp.formulaone.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import it.polito.tdp.formulaone.model.Circuit;
import it.polito.tdp.formulaone.model.Constructor;
import it.polito.tdp.formulaone.model.Drivers;
import it.polito.tdp.formulaone.model.Race;
import it.polito.tdp.formulaone.model.Season;


public class FormulaOneDAO {

	public List<Integer> getAllYearsOfRace() {
		
		String sql = "SELECT year FROM races ORDER BY year" ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet rs = st.executeQuery() ;
			
			List<Integer> list = new ArrayList<>() ;
			while(rs.next()) {
				list.add(rs.getInt("year"));
			}
			
			conn.close();
			return list ;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}
	
	public List<Season> getAllSeasons() {
		
		String sql = "SELECT year, url FROM seasons ORDER BY year" ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet rs = st.executeQuery() ;
			
			List<Season> list = new ArrayList<>() ;
			while(rs.next()) {
				list.add(new Season(Year.of(rs.getInt("year")), rs.getString("url"))) ;
			}
			
			conn.close();
			return list ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Circuit> getAllCircuits() {

		String sql = "SELECT circuitId, name FROM circuits ORDER BY name";

		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			List<Circuit> list = new ArrayList<>();
			while (rs.next()) {
				list.add(new Circuit(rs.getInt("circuitId"), rs.getString("name")));
			}

			conn.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}
	
	public List<Constructor> getAllConstructors() {

		String sql = "SELECT constructorId, name FROM constructors ORDER BY name";

		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			List<Constructor> constructors = new ArrayList<>();
			while (rs.next()) {
				constructors.add(new Constructor(rs.getInt("constructorId"), rs.getString("name")));
			}

			conn.close();
			return constructors;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}


	public static void main(String[] args) {
		FormulaOneDAO dao = new FormulaOneDAO() ;
		
		List<Integer> years = dao.getAllYearsOfRace() ;
		System.out.println(years);
		
		List<Season> seasons = dao.getAllSeasons() ;
		System.out.println(seasons);

		
		List<Circuit> circuits = dao.getAllCircuits();
		System.out.println(circuits);

		List<Constructor> constructors = dao.getAllConstructors();
		System.out.println(constructors);
		
		List<Race> races = dao.getRacesYear(2009);
		System.out.println(races);
	
		System.out.println(races.get(10).getRaceId());
		List<Drivers> drivers = dao.getDriversForRace(races.get(10)) ;
		
		System.out.println(drivers);
		
		System.out.println(drivers.get(1));
		System.out.println(drivers.get(2));
		
		System.out.println(dao.getVittorie(drivers.get(1), drivers.get(2), 2009));
		
	}

	public List<Drivers> getDriversForRace(Race r) {
		
		//tutti i drivers
		
		String sql = "select * from drivers as d, results as r where d.driverId=r.driverId and r.position>0 and raceId=? " ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, r.getRaceId());
			ResultSet rs = st.executeQuery() ;
			
			List<Drivers> list = new ArrayList<>() ;
			while(rs.next()) {				
				list.add(new Drivers(rs.getInt("driverId"),rs.getString("driverRef"),rs.getInt("number"),
						rs.getString("code"), rs.getString("forename"),rs.getString("surname"),
						rs.getDate("dob").toLocalDate(), rs.getString("nationality"),rs.getString("url"))) ;
			}
			
			conn.close();
			return list ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public List<Race> getRacesYear(int year) {

		String sql = "select * from races where year=? ";
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, year);
			
			ResultSet rs = st.executeQuery() ;
			
			List<Race> list = new ArrayList<>() ;
			while(rs.next()) {				
				Race r = new Race(rs.getInt("raceId"),Year.of(rs.getInt("year")), rs.getInt("round"),
						rs.getInt("circuitId"), rs.getString("name"), rs.getDate("date").toLocalDate(), 
						null, rs.getString("url"));
			list.add(r)	;	
			}
			
			conn.close();
			return list ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public int getVittorie(Drivers d1, Drivers d2, int year) {
String sql = "SELECT ra.raceId FROM results as r1, results as r2, races as ra WHERE r1.raceId=r2.raceId AND ra.raceId=r1.raceId AND ra.year=? AND r1.position<r2.position AND r1.driverId=? AND r2.driverId=?";
try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, year);
			st.setInt(2, d1.getDriverId());
			st.setInt(3, d2.getDriverId());
		
			
			ResultSet rs = st.executeQuery() ;
			
			List<Integer> list = new ArrayList<>() ;
			while(rs.next()) {				
				list.add(rs.getInt("raceId"));
					}
			
			conn.close();
			return list.size() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0 ;
		}

		
		

	}
	
}
