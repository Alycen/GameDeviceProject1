package ie.itcarlow.snipersim;

import java.util.Random;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import com.badlogic.gdx.math.Vector2;

public class Civilian {
	//Position & movement
	protected Vector2 m_position;
	private Vector2 m_velocity;
	private Vector2 m_maxVel = new Vector2(4, 2);
	
	//Drawing
	protected Sprite m_sprite;
	
	//States
	public boolean m_active, m_alive, m_panic;
	
	//Dress
	public int m_top, m_middle, m_bottom;
	
	//Times
	private long m_spawnTime;
	private long m_tod;
	private long m_moveTimer;
	private long m_lastMove;
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
		m_panic = false;
		m_moveTimer = rand.nextInt(4000);
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
	
	public final boolean compare(Civilian civ)
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
	
	public final void panic()
	{
		
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
	
	private void move(long time)
	{
		float w = 800, h = 480;		
		
		float x = m_sprite.getX();
		float y = m_sprite.getY();	
		
		//Random rolls
		if (!m_panic)
		{
			if (time - m_lastMove > m_moveTimer / 2)
			{
				//40% chance
				if (rand.nextInt(10) > 6)
				{
					int i = 1;
					if (rand.nextBoolean())
						i = -1;
					
					m_velocity.x = (rand.nextInt(2) + 1) * i;
					m_lastMove = time;
					m_moveTimer = rand.nextInt(3000) + 2000;
				}
			}
			
		}
		
		//Panicked running
		else
		{
			if (time - m_lastMove > m_moveTimer)
			{
				//60% chance
				if (rand.nextInt(10) > 4)
				{
					int i = 1;
					if (rand.nextBoolean())
						i = -1;
					
					m_velocity.x = (rand.nextInt(4) + 2) * i;
					m_lastMove = time;
					m_moveTimer = rand.nextInt(4000);
				}
			}
		}
		
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
			move(time);
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

