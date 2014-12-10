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
	private BitmapTextureAtlas m_play_a;
	public ITextureRegion m_play_r;
	
	private BitmapTextureAtlas m_exit_a;
	public ITextureRegion m_exit_r;
	
	private BitmapTextureAtlas m_bg_a;
	public ITextureRegion m_bg_r;
	
	//Game
	private BitmapTextureAtlas g_dlvl_a;
	public ITextureRegion g_dlvl_r;
	
	private BitmapTextureAtlas g_l_city_a;
	public ITextureRegion g_l_city_r;
	
	private BitmapTextureAtlas g_l_tent_a;
	public ITextureRegion g_l_tent_r;
	
	private BitmapTextureAtlas g_scope_a;
	public ITextureRegion g_scope_r;
	
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
		
		//Play button
		m_play_a = new BitmapTextureAtlas(activity.getTextureManager(), 256, 64, TextureOptions.BILINEAR);
		m_play_r = BitmapTextureAtlasTextureRegionFactory.createFromAsset(m_play_a, activity, "menu/buttonPlay.png", 0, 0);
		m_play_a.load();
		
		//Exit button
		m_exit_a = new BitmapTextureAtlas(activity.getTextureManager(), 256, 64, TextureOptions.BILINEAR);
		m_exit_r = BitmapTextureAtlasTextureRegionFactory.createFromAsset(m_exit_a, activity, "menu/buttonExit.png", 0, 0);
		m_exit_a.load();
		
		//Menu background
		m_bg_a = new BitmapTextureAtlas(activity.getTextureManager(), 1024, 512, TextureOptions.BILINEAR);
		m_bg_r = BitmapTextureAtlasTextureRegionFactory.createFromAsset(m_bg_a, activity, "menu/bg.png", 0, 0);
		m_bg_a.load();
	}
	
	public void unloadMenuResources() {
		m_play_a.unload();
		m_play_a = null;
		
		m_exit_a.unload();
		m_exit_a = null;
	}
	
	public void loadGameResources(){
		//Default level
		g_dlvl_a = new BitmapTextureAtlas(activity.getTextureManager(), 1024, 512, TextureOptions.BILINEAR);
		g_dlvl_r = BitmapTextureAtlasTextureRegionFactory.createFromAsset(g_dlvl_a, activity, "game/deflevel.png", 0, 0);
		g_dlvl_a.load();
		
		//City level
		g_l_city_a = new BitmapTextureAtlas(activity.getTextureManager(), 1024, 512, TextureOptions.BILINEAR);
		g_l_city_r = BitmapTextureAtlasTextureRegionFactory.createFromAsset(g_l_city_a, activity, "game/city.jpg", 0, 0);
		g_l_city_a.load();
		
		//Tent level
		g_l_tent_a = new BitmapTextureAtlas(activity.getTextureManager(), 1024, 512, TextureOptions.BILINEAR);
		g_l_tent_r = BitmapTextureAtlasTextureRegionFactory.createFromAsset(g_l_tent_a, activity, "game/tent.jpg", 0, 0);
		g_l_tent_a.load();
		
		//Scope texture
		g_scope_a = new BitmapTextureAtlas(activity.getTextureManager(), 1024, 512, TextureOptions.BILINEAR);
		g_scope_r = BitmapTextureAtlasTextureRegionFactory.createFromAsset(g_scope_a, activity, "game/scope.png", 0, 0);
		g_scope_a.load();
	}
	
	public void unloadGameResources(){
		g_l_city_a.unload();
		g_l_city_a = null;
		
		g_l_tent_a.unload();
		g_l_tent_a = null;
		
		g_dlvl_a.unload();
		g_dlvl_a = null;
	}
	
	public void loadFonts(){
		
	}
	
}
