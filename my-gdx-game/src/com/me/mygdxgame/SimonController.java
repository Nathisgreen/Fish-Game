package com.me.mygdxgame;

import java.util.Random;

import com.badlogic.gdx.utils.Array;

public class SimonController {

	//are we playing the simon game or not
	private boolean isSimoning = false;
	
	//the lists that must be completed
	private int[] list1 = new int[3];
	private int[] list2 = new int[4];
	private int[] list3 = new int[5];
	
	//what the user has inputted
	private Array<Integer> userInputer = new Array<Integer>();
	
	//need to generate random numbers
	private Random aRandom = new Random();
	
	//reference to world object
	private World parentWorld;
	
	//const values for shell open and close time
	private static final int closeTime = 30;
	private static final int openTime = 60;
	
	//counter
	private int timer = 0;
	//target value
	private int DelayTime = closeTime;
	
	//if going through a simon sequence or not
	private boolean sequence = false;
	
	//the index in the sequence we are at
	private int index = 0;
	
	//if the shell in teh sequence was opened or closed last
	private boolean open = false;
	
	//if we should collect user input
	private boolean collect = false;
	
	public SimonController(World aWorld)
	{
		//store the reference
		parentWorld = aWorld;
		
		//store the previous generated number
		int previousNumber = -1;
		//the current generated number
		int temp;
		
		//generate the random lists
		for (int i = 0; i < 2; i ++)
		{
			//store the number
			temp = aRandom.nextInt(3);

			//make sure it isnt the same as the previous number
			while (temp == previousNumber)
			{
				//store the new number
				temp = aRandom.nextInt(3);
			}
			
			//set the previous number to this number
			previousNumber = temp;
			//set the index to be this number
			list1[i] = temp;
			
			//this *should* generate sequences where the number isnt the same twice in a row
			//(doesnt always work)
			
		}
		
		//same as above for second sequence
		for (int i = 0; i < 3; i ++)
		{
			
			temp = aRandom.nextInt(3);
			
			while (temp == previousNumber)
			{
				temp = aRandom.nextInt(3);
			}
			previousNumber = temp;
			list2[i] = temp;
		}
		
		
		//same as above for third sequence
		for (int i = 0; i < 4; i ++)
		{
			temp = aRandom.nextInt(3);
			
			while (temp == previousNumber)
			{
				temp = aRandom.nextInt(3);
			}
			previousNumber = temp;
			list3[i] = temp;
		}
		
		//debug prints
		System.out.println("list 1: " );
		for (int i = 0; i < list1.length; i++)
		{
			System.out.println(" " + list1[i] +"," );
		}
		
		System.out.println("list 2: " );
		for (int i = 0; i < list2.length; i++)
		{
			System.out.println(" " + list2[i] +"," );
		}
		
		System.out.println("list 3: " );
		for (int i = 0; i < list3.length; i++)
		{
			System.out.println(" " + list3[i] +"," );
		}
	}
	
	/**
	 * Open a clam
	 * @param ID the clam to open
	 */
	private void openClam(int ID)
	{
		for (Clam aClam: parentWorld.getClamArray())
		{
			if (aClam.getID() == ID)
			{
				aClam.open();
			}
		}
	}
	
	/**
	 * Close a clam
	 * @param ID the clam to close
	 */
	private void closeClam(int ID)
	{
		for (Clam aClam: parentWorld.getClamArray())
		{
			if (aClam.getID() == ID)
			{
				aClam.close();
			}
		}
	}
	
	/**
	 * Begin the simon game
	 */
	public void simonBegin()
	{
		for (Clam aClam: parentWorld.getClamArray())
		{
			aClam.setSimon(true);
			aClam.close();
		}
		
		sequence = true;
		index = 0;
	}
	
	private void setCollect(boolean aBool)
	{
		for (Clam aClam: parentWorld.getClamArray())
		{
			aClam.setCollect(aBool);
		}
	}
	
	public void addToPressList(int aID)
	{
		//user cant input while sequence is happening
		if (!sequence)
		{
			userInputer.add(aID);
		}
	}
	
	private boolean checkInput()
	{
		return true;
	}
	
	/**
	 * Update per frame operations
	 * @param delta times since last frame
	 */
	public void update (float delta)
	{
		if (sequence)
		{
			if (timer < DelayTime)
			{
				timer += 70 * delta;
			}
			else
			{
				if (!open)
				{
					if (index < list1.length)
					{
						openClam(list1[index]);
						open = true;
						DelayTime = closeTime;
						timer = 0;
					}
					else
					{
						sequence = false;
						collect = true;
						setCollect(collect);
					}
				}
				else
				{
					closeClam(list1[index]);
					open = false;
					DelayTime = openTime;
					timer = 0;
					if (index < list1.length)
					{
						index++;
					}
				}
			}
		}
		
		//Note: the clams are sending input to this class when they
		//are pressed and adding their input to userInputer
		//when userInputer is the length of the list we check if they are the
		//same
		if (collect)
		{
			if (userInputer.size == list1.length)
			{
				System.out.println("user input: " + userInputer);
				checkInput();
				collect = false;
				setCollect(collect);
			}
		}
	}
	
}
