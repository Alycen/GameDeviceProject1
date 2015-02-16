package ie.itcarlow.snipersim.scene;

import ie.itcarlow.snipersim.Level;
import ie.itcarlow.snipersim.ResourceManager;

import java.util.ArrayList;

import org.andengine.audio.music.Music;
import org.andengine.engine.camera.ZoomCamera;
import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;

public class GameScene extends BaseScene implements IOnSceneTouchListener{
	
	public final ArrayList<Level> levelList  = new ArrayList<Level>();
	int curLevel = 0;
	
	Sprite spr_level;
	Sprite spr_scope;
	
	IEntity lBG;
	IEntity lSprite;
	IEntity lOverlay;
	IEntity lScope;
	IEntity lHUD;
	
	boolean m_showscope;
	boolean m_ready;
	float m_shotX, m_shotY;
	int m_ammo = 3;
	
	private Music bgm;
	
	@Override
	public void createScene() {
		//Set up layers
		lBG = new Entity();
		lSprite = new Entity();
		lOverlay = new Entity();
		lScope = new Entity();
		
		attachChild(lBG);
		attachChild(lSprite);
		attachChild(lOverlay);
		attachChild(lScope);
		
		//Set up levels
		levelList.add(new Level(ResourceManager.getInstance().g_l_tent_r, 1));
		levelList.add(new Level(ResourceManager.getInstance().g_l_city_r, 6));
		
		//set level
		loadLevel(0);
		
		//set up rifle
		spr_scope = new Sprite(0, 0, ResourceManager.getInstance().g_scope_r, vbom){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY)
			{
	              this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
	              return false;
			}
		};
		
		spr_scope.setVisible(false);
	    this.registerTouchArea(spr_scope);
		lScope.attachChild(spr_scope);
		m_ready = true;
		
		//Audio handling
		bgm = ResourceManager.getInstance().g_game_bgm;
		
		if(activity.getAudio())
		{
			bgm.play();
		}
		
		//Scene touch
		this.setOnSceneTouchListener(this);
		
	}

	@Override
	public void onBackPressed() {
		nextLevel();
		//SceneManager.getInstance().setMenuScene();
	}

	@Override
	public void disposeScene() {
		this.detachChildren();
		bgm.stop();
	}
	
	private void loadLevel(int index)
	{
		//Unload current level entities
		levelList.get(curLevel).unloadCivs(lSprite);
		
		//set the current level to the new level
		curLevel = index;
		
		
		//Load background, set entities for new level
		setLevelBG(levelList.get(index).m_texture);
		levelList.get(index).loadCivs(lSprite);
		//setLevelOverlay(levelList.get(index).m_overlay;
		//m_ammo = levelList.get(index).m_ammo;
	}
	
	private void setLevelBG(ITextureRegion levtex)
	{
		lBG.detachChild(spr_level);
		
		spr_level = new Sprite(0, 0, levtex, vbom);
		
		lBG.attachChild(spr_level);
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
			//m_ready = false;
			//Send shoot message here
			//activity.sendMessage(packMessage(websocket.type.SHOOT, websocket.Address.OTHER); 
			
			//Check if we hit something here
			
		}
		
		else ResourceManager.getInstance().g_empty.play();
	}

	@Override
	public void onUpdate() {
		levelList.get(curLevel).Update();
		
		//Scope and zoom
		if (m_showscope)
		{
			spr_scope.setVisible(true);
			camera.setZoomFactor(1.5f);
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
			m_shotX = -2;
			m_shotY = -2;
		}
		
		else if (m_shotX == -2 && m_shotY == -2)
		{
			
		}
		
		//Some sort of timer tick to reload
		//if (!m_ready && curTime - lastShot > 2s)
		//{
		//	m_ready = true;
		//}
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		
		if(pSceneTouchEvent.isActionDown())
		{
			//If we're not zoomed, zoom in
			if (!m_showscope)
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
				m_shotY = pSceneTouchEvent.getY();
				m_showscope = false;
			}
		}
		
		return true;
	}
}
