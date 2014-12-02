package ie.itcarlow.box2ddemo;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.badlogic.gdx.math.Vector2;

public class Civilian {
	//position
	private Vector2 m_position;
	//texture
	private BitmapTextureAtlas mTexture;
	private ITextureRegion mTextureRegion;
	private Sprite mSprite, markSprite;
	//scale
	private float m_width, m_height, m_scale;
	//bools
	private boolean m_marked, m_shot;
	//(States) Normal, Alert
	
	//Marked()
	//Shot()
	//Move()
	//Update()
	
	//public void Load(TextureManager mTextureManager) {
	//	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/"); 
	//	mTexture = new BitmapTextureAtlas(mTextureManager, 32, 40);
		//mTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mTexture, /* context*/ , "tempNPC.png", 0, 0);
	//	mTexture.load();
	//}
	
	//public boolean CheckMark() {
		// if(screentaped and crosshair in sprite area)
		//		shot = true; 
		// else
		//		shot = false;
	//	return marked;
	//}
	
	//public boolean CheckHit() {
		// if(screentaped and crosshair in sprite area)
		//		shot = true;
		// else
		//		shot = false;
		
	//	return shot;
	//}
	
	//public void Update() {
	//	Move();
	//}
	
	//public void Draw() {
	//	mSprite = new Sprite(50, 50, this.mTextureRegion, this.getVertexBufferObjectManager());
		// if CheckMark() == true ) 
		// 		draw marker;
	//}
}

