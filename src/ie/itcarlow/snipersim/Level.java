package ie.itcarlow.snipersim;


import java.util.ArrayList;

import org.andengine.opengl.texture.region.ITextureRegion;

public class Level {
	//==========//Variables
	public Civilian civ;
	public ArrayList<Civilian> civArray = new ArrayList<Civilian>();
	
	public final ITextureRegion m_texture;
	
	//==========//Methods
	
	//=====//Make & Load
	
	//Constructor
	public Level() {
		m_texture = ResourceManager.getInstance().g_l_tent_r;
		civ = new Civilian();
		for (int i = 0; i < 5; i ++) {
			civArray.add(new Civilian(30.0f * (float)i, 40.0f ));
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
		//Actual panning
	
	
	
}
