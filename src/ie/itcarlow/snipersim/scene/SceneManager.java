package ie.itcarlow.snipersim.scene;

import ie.itcarlow.snipersim.ResourceManager;

import org.andengine.engine.Engine;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

public class SceneManager {

	//Scene vars
	 private BaseScene menuScene;
	 private BaseScene gameScene;
	 
	 private BaseScene currentScene;
	 
	 private Engine engine = ResourceManager.getInstance().engine;
	 
	 //Scene enum
	 public enum SceneType {
		SCENE_MENU,
		SCENE_GAME,
	 }
	 
	 private static final SceneManager INSTANCE = new SceneManager();
	 
	 public static SceneManager getInstance() {
		return INSTANCE;
	 }
	 
	 public void setMenuScene(OnCreateSceneCallback cb) {
		 ResourceManager.getInstance().loadMenuResources();
		 menuScene = new MainMenuScene();
		 setScene(menuScene);
		 currentScene.createScene();
		 cb.onCreateSceneFinished(menuScene);
	 }
	 
	 public void setScene(BaseScene scene) {
		engine.setScene(scene);
		currentScene = scene;
	 }
	 
	 //Scene switcher
	 public void setScene(SceneType type)
	 {
		switch(type) {
			case SCENE_MENU:
				setScene(menuScene);
			case SCENE_GAME:
				setScene(gameScene);
			default:
				setScene(gameScene);
		}
	 }
	 
	 public BaseScene getCurrentScene(){
		 return currentScene;
	 }
}