package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import play.data.Form.*;

import views.html.*;

// Import required classes
import java.util.ArrayList;
import java.util.List;

// Import models
import models.*;
//import controllers.security.*;

public class Application extends Controller {
	

    public Result index() {
		
		return ok(index.render(User.getLoggedIn(session().get("email"))));
    }
			
	@Security.Authenticated(Secured.class)
	public Result options(){
		
		return ok(options.render(User.getLoggedIn(session().get("email"))));
	}
	
	
}
	