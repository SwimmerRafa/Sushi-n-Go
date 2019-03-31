package mx.itesm.sushingo.States;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.itesm.sushingo.Sprites.BackGround;

public class GameScreen extends ScreenAdapter {
    private final Game game;
    private static final float WORLD_WIDTH = 1280;
    private static final float WORLD_HEIGHT = 720;
    private Viewport viewport;

    //Personajes
    private Texture cam;
    private Texture paul;
    private Texture sam;

    //Nivel
    private int level;

    //Musica
    private Music music;

    private Stage stage;
    private OrthographicCamera camera;

    private static final float SCENE_WIDTH = 800;
    private static final float SCENE_HEIGHT = 600f;


    public GameScreen (Game game){
        this.game = game;
    }

    @Override
    public void show() {

        camera = new OrthographicCamera();
        viewport = new FitViewport(SCENE_WIDTH, SCENE_WIDTH, camera);

        camera.position.set(SCENE_WIDTH*0.5f, SCENE_HEIGHT*0.5f, camera.position.z);
        camera.update();

        stage = new Stage(new FitViewport(SCENE_WIDTH, SCENE_HEIGHT));


        Array<Texture> textures = new Array<Texture>();
        for(int i = 1; i <=6;i++){
            textures.add(new Texture(Gdx.files.internal("Ejemplo/img"+i+".png")));
            textures.get(textures.size-1).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        }

        BackGround parallaxBackground = new BackGround(textures, SCENE_WIDTH, SCENE_HEIGHT);
        parallaxBackground.setSize(SCENE_WIDTH,SCENE_HEIGHT);
        parallaxBackground.setSpeed(1);
        stage.addActor(parallaxBackground);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        stage.act();
        stage.draw();
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        stage.getViewport().update(width, height);
    }


    @Override
    public void dispose() {
        stage.dispose();
    }



}
