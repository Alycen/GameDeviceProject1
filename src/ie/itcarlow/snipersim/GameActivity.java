package ie.itcarlow.snipersim;

import ie.itcarlow.snipersim.scene.SceneManager;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.controller.MultiTouch;
import org.andengine.ui.activity.BaseGameActivity;

import android.view.KeyEvent;
import android.widget.Toast;

import org.andengine.engine.camera.Camera;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;

public class GameActivity extends BaseGameActivity implements IUpdateHandler {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;

	// ===========================================================
	// Fields
	// ===========================================================

	private float m_camSpeed = 4;
	private Camera m_camera;
	
	Civilian civTest;
	
	//=====//Leftovers
	private Scene mScene;
	
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
		final Camera m_camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), m_camera);
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
	public void onCreateResources(OnCreateResourcesCallback cb)	throws Exception { 
    	//Prepare Resource Manager
    	ResourceManager.prepareManager(this.getEngine(), this, m_camera, this.getVertexBufferObjectManager());
    	
    	//Load all menu items
    	 
    	
    	//loadGfx();
    	 
    	 
		 cb.onCreateResourcesFinished();

    }

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

    @Override
  	public void onCreateScene(OnCreateSceneCallback cb) throws Exception {
    	SceneManager.getInstance().setMenuScene(cb);
    	
    	cb.onCreateSceneFinished(this.mScene);  		
  	}
    
    @Override
	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback cb) throws Exception { 
    		 
    	cb.onPopulateSceneFinished();
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
	
    // ===========================================================
 	// Inner and Anonymous Classes
 	// ===========================================================
}
