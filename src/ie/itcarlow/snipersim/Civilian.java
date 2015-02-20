package ie.itcarlow.snipersim;

import java.util.Random;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import ie.itcarlow.snipersim.scene.SceneManager;

import com.badlogic.gdx.math.Vector2;

public class Civilian {
	
	//Position & movement
	private Vector2 m_position;
	private Vector2 m_velocity;
	private Vector2 m_maxVel = new Vector2(4, 2);
	private int dir;////
	
	//Drawing
	private Sprite m_sprite;
	
	//States
	public boolean m_active, m_alive;
	
	//Direction
	private int UP = 1,DOWN = 2, LEFT = 3, RIGHT = 4, STOP =5;

	//Dress
	public int m_top, m_middle, m_bottom;
	
	private int timer = 100;

	//Times
	private long m_spawnTime;
	private long m_tod;
	private long m_moveTimer = 5000;
	private long m_despawnTime = 5000;
	
	Random rand = new Random(System.currentTimeMillis());
	
	public Civilian(float x, float y, int top, int middle, int bottom)
	{
		m_position = new Vector2(x, y);
		m_velocity = new Vector2(0, 0);
		m_top = top;
		m_middle = middle;
		m_bottom = bottom;
		reset();
	}
	
	public void reset()
	{
		genSprite(m_top, m_middle, m_bottom);
		m_spawnTime = System.currentTimeMillis();
		m_active = true;
		m_alive = true;
	}
	
	public void genSprite(int top, int middle, int bottom)
	{
		TiledTextureRegion tex;
		
		switch(top)
		{
		case 0:
			tex = ResourceManager.getInstance().g_civ_a_t;
			break;
		case 1:
			tex = ResourceManager.getInstance().g_civ_b_t;
			break;
		case 2:
			tex = ResourceManager.getInstance().g_civ_c_t;
			break;
		case 3:
			tex = ResourceManager.getInstance().g_civ_d_t;
			break;
		default:
			tex = ResourceManager.getInstance().g_civ_a_t;
			break;
		}
		
		tex.setCurrentTileIndex((int)((bottom * 4) + middle));
		
		m_sprite = new Sprite(m_position.x, m_position.y, tex, ResourceManager.getInstance().vbom);
	}
	
	public boolean compare(Civilian civ)
	{
		//Returns true if the civilians look the same
		return (civ.m_top == m_top && civ.m_middle == m_middle && civ.m_bottom == m_bottom);
	}
	
	public void checkShot(float x, float y)
	{
		if (m_sprite.contains(x, y))
		{
			Shoot();
		}
	}
	
	public void Shoot()
	{
		m_alive = false;
		m_tod = System.currentTimeMillis();
		
		//Fall over
		if (rand.nextBoolean())
			m_sprite.setRotation(90);
		else m_sprite.setRotation(-90);
		
		m_sprite.setColor(0.6f, 0, 0);
	}
	
	public void Move() {
		//if statements for each state i.e: if (Normal) move this way, else if (Alert) Move this way // or increase / decrease movement speed
				
		int verticalSpeed = 1, horizontalSpeed = 2; // if in different we can change the speeds here to imply running
		float width = ResourceManager.getInstance().activity.CAMERA_WIDTH, height = ResourceManager.getInstance().activity.CAMERA_HEIGHT;
		if ( timer == 0 ) {
			timer = 100;
			dir = rand.nextInt((STOP + 1) - UP) + UP;
		}
		// need a sub timer for the movement to look good, 
		// look up disco zoo as reference to the ideal sprite movement
		if ( dir == UP ) {
			m_sprite.setY(m_sprite.getY() - verticalSpeed);
		}
		else if ( dir == DOWN ) {
			m_sprite.setY(m_sprite.getY() + verticalSpeed);
		}
		else if ( dir == LEFT ) {
			m_sprite.setX(m_sprite.getX() - horizontalSpeed);
		}
		else if ( dir == RIGHT ) {
			m_sprite.setX(m_sprite.getX() + horizontalSpeed);
		}
		// STOP Should not be active if Alert State is active
		else { // dir == 5 == STOP
			// do nothing
		}
		
		if (m_sprite.getX() < 0 && dir == LEFT) {
			dir = RIGHT;
		}
		if (m_sprite.getX() > (int)width && dir == RIGHT) {
			dir = LEFT;
		}
		if (m_sprite.getY() < 0 && dir == UP) {
			dir = DOWN;
		}
		if (m_sprite.getY() > (int)height && dir == DOWN) {
			dir = UP;
		}
		timer --;
		
	}
	
	private void newMove()
	{
		float w = 800, h = 480;		
		
		float x = m_sprite.getX();
		float y = m_sprite.getY();	
		
		//Off left edge
		if (x + 24 < 0)
			m_velocity.x = rand.nextInt(4);
		
		//Right edge
		if (x > w)
			m_velocity.x = rand.nextInt(4) * -1;	
		
		
		m_sprite.setX(m_sprite.getX() + m_velocity.x);
	}
	
	
	public void Update(long time) {
		
		if (m_alive)
		{
			newMove();
		}
		
		//Otherwise we're dead
		else 
		{
			//Time since death
			if (time - m_tod > m_despawnTime)
			{
				//mark as inactive
				m_active = false;
			}
		}
	}
	
	///////////////////////////////////////
	// GET METHODS
	///////////////////////////////////////
	
	public Sprite sprite()
	{
		return m_sprite;
	}
	
	///////////////////////////////////////
	// SET METHODS
	///////////////////////////////////////
	
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
	
	public void setVel(float x, float y)
	{
		m_velocity.x = x;
		m_velocity.y = y;
	}
}

