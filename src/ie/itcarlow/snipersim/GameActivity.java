package ie.itcarlow.snipersim;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.controller.MultiTouch;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import android.view.MotionEvent;
//
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
	private SmoothCamera m_camera;
	
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
		final SmoothCamera m_camera = new SmoothCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, m_camSpeed, m_camSpeed, 6);
		m_camera.setZoomFactor(0);
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), m_camera);
		engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);
		
		if (!MultiTouch.isSupported(this))
		{
			Toast.makeText(this,  "MultiTouch unsupported", Toast.LENGTH_LONG);
		}
		
		return engineOptions;
	}

    @Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback)	throws Exception { 
    	ResourceManager.prepareManager(this.getEngine(), this, m_camera, this.getVertexBufferObjectManager());
    	 loadGfx();
		 pOnCreateResourcesCallback.onCreateResourcesFinished();

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
  	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
  			throws Exception {

  		this.mScene = new Scene();
  		this.mScene.setBackground(new Background(0, 125, 58));
  	    pOnCreateSceneCallback.onCreateSceneFinished(this.mScene);  		
  	}
    
    @Override
	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) 
          throws Exception { 
    	this.mEngine.getVertexBufferObjectManager();
		
    	this.mEngine.registerUpdateHandler(this);
	 	 
    	pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

	// ===========================================================
	// Methods
	// ===========================================================

	@Override
	public void onUpdate(float pSecondsElapsed) {
				
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

    // ===========================================================
 	// Inner and Anonymous Classes
 	// ===========================================================
}
