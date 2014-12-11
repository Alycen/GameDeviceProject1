package ie.itcarlow.snipersim.scene;

import ie.itcarlow.snipersim.ResourceManager;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.util.GLState;
import org.andengine.util.color.Color;

public class MainMenuScene extends BaseScene{
		
	private Sprite play;
	private Sprite exit;
	private Sprite audio;
	private Sprite bg;
	
	@Override
	public void createScene() {
		setBackground(new Background(Color.CYAN));
		
		bg = new Sprite(0, 0, ResourceManager.getInstance().m_bg_r, vbom);
		setBackground(new SpriteBackground(0, 0, 0, bg));
		
		//Play button
		play = new Sprite(400 - 128, 240 - 32, ResourceManager.getInstance().m_play_r, vbom)
		{
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLoxalY)
			{
								
				if(pSceneTouchEvent.isActionUp())
				{
					ResourceManager.getInstance().activity.buttonPress();
					SceneManager.getInstance().setGameScene();
				}
				
				return true;
			}
		};
	
		//Exit button
		exit = new Sprite(400 - 128, 240 + 64, ResourceManager.getInstance().m_exit_r, vbom)
		{
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLoxalY)
			{			
				if(pSceneTouchEvent.isActionUp())
				{
					ResourceManager.getInstance().activity.buttonPress();
					System.exit(0);
				}
				
				return true;
			}
		};
		
		//music button
		audio = new Sprite(20, 20, ResourceManager.getInstance().m_audio_r, vbom)
		{
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLoxalY)
			{			
				if(pSceneTouchEvent.isActionUp())
				{
					activity.buttonPress();
					activity.toggleAudio();
				}
				
				return true;
			}
		};
		
		//Attach to the scene
		attachChild(play);
		attachChild(exit);
		attachChild(audio);
		
		//Register as touchable
		registerTouchArea(play);
		registerTouchArea(exit);
		registerTouchArea(audio);
		
		if(activity.getAudio())
		{
			ResourceManager.getInstance().m_menu_bgm.play();
		}
	}

	@Override
	public void onBackPressed() {
		System.exit(0);
	}

	@Override
	public void disposeScene(){
		//Detach all children
		this.detachChildren();
		
		ResourceManager.getInstance().unloadMenuResources();
	}	
}
