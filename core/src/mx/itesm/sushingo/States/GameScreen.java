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

import java.util.Random;

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

    private Music music;

    private Stage stage;
    private OrthographicCamera camera;

    private Sam sam;
    private Hud hud;

    private static final float GAP_BETWEEN_OBSTACLES = 40f;
    private float[] PADS = {0,97,194,291,388, 485, 582, 679};
    private Array<Items> items = new Array<Items>();
    private int lifes = 3;
    private Texture arroz, dango, naruto, chilew;


    public GameScreen(Game game) {
        this.game = game;
    }


    @Override
    public void show() {
        super.show();
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, camera.position.z);
        camera.update();

        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT));

        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        hud = new Hud(batch);

        sam = new Sam();
        sam.setPosition(WORLD_WIDTH / 4, WORLD_HEIGHT / 2);

        arroz = new Texture(Gdx.files.internal("Items/arrozc.png"));
        naruto = new Texture(Gdx.files.internal("Items/naruto.png"));
        chilew = new Texture(Gdx.files.internal("Items/chile2.png"));
        dango = new Texture(Gdx.files.internal("Items/dango.png"));


        Array<Texture> textures = new Array<Texture>();
        for (int i = 1; i <= 3; i++) {
            textures.add(new Texture(Gdx.files.internal("Ejemplo/fondo" + i + ".png")));
            textures.get(textures.size - 1).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        }

        BackGround parallaxBackground = new BackGround(textures, WORLD_WIDTH, WORLD_HEIGHT);
        parallaxBackground.setSize(WORLD_WIDTH, WORLD_HEIGHT);
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
        //Sam toca el piso
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
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        sam.draw(batch);
        drawItems();
        batch.end();
        shapeRenderer.end();
        batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    private void drawItems() {

        for (Items item : items){
            item.draw(batch);
        }
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
        arroz.dispose();
        chilew.dispose();
        dango.dispose();
        naruto.dispose();
        batch.dispose();
    }

    public Hud getHud() {
        return hud;
    }

    public void update(float delta) {
        updateSam(delta);
        updateItems(delta);
        if (checkForCollision()){ restLife();}
    }

    private void restLife() {
        if (lifes<=0)restart();
        else {lifes--;
        sam.setHit(true);
        }
    }

    private void restart() {
        sam.setPosition(WORLD_WIDTH / 4, WORLD_HEIGHT / 2);
        items.clear();
        lifes = 3;
    }

    private void updateItems(float delta) {
        for (Items item : items){
            item.update(delta);
        }
        checkIfNewItemNeeded();
        removeItemsIfPassed();
    }

    private void createNewObstacle(){
        Random rnd = new Random();
        int RandomPad = rnd.nextInt(8);
        int RandomTexture = rnd.nextInt(4);
        Items newObstacle;

        if(RandomTexture ==1){
            newObstacle = new Items(arroz);
            float y = PADS[RandomPad];
            newObstacle.setPosition(WORLD_WIDTH + Items.WIDTH,  y + newObstacle.WIDTH/2);
            items.add(newObstacle);
        }
        else if(RandomTexture == 2){
            newObstacle = new Items(chilew);
            float y = PADS[RandomPad];
            newObstacle.setPosition(WORLD_WIDTH + Items.WIDTH,  y + newObstacle.WIDTH/2);
            items.add(newObstacle);
        }

        else if(RandomTexture == 3){
            newObstacle = new Items(dango);
            float y = PADS[RandomPad];
            newObstacle.setPosition(WORLD_WIDTH + Items.WIDTH,  y + newObstacle.WIDTH/2);
            items.add(newObstacle);
        }
        else if(RandomTexture == 4){
            newObstacle = new Items(naruto);
            float y = PADS[RandomPad];
            newObstacle.setPosition(WORLD_WIDTH + Items.WIDTH,  y + newObstacle.WIDTH/2);
            items.add(newObstacle);
        }

    }

    private void checkIfNewItemNeeded() {
        if(items.size==0){
            createNewObstacle();
        }else{
            Items item = items.peek();
            if(item.getX()<WORLD_WIDTH-GAP_BETWEEN_OBSTACLES){
                createNewObstacle();
            }
        }
    }

    private void removeItemsIfPassed() {
        if(items.size > 0){
            Items firstObstacle = items.first();
            if(firstObstacle.getX() < -Items.WIDTH){
                items.removeValue(firstObstacle, true);
            }
        }
    }

    private  boolean checkForCollision(){
        for (Items obstacle : items){
            if (obstacle.isSamColliding(sam)&& (!sam.isHit())){
                return true;
            }
        }
        return false;
    }

    private void clearScreen () {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }


}

