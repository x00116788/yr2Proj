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

@Security.Authenticated(Secured.class)

public class OutfitCtrl extends Controller {	
		// Show a list of all outfits
		public Result listOutfits(Long cat) {
			// Get list of categories
			List<Category> categories = Category.find.where().orderBy("name asc").findList();
			// Instansiate outfits, an Arraylist of outfits			
			List<Outfit> outfits = new ArrayList<Outfit>();
		
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
			// Pass the list to the index view and render
		  return ok(listOutfits.render(categories, outfits, User.getLoggedIn(session().get("email"))));
    }
@With(CheckManager.class)

		public Result create() {
			// Instantiate a form object based on the Outfit class
			Form<Outfit> addOutfitForm = Form.form(Outfit.class);
			
			// Render the Add Outfit View, passing the form object
			return ok(create.render(addOutfitForm,User.getLoggedIn(session().get("email"))));
		}

		public Result save() {

			// Create an outfit form object (to hold submitted data)
			// 'Bind' the object to the submitted form (this copies the filled form)
			Form<Outfit> newOutfitForm = Form.form(Outfit.class).bindFromRequest();	

				// Check for errors (based on Outfit class annotations)	
        if(newOutfitForm.hasErrors()) {
						// Display the form again
			flash("Error");
            return badRequest(create.render(newOutfitForm,User.getLoggedIn(session().get("email"))));
        }
				// Save the Outfit using Ebean (remember Outfit extends Model)
        newOutfitForm.get().save();
        flash("success", "Outfit " + newOutfitForm.get().id + " has been created");
			
			// Render the Add Outfit View, passing the form object
			return redirect("/listOutfits");
		}
@With(CheckManager.class)

		public Result updateOutfit(Long id) {

				// Create a form based on the Outfit class
				// Fill the form with outfit object matching id
				// Use the finder to find the object by ID in the DB
        Form<Outfit> outfitForm = Form.form(Outfit.class).fill(
						Outfit.find.byId(id)
        );
				// Render the updateOutfit view
				// pass the id and form as parameters
        return ok(updateOutfit.render(id, outfitForm,User.getLoggedIn(session().get("email"))));					
		}

		// Handle the form data when an updated outfit is submitted
		
		public Result updateOutfitSubmit(Long id) {

			// Create a outfit form object (to hold submitted data)
			// 'Bind' the object to the submitted form (this copies the filled form)
			Form<Outfit> updateOutfitForm = Form.form(Outfit.class).bindFromRequest();	

				// Check for errors (based on Outfit class annotations)	
        if(updateOutfitForm.hasErrors()) {
						// Display the form again
            return badRequest(updateOutfit.render(id, updateOutfitForm,User.getLoggedIn(session().get("email"))));
        }
				// Update the Outfit (using its ID to select the existing object))
        Outfit p = updateOutfitForm.get();
				p.id = id;
				p.update();

				// Add a success message to the flash session
        flash("success", "Outfit " + updateOutfitForm.get().id + " has been updated");
			
			// Render the Add Outfit View, passing the form object
			return redirect("/listOutfits");
		}
		
    // Delete Outfit
	@With(CheckManager.class)

    public Result delete(Long id) {
				// Call delete method
        Outfit.find.ref(id).delete();
        
				// Add message to flash session 
				flash("success", "Outfit has been deleted");

				// Redirect home
				return redirect("/listOutfits");
    }
}