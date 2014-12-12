package ie.itcarlow.snipersim.scene;

import ie.itcarlow.snipersim.Level;
import ie.itcarlow.snipersim.ResourceManager;

import java.util.ArrayList;

import org.andengine.audio.music.Music;
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
	
	IEntity lSprite;
	IEntity lScope;
	
	private Music bgm;
	
	@Override
	public void createScene() {
		//Set up layers
		lSprite = new Entity();
		lScope = new Entity();
		
		attachChild(lSprite);
		attachChild(lScope);
		
		
		//Set up levels
		levelList.add(new Level(ResourceManager.getInstance().g_l_tent_r, 1));
		levelList.add(new Level(ResourceManager.getInstance().g_l_city_r, 6));
		
		//set level
		loadLevel(0);
		
		//set up scope
		spr_scope = new Sprite(0, 0, ResourceManager.getInstance().g_scope_r, vbom);
		spr_scope.setVisible(false);
		
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
		spr_level = new Sprite(0, 0, levtex, vbom);

		SpriteBackground bg = new SpriteBackground(spr_level);
		
		setBackground(bg);
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
		// TODO Auto-generated method stub
		levelList.get(curLevel).Update();
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		
		if(pSceneTouchEvent.isActionDown())
		{
			spr_scope.setVisible(true);
		}
		
		if(pSceneTouchEvent.isActionUp())
		{
			spr_scope.setVisible(false);
		}
		
		return true;
	}
}
