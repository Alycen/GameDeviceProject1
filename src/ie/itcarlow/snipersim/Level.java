package ie.itcarlow.snipersim;

import ie.itcarlow.snipersim.scene.BaseScene;

import java.util.ArrayList;
import java.util.Random;

import org.andengine.entity.IEntity;
import org.andengine.opengl.texture.region.ITextureRegion;

import android.util.Log;

public class Level {
	//==========//Variables
	
	//Images
	public ITextureRegion m_background;
	public ITextureRegion m_overlay;
	
	//Times
	public long m_startTime;
	public long m_totalTime;
	public long m_copTime;
	long m_spawnTime;
	public boolean m_alert;
	
	//Civilians
	int m_maxCivs;
	public ArrayList<Civilian> m_civArray = new ArrayList<Civilian>();
	public Civilian m_target;
	
	//Police
	int m_maxCops;
	public ArrayList<Police> m_copArray = new ArrayList<Police>();
	boolean m_alerted;
	
	//Bounds
	int m_top;
	int m_bottom;
	
	//Player
	public int m_ammo;
	
	//Scene
	IEntity m_gScene;
	
	//Random (Seed)
	Random rand = new Random(System.currentTimeMillis());
	
	//Complete
	public boolean m_complete = false;
	
	//==========//Methods
	
	//=====//Make & Load
	//Constructor
	public Level(int level, IEntity gScene)
	{
		//These values are level-independent
		m_gScene = gScene;
		m_startTime = System.currentTimeMillis();
		m_civArray = new ArrayList<Civilian>();
		m_copArray = new ArrayList<Police>();
		m_alerted = false; //Override alert state if you wish
		
		switch (level)
		{
		case 1:
			Level1();
			break;
		case 2:
			Level2();
			break;
		default:
			LevelDef();
			break;
		}
		
		//Populate civilians & cops
		createCivs();
		createCops();
	}
	
	//Default level variables
	private void LevelDef()
	{
		//Default values, can be overridden by level methods
		m_background = ResourceManager.getInstance().g_l_tent_r;
		m_overlay = ResourceManager.getInstance().g_lo_blnk_r;
		
		//Times
		m_totalTime = 30000;
		m_copTime = 10000;
				
		//Civilians
		m_maxCivs = 5;
		m_spawnTime = 1000;
		m_target = randomCiv();
		
		//Police
		m_maxCops = 1;
				
		//Bounds
		m_top = 400;
		m_bottom = 460;
				 
		//Player
		m_ammo = 3;
	}
	
	//Level 1 variables
	private void Level1()
	{
		//Default values, can be overridden by level methods
		m_background = ResourceManager.getInstance().g_l_tent_r;
		m_overlay = ResourceManager.getInstance().g_lo_blnk_r;
		
		//Times
		m_totalTime = 20000;
		m_copTime = 7500;
				
		//Civilians
		m_maxCivs = 15;
		m_spawnTime = 1000;
		m_target = randomCiv();
				
		//Police
		m_maxCops = 0;
				
		//Bounds
		m_top = 400;
		m_bottom = 460;
				 
		//Player
		m_ammo = 5;
	}
	
	//Level 2 variables
	private void Level2()
	{
		//Default values, can be overridden by level methods
		m_background = ResourceManager.getInstance().g_l_city_r;
		m_overlay = ResourceManager.getInstance().g_lo_curtains_r;
		
		//Times
		m_totalTime = 15000;
		m_copTime = 5000;
				
		//Civilians
		m_maxCivs = 30;
		m_spawnTime = 1000;
		m_target = randomCiv();
				
		//Police
		m_maxCops = 5;
				
		//Bounds
		m_top = 400;
		m_bottom = 460;
		
		//Player
		m_ammo = 3;
	}
	
	//====// Civilians
	public void createCivs()
	{		
		//Civilian civ = randomCiv();
		
		//Half the civilians start on the level
		for (int i = 0, max = (m_maxCivs / 2) - 1 ; i < max ; i++) {

			//If we're the same as target, re-roll
			//while (m_target.compare(civ))
			//{
			//	civ = randomCiv();
			//}
			
			m_civArray.add(randomCiv());
		}
	}
	
	public void createCops()
	{
		
	}
	
	public void loadCivs()
	{
		for (int i = 0, max = m_civArray.size(); i < max; i ++) {
			m_civArray.get(i).reset();
			m_gScene.attachChild(m_civArray.get(i).sprite());
		}
		
		m_gScene.attachChild(m_target.sprite());
	}
	
	public void loadCops()
	{
		for (int i = 0, max = m_copArray.size(); i < max; i ++) {
			m_gScene.attachChild(m_copArray.get(i).sprite());
		}
	}
	
	public void unloadCivs()
	{
		for (int i = 0, max = m_civArray.size(); i < max; i ++) {
			m_gScene.detachChild(m_civArray.get(i).sprite());
		}
		
		m_gScene.detachChild(m_target.sprite());
	}
	
	public void unloadCops()
	{
		for (int i = 0, max = m_copArray.size(); i < max; i ++) {
			m_gScene.detachChild(m_copArray.get(i).sprite());
		}
	}
	
	public void start(long time)
	{
		m_startTime = time;
	}
	
	public Civilian randomCiv()
	{
		boolean left = rand.nextBoolean();
		float distX = rand.nextInt(65);
		float distY = rand.nextInt(40);
		float spd = rand.nextInt(2);
		
		int top = rand.nextInt(4);
		int middle = rand.nextInt(4);
		int bottom = rand.nextInt(3);
		
		if (left)
		{
			//Off the left edge is -
			distX *= -1;
		}
		
		else
		{
			//Moving left is -
			spd *= -1;
			//Off the far edge
			distX += 800;
		}
		
		Civilian civ = new Civilian(distX, 420 - distY, top, middle, bottom);
		civ.setVel(spd, 0);
		
		return civ;
	}
	
	public void checkShot(float x, float y)
	{
		for (int i = 0, max = m_civArray.size(); i < max; i ++) {
			 m_civArray.get(i).checkShot(x, y);
		}
		
		m_target.checkShot(x,  y);
	}
	
	public void Update(long time) {
		
		//Update civilians
		for (int i = 0, max = m_civArray.size(); i < max; i ++) {
			
			//Only update if we're active
			if (m_civArray.get(i).m_active)
			{
				m_civArray.get(i).Update(time);
			}
			
			else
			{
				m_gScene.detachChild(m_civArray.get(i).sprite());
			}
			
		}
		
		//Update target
		if (m_target.m_active)
		{
			m_target.Update(time);
		}
		
		else
		{
			m_complete = true;
		}
			
	}
}