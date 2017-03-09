package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import play.data.Form.*;

import views.html.*;

// Import required classes
import java.util.ArrayList;
import java.util.List;

import models.*;
//import controllers.security.*;

@Security.Authenticated(Secured.class)
public class CustomerCtrl extends Controller {


	public Result addCustomer(){
		Form<Customer> addCustomerForm = Form.form(Customer.class);
		

		return ok(addCustomer.render(addCustomerForm, User.getLoggedIn(session().get("email"))));
	}

	public Result addCustomerSubmit(){
		Form<Customer> newCustomerForm = Form.form(Customer.class).bindFromRequest();
			if (newCustomerForm.hasErrors()){
				return badRequest(addCustomer.render(newCustomerForm, User.getLoggedIn(session().get("email"))));
			}
			
		newCustomerForm.get().save();
		flash("success", "Customer " + newCustomerForm.get() + " has been created");
		return redirect("/listCustomer");
	}

	@With(CheckManager.class)
	public Result deleteCustomer(Long id) {
		Customer.find.ref(id).delete();	
		flash("success", "Customer has been deleted");
		return redirect("/listCustomer");
		}
		
	public Result updateCustomer(Long id){
		Form<Customer> updateCustomerForm = Form.form(Customer.class).fill(Customer.find.byId(id));
		return ok (updateCustomer.render(id, updateCustomerForm, User.getLoggedIn(session().get("email"))));
	}	

	public Result updateCustomerSubmit(Long id) {
		Form<Customer> updateCustomerForm = Form.form(Customer.class).bindFromRequest();
		if(updateCustomerForm.hasErrors()) {
		return badRequest(updateCustomer.render(id, updateCustomerForm, User.getLoggedIn(session().get("email"))));
		}
		Customer c= updateCustomerForm.get();
		c.id = id;
		c.update();
		flash("success", "Customer " + updateCustomerForm.get() + " has been updated");
	return redirect("/listCustomer");
	}


	public Result listCustomer() {
		
		List<Customer> customer = Customer.findAll();
		return ok(listCustomer.render(customer, User.getLoggedIn(session().get("email"))));
		
    }
	@With(CheckManager.class)
	public Result listUsers() {		
		List<User> users = User.findAll();
		return ok(listUsers.render(users, User.getLoggedIn(session().get("email"))));
    }
    }
