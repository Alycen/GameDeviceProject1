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
	private float m_width = 23, m_height = 49, m_scale = 1; // that is the width and height of the TEMP sprite for Civilian
	private boolean m_marked = false, m_shot = false;
	//(States) Normal, Alert
	
	public void Load(TextureManager mTextureManager) { // Civilians will not be touch regions just drawn on sprites
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/"); 
		mTexture = new BitmapTextureAtlas(mTextureManager, 32, 40);
		//mTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mTexture, /* context*/ , "tempNPC.png", 0, 0);
		mTexture.load();
	}
	
	public void Move() {
		int timer = 0;
		for ( ; timer < 30 ; timer ++ ) {
			//do move things
		}
	}
	
	public void setMarked(boolean mark) {
		m_marked = mark;
	}
	
	public boolean Marked() { // 
		// if(screentaped && crosshair in sprite area)
		//		shot = true; 
		// else
		//		shot = false;
		return m_marked;
	}
	
	public void setShot(boolean shoot) {
		m_shot = shoot;
	}
	
	public boolean Shot() {
		// if(screentaped && crosshair in sprite area)
		//		shot = true;
		// else
		//		shot = false;
		
		return m_shot;
	}
	
	public void Update() {
		Move();
	}
}

