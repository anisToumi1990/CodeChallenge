package com.Number26.codeChallenge;

import java.io.Serializable;
import static java.lang.Math.toIntExact;

/**
 * @author Anis
 *
 */
public class TransactionDAO implements Serializable {

   private static final long serialVersionUID = 1L;
   private long id;
   private double amount;
   private String type;
   private Long parent_id;

   public TransactionDAO(){}
   
   public TransactionDAO(long id, double amount, String type, Long parent_id){
	   this.id = id;
	   this.amount = amount;
	   this.type = type;
	   this.parent_id = parent_id;
   }

   public long getId() {
      return id;
   }
   public void setId(long id) {
      this.id = id;
   }
   
   public double getAmount() {
      return amount;
   }
   public void setAmount(double amount) {
      this.amount = amount;
   }
   
   public String getType() {
      return type;
   }
   public void setType(String type) {
      this.type = type;
   }

   public Long getParentId() {
	   return parent_id;
   }
   public void setParentId(Long parent_id) {
	   this.parent_id = parent_id;
   }
   
   //override equals and hashCode to prevent 
   		//duplicate transaction with the same id
   @Override
   public boolean equals(Object tnx) {

	   return (this.id ==((TransactionDAO)tnx).id);
   }
   
   @Override
   public int hashCode(){

	   return toIntExact(this.id);

   }
}