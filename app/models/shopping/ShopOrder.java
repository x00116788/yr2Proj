package models.shopping;

import java.util.*;
import javax.persistence.*;
import java.util.Date;

import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

import models.*;


// ShopOrder entity managed by Ebean
@Entity
public class ShopOrder extends Model {

    @Id
    public Long id;    
    public Date orderDate;
	public Date dueDate;
    
    // Order contains may items.
    // mappedBy makes this side of the mapping the owner
    // so foreign key will be placed in resulting OrderItem table
    // All changes, including deletes will be cascaded
    @OneToMany(mappedBy="order", cascade = CascadeType.ALL)
    public List<OrderItem> items;
    
	
    @ManyToOne
    public User user;
	@ManyToOne
	public Customer customer;
    double totDeposit;

    // Default constructor
    public  ShopOrder() {
        orderDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 10); // add 10 days
		dueDate = cal.getTime();
		for (OrderItem i: items) {
            totDeposit += i.deposit;
        }
    }
		public Date getDue(){
			return dueDate;
		}
	public double getTotalDeposit() {
		totDeposit=0;
		for (OrderItem i: items) {
            totDeposit += i.deposit;
			i.outfit.available= false;
        }
        return totDeposit;
	}
    public double getOrderTotal() {
        
        double total = 0;
        
        for (OrderItem i: items) {
            total += i.getItemTotal();
        }
        return total;
    }
	
	//Generic query helper for entity with id Long
    public static Model.Finder<Long,ShopOrder> find = new Model.Finder<Long,ShopOrder>(Long.class, ShopOrder.class);
	
	// Generate options for an HTML select control
    public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(ShopOrder s: ShopOrder.find.orderBy("id").findList()) {
            options.put(s.id.toString(), s.customer.firstname);
        }
        return options;
    }
	//Find all Outfits in the database
	public static List<ShopOrder> findAll() {
		return ShopOrder.find.all();
	}
	/* //Generic query helper
    public static Finder<Long,ShopOrder> find = new Finder<Long,ShopOrder>(ShopOrder.class);

    //Find all Products in the database
    public static List<ShopOrder> findAll() {
        return ShopOrder.find.all();
    }
	 */
}

