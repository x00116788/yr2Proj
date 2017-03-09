package models;

import java.util.*;
import javax.persistence.*;
import play.data.format.*;
import play.data.validation.*;
import com.avaje.ebean.*;

@Entity
// This is a User of type manager
@DiscriminatorValue("manager")

// Manager inherits from the User class
public class Manager extends User {
		
	public Manager(String email, String name, String password)
	{
		super(email, name, password);
	}	
} 