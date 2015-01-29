package ie.itcarlow.snipersim.scene;

import ie.itcarlow.snipersim.GameActivity;
import ie.itcarlow.snipersim.ResourceManager;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.ZoomCamera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public abstract class BaseScene extends Scene {

protected Engine engine;
protected VertexBufferObjectManager vbom;
protected GameActivity activity;
protected ZoomCamera camera;

public BaseScene() {
	this.engine = ResourceManager.getInstance().engine;
	this.vbom = ResourceManager.getInstance().vbom;
	this.activity = ResourceManager.getInstance().activity;
	this.camera = ResourceManager.getInstance().camera;
	}
	
	public abstract void createScene();
	
	public abstract void onBackPressed();
	
	public abstract void disposeScene();
	
	public abstract void onUpdate();
}