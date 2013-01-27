package com.example.debts.model;

import java.util.ArrayList;

public class DebtCollection 
{
	private ArrayList<Debt> debts;
	
	/**
	 * Constructor.
	 */
	public DebtCollection() 
	{
		super();
	    debts = new ArrayList<Debt>();
	}
	
	/**
	 * Constructor.
	 * @param data The debts array.
	 */
	public DebtCollection(ArrayList<Debt> data) 
	{
		super();
	    debts = new ArrayList<Debt>(data);
	}
	
	/**
	 * Adds a debt to the debt collection.
	 * @param debt The debt to be added.
	 */
	public void addDebt(Debt debt)
	{
		debts.add(debt);
	}
	
	/**
	 * Deletes a debt from the debt collection.
	 * @param debt The debt to be deleted.
	 */
	public void deleteDebt(Debt debt)
	{
		debts.remove(debt);
	}
	
	/**
	 * Deletes a debt from the debt collection.
	 * @param index The index of the debt to be deleted.
	 */
	public void deleteDebt(int index)
	{
		debts.remove(index);
	}
	
	/**
	 * Returns a debt at a specific index.
	 * @param index The index of the debt.
	 * @return The debt at the provided index.
	 */
	public Debt getDebt(int index)
	{
		return debts.get(index);
	}

	/**
	 * Returns the debts array.
	 * @return The debts array.
	 */
	public ArrayList<Debt> getDebts()
	{
		return debts;
	}
	
	/**
	 * Generates 50 random debts and adds the to the collection.
	 */
	public void generateRandomCollection()
	{
		for (int i = 0; i < 3; i++)
		{
			Debt newDebt = new Debt();
			debts.add(newDebt);
		}
	}
}
