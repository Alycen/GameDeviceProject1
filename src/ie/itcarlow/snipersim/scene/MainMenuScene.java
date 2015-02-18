package ie.itcarlow.snipersim.scene;

import ie.itcarlow.snipersim.ResourceManager;
import org.andengine.audio.music.Music;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;

public class MainMenuScene extends BaseScene{
	
	//Buttons
	private Sprite m_playbtn;
	private Sprite m_exitbtn;
	private Sprite m_audiobtn;
	private Sprite m_multibtn;
	
	//Overlays
	private Sprite m_audio;
	private Sprite m_multi;
	
	//Background
	private Sprite m_bg;
	
	//Music
	private Music bgm;
	
	@Override
	public void createScene() {
		setBackground(new Background(Color.BLACK));
		m_bg = new Sprite(0, 0, ResourceManager.getInstance().m_bg_r, vbom);
		setBackground(new SpriteBackground(0, 0, 0, m_bg));
		
		//Play button
		m_playbtn = new Sprite(400 - 128, 240 - 32, ResourceManager.getInstance().m_play_r, vbom)
		{
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLoxalY)
			{
								
				if(pSceneTouchEvent.isActionUp())
				{
		    		ResourceManager.getInstance().m_click.play();
					SceneManager.getInstance().setGameScene();
				}
				
				return true;
			}
		};
	
		//Exit button
		m_exitbtn = new Sprite(400 - 128, 240 + 64, ResourceManager.getInstance().m_exit_r, vbom)
		{
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLoxalY)
			{			
				if(pSceneTouchEvent.isActionUp())
				{
		    		ResourceManager.getInstance().m_click.play();
					System.exit(0);
				}
				
				return true;
			}
		};
		
		//music button
		m_audiobtn = new Sprite(20, 20, ResourceManager.getInstance().m_btnsm_r, vbom)
		{
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLoxalY)
			{			
				if(pSceneTouchEvent.isActionUp())
				{
		    		ResourceManager.getInstance().m_click.play();
					activity.toggleAudio();
					
					//If music is playing stop it
					if (!activity.getAudio() && bgm.isPlaying())
					{
						bgm.pause();						
					}
					
					//If we turned audio on and it's not, play
					if (activity.getAudio())
					{
						bgm.seekTo(0);
						bgm.play();
					}
					
					genAudioButton();
				}
				
				return true;
			}
		};
		
		//multiplayer button
		m_multibtn = new Sprite(716, 20, ResourceManager.getInstance().m_btnsm_r, vbom)
		{
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLoxalY)
			{			
				if(pSceneTouchEvent.isActionUp())
				{
		    		ResourceManager.getInstance().m_click.play();
					activity.toggleMP();
					genMPButton();
				}
				
				return true;
			}
		};
		
		//Attach to the scene
		attachChild(m_playbtn);
		attachChild(m_exitbtn);
		attachChild(m_audiobtn);
		attachChild(m_multibtn);
		genAudioButton();
		genMPButton();
		
		//Register as touchable
		registerTouchArea(m_playbtn);
		registerTouchArea(m_exitbtn);
		registerTouchArea(m_audiobtn);
		registerTouchArea(m_multibtn);
		
		//Audio handling
		bgm = ResourceManager.getInstance().m_menu_bgm;
		
		if(activity.getAudio())
		{
			bgm.play();
		}
	}

	public void genAudioButton()
	{
		detachChild(m_audio);
		
		if (activity.getAudio() == true)
		{
			ResourceManager.getInstance().m_audio_t.setCurrentTileIndex(1);
		}
		
		//Otherwise if negative or zero
		else ResourceManager.getInstance().m_audio_t.setCurrentTileIndex(0);

		m_audio = new Sprite(20, 20, ResourceManager.getInstance().m_audio_t, vbom);
		
		attachChild(m_audio);
	}
	
	public void genMPButton()
	{
		detachChild(m_multi);
		
		if (activity.getMP() == true)
		{
			ResourceManager.getInstance().m_multi_t.setCurrentTileIndex(1);
		}
		
		//Otherwise if negative or zero
		else ResourceManager.getInstance().m_multi_t.setCurrentTileIndex(0);

		m_multi = new Sprite(716, 20, ResourceManager.getInstance().m_multi_t, vbom);
		
		attachChild(m_multi);
	}
	
	
	@Override
	public void onBackPressed() {
		System.exit(0);
	}

	@Override
	public void disposeScene(){
		//Detach all children
		this.detachChildren();
		bgm.stop();
		
		ResourceManager.getInstance().unloadMenuResources();
	}

	@Override
	public void onUpdate() {
				
	}	
}
