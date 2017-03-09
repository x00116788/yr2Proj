package models;

import java.util.*;
import javax.persistence.*;
import play.data.format.*;
import play.data.validation.*;
import com.avaje.ebean.*;

@Entity
// This User is of type staff/clerk
@DiscriminatorValue("staff")

public class Staff extends User{
	
	public Staff(String email, String name, String password)
	{
		super(email, name, password);
	}
	
} 