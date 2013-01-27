package com.example.debts.model;

import java.util.Random;

public class Debt
{
	private String name;
	private double sum;
	
	// true if you have to give, false otherwise
	private boolean shouldGive;
	
	// used by the list view
	private boolean checked;
	
	/**
	 * Constructor. Creates random values for sum and type of debt.
	 */
	public Debt()
	{
		super();
		Random randomGenerator = new Random();
		
		this.name = "John Doe";
		this.sum = randomGenerator.nextInt(600);
		this.shouldGive = randomGenerator.nextBoolean();
		this.checked = false;
	}
	
	/**
	 * Constructor.
	 */
	public Debt(String name, double sum, boolean shouldGive)
	{
		super();
		this.name = name;
		this.sum = sum;
		this.shouldGive = shouldGive;
		this.checked = false;
	}
	
	/**
	 * Setter for the name of the person involved in the debt.
	 * @param name The name of the person.
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * Getter for the person involved in the debt.
	 * @return The name of the person involved in the debt.
	 */
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * Setter for the sum involved in the debt.
	 * @param sum The sum involved in the debt.
	 */
	public void setSum(double sum)
	{
		this.sum = sum;
	}
	
	/**
	 * Getter for the debt sum.
	 * @return The sum of money involved in the debt.
	 */
	public double getSum()
	{
		return this.sum;
	}
	
	public boolean isChecked()
	{
		return this.checked;
	}
	
	public void setChecked(boolean checked)
	{
		this.checked = checked;
	}
	
	/**
	 * Gets the kind of debt.
	 * @return true if I have to give, false otherwise.
	 */
	public boolean give()
	{
		return this.shouldGive;
	}
	
	@Override
    public String toString()
	{
        return this.name + ": " + this.sum;
    }
}
