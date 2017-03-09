package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.*;
import play.data.format.*;
import play.data.validation.*;


// Outfit entity managed by Ebean
@Entity
public class Outfit extends Model{

    // Fields - note that these are defined as public and not private
    // During compile time, The Play Framework
    // automatically generates getters and setters

    @Id
    public Long id;

    public String colour;

	
    public double price;
	
	
    public int	size;
	
	public Boolean available = false;
	
	@ManyToOne
    public Category category;
   

    // Default constructor
    public  Outfit() {
		this.available = true;
    }

    // Constructor to initialise object
    public  Outfit(String colour, double price, int size, Category category){
        
        this.colour = colour;
        this.price = price;
        this.size = size;
        this.category = category;
		this.available = true;
    }
	
	//Generic query helper for entity Computer with id Long
    public static Model.Finder<Long,Outfit> find = new Model.Finder<Long,Outfit>(Long.class, Outfit.class);
	//Find all Outfits in the database
	public static List<Outfit> findAll() {
		return Outfit.find.all();
	}
	
	public static Outfit findByID(Long id){
		return Outfit.find.byId(id);
	}
	
}

