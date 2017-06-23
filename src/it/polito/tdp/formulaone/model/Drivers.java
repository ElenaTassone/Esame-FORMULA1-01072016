package it.polito.tdp.formulaone.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Drivers {
	
	private int driverId ;
	private String driverRef ;
	private int number ;
	private String code ;
	private String forename ;
	private String surname ;
	private LocalDate dob ; // date of birth
	private String nationality ;
	private String url ;
	private List<Drivers> vincenti ;
	
	public Drivers(int driverId, String driverRef, int number, String code, String forename, String surname,
			LocalDate dob, String nationality, String url) {
		super();
		this.driverId = driverId;
		this.driverRef = driverRef;
		this.number = number;
		this.code = code;
		this.forename = forename;
		this.surname = surname;
		this.dob = dob;
		this.nationality = nationality;
		this.url = url;
		this.vincenti = new ArrayList<Drivers> ();
	}
	public int getDriverId() {
		return driverId;
	}
	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}
	public String getDriverRef() {
		return driverRef;
	}
	public void setDriverRef(String driverRef) {
		this.driverRef = driverRef;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getForename() {
		return forename;
	}
	public void setForename(String forename) {
		this.forename = forename;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public LocalDate getDob() {
		return dob;
	}
	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return surname ;
	}
	public List<Drivers> getVincenti() {
		return vincenti;
	}
	public void addVincente(Drivers vincente) {
		this.vincenti.add(vincente);
	}
	public int getSconfitte(Drivers vincente) {
		int tot = 0  ;
		for(Drivers d : this.getVincenti()){
			if(d.equals(vincente))
				tot++;
		}
		return tot ;
	}
	
	
	

}
