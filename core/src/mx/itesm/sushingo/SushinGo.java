package mx.itesm.sushingo;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;


public class SushinGo extends Game {
	private final AssetManager assetManager = new AssetManager();

	public void create() {
		Gdx.input.setCatchBackKey(true);
		setScreen(new TransitionScreen(this));
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}


}
