package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import play.data.Form.*;
import play.mvc.Http.Context;

import java.util.ArrayList;
import java.util.*;

import views.html.*;

// Import models
//import models.*;
import models.*;
import models.shopping.*;

// Import security controllers
//import controllers.*;

// Authenticate user
@Security.Authenticated(Secured.class)
@With(CheckStaff.class)

public class ShoppingCtrl extends Controller {
    
    // Get a user - if logged in email will be set in the session
	private User getCurrentUser() {
		return User.getLoggedIn(session().get("email"));
	}
	public Result listOrders(Long cat) {
			// Get list of categories
			List<Customer> cust = new ArrayList<Customer>();
			List<ShopOrder> o = new ArrayList<ShopOrder>();
			o = ShopOrder.findAll();
			for(int i=0; i<o.size();i++){

				if(!cust.contains(o.get(i).customer)){
					cust.add(o.get(i).customer);
				}
				
			}
			//List<Customer> cust = Customer.find.where().orderBy("firstname asc").findList();
			List<ShopOrder> order = new ArrayList<ShopOrder>();
		
			if (cat == 0) {
				// Get the list of ALL outfits
				order = ShopOrder.findAll();
			}
			else {
				// Get outfits for the selected category
				// Each category object contains a list of outfits
				for (int i = 0; i < cust.size(); i++) {
					if (cust.get(i).id == cat) {
						order = cust.get(i).shopOrders;
						break;
					}
				}
			}
			// Pass the list to the index view and render
		  return ok(listOrders.render(cust, order, User.getLoggedIn(session().get("email"))));
    }
	public Result returns(Long cat) {
			// Get list of categories
			List<Customer> cust = new ArrayList<Customer>();
			List<ShopOrder> o = new ArrayList<ShopOrder>();
			o = ShopOrder.findAll();
			
			for(int i=0; i<o.size();i++){
//				if(o.get(i).items != null){
					for(OrderItem u : o.get(i).items){
						if(!cust.contains(o.get(i).customer)){
						cust.add(o.get(i).customer);
						}

					}
//				}
			}
			//List<Customer> cust = Customer.find.where().orderBy("firstname asc").findList();
			List<ShopOrder> order = new ArrayList<ShopOrder>();
		
			if (cat == 0) {
				// Get the list of ALL outfits
				order = ShopOrder.findAll();
			}
			else {
				// Get outfits for the selected category
				// Each category object contains a list of outfits
				for (int i = 0; i < cust.size(); i++) {
					if (cust.get(i).id == cat) {
						order = cust.get(i).shopOrders;
						break;
					}
				}
			}
			// Pass the list to the index view and render
		  return ok(returns.render(cust, order, User.getLoggedIn(session().get("email"))));
    }
	
	public Result returnOutfit(Long id){
		
		Form<OrderItem> orderItemForm = Form.form(OrderItem.class).fill(OrderItem.find.byId(id));
		
		return ok(returnForm.render(id, orderItemForm,User.getLoggedIn(session().get("email"))));

	}
	
	public Result returnOutfitSubmit(Long id){
		Form<OrderItem> orderItemForm = Form.form(OrderItem.class).bindFromRequest();	

				// Check for errors (based on Outfit class annotations)	
        if(orderItemForm.hasErrors()) {
						// Display the form again
            return badRequest(returnForm.render(id, orderItemForm,User.getLoggedIn(session().get("email"))));
        }
				// Update the Outfit (using its ID to select the existing object))
        OrderItem p = orderItemForm.get();
        OrderItem o = OrderItem.find.byId(id);
				p.id = id;
				p.update();

			
				o.setReturn();
				o.update();
Outfit returned = o.getOutfit();
returned.available = true;
returned.update();

				// Add a success message to the flash session
			
			// Render the Add Outfit View, passing the form object
			return ok(returnConfirmed.render(o, User.getLoggedIn(session().get("email"))));
	}
	
	public Result calcRefund(Long id){
		        OrderItem o = OrderItem.find.byId(id);
				o.calcRefund();
				o.update();
				
        flash("success", "Outfit " + o.outfit.id + " has been returned");
			
		return redirect(controllers.routes.ShoppingCtrl.listOrders(o.order.customer.id));
	}
	
    public Result showBasket() {
        return ok(basket.render(getCurrentUser()));
    }
	
	public Result addBasketCustomer(Long id){
		Customer c = Customer.find.byId(id);
		User user = User.getLoggedIn(session().get("email"));

		// Check if item in basket
        if (user.basket == null) {
            // If no basket, create one
            user.basket = new Basket();
            user.basket.user = user;
            user.update();
        }
		user.basket.addCustomer(c);
        user.update();
		return redirect(controllers.routes.RentalCtrl.rental(0,0));

	}		
	
    
    // Add item to user/staff basket
    public Result addToBasket(Long id) {
        
        // Find the product
        Outfit o = Outfit.find.byId(id);
        
        // Get basket for logged in user
        User user = User.getLoggedIn(session().get("email"));
        
        // Add product to the basket and save
        user.basket.addOutfit(o);
        user.update();
        
        // Show the basket contents     
        return ok(basket.render(user));
    } 
    
    
     public Result removeOne(Long itemId) {
        
        // Get the order item
        OrderItem item = OrderItem.find.byId(itemId);
        // Get user
        User u = getCurrentUser();
        // Call basket remove item method
        u.basket.removeItem(item);
        u.basket.update();
        // back to basket
        return ok(basket.render(u));
    } 

    // Empty Basket
     public Result emptyBasket() {
        
        User u = getCurrentUser();
        u.basket.removeAllItems();
        u.basket.update();
        
        return ok(basket.render(u));
    } 
    
     public Result placeOrder() {
        User u = getCurrentUser();
        Customer c = u.basket.customer;
        // Create an order instance
        ShopOrder order = new ShopOrder();
        
        // Associate order with customer
        order.user = u;
		order.customer = c;
        
        // Copy basket to order
        order.items = u.basket.basketItems;
		//c.hired = u.basket.basketItems.outfit;
        
        // Save the order now to generate a new id for this order
        order.save();
       
       // Move items from basket to order
        for (OrderItem i: order.items) {
            // Associate with order
            i.order = order;
			Outfit hired = i.getOutfit();
			hired.available = false;
			hired.update();
            // Remove from basket
            i.basket = null;
            // update item
            i.update();
			
        }
        order.getTotalDeposit();
        // Update the order
        order.update();
        
        // Clear and update the shopping basket
        u.basket.basketItems = null;
        u.basket.update();
        
        // Show order confirmed view
        return ok(orderConfirmed.render(u, order));
    } 
    
	//view all orders
	/* public Result listOrders(){
		List<ShopOrder> orders = new ArrayList<ShopOrder>();
		orders = ShopOrder.findAll();
        return ok(order.render(getCurrentUser(), orders));
	} */
    // View an individual order
     public Result viewOrder(long id) {
        ShopOrder order = ShopOrder.find.byId(id);
        return ok(orderConfirmed.render(getCurrentUser(), order));
    } 

	 
}
