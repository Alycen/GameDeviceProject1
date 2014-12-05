package ie.itcarlow.snipersim;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.badlogic.gdx.math.Vector2;

public class Civilian {
	private Vector2 m_position;
	private BitmapTextureAtlas mTexture;
	private ITextureRegion mTextureRegion;
	private Sprite mSprite, markSprite;
	private float m_width, m_height, m_scale;
	private boolean m_marked, m_shot;
	//(States) Normal, Alert
	
	//Marked()
	//Shot()
	//Move()
	//Update()
	
	public void Load(TextureManager mTextureManager) {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/"); 
		mTexture = new BitmapTextureAtlas(mTextureManager, 32, 40);
		//mTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mTexture, /* context*/ , "tempNPC.png", 0, 0);
		mTexture.load();
	}
	
	public void Move() {
		
	}
	
	public boolean Marked() {
		// if(screentaped and crosshair in sprite area)
		//		shot = true; 
		// else
		//		shot = false;
		return m_marked;
	}
	
	public boolean Shot() {
		// if(screentaped and crosshair in sprite area)
		//		shot = true;
		// else
		//		shot = false;
		
		return m_shot;
	}
	
	public void Update() {
		Move();
	}
}
