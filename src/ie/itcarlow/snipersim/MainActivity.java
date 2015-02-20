package ie.itcarlow.snipersim;

import ie.itcarlow.snipersim.SharedPreferencesManager;
import ie.itcarlow.snipersim.scene.SceneManager;

import org.andengine.engine.camera.ZoomCamera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.controller.MultiTouch;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.view.KeyEvent;
import android.widget.Toast;

public class MainActivity extends SimpleBaseGameActivity implements IUpdateHandler {
	// ===========================================================
	// Constants
	// ===========================================================

	static final int CAMERA_WIDTH = 800;
	static final int CAMERA_HEIGHT = 480;

	// ===========================================================
	// Fields
	// ===========================================================
	private ZoomCamera m_camera;
	private boolean audio;
	private boolean multi = false;
	
	
	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public EngineOptions onCreateEngineOptions() {
		m_camera = new ZoomCamera(0,0, CAMERA_WIDTH, CAMERA_HEIGHT);
		
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), m_camera);
		engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);
		
		if (!MultiTouch.isSupported(this))
		{
			Toast.makeText(this,  "MultiTouch unsupported", Toast.LENGTH_LONG).show();			
		}
		
		SharedPreferencesManager.getInstance(this);
		audio = SharedPreferencesManager.getInstance(this).getMusic();
		
		return engineOptions;
	}

    @Override
	public void onCreateResources()
    {
    	//Prepare Resource Manager
    	ResourceManager.prepareManager(this.getEngine(), this, m_camera, this.getVertexBufferObjectManager());
    }

    @Override
  	public Scene onCreateScene() 
    {
    	SceneManager.getInstance().setMenuScene();
    	mEngine.registerUpdateHandler(this);
    	return SceneManager.getInstance().getCurrentScene();
  	}
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	System.exit(0);
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
    	if (keyCode == KeyEvent.KEYCODE_BACK)
    		{
    			SceneManager.getInstance().getCurrentScene().onBackPressed();
    		}
    	
    	if (keyCode == KeyEvent.KEYCODE_HOME)
    		{
    			System.exit(0);
    		}
    	
    	if (keyCode == KeyEvent.KEYCODE_APP_SWITCH)
    		{
    			toggleAudio();
    		}
    	return false;
    }
    
    public boolean getAudio()
    {
    	return audio;
    }
    
    public void toggleAudio()
    {
    	audio = !audio;
    	SharedPreferencesManager.getInstance(this).saveMusic(audio);
    }
    
    public boolean getMP()
    {
    	return multi;
    }
    
    public void toggleMP()
    {
    	multi = !multi;
    }
    
    
	// ===========================================================
	// Methods
	// ===========================================================

	@Override
	public void onUpdate(float pSecondsElapsed) {
		SceneManager.getInstance().getCurrentScene().onUpdate();
	}

	@Override
	public void reset() {
		
	}
	
	public void create() {
	}
	
    // ===========================================================
 	// Inner and Anonymous Classes
 	// ===========================================================
}
