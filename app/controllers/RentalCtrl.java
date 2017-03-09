package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import play.data.Form.*;

import views.html.*;

import java.util.ArrayList;
import java.util.List;

import models.*;
//import controllers.security.*;

	@Security.Authenticated(Secured.class)

public class RentalCtrl extends Controller {
	
	// Show a list of customers
	public Result rentalCustomer() {		
		List<Customer> customer = Customer.findAll();
		return ok(rentalCustomer.render(customer, User.getLoggedIn(session().get("email"))));
		
    }
		@Security.Authenticated(Secured.class)	
		public Result rental(Long cat, Long id) {
			// Get list of categories
			List<Category> categories = Category.find.where().orderBy("name asc").findList();
			// Instansiate outfits, an Arraylist of outfits			
			List<Outfit> outfits = new ArrayList<Outfit>();
		
			if (id == 0){
				// Not a search
				if (cat == 0) {
					// Get the list of ALL outfits
					outfits = Outfit.findAll();
				}
				else {
					// Get outfits for the selected category
					// Each category object contains a list of outfits
					for (int i = 0; i < categories.size(); i++) {
						if (categories.get(i).id == cat) {
							outfits = categories.get(i).outfits;
							break;
						}
					}
				}					
			}
			else {
				if((Outfit.findByID(id)) == null){
					flash("success", "The ID does not exist");

				}else{
					outfits.add(Outfit.findByID(id));
				}
				
			}

			// Pass the list to the view and render
			
		  return ok(rental.render(id, cat, categories, outfits, User.getLoggedIn(session().get("email"))));
    }
	
}
