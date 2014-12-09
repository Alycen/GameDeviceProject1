package ie.itcarlow.snipersim;

import ie.itcarlow.snipersim.scene.SceneManager;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.controller.MultiTouch;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.view.KeyEvent;
import android.widget.Toast;

public class GameActivity extends SimpleBaseGameActivity implements IUpdateHandler {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 480;

	// ===========================================================
	// Fields
	// ===========================================================

	private float m_camSpeed = 4;
	private SmoothCamera m_camera;
	
	Civilian civTest;
	
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
		final SmoothCamera m_camera = new SmoothCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, m_camSpeed, m_camSpeed, 0);
		
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), m_camera);
		engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);
		
		if (!MultiTouch.isSupported(this))
		{
			Toast.makeText(this,  "MultiTouch unsupported", Toast.LENGTH_LONG).show();			
		}
		
		return engineOptions;
	}

    @Override
	public void onCreateResources()
    {
    	//Prepare Resource Manager
    	ResourceManager.prepareManager(this.getEngine(), this, m_camera, this.getVertexBufferObjectManager());
    }
/*
    private void loadGfx() {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        //pass civilian textures here
    	BitmapTextureAtlas civSprite = new BitmapTextureAtlas(getTextureManager(),23,49);
    	ITextureRegion civRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(civSprite, this, "tempNPC.png", 0, 0);
    	//Make mark sprite and also pass it to the load method in civilian
    	BitmapTextureAtlas markSprite = new BitmapTextureAtlas(getTextureManager(),5,8);
    	ITextureRegion markRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(markSprite, this, "mark.png", 0,0);
        //mTextureAustrianBear = new BitmapTextureAtlas(getTextureManager(), 46, 54);  
        //mAustrianBearTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mTextureAustrianBear, this, "austrian_bear.png", 0, 0);
        //mTextureAustrianBear.load();
    }
    */

    @Override
  	public Scene onCreateScene() 
    {
    	SceneManager.getInstance().setMenuScene();
    	
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
    	return false;
    }
    
	// ===========================================================
	// Methods
	// ===========================================================

	@Override
	public void onUpdate(float pSecondsElapsed) {
				
	}

	@Override
	public void reset() {
		
	}
	
	public void create() {
		//setBackground(new Background(Color.CYAN));
		//BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		//play_button_atlas = new BitmapTextureAtlas(getTextureManager(), 512, 512);
		
		//play_button_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(play_button_atlas, this, "buttonPlay.png", 0, 0);
		//play_button_atlas.load();
		
		//play = new Sprite(400, 240, play_button_region, this.getVertexBufferObjectManager());
		//ResourceManager.getInstance().loadMenuResources();
		//play = new Sprite (400, 240, ResourceManager.getInstance().play_button_region, ResourceManager.getInstance().vbom);
		//this.mScene.attachChild(play);
		//SceneManager.getInstance().getCurrentScene().attachChild(play);
		//attachChild(play);
		//createMenu();
	}
    // ===========================================================
 	// Inner and Anonymous Classes
 	// ===========================================================
}
