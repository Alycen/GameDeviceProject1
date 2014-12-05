package ie.itcarlow.snipersim;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.badlogic.gdx.math.Vector2;

public class Civilian {
	private Vector2 m_position;
	private BitmapTextureAtlas mTexture;
	private ITextureRegion mTextureRegion;
	private Sprite mSprite, markSprite;
	private float m_width = 23, m_height = 49, m_scale = 1; // that is the width and height of the TEMP sprite for Civilian
	private boolean m_marked = false, m_shot = false;
	//(States) Normal, Alert - > ENUMS??? that effect the Move() method??
	
	//should use this for mark sprite too ??
	public void Load(BitmapTextureAtlas texAtlas , ITextureRegion texRegion ) { // Civilians will not be touch regions just drawn on sprites -> need to look more into sprites
		mTexture = texAtlas;
		mTextureRegion = texRegion;
		mTexture.load();
	}
	
	public void Move() {
		int timer = 0;
		int target = 30 * 2; // assuming 30 frames per second in eclipse -> might use a rand num generator for the seconds
		// do random number thing to decide direction
		for ( ; timer <= target; timer ++ ) {
			m_position.x += 2; // need to take scale into consideration too - > only going right for test purposes
		}
		if (timer == target) {
			timer = 0;
		}
	}
	
	public void Update() {
		Move();
	}
	
	///////////////////////////////////////
	// GET METHODS
	///////////////////////////////////////
	
	public float getScale() {
		return m_scale;
	}
	
	public Vector2 getPosition() {
		return m_position;
	}
	
	public boolean Marked() { // in level class ??
		// if(screen taped && crosshair in sprite area)
		//		shot = true; 
		// else
		//		shot = false;
		return m_marked;
	}
	
	public boolean Shot() { // in level class ?? 
		// if(screentaped && crosshair in sprite area)
		//		shot = true;
		// else
		//		shot = false;
		
		return m_shot;
	}
	
	///////////////////////////////////////
	// SET METHODS
	///////////////////////////////////////
	
	public void setScale(float scale) {
		m_scale = scale;
	}
	
	public void setPosition(Vector2 position) {
		m_position = position;
	}
	
	public void setMarked(boolean mark) {
		m_marked = mark;
	}
	
	public void setShot(boolean shoot) {
		m_shot = shoot;
	}
}

