package ie.itcarlow.snipersim.scene;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.background.Background;
import org.andengine.util.color.Color;

public class GameScene extends BaseScene{

	private HUD gameHUD;
	
	@Override
	public void createScene() {
		setBackground();
		createHUD();
		
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		
	}
	
	private void setBackground() {
		setBackground(new Background(Color.RED));
	}
	
	private void createHUD() {
		gameHUD = new HUD();
	}

}
