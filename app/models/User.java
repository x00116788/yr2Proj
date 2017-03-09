package models;

import java.util.*;
import javax.persistence.*;
import play.data.format.*;
import play.data.validation.*;
import com.avaje.ebean.*;

import models.Outfit.*;
import models.shopping.*;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) 
@DiscriminatorColumn(name = "userType")
@DiscriminatorValue("user") 

public class User extends Model {

    @Id
    public String email;

    @Constraints.Required
    public String name;

    @Constraints.Required
    public String password;
	
	@OneToOne(mappedBy="user", cascade = CascadeType.ALL)
    public Basket basket;
	
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL)
    public List<ShopOrder> orders;

	public  User() {
    }

    public  User(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
    
	public static Finder<String,User> find = new Finder<String,User>(String.class, User.class);
	public static List<User> findAll() {
		return User.find.all();
	}
	
    public	static User authenticate(String email, String password) {
        return find.where().eq("email", email).eq("password", password).findUnique();
    }

    public static User getLoggedIn(String id) {
        if (id == null)
                return null;
        else
            return find.byId(id);
    }
		
    @Transient
    public String getUserType(){
        DiscriminatorValue val = this.getClass().getAnnotation( DiscriminatorValue.class );
        return val == null ? null : val.value();
    }
        
}

