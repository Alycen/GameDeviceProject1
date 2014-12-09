package ie.itcarlow.snipersim;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

public class ResourceManager {
	
	private static final ResourceManager INSTANCE = new ResourceManager();
	
	public Engine engine;
	public GameActivity activity;
	public SmoothCamera camera;
	public VertexBufferObjectManager vbom;

	//Menu
	private BitmapTextureAtlas play_button_atlas;
	public ITextureRegion play_button_region;
	
	private BitmapTextureAtlas exit_button_atlas;
	public ITextureRegion exit_button_region;
	
	//Game
	
	
	//
	
	public static ResourceManager getInstance() {
		return INSTANCE;
	}
	
	public static void prepareManager(Engine engine, GameActivity activity, SmoothCamera camera, VertexBufferObjectManager vbom)
	{
		getInstance().engine = engine;
		getInstance().activity = activity;
		getInstance().camera = camera;
		getInstance().vbom = vbom;
	}
	
	public void loadMenuResources(){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
        //mScopeTexture = new BitmapTextureAtlas(getTextureManager(), 739, 491);  
        //mScopeTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mScopeTexture, this, "Scope.png", 0, 0);
        //mScopeTexture.load();
		
		play_button_atlas = new BitmapTextureAtlas(activity.getTextureManager(), 256, 64, TextureOptions.BILINEAR);
		play_button_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(play_button_atlas, activity, "buttonPlay.png", 0, 0);
		play_button_atlas.load();
		
		exit_button_atlas = new BitmapTextureAtlas(activity.getTextureManager(), 512, 512, TextureOptions.BILINEAR);
		exit_button_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(exit_button_atlas, activity, "buttonExit.png", 0, 0);
		exit_button_atlas.load();
		
	}
	
	public void unloadMenuResources() {
		play_button_atlas.unload();
		play_button_atlas = null;
		
		exit_button_atlas.unload();
		exit_button_atlas = null;
	}
	
	public void loadGameResources(){
		
	}
	
	public void unloadGameResources(){

	}
	
	public void loadFonts(){
		
	}
	
}
