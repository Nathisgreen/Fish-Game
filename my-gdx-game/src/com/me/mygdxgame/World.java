package com.me.mygdxgame;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


public class World {
	
	private Array<Block> blocks = new Array<Block>();
	private Array<PowerUp> powerupList = new Array<PowerUp>();
	private Array<Block> tempList = new Array<Block>();
	private Array<PowerUp> tempListPower = new Array<PowerUp>();
	
	private Array<JellyFish> jellyArray = new Array<JellyFish>();
	
	public static Array<Clam> clamArray = new Array<Clam>();
	
	private Array<ScoreText> scoreArray = new Array<ScoreText>();
	
	private Array<CrabBullet> crabBulletArray = new Array<CrabBullet>();
	
	
	//Array of multiplyer texts
	public static Array<MultiplyerText> multiplyerList = new Array<MultiplyerText>();
	
	private Bob bob;
	private SelectSquare selector;
	private int score = 0;
	private int level = 1;
	private int numberInSelected = 0;
	private int selectedColor = 0;
	private Random aRandom;
	private int powerBar = 0;
	
	private boolean isFrozen = false;
	private int frozenTimer = 0;
	
	private Vector2 fingerPos = new Vector2();
	private Rectangle fingerBox = new Rectangle();
	
	private boolean hurt = false;
	private int hurtTime = 0;
	private int hurtCount = 0;
	
	private int life = 3;
	
	private Crab theCrab;
	private float wallTimer = 0;
	private float wallLastTime = 30;
	
	private boolean doDestroy = false;
	
	private CrabAvoidController aCrabAvoidController = new CrabAvoidController();
	
	private SimonController aSimonController = new SimonController(this);
	
	
	//GETTERS
	public Array<Block> getBlocks(){
		return blocks;
	}	
	public Bob getBob(){
		return bob;
	}
	public SelectSquare getSelector()
	{
		return this.selector;
	}
	public int getScore()
	{
		return score;
	}
	public int getNumberIn()
	{
		return numberInSelected;
	}
	public int getColorIn()
	{
		return selectedColor;
	}
	public int getPowerBar()
	{
		return powerBar;
	}
	public Array<PowerUp> getPowerupList()
	{
		return powerupList;
	}
	public Array<JellyFish> getJellyList()
	{
		return jellyArray;
	}
	public void setFingerPos(int aX, int aY)
	{

		fingerPos.x = aX ;
		fingerPos.y = aY ;
		fingerBox.x = aX - 15;
		fingerBox.width = 25;
		fingerBox.y =  aY + 15 ;
		fingerBox.height = 25;
	}
	public Vector2 getFingerPos()
	{
		return fingerPos;
	}
	public Boolean getHurt()
	{
		return hurt;
	}
	public Rectangle getFingerRect()
	{
		return fingerBox;
	}
	public Array<Clam> getClamArray()
	{
		return clamArray;
	}
	public int getLife()
	{
		return life;
	}
	public Array<ScoreText> getScoreArray()
	{
		return scoreArray;
	}
	public Array<MultiplyerText> getMultiplyerList()
	{
		return multiplyerList;
	}
	public Crab getCrab()
	{
		return theCrab;
	}
	public Array<CrabBullet> getCrabBulletArray()
	{
		return crabBulletArray;
	}

	
	public World(){
		aRandom = new Random();
		selector = new SelectSquare();	
		createDemoWorld();
		aSimonController.simonBegin();
	}
	
