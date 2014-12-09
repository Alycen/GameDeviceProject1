package ie.itcarlow.snipersim.scene;

import ie.itcarlow.snipersim.ResourceManager;

import org.andengine.engine.Engine;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

public class SceneManager {

	//Scene vars
	 private MainMenuScene menuScene;
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
	 
	 public void setMenuScene() {
		 
		 //Load Menu Resources
    	ResourceManager.getInstance().loadMenuResources();
		 
		 //Create the MainMenuScene
		 menuScene = new MainMenuScene();
		 menuScene.createScene();
		 
		 //Set the menuScene as the current scene
		 setScene(menuScene);
	 }
	 
	 public void setGameScene() {
		 //Load Game resources
		 ResourceManager.getInstance().loadGameResources();
		 
		 //Create the GameScene
		 gameScene = new GameScene();
		 gameScene.createScene();
		 
		 //Set the gameScene as the current scene
		 setScene(gameScene);
		 
	 }
	 
	 public void setScene(BaseScene scene) {
		 //If we're in a scene, dispose of it
		 if (currentScene != null)
			 currentScene.disposeScene();
		 
		 //set the new scene
		engine.setScene(scene);
		currentScene = scene;
	 }
	 
	 //Scene switcher
	 public void changeScene(SceneType type)
	 {
		switch(type) {
			case SCENE_MENU:
				setMenuScene();
			case SCENE_GAME:
				setGameScene();
			default:
				setMenuScene();
		}
	 }
	 
	 public BaseScene getCurrentScene(){
		 return currentScene;
	 }
}