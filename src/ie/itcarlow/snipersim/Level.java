package ie.itcarlow.snipersim;

import ie.itcarlow.snipersim.scene.BaseScene;

import java.util.ArrayList;
import java.util.Random;

import org.andengine.entity.IEntity;
import org.andengine.opengl.texture.region.ITextureRegion;

public class Level {
	//==========//Variables
	
	//Images
	public ITextureRegion m_background;
	public ITextureRegion m_overlay;
	
	//Times
	long m_startTime;
	long m_curTime;
	long m_totalTime;
	long m_copTime;
	
	//Civilians
	int m_maxCivs;
	public ArrayList<Civilian> m_civArray = new ArrayList<Civilian>();
	Civilian m_target;
	
	//Police
	int m_maxCops;
	public ArrayList<Police> m_copArray = new ArrayList<Police>();
	boolean m_alerted;
	
	//Bounds
	int m_top;
	
	//Player
	public int m_ammo;
	
	//Scene
	IEntity m_gScene;
	
	//Random (Seed)
	Random rand = new Random(System.currentTimeMillis());
	
	//==========//Methods
	
	//=====//Make & Load
	
	//Constructor
	public Level(ITextureRegion texture, int civNum) {
		m_background = texture;
		

	}
	
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
			return;
		case 2:
			Level2();
			return;
		default:
			LevelDef();
		}
		
		//Spawn civilians
		for (int i = 0; i < m_maxCivs; i ++) {
			m_civArray.add(new Civilian(rand.nextInt((250 + 30)+ 30), rand.nextInt((200 + 30)+ 30) ));
		}
		
		//Spawn cops
		
		//Have methods to attempt each above and check if the numbers are within max
	}
	
	//Default level variables
	private void LevelDef()
	{
		//Default values, can be overridden by level methods
		m_background = ResourceManager.getInstance().g_l_def_r;
		m_overlay = ResourceManager.getInstance().g_lo_def_r;
		
		//Times
		m_totalTime = 30000;
		m_copTime = 10000;
				
		//Civilians
		m_maxCivs = 5;
		m_target = new Civilian(); //spawnCivilian(); //Write random civ constructor that can check it's not target
				
		//Police
		m_maxCops = 1;
				
		//Bounds
		m_top = 40;
				 
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
		m_target = new Civilian(); //spawnCivilian(); //Write random civ constructor that can check it's not target
				
		//Police
		m_maxCops = 0;
				
		//Bounds
		m_top = 60;
				 
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
		m_target = new Civilian(); //spawnCivilian(); //Write random civ constructor that can check it's not target
				
		//Police
		m_maxCops = 5;
				
		//Bounds
		m_top = 40;
				 
		//Player
		m_ammo = 3;
	}
	
	//====// Civilians
	public void loadCivs()
	{
		for (int i = 0, max = m_civArray.size(); i < max; i ++) {
			m_gScene.attachChild(m_civArray.get(i).getCivSprite());
		}
	}
	
	public void unloadCivs()
	{
		for (int i = 0, max = m_civArray.size(); i < max; i ++) {
			m_gScene.detachChild(m_civArray.get(i).getCivSprite());
		}
	}
	
	public void randomCiv()
	{
		//roll for random numbers
		//if they're the same as target, re-roll
	}
	
	//=====//Movement
	
	//=====//Update and Draw
	
	//Update
	public void Update() {
		
		//update time
		m_curTime = System.currentTimeMillis();
		
		for (int i = 0, max = m_civArray.size(); i < max; i ++) {
			m_civArray.get(i).Update();
		}
	}
	
	
	
}
