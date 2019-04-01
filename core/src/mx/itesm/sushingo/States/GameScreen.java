package mx.itesm.sushingo.States;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.itesm.sushingo.Sprites.BackGround;
import mx.itesm.sushingo.Sprites.Sam;
import mx.itesm.sushingo.SushinGo;
;

public class GameScreen extends ScreenAdapter {
    private static final float WORLD_WIDTH = 1280;
    private static final float WORLD_HEIGHT = 720;
    private Game game;
    private Viewport viewport;

    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;

    //Musica
    private Music music;

    private Stage stage;
    private OrthographicCamera camera;

    private static final float SCENE_WIDTH = 1280;
    private static final float SCENE_HEIGHT = 720;

    private Sam sam;


    public GameScreen (Game game){ this.game = game; }

    @Override
    public void show() {
        super.show();
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        camera.position.set(SCENE_WIDTH, SCENE_HEIGHT, camera.position.z);
        camera.update();

        stage = new Stage(new FitViewport(SCENE_WIDTH, SCENE_HEIGHT));

        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();


        sam = new Sam(50,100);

        Array<Texture> textures = new Array<Texture>();
        for(int i = 1; i <=3;i++){
            textures.add(new Texture(Gdx.files.internal("Ejemplo/fondo"+i+".png")));
            textures.get(textures.size-1).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        }

        BackGround parallaxBackground = new BackGround(textures, SCENE_WIDTH, SCENE_HEIGHT);
        parallaxBackground.setSize(SCENE_WIDTH,SCENE_HEIGHT);
        parallaxBackground.setSpeed(1);
        stage.addActor(parallaxBackground);
        stage.addActor(sam);

        Gdx.input.setInputProcessor(stage);

        music = Gdx.audio.newMusic(Gdx.files.internal("Audio/Mo_Shio.mp3"));
        music.setLooping(true);
        music.setVolume(.3f);
        music.play();

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        clearScreen();
        draw();
    }

    private void draw() {
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        batch.begin();
        batch.draw(sam.getTexture(),sam.getPosition().x, sam.getPosition().y);
        stage.act();
        stage.draw();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height);
        stage.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
        music.dispose();
    }


    private void updateSam (float delta){
        sam.update(delta);
    }

    public void update (float delta){
        updateSam(delta);
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

}
