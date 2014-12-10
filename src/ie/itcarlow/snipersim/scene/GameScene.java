package ie.itcarlow.snipersim.scene;

import ie.itcarlow.snipersim.ResourceManager;

import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;

public class GameScene extends BaseScene{
	
	int curLevel = 0;
	int maxLevel = 1;
	
	Sprite scope;
	
	@Override
	public void createScene() {
		setLevel(curLevel);
		
		scope = new Sprite(0, 0, ResourceManager.getInstance().g_scope_r, vbom);
		
		scope.setVisible(false);
		
		attachChild(scope);
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
			temp = ResourceManager.getInstance().g_dlvl_r;
			break;
		}

		Sprite bg = new Sprite(0, 0, temp, vbom)
		{
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLoxalY)
			{
				if(pSceneTouchEvent.isActionDown())
				{
					scope.setVisible(true);
				}
				
				if(pSceneTouchEvent.isActionUp())
				{
					scope.setVisible(false);
				}
				
				return true;
			}
		};
		
		//Actually set background
		setBackground(new SpriteBackground(0, 0, 0, bg));
	}
	
	private void nextLevel() {
		if (curLevel + 1 <= maxLevel)
		{
			setLevel(curLevel + 1);
		}
		
		else setLevel(0);
	}
}
