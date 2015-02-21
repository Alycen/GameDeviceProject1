package ie.itcarlow.snipersim.scene;

import ie.itcarlow.snipersim.Level;
import ie.itcarlow.snipersim.ResourceManager;

import java.util.ArrayList;
import java.util.Date;

import org.andengine.audio.music.Music;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.Log;

public class GameScene extends BaseScene implements IOnSceneTouchListener{
	
	//Levels
	public final ArrayList<Level> levelList  = new ArrayList<Level>();
	int curLevel = 0;
	int maxLevel = 1;
	
	//Sprites & layers
	IEntity lBG;
	Sprite spr_level;
	IEntity lSprite;
	IEntity lOL;
	Sprite spr_overlay;
	IEntity lScope;
	Sprite spr_scope;
	
	//HUD
	HUD hud;
	Sprite spr_reload, spr_ammo, spr_timer, spr_targ, spr_mask;
	float m_maxMaskHeight = 64;
	float m_maxTimerWidth = 694;
	float m_maxReloadHeight = 320;
	
	//Times
	long m_curTime, m_reloadTime;
	
	//Gameflow
	boolean m_pause;
	boolean m_complete;
	boolean m_success;
	
	//Rifle
	boolean m_showscope, m_ready = false;
	float m_shotX, m_shotY;
	int m_ammo;
	long m_lastShot;
	long m_maxReloadTime = 10;//2000;
	float aimAdjust = -40;
	float zoom = 1.5f;
	
	//Sounds
	private Music bgm;
	
