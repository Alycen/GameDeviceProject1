package ie.itcarlow.box2ddemo;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import android.view.MotionEvent;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;

public class Box2DSpriteCollisions extends BaseGameActivity implements IUpdateHandler {
	// ===========================================================
	// Constants
	// ===========================================================
	
	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;

	// ===========================================================
	// Fields
	// ===========================================================

	private BitmapTextureAtlas mTextureAustrianBear;
	private BitmapTextureAtlas mTexturePiglet;
	private ITextureRegion mAustrianBearTextureRegion;
	private ITextureRegion mPigletTextureRegion;
	private Scene mScene;
	private Sprite mPiglet;	
	private Sprite austrianBear;

	private PhysicsWorld mPhysicsWorld;
	
	private boolean hit;
	
	Vector2 vel;
	
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
		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
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
        
        mTexturePiglet = new BitmapTextureAtlas(getTextureManager(), 46, 54);  
        mPigletTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mTexturePiglet, this, "piglet.png", 0, 0);
        mTexturePiglet.load();
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
               //setBodyPosition(this, pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
               //return true;
               
               if (pSceneTouchEvent.getAction() == MotionEvent.ACTION_UP) {
                   //setBodyPosition(this, event.getX() - this.getWidth() / 2, event.getY() - this.getHeight() / 2);
                   int speed = 10;
                   vel = new Vector2((mPiglet.getX() - austrianBear.getX()) / speed, (mPiglet.getY() - austrianBear.getY()) / speed); 
                   Vector2 velocity = Vector2Pool.obtain(vel.x, vel.y);
                   Body b = (Body) this.getUserData();
                   b.applyLinearImpulse(velocity, b.getWorldCenter());
                   Vector2Pool.recycle(velocity);           
                   }
                       return true;

           }
       };	   
	   
		mPiglet = new Sprite(centerX - 100, centerY - 100,
				mPigletTextureRegion,
				this.mEngine.getVertexBufferObjectManager());
		this.mScene.registerTouchArea(austrianBear);

		mScene.attachChild(austrianBear);
		mScene.attachChild(mPiglet);

		setUpBox2DWorld();
		// The bear sprite (unlike the piglet sprite) is a local variable,
		// so it must be passed to method createPhysicsBodies
		createPhysicsBodies(austrianBear);
	   
	   // The bear sprite (unlike the piglet sprite) is a local variable, 
	   //  so it must be passed to method createPhysicsBodies
	  this.mEngine.registerUpdateHandler(this);
	 	 
	   pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

	// ===========================================================
	// Methods
	// ===========================================================

    private void setUpBox2DWorld() {
    	// Set up your physics world here.
    	final Vector2 v = Vector2Pool.obtain(0, 0);
    	// Establish world with no gravity, second param 
    	// (false) means PhysicsWorld cannot sleep
    	mPhysicsWorld = new PhysicsWorld(v, false); 
    	Vector2Pool.recycle(v);
    	mPhysicsWorld.setContactListener(createContactListener());
    	this.mScene.registerUpdateHandler(mPhysicsWorld); 
    	
    }
    
    private void createPhysicsBodies(final Sprite sprite) {
    	// Create your Box2D bodies here.
    	final FixtureDef PLAYER_FIX = PhysicsFactory.createFixtureDef(1.5f,0.45f, 0.3f);
    	Body body = PhysicsFactory.createCircleBody(mPhysicsWorld, sprite, BodyType.DynamicBody, PLAYER_FIX);
    	body.setLinearDamping(0.4f);
    	sprite.setUserData(body); 
    	body.setUserData("bear");
    	
    	Body body2 = PhysicsFactory.createCircleBody(mPhysicsWorld, mPiglet, BodyType.DynamicBody, PLAYER_FIX);
    	body2.setLinearDamping(0.4f);
    	mPiglet.setUserData(body2); 
    	body2.setUserData("pig");
    	
    	/* PhysicsConnector params are: area shape (sprite), physics body, update the body’s position(true) and enable rotations (true)*/
    	mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(sprite, body, true, true));
    	mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(mPiglet, body2, true, true));
   
    }
    
    /*
     * Helper method that translates the associated physics body to the specified coordinates.
     * 
	 * @param pX The desired x coordinate for this sprite.
	 * @param pY The desired y coordinate for this sprite.
     */
    private void setBodyPosition(final Sprite sprite, final float pX, final float pY) {
    	
    	final Body body = (Body) sprite.getUserData();
        final float widthD2 = sprite.getWidth() / 2;
        final float heightD2 = sprite.getHeight() / 2;
        final float angle = body.getAngle(); // keeps the body angle       
        final Vector2 v2 = Vector2Pool.obtain((pX + widthD2) / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, (pY + heightD2) / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
        body.setTransform(v2, angle);
        Vector2Pool.recycle(v2);
    }
    
    private ContactListener createContactListener() {
        ContactListener levelContactListener = new ContactListener() {
         @Override
         public void beginContact(Contact contact) {
             String a = (String) contact.getFixtureA().getBody().getUserData();
             String b = (String) contact.getFixtureB().getBody().getUserData();
            
             if(a != null && b != null) {
                if(a.equals("bear") && b.equals("pig") ||
                             a.equals("pig") && b.equals("bear")) {
                     hit = true;
                }
             }
         }
         @Override
         public void endContact(Contact contact) {
            hit = false;
                        
         }
         @Override
         public void preSolve(Contact contact, Manifold oldManifold) {
             // TODO Auto-generated method stub
            
         }
         @Override
         public void postSolve(Contact contact, ContactImpulse impulse) {
             // TODO Auto-generated method stub
            
         }
     };
     return levelContactListener;
     }

	@Override
	public void onUpdate(float pSecondsElapsed) {
		// TODO Auto-generated method stub
		if (hit) {
			// Only do this ONCE if a collision has occurred (hint: you need a boolean variable to
			// store the collision state)
			mEngine.runOnUpdateThread(new Runnable() {
				@Override
				public void run() {
					// Find the physics connector associated with the sprite
					// mPiglet
					PhysicsConnector physicsConnector = mPhysicsWorld
							.getPhysicsConnectorManager()
							.findPhysicsConnectorByShape(mPiglet);
					// Unregister the physics connector
					mPhysicsWorld.unregisterPhysicsConnector(physicsConnector);
					// Destroy the body
					mPhysicsWorld.destroyBody(physicsConnector.getBody());
					mScene.detachChild(mPiglet);
					hit = false;
				}
			});
		}
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	    
    // ===========================================================
 	// Inner and Anonymous Classes
 	// ===========================================================
    
}
