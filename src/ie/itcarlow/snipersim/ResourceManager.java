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
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

public class ResourceManager {
	
	private static final ResourceManager INSTANCE = new ResourceManager();
	
	public Engine engine;
	public MainActivity activity;
	public ZoomCamera camera;
	public VertexBufferObjectManager vbom;

	//======Menu
	private BuildableBitmapTextureAtlas menu_atlas;

	//Buttons
	public ITextureRegion m_play_r;
	public ITextureRegion m_exit_r;
	public TiledTextureRegion m_audio_t;
	public TiledTextureRegion m_multi_t;

	public ITextureRegion m_btn_r;
	public ITextureRegion m_btnsm_r;
	
	public ITextureRegion m_bg_r;
	
	public Music m_menu_bgm;
	public Sound m_click;
	
	//======Game
	private BuildableBitmapTextureAtlas game_atlas;
	private BuildableBitmapTextureAtlas game_npc_atlas;
	
	//Levels
	public ITextureRegion g_l_def_r;	
	public ITextureRegion g_l_city_r;
	public ITextureRegion g_l_tent_r;
	
	//Level Overlays
	public ITextureRegion g_lo_def_r;
	public ITextureRegion g_lo_curtains_r;
	public ITextureRegion g_lo_blnk_r;
	
	//Scope
	public ITextureRegion g_scope_r;
	
	//HUD
	public ITextureRegion g_h_reload_r;
	public TiledTextureRegion g_h_ammo_t;
	public ITextureRegion g_h_timer_r;
	public ITextureRegion g_h_timercop_r;
	
	//NPCs
	public TiledTextureRegion g_civ_a_t;
	public TiledTextureRegion g_civ_b_t;
	public TiledTextureRegion g_civ_c_t;
	public TiledTextureRegion g_civ_d_t;
	public TiledTextureRegion g_cop_t;
	public ITextureRegion g_civ_r;
	public ITextureRegion g_cop_r;
	
	//Music
	public Music g_game_bgm;
	
	//SFX
	public Sound g_shot;
	public Sound g_cell;
	public Sound g_empty;
	
	public static ResourceManager getInstance() {
		return INSTANCE;
	}
	
	public static void prepareManager(Engine engine, MainActivity activity, ZoomCamera camera, VertexBufferObjectManager vbom)
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
		
		//Default buttons
		m_btn_r = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menu_atlas, activity, "menu/buttonBase.png");
		m_btnsm_r = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menu_atlas, activity, "menu/buttonBaseSmall.png");
		
		//Play button
		m_play_r = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menu_atlas, activity, "menu/buttonPlay.png");
		
		//Exit button
		m_exit_r = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menu_atlas, activity, "menu/buttonExit.png");
		
		//Audio button
		m_audio_t = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(menu_atlas, activity, "menu/buttonAudio.png", 1, 2);
		m_multi_t = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(menu_atlas, activity, "menu/buttonMulti.png", 1, 2);
		
		//Menu background
		m_bg_r = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menu_atlas, activity, "menu/bg2.png");
		
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
		//Set up atlas
		game_atlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR);
		game_npc_atlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR);
		
		//Default level
		//g_l_def_r = BitmapTextureAtlasTextureRegionFactory.createFromAsset(game_atlas, activity, "game/level/deflevel.png");
		//g_lo_def_r = BitmapTextureAtlasTextureRegionFactory.createFromAsset(game_atlas, activity, "game/level/defoverlay.png");
		
		//Blank overlay
		g_lo_blnk_r = BitmapTextureAtlasTextureRegionFactory.createFromAsset(game_atlas, activity, "game/level/blankoverlay.png");
		
		//City level
		g_l_city_r = BitmapTextureAtlasTextureRegionFactory.createFromAsset(game_atlas, activity, "game/level/city.jpg");
		g_lo_curtains_r = BitmapTextureAtlasTextureRegionFactory.createFromAsset(game_atlas, activity, "game/level/curtains.png");
		
		//Tent level
		g_l_tent_r = BitmapTextureAtlasTextureRegionFactory.createFromAsset(game_atlas, activity, "game/level/tent.jpg");
		
		//Scope texture
		g_scope_r = BitmapTextureAtlasTextureRegionFactory.createFromAsset(game_atlas, activity, "game/rifle/scope.png");
		
		//NPCs
		g_civ_a_t = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(game_npc_atlas, activity, "game/npc/a.png", 4, 3);
		g_civ_b_t = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(game_npc_atlas, activity, "game/npc/b.png", 4, 3);
		g_civ_c_t = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(game_npc_atlas, activity, "game/npc/c.png", 4, 3);
		g_civ_d_t = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(game_npc_atlas, activity, "game/npc/d.png", 4, 3);
		g_cop_t = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(game_npc_atlas, activity, "game/police/police.png", 3, 3);
		
		g_civ_r = BitmapTextureAtlasTextureRegionFactory.createFromAsset(game_npc_atlas, activity, "game/npc.png");
		g_cop_r = BitmapTextureAtlasTextureRegionFactory.createFromAsset(game_npc_atlas, activity, "game/police.png");
		
		//HUD
		g_h_ammo_t = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(game_atlas, activity, "game/hud/ammo.png", 1, 8);
		g_h_reload_r = BitmapTextureAtlasTextureRegionFactory.createFromAsset(game_atlas, activity, "game/hud/reload.png");
		g_h_timer_r = BitmapTextureAtlasTextureRegionFactory.createFromAsset(game_atlas, activity, "game/hud/timer.png");
		g_h_timercop_r = BitmapTextureAtlasTextureRegionFactory.createFromAsset(game_atlas, activity, "game/hud/timer2.png");
		
		
		//Load game texture atlas
    	try 
		{
    		game_atlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
    		game_atlas.load();
    		game_npc_atlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
    		game_npc_atlas.load();
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
    	
    	//Load game sounds
    	try
    	{
    		g_shot = SoundFactory.createSoundFromAsset(engine.getSoundManager(), activity, "shotloadbrass.ogg");
    		g_cell = SoundFactory.createSoundFromAsset(engine.getSoundManager(), activity, "cell.ogg");
    		g_empty = SoundFactory.createSoundFromAsset(engine.getSoundManager(), activity, "empty.ogg");
    	}
    	catch (IOException e)
    	{
    		e.printStackTrace();
    	}
    	
	}
	
	public void unloadGameResources(){
		game_atlas.unload();
		game_atlas = null;
		game_npc_atlas.unload();
		game_npc_atlas = null;
		
		g_shot = null;
		g_cell = null;
		g_empty = null;
	}
	
	public void loadFonts(){
		
	}
	
}
