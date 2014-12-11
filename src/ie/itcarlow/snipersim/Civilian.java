package ie.itcarlow.snipersim;

import java.util.Random;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import ie.itcarlow.snipersim.scene.SceneManager;

import com.badlogic.gdx.math.Vector2;

public class Civilian {
	private Vector2 m_position, m_markPosition;
	private BitmapTextureAtlas mTexture, mMarkTexture;
	private ITextureRegion mTextureRegion, mMarkTextureRegion;
	private Sprite mSprite, mMarkSprite;
	private float m_width = 23, m_markWidth = 5, m_height = 49, m_markHeight = 8, m_scale = 1; // that is the width and height of the TEMP sprite for Civilian
	private boolean m_marked = false, m_shot = false;
	//(States) Normal, Alert - > ENUMS or ints?? effects the Move() method
	
	public Civilian() {
		m_position = new Vector2(25,25);
		Load(ResourceManager.getInstance().activity, SceneManager.getInstance().getCurrentScene());
		
	}
	
	public Civilian(float x, float y) {
		//m_position.x = x;
		//m_position.y = y;
		mSprite = new Sprite(x, y, ResourceManager.getInstance().g_civ_r, ResourceManager.getInstance().activity.getVertexBufferObjectManager());
	}
	
	public enum State {
		DEFAULT_STATE,
		ALERT_STATE,
	 }
	
	public void Load(BaseGameActivity base, Scene scene) {
		mSprite = new Sprite(m_position.x, m_position.y, ResourceManager.getInstance().g_civ_r, base.getEngine().getVertexBufferObjectManager());
	}
	
	public void Move() {
		//if statements for each state i.e: if (Normal) move this way, else if (Alert) Move this way // or increase / decrease movement speed
		int timer = 30 * 3; // assuming 30 frames per second in eclipse
		int UP = 1, DOWN = 2, LEFT = 3, RIGHT = 4, STOP = 5;
		Random rand = new Random();
		int verticleSpeed = 1, horizontalSpeed = 2; // if in different we can change the speeds here to imply running
		int dir = rand.nextInt((STOP + 1) - UP) + UP;
		
		for ( ; timer >= 0 ; timer -- ) {
			if ( timer == 0 ) {
				timer = 30 * 3;
				dir = rand.nextInt((STOP + 1) - UP) + UP;
			}
			// need a sub timer for the movement to look good, 
			// look up disco zoo as reference to the ideal sprite movement
			if ( dir == UP ) {
				m_position.y -= verticleSpeed;
			}
			else if ( dir == DOWN ) {
				m_position.y += verticleSpeed;
			}
			else if ( dir == LEFT ) {
				m_position.x -= horizontalSpeed;
			}
			else if ( dir == RIGHT ) {
				m_position.x += horizontalSpeed;
			}
			// STOP Should not be active if Alert State is active
			else { // dir == 5 == STOP
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
	
	public float getX() {
		return m_position.x;
	}
	public float getY() {
		return m_position.y;
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
	
	public Sprite getCivSprite() {
		return mSprite;
	}
	
	///////////////////////////////////////
	// SET METHODS
	///////////////////////////////////////
	
	public void setScale(float scale) {
		m_scale = scale;
	}
	
	public void setPosition(float x, float y) {
		m_position.x = x;
		m_position.y = y;
	}
	
	public void setX(float x) {
		m_position.x = x;
	}
	public void setY(float y) {
		m_position.y = y;
	}
	
	public void setMarked(boolean mark) {
		m_marked = mark;
	}
	
	public void setShot(boolean shoot) {
		m_shot = shoot;
	}
	
	//////////////////////////////////////
	// SWITCH STATEMENTS
	//////////////////////////////////////
	
	
	
}

