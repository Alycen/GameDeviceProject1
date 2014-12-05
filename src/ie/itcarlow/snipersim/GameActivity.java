package ie.itcarlow.snipersim;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import android.view.MotionEvent;
//

// !!! There's a big problem with this main class, the project wont run at all on android virtual devices and it's a problem with this file
// !!! Needs to be fixed ASAP
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
		m_camera.setZoomFactor(5);
		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), m_camera);
	}

    @Override
	public void onCreateResources(
       OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception { 

    	 loadGfx();
		 pOnCreateResourcesCallback.onCreateResourcesFinished();

    }

    private void loadGfx() {     
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        //pass civilian textures here, make them local??? 
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
