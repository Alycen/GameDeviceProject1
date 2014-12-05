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
	private BitmapTextureAtlas mTextureAustrianBear;
	private ITextureRegion mAustrianBearTextureRegion;
	private Sprite austrianBear;
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
        mTextureAustrianBear = new BitmapTextureAtlas(getTextureManager(), 46, 54);  
        mAustrianBearTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mTextureAustrianBear, this, "austrian_bear.png", 0, 0);
        mTextureAustrianBear.load();
        
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

       // Setup coordinates for the sprite in order that it will
       //  be centered on the camera.
	   final float centerX = (CAMERA_WIDTH - this.mAustrianBearTextureRegion.getWidth()) / 2;
	   final float centerY = (CAMERA_HEIGHT - this.mAustrianBearTextureRegion.getHeight()) / 2;
 
	   // Create the austrian bear and add it to the scene.
	   
	   austrianBear = new Sprite(centerX, centerY, this.mAustrianBearTextureRegion, this.getVertexBufferObjectManager())
	   {
           @Override
           public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
                                        final float pTouchAreaLocalX,
                                        final float pTouchAreaLocalY) {
        	   //mAustrianB	
               //setBodyPosition(this, pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
               //return true;
               
               if (pSceneTouchEvent.getAction() == MotionEvent.ACTION_UP) {
                   //setBodyPosition(this, event.getX() - this.getWidth() / 2, event.getY() - this.getHeight() / 2);
                     
                   }
                       return true;

           }
       };	   
	   
				this.mEngine.getVertexBufferObjectManager();
		this.mScene.registerTouchArea(austrianBear);

		mScene.attachChild(austrianBear);

		// The bear sprite (unlike the piglet sprite) is a local variable,
		// so it must be passed to method createPhysicsBodies
	   
	   // The bear sprite (unlike the piglet sprite) is a local variable, 
	   //  so it must be passed to method createPhysicsBodies
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
