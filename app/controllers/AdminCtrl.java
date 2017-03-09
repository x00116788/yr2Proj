package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import play.data.Form.*;

import views.html.*;

import models.*;
import controllers.*;

// Import required classes
import java.util.ArrayList;
import java.util.List;

@Security.Authenticated(Secured.class)
// Authorise user (check if admin)
@With(CheckAdmin.class)

public class AdminCtrl extends Controller{
	
	public Result addStaff(){
		Form<Staff> addStaffForm = Form.form(Staff.class);
		return ok(addStaff.render(addStaffForm,User.getLoggedIn(session().get("email"))));
	}
	
	public Result addStaffSubmit(){
		Form<Staff> newStaffForm = Form.form(Staff.class).bindFromRequest();
		
		if(newStaffForm.hasErrors()){
			return badRequest(addStaff.render(newStaffForm, User.getLoggedIn(session().get("email"))));
		}
		
		newStaffForm.get().save();
		
		flash("Success", "Staff "+newStaffForm.get().email + "has been created");
		
		return redirect("/options");
	}	
	
	public Result addManager(){
		Form<Manager> addManagerForm = Form.form(Manager.class);
		return ok(addManager.render(addManagerForm,User.getLoggedIn(session().get("email"))));
	}
	
	public Result addManagerSubmit(){
		Form<Manager> newManagerForm = Form.form(Manager.class).bindFromRequest();
		
		if(newManagerForm.hasErrors()){
			return badRequest(addManager.render(newManagerForm, User.getLoggedIn(session().get("email"))));
		}
		
		newManagerForm.get().save();
		
		flash("Success", "Manager "+newManagerForm.get().email + "has been created");
		
		return redirect("/options");
	}	
	
	public Result deleteUser(String id) {
	User.find.ref(id).delete();	
	flash("success", "User has been deleted");
	return redirect("/listUsers");
	}
	
	public Result updateUser(String id){
		Form<User> updateUserForm = Form.form(User.class).fill(User.find.byId(id));
		return ok (updateUser.render(id, updateUserForm, User.getLoggedIn(session().get("email"))));
	}	

	public Result updateUserSubmit(String id) {
		Form<User> updateUserForm = Form.form(User.class).bindFromRequest();
		if(updateUserForm.hasErrors()) {
		return badRequest(updateUser.render(id, updateUserForm, User.getLoggedIn(session().get("email"))));
		}
		User u= updateUserForm.get();
		u.email = id;
		u.update();
		flash("success", "User " + updateUserForm.get() + " has been updated");
	return redirect("/listUsers");
	}
}