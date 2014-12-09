package ie.itcarlow.snipersim;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.badlogic.gdx.math.Vector2;
import java.util.Random;

public class Civilian {
	private Vector2 m_position;
	private BitmapTextureAtlas mTexture, mMarkTexture;
	private ITextureRegion mTextureRegion, mMarkTextureRegion;
	private Sprite mSprite, markSprite;
	private float m_width = 23, m_height = 49, m_scale = 1; // that is the width and height of the TEMP sprite for Civilian
	private boolean m_marked = false, m_shot = false;
	//(States) Normal, Alert - > ENUMS??? that effect the Move() method??
	
	//should use this for mark sprite too ??
	public void Load(BitmapTextureAtlas texAtlasCiv, ITextureRegion texRegionCiv , BitmapTextureAtlas texAtlasMark, ITextureRegion texRegionMark) { // Civilians will not be touch regions just drawn on sprites -> need to look more into sprites
		mTexture = texAtlasCiv;
		mMarkTexture = texAtlasMark;
		mTextureRegion = texRegionCiv;
		mMarkTextureRegion = texRegionMark;
		mTexture.load();
		mMarkTexture.load();
	}
	
	public void Move() {
		//if statements for each state i.e: if (Normal) move this way, else if (Alert) Move this way
		int timer = 30 * 3; // assuming 30 frames per second in eclipse
		int UP = 0, DOWN = 1, LEFT = 3, RIGHT = 4, STOP = 5;
		Random rand = new Random();
		
		int dir = rand.nextInt((RIGHT + 1) - UP) + UP;
		
		for ( ; timer >= 0 ; timer -- ) {
			if ( timer == 0 ) {
				timer = 30 * 3;
				dir = rand.nextInt((RIGHT + 1) - UP) + UP;
			}
			
			if ( dir == UP ) {
				m_position.y -= 1;
			}
			else if ( dir == DOWN ) {
				m_position.y += 1;
			}
			else if ( dir == LEFT ) {
				m_position.x -= 2;
			}
			else if ( dir == RIGHT ) {
				m_position.x += 2;
			}
			else { // dir == 0 == STOP
				// do nothing
			}
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

