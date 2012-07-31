package com.me.mygdxgame;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.utils.Array;

public class SimonController {

	//are we playing the simon game or not
	private boolean isSimoning = false;
	
	private ArrayList<ArrayList<Integer>> sequenceList = new ArrayList<ArrayList<Integer>>();
	//used to determin how many sequences have been generated
	private int sequenceCount = 1;
	
	private ArrayList<Integer> activeList;
	private int sequencesCompleted = 0;
	
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
	
	//if the shell in the sequence was opened or closed last
	private boolean open = false;
	
	//if we should collect user input
	private boolean collect = false;
	
	private boolean nextLevel = false;
	private int nextLevelCount = 0;
	private int nextLevelTime = 80;
	
	public static boolean green = false;
	private int greenCounter = 0;
	private int greenTime = 15;
	
	//is the game over or not
	private boolean isDone = false;
	
	public SimonController(World aWorld)
	{
		//store the reference
		parentWorld = aWorld;
	}
	
	@SuppressWarnings("unchecked")
	private void setUpSequences() {
		
		//store the previous generated number
		int previousNumber = -1;
		//the current generated number
		int temp;
		
		ArrayList<Integer> tempList = new ArrayList<Integer>();
		while (sequenceCount < 4)
		{
			//generate the random lists
			for (int i = 0; i < 2 ; i ++)
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
				tempList.add(temp);
				
				//this *should* generate sequences where the number isnt the same twice in a row
				
			}
			
			System.out.println("List " +sequenceCount+ " contains: " + tempList);
			ArrayList<Integer> tempList1;
			tempList1 = (ArrayList<Integer>) tempList.clone();
			sequenceList.add(tempList1);
			sequenceCount++;
		}
		
		activeList = sequenceList.get(0);
		System.out.println("Active list is:" +sequenceList);
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
		setUpSequences();
		for (Clam aClam: parentWorld.getClamArray())
		{
			aClam.setSimon(true);
			aClam.close();
			aClam.setIsSequencing(true);
		}
		isSimoning = true;
		sequence = true;
		index = 0;
	}
	
	/**
	 * reset the simon game incase of game over
	 */
	public void reset()
	{
		index = 0;
		sequencesCompleted = 0;
		sequenceCount = 1;
		isDone = false;
		isSimoning = false;
		
		//clear the sequence list
		for (ArrayList<Integer> aList: sequenceList)
		{
			aList.clear();
		}
		sequenceList.clear();
		
		for (Clam aClam: World.clamArray)
		{
			aClam.setSimon(false);
		}
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
			System.out.println("Adding " +aID+ " to input");
			userInputer.add(aID);
		}
	}
	
	private boolean checkInput()
	{
		//boolean ok = true;
		for (int i = 0; i < userInputer.size; i++)
		{
			if (userInputer.get(i) != activeList.get(i))
			{
				parentWorld.hurt();
				return false;
			}
		}
		System.out.println("INPUT THE SAME");
		return true;
	}
	
	/**
	 *Returns if currently playing the simon game 
	 * @return isSimoning
	 */
	public boolean getIsSimoning()
	{
		return isSimoning;
	}
	
	/**
	 * gets if the game has been completed or not
	 * @return isDone
	 */
	public boolean getIsDone()
	{
		return isDone;
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
					if (index < activeList.size())
					{
						openClam(activeList.get(index));
						open = true;
						DelayTime = closeTime;
						timer = 0;
					}
					else
					{
						sequence = false;
						collect = true;
						setCollect(collect);
						index = 0;
						
						for (Clam aClam: World.clamArray)
						{
							aClam.setIsSequencing(false);
						}
					}
				}
				else
				{
					closeClam(activeList.get(index));
					open = false;
					DelayTime = openTime;
					timer = 0;
					if (index < activeList.size())
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
			if (userInputer.size == activeList.size())
			{
				System.out.println("user input: " + userInputer);
				collect = false;
				setCollect(collect);
				
				if (checkInput())
				{
					sequencesCompleted++;
					green = true;
				}
				
				userInputer.clear();
				
				for (Clam aClam:World.clamArray)
				{
					aClam.close();
				}
				
				if (sequencesCompleted < sequenceCount -1)
				{
					nextLevel = true;

				}
				else
				{
					isSimoning = false;
					isDone = true;
					for (Clam aClam: World.clamArray)
					{
						aClam.setSimon(false);
					}
							
				}
			}
		}
		
		if (nextLevel)
		{
			if (nextLevelCount < nextLevelTime)
			{
				nextLevelCount += 70 * delta;
			}
			else
			{
				for (Clam aClam: World.clamArray)
				{
					aClam.close();
					aClam.setIsSequencing(true);
				}
				
				activeList = sequenceList.get(sequencesCompleted);
				sequence = true;
				nextLevel = false;
				nextLevelCount = 0;
			}
		}
		
		//green flash when correct
		if (green)
		{
			if (greenCounter < greenTime)
			{
				greenCounter+= 70 * delta;
			}
			else
			{
				greenCounter = 0;
				green = false;
			}
		}
	}
	
}