	public void checkSelected()
	{
		Array<Block> allBlocks = new Array<Block>();
		
		allBlocks.addAll(blocks);
		allBlocks.addAll(powerupList);
		
		for (Block aBlock: allBlocks)
		{
			float yy = WorldRenderer.getCameraHeight() - aBlock.getPosition().y;
			if ((aBlock.getPosition().x + aBlock.getBounds().width)  * WorldRenderer.ppuX < selector.getX1() &&
					aBlock.getPosition().x* WorldRenderer.ppuX > selector.getX() &&
					(yy - aBlock.getBounds().height) * WorldRenderer.ppuY > selector.getY() &&
					yy * WorldRenderer.ppuY < selector.getY1())
			{
				tempList.add(aBlock);
			}
			
			if (aBlock.getPosition().x * WorldRenderer.ppuX > selector.getX1() &&
					(aBlock.getPosition().x + aBlock.getBounds().width)* WorldRenderer.ppuX < selector.getX() &&
					yy * WorldRenderer.ppuY < selector.getY1() &&
					(yy - aBlock.getBounds().height)  * WorldRenderer.ppuY > selector.getY())
			{
				tempList.add(aBlock);
			}
			
			if (aBlock.getPosition().x * WorldRenderer.ppuX > selector.getX1() &&
					(aBlock.getPosition().x + aBlock.getBounds().width)* WorldRenderer.ppuX < selector.getX() &&
					(yy- aBlock.getBounds().height) * WorldRenderer.ppuY  > selector.getY1() &&
					yy  * WorldRenderer.ppuY < selector.getY())
			{
				tempList.add(aBlock);
			}
			
			if (aBlock.getPosition().x * WorldRenderer.ppuX < selector.getX1() &&
					aBlock.getPosition().x * WorldRenderer.ppuX > selector.getX() &&
					(yy- aBlock.getBounds().height) * WorldRenderer.ppuY  > selector.getY1() &&
					yy  * WorldRenderer.ppuY < selector.getY())
			{
				tempList.add(aBlock);
			}
		}

		doDestroy = true;
		int firstColor = -1;
		numberInSelected = tempList.size;
		if (tempList.size > 0)
		{
			for (Block aBlock2: tempList)
			{
				if (firstColor == -1)
				{
					firstColor = aBlock2.getColor();
					selectedColor = aBlock2.getColor();
				}
				else
				{
					if (aBlock2.getColor() != firstColor)
					{
						doDestroy = false;
					}
				}
			}
			
			if (doDestroy)
			{
				if (tempList.size > 1)
				{
					float yy = WorldRenderer.getCameraHeight() - tempList.get(0).getPosition().y;
	
					createMultiplyerText(tempList.size, new Vector2 (tempList.get(0).getPosition().x * WorldRenderer.ppuX,
							yy * WorldRenderer.ppuY),0,1,2.5f);
					
					if (powerBar < 100)
					{
						powerBar += 10 * tempList.size;
					}
					else
					{
						powerBar = 0;
						createPowerUp();
					}
				}
				
				score += (tempList.size * 20) * tempList.size;
				
				for (Block aBlock1: tempList)
				{
					if (aBlock1 instanceof Block)
					{
						blocks.removeValue(aBlock1, true);	
					}
					
					if (tempList.size > 1)
					{
						if (aBlock1 instanceof PowerUp)
						{
							PowerUp aPower = (PowerUp) aBlock1;
							if (aPower.getType() == 0)
							{
								isFrozen = true;
							}
							powerupList.removeValue((PowerUp)aBlock1, true);
						}
					}
				}
			}
		}
		
		if (blocks.size == 0)
		{
			level ++;
			createBlocks();
			
			createMultiplyerText(level, new Vector2(3 * WorldRenderer.ppuX, 2 * WorldRenderer.ppuY),1,2, 4f);

		}
		
		tempList.clear();
		
	}
	
	private void createPowerUp() {
		powerupList.add(new PowerUp(new Vector2(aRandom.nextInt(10),aRandom.nextInt(8)),0,60*10));
		
	}
	private void createDemoWorld(){
	
		createBlocks();
		
		theCrab = new Crab(new Vector2(5,0.5f));
		
		jellyArray.add(new JellyFish((new Vector2(1 + aRandom.nextInt(8), 1+ aRandom.nextInt(6)))));
		
		for (int i = 1; i < 4; i ++)
		{
			clamArray.add(new Clam(new Vector2(i *2 , (aRandom.nextFloat() /2) + 0.2f),i -1,aSimonController));
		}

	}
	
	private void createBlocks()
	{	
		//add all blocks to the world
		for (int i = 0; i < level; i++){
			blocks.add(new Block(new Vector2(1 + aRandom.nextInt(8), 1+ aRandom.nextInt(6))));
		}
		
		if (level % 5 == 0)
		{
			jellyArray.add(new JellyFish((new Vector2(-1, 1+ aRandom.nextInt(6)))));
		}
		
		
	}
	
