package mx.itesm.sushingo.States;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.Random;
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
    private static final float GAP_BETWEEN_OBSTACLES = 40f;
    private float[] PADS = {0, 97, 194, 291, 388, 485, 582, 679};
    private Array<Items> badItems = new Array<Items>();
    private Array<Items> goodItems = new Array<Items>();
    private Texture arroz, salsa, naruto, cuchillo;
    private static Integer lives;
    private static Integer score;
    private Label livesLabel;
    private static Label scoreLabel;
    private Label samLabel;
    private Label scoreL;
    private Sound hitSound;
    private Sound powerSound;

    public GameScreen(Game game) {
        lives = 3;
        score = 0;
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

        sam = new Sam();
        sam.setPosition(WORLD_WIDTH / 4, WORLD_HEIGHT / 2);

        arroz = new Texture(Gdx.files.internal("Items/arrozc.png"));
        naruto = new Texture(Gdx.files.internal("Items/naruto.png"));
        cuchillo = new Texture(Gdx.files.internal("Items/cuchillo.png"));
        salsa = new Texture(Gdx.files.internal("Items/salsa.png"));

        powerSound = Gdx.audio.newSound(Gdx.files.internal("Audio/power.mp3"));
        hitSound = Gdx.audio.newSound(Gdx.files.internal("Audio/Hit.mp3"));



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

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        livesLabel = new Label(String.format("%03d", lives), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        scoreL = new Label("SCORE", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        samLabel = new Label("LIVES", new Label.LabelStyle(new BitmapFont(), Color.BLACK));


        table.add(samLabel).expandX().padTop(10f);
        table.add(scoreL).expandX().padTop(10f);
        table.row();
        table.add(livesLabel).expandX();
        table.add(scoreLabel);

        stage.addActor(table);
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
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        batch.begin();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        sam.draw(batch);
        drawBadItems();
        drawGoodItems();
        batch.end();
        shapeRenderer.end();
    }

    private void drawBadItems() {

        for (Items badItem : badItems) {
            badItem.draw(batch);
        }
    }

    private void drawGoodItems(){
        for (Items goodItem : goodItems){
            goodItem.draw(batch);
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
        sam.dispose();
        arroz.dispose();
        salsa.dispose();
        cuchillo.dispose();
        naruto.dispose();
        batch.dispose();
        powerSound.dispose();
        hitSound.dispose();
    }

    public void update(float delta) {
        updateSam(delta);
        updateBadItems(delta);
        updateGoodItems(delta);

        if (checkForBadCollision()) {
            hitSound.play(6);
            restLives(1);
        }
        if (checkForGoodCollision()) {
            powerSound.play(6);
            addScore(10);
        }
    }

    private void restart() {
        sam.setPosition(WORLD_WIDTH / 4, WORLD_HEIGHT / 2);
        badItems.clear();
        goodItems.clear();
        lives = 3;
        score=0;
        scoreLabel.setText(String.format("%06d", score));
        livesLabel.setText(String.format("%03d", lives));
    }

    private void updateBadItems(float delta) {
        for (Items item : badItems) {
            item.update(delta);
        }
        checkIfNewBadItemNeeded();
        removeBadItemsIfPassed();
    }

    private void createNewBadItem() {
        Random rnd = new Random();
        int RandomPad = rnd.nextInt(8);
        int RandomTexture = rnd.nextInt(3);
        Items newObstacle;
        if (RandomTexture == 1) {
            newObstacle = new Items(salsa);
            float y = PADS[RandomPad];
            newObstacle.setPosition(WORLD_WIDTH + Items.WIDTH, y + newObstacle.WIDTH / 2);
            badItems.add(newObstacle);

        } else if (RandomTexture == 2) {
            newObstacle = new Items(cuchillo);
            float y = PADS[RandomPad];
            newObstacle.setPosition(WORLD_WIDTH + Items.WIDTH, y + newObstacle.WIDTH / 2);
            badItems.add(newObstacle);
        }
    }

    private void checkIfNewBadItemNeeded() {
        if (badItems.size == 0) {
            createNewBadItem();
        } else {
            Items item = badItems.peek();
            if (item.getX() < WORLD_WIDTH - GAP_BETWEEN_OBSTACLES) {
                createNewBadItem();
            }
        }
    }

    private void removeBadItemsIfPassed() {
        if (badItems.size > 0) {
            Items firstObstacle = badItems.first();
            if (firstObstacle.getX() < -Items.WIDTH) {
                badItems.removeValue(firstObstacle, true);
            }
        }
    }

    private boolean checkForBadCollision() {
        for (Items obstacle : badItems) {
            if (obstacle.isSamColliding(sam) && (!sam.isHit())) {
                return true;
            }
        }
        return false;
    }

    public static void addScore(int value) {
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }

    public void restLives(int value) {
        if(lives <= 0){
            restart();
        }
        else {
            lives -= value;
            livesLabel.setText(String.format("%03d", lives));
        }
    }

    private void updateGoodItems(float delta) {
        for (Items item : goodItems) {
            item.update(delta);
        }
        checkIfNewGoodItemNeeded();
        removeGoodItemsIfPassed();
    }

    private void createNewGoodItem() {
        Random rnd = new Random();
        int RandomPad = rnd.nextInt(8);
        int RandomTexture = rnd.nextInt(3);
        Items newObstacle;

        if (RandomTexture == 1) {
            newObstacle = new Items(arroz);
            float y = PADS[RandomPad];
            newObstacle.setPosition(WORLD_WIDTH + Items.WIDTH, y + newObstacle.WIDTH / 2);
            goodItems.add(newObstacle);

        } else if (RandomTexture == 2) {
            newObstacle = new Items(naruto);
            float y = PADS[RandomPad];
            newObstacle.setPosition(WORLD_WIDTH + Items.WIDTH, y + newObstacle.WIDTH / 2);
            goodItems.add(newObstacle);
        }
    }

    private void checkIfNewGoodItemNeeded() {
        if (goodItems.size == 0) {
            createNewGoodItem();
        } else {
            Items item = goodItems.peek();
            if (item.getX() < WORLD_WIDTH - GAP_BETWEEN_OBSTACLES) {
                createNewGoodItem();
            }
        }
    }

    private void removeGoodItemsIfPassed() {
        if (goodItems.size > 0) {
            Items firstObstacle = goodItems.first();
            if (firstObstacle.getX() < -Items.WIDTH) {
                goodItems.removeValue(firstObstacle, true);
            }
        }
    }

    private boolean checkForGoodCollision() {
        for (Items obstacle : goodItems) {
            if (obstacle.isSamColliding(sam) && (!sam.isHit())) {
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

