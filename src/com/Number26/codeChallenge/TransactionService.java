package com.Number26.codeChallenge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * @author Anis
 *
 */
@Path("/transactionservice")
public class TransactionService {

	//this set will serve for storing the transactions added by the user
	private static Set<TransactionDAO> listOfTransactions = new HashSet<TransactionDAO>();

	/**
	 * Add a new transaction
	 * @param id : the id of the transaction to be added
	 * @param amount : double specifying the amount of the transaction
	 * @param type : string specifying the type of the transaction 
	 * @param parent_id : (optional) long that specify the parent transaction of this transaction
	 * @return string indicating the success of the operation
	 */
	@PUT
	@Path("/transaction")
	public String AddTransaction(@QueryParam("id") Long id,@QueryParam("amount") Double amount,@QueryParam("type") String type,@QueryParam("parentId") Long parent_id)
	{
		if((id == null) || (amount == null) || (type == null))
		{
			return "id & amount & type are required!";
		}
		else
		{
			TransactionDAO newTransaction;
			newTransaction = new TransactionDAO(id,amount,type,parent_id);	
			listOfTransactions.add(newTransaction);
			return listOfTransactions.size() + " transactions added";
		}
	}

	/**
	 * Get a transaction with its id.
	 * @param id : the id of the transaction to be found
	 * @return the found transaction or	 null
	 *    if the transaction does not exist
	 */
	@GET
	@Path("/transaction")
	@Produces(MediaType.APPLICATION_JSON)
	public TransactionDAO GetTransactionById(@QueryParam("id") Long id)
	{
		TransactionDAO savedTransaction = null;
		if(id != null)
		{
			Iterator<TransactionDAO> itr = listOfTransactions.iterator();
			TransactionDAO toIetrateTransaction = null;
			while (itr.hasNext()) {
				toIetrateTransaction = itr.next();
				if(toIetrateTransaction.getId() == id)
				{
					savedTransaction = toIetrateTransaction;
					break;
				}
			}
		}
		return savedTransaction;
	}

	/**
	 * Get all transaction that share the same inputted type.
	 * @param type : the type of the transactions to be found
	 * @return List of the found transactions or empty list
	 *    if the no match was found
	 */
	@GET
	@Path("/types")
	@Produces(MediaType.APPLICATION_JSON)	
	public List<Long> GetTransactionsByType(@QueryParam("type") String type)
	{
		List<Long> transactionsFound = new ArrayList<Long>();
		if(type != null)
		{
			TransactionDAO toIetrateTransaction;
			Iterator<TransactionDAO> itr = listOfTransactions.iterator();
			while (itr.hasNext()) {
				toIetrateTransaction = itr.next();
				if(toIetrateTransaction.getType().compareTo(type) == 0)
				{
					transactionsFound.add(toIetrateTransaction.getId());
				}
			}
		}
		return transactionsFound;
	}

	/**
	 * Sum the transaction's amount with related inputted id.
	 * @param id : the id of the parent transaction
	 * @return the sum of the found transactions' amounts or 0
	 *    if no match was found
	 */
	@GET
	@Path("/sum")
	@Produces(MediaType.APPLICATION_JSON)	
	public Double GetTransactionsSum(@QueryParam("id") Long id)
	{
		Double totalSum = 0.0;
		if(id != null)
		{
			TransactionDAO toIetrateTransaction;
			Iterator<TransactionDAO> itr = listOfTransactions.iterator();

			while (itr.hasNext()) {
				toIetrateTransaction = itr.next();
				//if the inputted id is equal to the id or the parent_id (not null) of the current transaction
				if((toIetrateTransaction.getId() == id) || 
						(toIetrateTransaction.getParentId() == id))
				{
					totalSum += toIetrateTransaction.getAmount();
				}
			}
		}
		return totalSum;
	}
}
