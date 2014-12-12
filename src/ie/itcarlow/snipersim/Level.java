package ie.itcarlow.snipersim;


import ie.itcarlow.snipersim.scene.BaseScene;

import java.util.ArrayList;
import java.util.Random;

import org.andengine.entity.IEntity;
import org.andengine.opengl.texture.region.ITextureRegion;

public class Level {
	//==========//Variables
	public Civilian civ;
	public final ArrayList<Civilian> civArray = new ArrayList<Civilian>();
	
	public final ITextureRegion m_texture;
	Random rand = new Random();
	
	//==========//Methods
	
	//=====//Make & Load
	
	//Constructor
	public Level(ITextureRegion texture, int civNum) {
		m_texture = texture;
		
		for (int i = 0; i < civNum; i ++) {
			civArray.add(new Civilian(rand.nextInt((250 + 30)+ 30), rand.nextInt((200 + 30)+ 30) ));
		}
	}
	
	public void loadCivs(IEntity ent)
	{
		for (int i = 0, max = civArray.size(); i < max; i ++) {
			ent.attachChild(civArray.get(i).getCivSprite());
		}
	}
	
	public void unloadCivs(IEntity ent)
	{
		for (int i = 0, max = civArray.size(); i < max; i ++) {
			ent.detachChild(civArray.get(i).getCivSprite());
		}
	}
	
	//=====//Movement
	
	//Movement
	//SetPoint
	//Pan(Vec2 dir)
	
	
	//=====//Update and Draw
	
	//Update
	public void Update() {
		for (int i = 0, max = civArray.size(); i < max; i ++) {
			civArray.get(i).Update();
		}
	}
		//Actual panning
	
	
	
}