	public void update(float delta)
	{	
		
		if (!isFrozen)
		{
			for (Block aBlock : blocks)
			{
				aBlock.update(delta);
			}
		}

		
		for (PowerUp aPowerup: powerupList)
		{
			if (!isFrozen)
			{
				aPowerup.update(delta);
			}
			
			if (aPowerup.getDestroy())
			{
				tempList.add(aPowerup);
			}
		}
		
		for (Block aPower: tempList)
		{
			powerupList.removeValue((PowerUp) aPower, true);
		}
		tempList.clear();
		
		if (isFrozen)
		{
			if (frozenTimer < 30 * 10)
			{
				frozenTimer++;
			}
			else
			{
				isFrozen = false;
				frozenTimer = 0;
			}
		}
		
		//update clams
		for (Clam aClam: clamArray)
		{
			aClam.update(delta);
			
			if (selector.getTouched())
			{
				if (aClam.checkInside(fingerBox))
				{
					if (aClam.getPearl())
					{
						aClam.setPearl(false);
						score += 400;
						
						//pooling system for score texts
						doDestroy = false;
						
						//first look for one that isnt being used 
						if (scoreArray.size != 0)
						{
							for(ScoreText aText: scoreArray)
							{
								//if the alpha is 0 its done being used
								if (aText.getAlpha() == 0)
								{
									//flag we found one to stop new one being made
									doDestroy = true;
									//reset up the text with new position
									aText.setup(400, new Vector2(aClam.getPos().x,aClam.getPos().y + (aClam.getBounds().height/2)));
									//stop the loop as we got what we need
									break;
								}
							}
						}
						
						//if one wasnt found
						if (!doDestroy)
						{
							//create a new one and add to array
							scoreArray.add(new ScoreText(400, new Vector2(aClam.getPos().x,aClam.getPos().y + (aClam.getBounds().height/2))));
						}
					}
				}
			}
		}
		
		for (MultiplyerText aText: multiplyerList)
		{
			aText.update();
		}
		
		//update jelly fish
		for (JellyFish aJelly: jellyArray)
		{
			aJelly.update(delta);
			
			if (selector.getTouched())
			{
				if (aJelly.checkInside(fingerBox))
				{
					hurt();
				}
			}
		}
		
		if (hurt)
		{
			if (hurtCount < hurtTime)
			{
				hurtCount++;
			}
			else
			{
				hurtCount = 0;
				hurtTime = 0;
				hurt = false;
			}
		}
		
		for (ScoreText aText: scoreArray)
		{
			if (aText.getAlpha() != 0)
			{
				aText.update(delta);
			}
		}
		
		
		if (level == 10 || level == 20)
		{
			//only do this when beginning
			if (wallTimer == 0)
			{
				aCrabAvoidController.begin();
				theCrab.setActive(true);
			}
			
			//every frame stuff
			if (wallTimer < wallLastTime )
			{
				wallTimer += 1 * delta ;
			}
			else
			{
				aCrabAvoidController.end();
				theCrab.setActive(false);
				wallTimer = 0;
				
				//change level
				level ++ ;
				createBlocks();
				createMultiplyerText(level, new Vector2(300,150),1,3, 5.5f);
			}
		}
		
		aSimonController.update(delta);
		
		aCrabAvoidController.update(delta);
		
		theCrab.update(delta);
		
		
		
		for (CrabBullet aBullet: crabBulletArray)
		{
			if (aBullet.getActive())
			{
				aBullet.update(delta);
			}
		}
		
		
		if (selector.getTouched())
		{
			if (theCrab.checkInside(fingerBox))
			{
				createCrabBullet(new Vector2(theCrab.getPosition().x + (theCrab.getBounds().width/2) - 0.1f, theCrab.getPosition().y + (theCrab.getBounds().height)));
			}
		}
		
		for(Wall aWall: CrabAvoidController.getWallArray())
		{
			if ( aWall.getActive())
			{
				if (theCrab.checkWall(aWall))
				{
					hurt();
				}
			}
		}
		
		for(Pearl aPearl: CrabAvoidController.getPearlArray())
		{
			if ( aPearl.getActive())
			{
				if (theCrab.checkPearl(aPearl))
				{
					aPearl.setActive(false);
					
					//show +100 text like with clams
					
					score += 100;
				}
			}
		}
		
	}
	
	//handles player getting hurt logic
	private void hurt() 
	{
		if (!hurt)
		{
			hurt = true;
			hurtTime = 10;
			Gdx.input.vibrate(100);
		}
		
		if (life > 1)
		{
			life--;
		}
		else
		{
			restartGame();
		}
	}
	
	//resets everything when game restarts
	private void restartGame() {
		
		level = 1;
		score = 0;
		life = 3;
		powerBar = 0;
		
		jellyArray.clear();
		blocks.clear();
		clamArray.clear();
		powerupList.clear();
		
		aCrabAvoidController.reset();
		createDemoWorld();
		
	}
	
	/**
	 * Finds or creates a multiplyer text to display inforomation
	 * @param value	the number the text should be drawn with
	 * @param pos	the position on the screen the text will be drawn
	 * @param aType	0 means multiplyer, 1 means level start text
	 * @param aSize	the start size of the text
	 * @param aMaxSize	how big the text can get
	 */
	private void createMultiplyerText(int value, Vector2 pos, int aType, float aSize, float aMaxSize)
	{		
		//pooling system for multiplyer texts
		Boolean hasCreated = false;
		
		//first look for one that isnt being used 
		if (multiplyerList.size != 0)
		{
			for(MultiplyerText aText: multiplyerList)
			{
				//if the alpha is 0 its done being used
				if (!aText.getActive())
				{
					//flag we found one to stop new one being made
					hasCreated = true;
					//reset up the text with new position
					aText.setup(value, pos, aType, aSize, aMaxSize);
					//stop the loop as we got what we need
					break;
				}
			}
		}
		
		//if one wasn't found
		if (!hasCreated)
		{
			//create a new one and add to array
			multiplyerList.add(new MultiplyerText(value, pos, aType, aSize, aMaxSize));
		}
	}
	
	/**
	 * Finds or creates a crab bullet
	 * @param pos	the position on the screen the text will be drawn
	 */
	private void createCrabBullet(Vector2 pos)
	{		
		//pooling system for crab bullets texts
		Boolean hasCreated = false;
		
		//first look for one that isnt being used 
		if (crabBulletArray.size != 0)
		{
			for(CrabBullet aBullet: crabBulletArray)
			{
				//if the alpha is 0 its done being used
				if (!aBullet.getActive())
				{
					//flag we found one to stop new one being made
					hasCreated = true;
					//reset up the text with new position
					aBullet.setup(pos);
					//stop the loop as we got what we need
					break;
				}
			}
		}
		
		//if one wasn't found
		if (!hasCreated)
		{
			//create a new one and add to array
			crabBulletArray.add(new CrabBullet(pos));
		}
	}
}
