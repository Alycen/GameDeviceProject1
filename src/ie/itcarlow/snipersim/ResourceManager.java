package ie.itcarlow.snipersim;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.ZoomCamera;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

public class ResourceManager {
	
	private static final ResourceManager INSTANCE = new ResourceManager();
	
	public Engine engine;
	public GameActivity activity;
	public ZoomCamera camera;
	public VertexBufferObjectManager vbom;

	//======Menu
	private BuildableBitmapTextureAtlas menu_atlas;

	//Buttons
	public ITextureRegion m_play_r;
	public ITextureRegion m_exit_r;
	public ITextureRegion m_audio_r;
	public ITextureRegion m_audio_off_r;
	
	public ITextureRegion m_bg_r;
	
	public Music m_menu_bgm;
	public Sound m_click;
	
	//======Game
	private BuildableBitmapTextureAtlas game_atlas;
	
	//Levels
	public ITextureRegion g_l_def_r;	
	public ITextureRegion g_l_city_r;	
	public ITextureRegion g_l_tent_r;
	
	//Scope
	public ITextureRegion g_scope_r;
	
	public ITextureRegion g_civ_r;
	
	public Music g_game_bgm;
	
	
	public static ResourceManager getInstance() {
		return INSTANCE;
	}
	
	public static void prepareManager(Engine engine, GameActivity activity, ZoomCamera camera, VertexBufferObjectManager vbom)
	{
		getInstance().engine = engine;
		getInstance().activity = activity;
		getInstance().camera = camera;
		getInstance().vbom = vbom;

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
    	MusicFactory.setAssetBasePath("mfx/");
    	SoundFactory.setAssetBasePath("mfx/");
	}
	
	public void loadMenuResources(){
		menu_atlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		
		//Play button
		m_play_r = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menu_atlas, activity, "menu/buttonPlay.png");
		
		//Exit button
		m_exit_r = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menu_atlas, activity, "menu/buttonExit.png");
		
		//Audio button
		m_audio_r = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menu_atlas, activity, "menu/buttonAudio.png");
		m_audio_off_r = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menu_atlas, activity, "menu/buttonAudioOff.png");
		
		//Menu background
		m_bg_r = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menu_atlas, activity, "menu/bg.png");
		
		//Load menu texture atlas
    	try 
		{
    		menu_atlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
    		menu_atlas.load();
		} 
    	
		catch (final TextureAtlasBuilderException e)
		{
			Debug.e(e);
		}
    	
    	//Load menu bgm
    	try
    	{
    	    m_menu_bgm = MusicFactory.createMusicFromAsset(engine.getMusicManager(), activity,"menubgm.ogg");
    	}
    	catch (IOException e)
    	{
    	    e.printStackTrace();
    	}
    	
    	//Load menu button click
    	try
    	{
    	    m_click = SoundFactory.createSoundFromAsset(engine.getSoundManager(), activity, "button.ogg");
    	}
    	catch (IOException e)
    	{
    	    e.printStackTrace();
    	}
	}
	
	public void unloadMenuResources() {
		menu_atlas.unload();
		menu_atlas = null;
		m_menu_bgm = null;
	}
	
	public void loadGameResources(){

		game_atlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR);
		
		//Default level
		//g_l_def_r = BitmapTextureAtlasTextureRegionFactory.createFromAsset(game_atlas, activity, "game/deflevel.png");
		
		//City level
		g_l_city_r = BitmapTextureAtlasTextureRegionFactory.createFromAsset(game_atlas, activity, "game/city.jpg");
		
		//Tent level
		g_l_tent_r = BitmapTextureAtlasTextureRegionFactory.createFromAsset(game_atlas, activity, "game/tent.jpg");
		
		//Scope texture
		g_scope_r = BitmapTextureAtlasTextureRegionFactory.createFromAsset(game_atlas, activity, "game/scope.png");
		
		g_civ_r = BitmapTextureAtlasTextureRegionFactory.createFromAsset(game_atlas, activity, "tempNPC.png");
		
		//Load game texture atlas
    	try 
		{
    		game_atlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
    		game_atlas.load();
		} 
    	
		catch (final TextureAtlasBuilderException e)
		{
			Debug.e(e);
		}
    	
    	//Load game bgm
    	try
    	{
    	    g_game_bgm = MusicFactory.createMusicFromAsset(engine.getMusicManager(), activity,"gamebgm.ogg");
    	}
    	catch (IOException e)
    	{
    	    e.printStackTrace();
    	}
    	
    	
	}
	
	public void unloadGameResources(){
		game_atlas.unload();
		game_atlas = null;
		
	}
	
	public void loadFonts(){
		
	}
	
}
