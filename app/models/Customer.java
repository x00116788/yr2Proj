package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.*;
import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

import models.shopping.*;
import models.Outfit;
@Entity
public class Customer extends Model{

@Id
public Long id;

@Constraints.Required
public String firstname;

@Constraints.Required
public String lastname;

@Constraints.Required
public String address;

@Constraints.Required
public String street;

@Constraints.Required
public String city;

@Constraints.Required
public String county;

@Constraints.Required
public String email;

@Constraints.Required
public String phone_number;

@OneToMany(mappedBy="customer", cascade = CascadeType.ALL)
public List<ShopOrder> shopOrders;

public Customer(){
}

public Customer (Long id, String firstname, String lastname, String address, String street, String city, String county,String email, String phone_number){
	this. id = id;
	this.firstname = firstname;
	this.lastname = lastname;
	this.address = address;
	this.street = street;
	this.city = city;
	this.county = county;	
	this.email = email;
	this.phone_number = phone_number;
}

	//Generic query helper for entity Computer with id Long
    public static Finder<Long,Customer> find = new Finder<Long,Customer>(Long.class, Customer.class);

		// Generate options for an HTML select control
    public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(Customer c: Customer.find.orderBy("firstname").findList()) {
            options.put(c.id.toString(), c.firstname);
        }
        return options;
    }

		//Find all Outfits in the database
		public static List<Customer> findAll() {
			return Customer.find.all();
		}
/* public static Model.Finder<Long, Customer> find = new
Model.Finder<Long, Customer> (Long.class, Customer.class);

public static List<Customer> findAll(){
return Customer.find.all();
} */

}