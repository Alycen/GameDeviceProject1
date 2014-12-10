package ie.itcarlow.snipersim.scene;

import ie.itcarlow.snipersim.ResourceManager;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.util.GLState;
import org.andengine.util.color.Color;

public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener{

	private MenuScene menu;
	
	private final int MENU_PLAY = 0;
	private final int MENU_EXIT = 1;
		
	private Sprite play;
	private Sprite exit;
	
	@Override
	public void createScene() {
		setBackground(new Background(Color.CYAN));
		
		//Play button
		play = new Sprite(400 - 128, 240 - 32, ResourceManager.getInstance().play_button_region, vbom)
		{
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLoxalY)
			{
								
				if(pSceneTouchEvent.isActionUp())
				{
					SceneManager.getInstance().setGameScene();
				}
				
				return true;
			}
		};
	
		//Exit button
		exit = new Sprite(400 - 128, 240 + 64, ResourceManager.getInstance().exit_button_region, vbom)
		{
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLoxalY)
			{			
				if(pSceneTouchEvent.isActionUp())
				{
					System.exit(0);
				}
				
				return true;
			}
		};
		
		//Attach to the scene
		attachChild(play);
		attachChild(exit);
		
		//Register as touchable
		registerTouchArea(play);
		registerTouchArea(exit);
		
		//createMenu();
	}

	@Override
	public void onBackPressed() {
		//System.exit(0);
		SceneManager.getInstance().setGameScene();
	}

	@Override
	public void disposeScene(){
		//Detach all children
		play.detachSelf();
		
		ResourceManager.getInstance().unloadMenuResources();
	}
	
	
	@Override
	public boolean onMenuItemClicked(MenuScene scene, IMenuItem item, float localX, float localY) {
		switch(item.getID())
		{
		case MENU_PLAY:
			return true;
		case MENU_EXIT:
			System.exit(0);
			return true;
		}
		
		return false;
	}
	
}
