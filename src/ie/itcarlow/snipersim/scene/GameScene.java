package ie.itcarlow.snipersim.scene;

import ie.itcarlow.snipersim.Civilian;
import ie.itcarlow.snipersim.Level;
import ie.itcarlow.snipersim.ResourceManager;

import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;

public class GameScene extends BaseScene{
	
	Level level_1;
	
	int curLevel = 0;
	int maxLevel = 1;
	
	Sprite spr_level;
	Sprite spr_scope;
	
	@Override
	public void createScene() {
		setLevel(curLevel);

		level_1 = new Level();
		
		spr_scope = new Sprite(0, 0, ResourceManager.getInstance().g_scope_r, vbom);
		
		spr_scope.setVisible(false);
		
		attachChild(level_1.civ.getCivSprite());
		for (int i = 0; i < 5; i ++) {
			//level_1.civArray.get(i).setPosition(10.0f * (float)i, 20.0f * (float)i);
			attachChild(level_1.civArray.get(i).getCivSprite());
		}
		attachChild(spr_scope);
	}

	@Override
	public void onBackPressed() {
		nextLevel();
		//SceneManager.getInstance().setMenuScene();
	}

	@Override
	public void disposeScene() {
		this.detachChildren();
	}
	
	private void setLevel(int level) {
		
		curLevel = level;
		ITextureRegion temp;
		//Set background sprite to level
		switch (level)
		{
		case 0:
			temp = ResourceManager.getInstance().g_l_city_r;			
			break;
		case 1:
			temp = ResourceManager.getInstance().g_l_tent_r;
			break;
		default:
			temp = ResourceManager.getInstance().g_l_def_r;
			break;
		}

		setLevelBG(temp);
		
		//Actually set background
		setBackground(new SpriteBackground(spr_level));
	}
	
	public void setLevel(Level lev)
	{
		setLevelBG(lev.m_texture);
	}
	
	private void setLevelBG(ITextureRegion levtex)
	{
		spr_level = new Sprite(0, 0, levtex, vbom)
		{
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLoxalY)
			{
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
		};
	}
	
	private void nextLevel() {
		if (curLevel + 1 <= maxLevel)
		{
			setLevel(curLevel + 1);
		}
		
		else setLevel(0);
	}
}
