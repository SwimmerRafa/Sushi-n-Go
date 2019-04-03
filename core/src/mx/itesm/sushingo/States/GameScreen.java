package mx.itesm.sushingo.States;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import mx.itesm.sushingo.Hud;
import mx.itesm.sushingo.Sprites.BackGround;
import mx.itesm.sushingo.Sprites.Items;
import mx.itesm.sushingo.Sprites.Sam;

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
    private Hud hud;
    private Array<Items> items = new Array<Items>();

    public GameScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        camera.position.set(SCENE_WIDTH / 2, SCENE_HEIGHT / 2, camera.position.z);
        camera.update();

        stage = new Stage(new FitViewport(SCENE_WIDTH, SCENE_HEIGHT));

        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        hud = new Hud(batch);

        sam = new Sam();
        sam.setPosition(WORLD_WIDTH / 4, WORLD_HEIGHT / 2);


        Array<Texture> textures = new Array<Texture>();
        for (int i = 1; i <= 3; i++) {
            textures.add(new Texture(Gdx.files.internal("Ejemplo/fondo" + i + ".png")));
            textures.get(textures.size - 1).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        }

        BackGround parallaxBackground = new BackGround(textures, SCENE_WIDTH, SCENE_HEIGHT);
        parallaxBackground.setSize(SCENE_WIDTH, SCENE_HEIGHT);
        parallaxBackground.setSpeed(2);
        stage.addActor(parallaxBackground);

        Gdx.input.setInputProcessor(stage);

        music = Gdx.audio.newMusic(Gdx.files.internal("Audio/Mo_Shio.mp3"));
        music.setLooping(true);
        music.setVolume(.3f);
        music.play();
    }

    private void updateSam(float delta) {
        sam.update(delta);
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isTouched()) {
            sam.jump();
        }
        blockSamLeavingTheWorld();
    }

    private void blockSamLeavingTheWorld() {
        if (sam.getY() < 0) {
            sam.setPosition(sam.getX(), 0);
        }
        if (sam.getY() > WORLD_HEIGHT) {
            sam.setPosition(sam.getX(), WORLD_HEIGHT);
        }
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        clearScreen();
        draw();
    }

    private void draw() {
        stage.act();
        stage.draw();


        hud = new Hud(batch);

        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);

        batch.begin();
        drawItems();
        sam.draw(batch);
        batch.end();

        batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
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
        hud.dispose();
        sam.dispose();
    }

    public Hud getHud() {
        return hud;
    }

    public void update(float delta) {
        updateSam(delta);
        updateItems(delta);
        if (checkForCollision()) {
            restart();
        }
    }

    private void restart() {
        sam.setPosition(WORLD_WIDTH / 4, WORLD_HEIGHT / 2);
        items.clear();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private boolean checkForCollision() {
        for (Items items : items) {
            if (items.isSamColliding(sam)) {
                return true;
            }
        }
        return false;
    }

    private void updateItems(float delta) {
        for (Items item : items) {
            item.update(delta);
        }
        checkIfNewItemIsNeeded();
        removeItemsIfPassed();
    }

    private void drawItems() {
        for (Items item : items) {
            item.draw(batch);
        }
    }

    private void checkIfNewItemIsNeeded() {
        if (items.size == 0) {
            createNewItem();
        } else {
            Items flower = items.peek();
            if (flower.getX() < Items.WIDTH ){
                createNewItem();
            }
        }
    }

    private void createNewItem() {
        Items newItems = new Items();
        newItems.setPosition(WORLD_WIDTH + Items.WIDTH);
        items.add(newItems);
    }

    private void removeItemsIfPassed() {
        if (items.size > 0) {
            Items firstItem = items.first();
            if (firstItem.getX() < -WORLD_WIDTH) {
                items.removeValue(firstItem, true);
            }
        }
    }
}
