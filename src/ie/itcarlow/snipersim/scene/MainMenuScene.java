package ie.itcarlow.snipersim.scene;

import ie.itcarlow.snipersim.ResourceManager;

import org.andengine.audio.music.Music;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;

public class MainMenuScene extends BaseScene{
		
	private Sprite play;
	private Sprite exit;
	private Sprite audio;
	private Sprite bg;
	
	private Music bgm;
	
	@Override
	public void createScene() {
		setBackground(new Background(Color.BLACK));
		
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
					//System.exit(0);
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
					
					//If music is playing stop it
					if (!activity.getAudio() && bgm.isPlaying())
						bgm.pause();
					
					//If we turned audio on and it's not, play
					if (activity.getAudio())
					{
						bgm.seekTo(0);
						bgm.play();
					}	
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
		
		//Audio handling
		bgm = ResourceManager.getInstance().m_menu_bgm;
		
		if(activity.getAudio())
		{
			bgm.play();
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
