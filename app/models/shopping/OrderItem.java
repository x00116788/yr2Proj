package models.shopping;

import java.util.*;
import javax.persistence.*;

import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

import models.*;

// OrderItem entity managed by Ebean
@Entity
public class OrderItem extends Model {

    @Id
    public Long id;

    @ManyToOne
    public ShopOrder order;
    
    @ManyToOne
    public Basket basket;
    
    // Unidirection mapping - Many order items can have one product
    // Product not interested in this
    @ManyToOne
    public Outfit outfit;    
    public double price;	
	public double deposit;
    public Date returnDate;
	public double refundDue;
	public Boolean damaged;
	public Boolean isEarly;
	
    // Default constructor
    public  OrderItem() {
    }
    
    public OrderItem(Outfit o) {
			
            outfit = o;
			outfit.available = false;
            price = o.price;
			deposit= o.price/2;
			
    }
		public void setDamaged(Boolean b){
	this.damaged = b;
	}
		public Boolean getDamaged(){
return this.damaged;
}
    public double getDeposit(){
		return this.deposit;
	}
	
	public double getRefund(){
		return this.refundDue;
	}
	
	public void setReturn(){
		
		this.returnDate = new Date();
			if ((returnDate.before(this.order.getDue()))||(returnDate.equals(this.order.getDue()))){
				isEarly = true;
			}else{
				isEarly = false;
			}
	}
	
	public void calcRefund(){

		if(this.isEarly){
			if (this.damaged){
				this.refundDue = this.deposit/2;
				
			}
			else{
				this.refundDue = this.deposit;
			}			
		
		}else{
			

			if (this.damaged == false){
				this.refundDue = this.deposit/2;
			}
			else{
				this.refundDue = 0.0;
			}		
		}
		
	}
		
	public Outfit getOutfit(){
		return this.outfit;
		
	} 
    // Calculate and return total price for this order item
    public double getItemTotal() {
        return this.price + this.deposit;
    }
	public void setReturnDate(){
		this.returnDate=new Date();
	}	
	//Generic query helper
    public static Finder<Long,OrderItem> find = new Finder<Long,OrderItem>(OrderItem.class);

    //Find all Products in the database
    public static List<OrderItem> findAll() {
        return OrderItem.find.all();
    }	
}

