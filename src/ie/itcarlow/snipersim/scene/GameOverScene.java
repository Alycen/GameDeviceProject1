package ie.itcarlow.snipersim.scene;

import ie.itcarlow.snipersim.ResourceManager;

public class GameOverScene extends BaseScene{
	
	@Override
	public void createScene() {
	}

	@Override
	public void onBackPressed() {
		SceneManager.getInstance().setMenuScene();
	}

	@Override
	public void disposeScene() {
		
	}
	
	@Override
	public void onUpdate() {
		
	}

}
