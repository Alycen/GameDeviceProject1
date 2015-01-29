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
	IEntity lScope;
	
	boolean m_scope;
	boolean m_shoot;
	boolean m_taken;
	
	private Music bgm;
	
	@Override
	public void createScene() {
		//Set up layers
		lBG = new Entity();
		lSprite = new Entity();
		lScope = new Entity();
		
		attachChild(lBG);
		attachChild(lSprite);
		attachChild(lScope);
		
		//Set up levels
		levelList.add(new Level(ResourceManager.getInstance().g_l_tent_r, 1));
		levelList.add(new Level(ResourceManager.getInstance().g_l_city_r, 6));
		
		//set level
		loadLevel(0);
		
		//set up scope
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

		//Audio handling
		bgm = ResourceManager.getInstance().g_game_bgm;
		
		if(activity.getAudio())
		{
			bgm.play();
		}
		
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

	@Override
	public void onUpdate() {
		levelList.get(curLevel).Update();
		
		if (m_scope)
		{
			spr_scope.setVisible(true);
			camera.setZoomFactor(1.5f);
		}
		
		if (!m_scope)
		{
			spr_scope.setVisible(false);
			camera.setCenter(camera.getWidth() / 2, camera.getHeight() / 2);
			camera.setZoomFactor(1f);
		}
		
		
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		
		if(pSceneTouchEvent.isActionDown())
		{
			//If we're not zoomed, zoom in
			if (!m_scope)
			{
				m_scope = true;
			}
			
			//Otherwise shoot (Second touch)
			else
			{
				m_shoot = true;
			}
		}
		
		//Release scope
		if(pSceneTouchEvent.isActionUp())
		{
			//Make sure we don't unscope when shooting
			if (m_shoot)
			{
				m_shoot = false;
			}
			
			//Actual unscope check
			else if (m_scope)
			{
				m_scope = false;
			}
			
		
		}
		
		return true;
	}
}
