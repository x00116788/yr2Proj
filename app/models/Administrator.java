package models;

import java.util.*;
import javax.persistence.*;
import play.data.format.*;
import play.data.validation.*;
import com.avaje.ebean.*;

@Entity
// This User is of type admin
@DiscriminatorValue("admin")

public class Administrator extends User{
	
	public Administrator(String email, String name, String password)
	{
		super(email, name, password);
	}
	
} 