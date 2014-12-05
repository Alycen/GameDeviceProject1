package ie.itcarlow.snipersim;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.badlogic.gdx.math.Vector2;

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
		//mTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mTexture, /* context*/ , "tempNPC.png", 0, 0);
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
	//	mSprite = new Sprite(50, 50, this.mTextureRegion, this.getVertexBufferObjectManager());
		// if CheckMark() == true ) 
		// 		draw marker;
	}
}