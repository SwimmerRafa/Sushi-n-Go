package mx.itesm.sushingo;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;


public class SushinGo extends Game {
	private final AssetManager assetManager = new AssetManager();

	public void create() {setScreen(new TransitionScreen(this));}
	public AssetManager getAssetManager() {
		return assetManager;
	}

}
