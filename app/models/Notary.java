package models;

import play.modules.morphia.Model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;


@Entity(value="notary")
public class Notary extends Model {

	@Id String _id;
	
	@Column("id")
	Long   identity;
	
	String last;
	String first;
	String middle;
	
	String expires;
	String dob;
	
	String mail;
	String city;
	String state;
	
	String zip;
	 
	
}
