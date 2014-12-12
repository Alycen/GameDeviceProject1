package ie.itcarlow.snipersim;


import java.util.ArrayList;
import java.util.Random;

import org.andengine.opengl.texture.region.ITextureRegion;

public class Level {
	//==========//Variables
	public Civilian civ;
	public ArrayList<Civilian> civArray = new ArrayList<Civilian>();
	
	public final ITextureRegion m_texture;
	Random rand = new Random();
	
	//==========//Methods
	
	//=====//Make & Load
	
	//Constructor
	public Level() {
		m_texture = ResourceManager.getInstance().g_l_tent_r;
		civ = new Civilian();
		for (int i = 0; i < 5; i ++) {
			civArray.add(new Civilian(rand.nextInt((250 + 30)+ 30), rand.nextInt((200 + 30)+ 30) ));
		}
	}
	public Level(ITextureRegion texture)
	{
		m_texture = texture;
	}
	
	//=====//Movement
	
	//Movement
	//SetPoint
	//Pan(Vec2 dir)
	
	
	//=====//Update and Draw
	
	//Update
	public void Update() {
		for (int i = 0; i < 5; i ++) {
			civArray.get(i).Update();
		}
	}
		//Actual panning
	
	
	
}
