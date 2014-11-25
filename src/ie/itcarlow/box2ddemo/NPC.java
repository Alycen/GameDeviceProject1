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
import org.andengine.opengl.texture.TextureManager;
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

public abstract class NPC {
	private BitmapTextureAtlas mTexture;
	private ITextureRegion mTextureRegion;
	private Sprite mSprite, markSprite;
	private boolean marked, shot;
	private float width, height, scale;
	private Vector2 position;
	private int timer;
	
	public void Move() {
		timer = 1;
		for ( ; timer <= 30 ; timer ++ ) {
			if ( timer == 30 )
				timer = 1;
			// do something
		}
	}
	
	//austrianBear = new Sprite(centerX, centerY, this.mAustrianBearTextureRegion, this.getVertexBufferObjectManager())
	//   {
	public void Load(TextureManager mTextureManager) {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/"); 
		mTexture = new BitmapTextureAtlas(mTextureManager, 32, 40);
		mTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mTexture, /* context*/ , "tempNPC.png", 0, 0);
		mTexture.load();
	}
	
	public boolean CheckMark() {
		// if(screentaped and crosshair in sprite area)
		//		shot = true; 
		// else
		//		shot = false;
		return marked;
	}
	
	public boolean CheckHit() {
		// if(screentaped and crosshair in sprite area)
		//		shot = true;
		// else
		//		shot = false;
		
		return shot;
	}
	
	public void Update() {
		Move();
	}
	
	public void Draw() {
		mSprite = new Sprite(50, 50, this.mTextureRegion, this.getVertexBufferObjectManager());
		// if CheckMark() == true ) 
		// 		draw marker;
	}
}