	@Override
	public void createScene() {
		//Set up layers
		lBG = new Entity();
		lSprite = new Entity();
		lOL = new Entity();
		lScope = new Entity();
		
		attachChild(lBG);
		attachChild(lSprite);
		attachChild(lOL);
		attachChild(lScope);
		
		//Set up levels
		levelList.add(new Level(1, lSprite));
		levelList.add(new Level(2, lSprite));
		
		//set up rifle
		spr_scope = new Sprite(0, 0, ResourceManager.getInstance().g_scope_r, vbom){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY)
			{
				if (m_showscope)
				{
					this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, (pSceneTouchEvent.getY() - this.getHeight() / 2) + aimAdjust);
				}
	            return false;
			}
		};
		m_shotX = -1;
		m_shotY = -1;
		spr_scope.setVisible(false);
	    this.registerTouchArea(spr_scope);
		lScope.attachChild(spr_scope);
		m_ready = true;
		
		//Set up HUD
		hud = new HUD();
		
		spr_reload = new Sprite(710, 74, ResourceManager.getInstance().g_h_reload_r, vbom);
		spr_reload.setHeight(m_maxReloadHeight);
		spr_reload.setVisible(false);
		
		spr_ammo = new Sprite(710, 16, ResourceManager.getInstance().g_h_ammo_t, vbom);
		spr_timer = new Sprite(16, 16, ResourceManager.getInstance().g_h_timer_r, vbom);
		spr_targ = new Sprite(16, 74, genCiv(levelList.get(curLevel).m_target.m_top, levelList.get(curLevel).m_target.m_middle, levelList.get(curLevel).m_target.m_bottom), vbom);
		spr_mask = new Sprite(16, 74, ResourceManager.getInstance().g_h_targetmask_r, vbom);
		
		hud.attachChild(spr_reload);
		hud.attachChild(spr_ammo);
		hud.attachChild(spr_timer);
		hud.attachChild(spr_targ);
		hud.attachChild(spr_mask);
		
		updateAmmo();
		camera.setHUD(hud);
		
		//Audio handling
		bgm = ResourceManager.getInstance().g_game_bgm;
		
		if(activity.getAudio())
		{
			bgm.play();
		}
		
		//Scene touch listener
		this.setOnSceneTouchListener(this);	

		//Load level
		loadLevel(curLevel);
		
	}

	@Override
	public void onBackPressed() {
		m_ammo += 1;
		updateAmmo();
		//nextLevel();
		//SceneManager.getInstance().setMenuScene();
	}

	@Override
	public void disposeScene() {
		this.detachChildren();
		hud.detachChildren();
		hud = null;
		bgm.stop();
	}
	
	public TiledTextureRegion genCiv(int top, int middle, int bottom)
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
		
		return tex;
	}
	
	private void loadLevel(int index)
	{
		//Unload current level entities
		levelList.get(curLevel).unloadCivs();
		
		//set the current level to the new level
		curLevel = index;
		
		//Load background, set entities for new level
		setLevelBG(levelList.get(curLevel).m_background);
		setLevelOL(levelList.get(curLevel).m_overlay);
		
		levelList.get(curLevel).loadCivs();
		
		m_ammo = levelList.get(curLevel).m_ammo;
		updateAmmo();
		levelList.get(curLevel).start(System.currentTimeMillis());
	}
	
	private void setLevelBG(ITextureRegion levtex)
	{
		lBG.detachChild(spr_level);
		
		spr_level = new Sprite(0, 0, levtex, vbom);
		
		lBG.attachChild(spr_level);
	}
	

	private void setLevelOL(ITextureRegion overtex)
	{
		lOL.detachChild(spr_overlay);
		
		spr_overlay = new Sprite(0, 0, overtex, vbom);
		
		lOL.attachChild(spr_overlay);
	}
	
	private void nextLevel() {
		int maxLevel = levelList.size();
		
		if (curLevel + 1 < maxLevel)
		{
			loadLevel(curLevel + 1);
		}
		
		else loadLevel(0);
	}
	
	
	private void shoot(float x, float y)
	{
		if (m_ammo > 0)
		{
			ResourceManager.getInstance().g_shot.play();
			m_ammo -= 1;
			m_ready = false;
			m_lastShot = System.currentTimeMillis();
			
			//Update ammo count
			updateAmmo();
			
			//Send shoot message here
			//activity.sendMessage(packMessage(websocket.type.SHOOT, websocket.Address.OTHER, { m_shotX, m_shotY }); 
			
			//Check if we hit something here
			
			levelList.get(curLevel).checkShot(x, y);
			
		}
		
		else ResourceManager.getInstance().g_empty.play();
	}
	
	private void reload()
	{
		//Show reload bar
		spr_reload.setVisible(true);
		
		//Get time taken in the current reload
		m_reloadTime = m_curTime - m_lastShot;
		
		//Update the reload bar
		float fraction = (float)m_reloadTime / (float)m_maxReloadTime; 
		spr_reload.setHeight(m_maxReloadHeight - (m_maxReloadHeight * fraction));
		
		//If we're over the time to reload, we're done and ready up
		if (m_reloadTime > m_maxReloadTime)
		{
			m_reloadTime = 0;
			m_ready = true;
			spr_reload.setVisible(false);
		}
	}

	private void updateAmmo()
	{
		hud.detachChild(spr_ammo);
		
		if (m_ammo > 0)
		{
			//Ternary to make sure we don't go over the maximum tile index
			ResourceManager.getInstance().g_h_ammo_t.setCurrentTileIndex(m_ammo < 8 ? m_ammo : 7);
		}
		
		//Otherwise if negative or zero
		else ResourceManager.getInstance().g_h_ammo_t.setCurrentTileIndex(0);

		spr_ammo = new Sprite(710, 16, ResourceManager.getInstance().g_h_ammo_t, vbom);
		
		hud.attachChild(spr_ammo);
	}
	
	private boolean updateTimer()
	{		
		float fraction = 0;
		
		if (!levelList.get(curLevel).m_alert)
		{
			long timeTaken = m_curTime - levelList.get(curLevel).m_startTime;
			fraction = (float)timeTaken / (float)levelList.get(curLevel).m_totalTime; 
		}
		
		else 
		{
			long timeTaken = m_curTime - levelList.get(curLevel).m_alertTime;
			fraction = (float)timeTaken / (float)levelList.get(curLevel).m_copTime;
		}
		
		//Update target mask
		spr_mask.setPosition(spr_mask.getX(), 58 + ((m_maxMaskHeight * fraction)));
		spr_mask.setHeight(m_maxMaskHeight - (m_maxMaskHeight * fraction));
		
		spr_timer.setWidth(m_maxTimerWidth - (m_maxTimerWidth * fraction));
		
		return (m_maxTimerWidth - (m_maxTimerWidth * fraction) <= 0);
	}
	
	@Override
	public void onUpdate() {
		
		if (m_complete)
		{
			//Win
			if (m_success)
			{
				//Next level (if there is one)
				if (curLevel < maxLevel)
					nextLevel();
				
				//Else go to main menu
				else 
				{
					SceneManager.getInstance().setMenuScene();
				}
			}
			
			//Loss
			else
			{
				System.exit(0);
			}
		}
		
		if (m_pause)
		{
			//pause
		}
		
		else
		{
			//Get time
			m_curTime = System.currentTimeMillis();
			
			//Update level
			levelList.get(curLevel).Update(m_curTime);
			m_success = levelList.get(curLevel).m_targDead;

			//Returns true if the timer is below 0
			m_complete = updateTimer();
		
			//Scope and zoom
			if (m_showscope)
			{
				spr_scope.setVisible(true);
				camera.setZoomFactor(zoom);
				camera.setCenter((camera.getWidth() / 2) * zoom, camera.getHeight());
			}
			
			//Unscope and unzoom
			if (!m_showscope)
			{
				camera.setCenter(camera.getWidth() / 2, camera.getHeight() / 2);
				camera.setZoomFactor(1f);
				spr_scope.setVisible(false);
			}
		
			//If valid coordinates
			if (m_ready && m_shotX >= 0 && m_shotY >= 0)
			{
				//Attempt to shoot
				shoot(m_shotX, m_shotY);
				
				//Reset the shot
				m_shotX = -1;
				m_shotY = -1;
			}
		
			//Reload timer
			if (!m_ready)
			{
				reload();
			}
		}
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		
		if(pSceneTouchEvent.isActionDown())
		{
			//If we're ready and not zoomed, zoom in
			if (m_ready && !m_showscope)
			{
				m_showscope = true;
			}
			
		}
		
		//Release scope
		if(pSceneTouchEvent.isActionUp())
		{
			//Unscope check
			if (m_showscope)
			{
				m_shotX = pSceneTouchEvent.getX();
				m_shotY = pSceneTouchEvent.getY() + aimAdjust;
				m_showscope = false;
			}
		}
		
		return true;
	}
}
