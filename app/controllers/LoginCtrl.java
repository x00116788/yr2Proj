package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import play.data.Form.*;
import play.mvc.Http.Context;

import views.html.*;

// Import required classes
import java.util.ArrayList;
import java.util.List;

// Import models
import models.*;

//import controllers.security.*;

public class LoginCtrl extends Controller {

    public Result login() {
	   return ok(login.render(Form.form(Login.class), User.getLoggedIn(session().get("email"))));
    }

    public Result authenticate() {
        Form<Login> loginForm = Form.form(Login.class).bindFromRequest();

        if (loginForm.hasErrors()) {
            return badRequest(login.render(loginForm, User.getLoggedIn(session().get("email"))));
        } 
        else {
            session().clear();
            session("email", loginForm.get().email);
            
            User u = User.getLoggedIn(loginForm.get().email);
            if (u != null && "admin".equals(u.getUserType())) {
                return redirect(controllers.routes.Application.options());
            }
            
            return redirect(controllers.routes.Application.options());
        }
    }	

    public Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(
            routes.LoginCtrl.login()
        );
    }
}
