package mx.itesm.sushingo.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.Random;

import mx.itesm.sushingo.Pantallas.GameOver;
import mx.itesm.sushingo.Pantallas.MainMenu;
import mx.itesm.sushingo.Pantallas.WinScreen;
import mx.itesm.sushingo.Sprites.BackGround;
import mx.itesm.sushingo.Sprites.Items;
import mx.itesm.sushingo.Sprites.Sam;
import mx.itesm.sushingo.SushinGo;

public class Infinity1 extends ScreenAdapter {
    private static final float WORLD_WIDTH = 1280;
    private static final float WORLD_HEIGHT = 720;
    private Viewport viewport;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private Music music;
    private SushinGo game;
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
    private Texture samLabel;
    private Texture scoreL;
    private Sound hitSound;
    private Sound powerSound;
    private final AssetManager assetManager = new AssetManager();
    private TextureRegionDrawable playTRDrawable;
    private TextureRegionDrawable pauseTRDrawable;
    private ImageButton pauseButton, musicButton;
    private OrthographicCamera cameraHUD;
    private Viewport viewportHUD;
    private Stage stageUI;

    private OrthographicCamera cameraPause;
    private Viewport viewportPause;
    private Stage stagePause;
    private Texture reanu, reanuPress, menuPress, menu, reini, reiniPress;
    private Table table2;
    private enum STATE {
        PLAYING, PAUSED
    }
    private STATE state = STATE.PLAYING;

    public Infinity1(SushinGo game) {
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

        final InputMultiplexer multiplexer = new InputMultiplexer();


        Array<Texture> textures = new Array<Texture>();
        for (int i = 1; i <= 3; i++) {
            textures.add(new Texture(Gdx.files.internal("Ejemplo/fondo" + i + ".png")));
            textures.get(textures.size - 1).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        }

        BackGround parallaxBackground = new BackGround(textures, WORLD_WIDTH, WORLD_HEIGHT);
        parallaxBackground.setSize(WORLD_WIDTH, WORLD_HEIGHT);
        parallaxBackground.setSpeed(0);
        stage.addActor(parallaxBackground);

        if (state == STATE.PLAYING){
            parallaxBackground.setSpeed(2);
        }

        music = Gdx.audio.newMusic(Gdx.files.internal("Audio/Mo_Shio.mp3"));
        music.setLooping(true);
        music.setVolume(.3f);
        music.play();

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        BitmapFont bitmapFont1 = new BitmapFont();
        bitmapFont1.getData().setScale(1.8f);
        livesLabel = new Label(String.format("%03d", lives), new Label.LabelStyle(bitmapFont1, Color.BLACK));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(bitmapFont1, Color.BLACK));
        scoreL = new Texture(Gdx.files.internal("puntaje.png"));
        samLabel = new Texture(Gdx.files.internal("vidas.png"));

        Image imageScore = new Image(scoreL);
        Image imageLives = new Image(samLabel);

        cameraHUD = new OrthographicCamera();
        viewportHUD = new FitViewport(WORLD_WIDTH,WORLD_HEIGHT,cameraHUD);
        cameraHUD.update();
        stageUI = new Stage(viewportHUD);
        playTRDrawable = new TextureRegionDrawable(new TextureRegion((Texture) game.getAssetManager().get("play.png")));
        pauseTRDrawable = new TextureRegionDrawable(new TextureRegion((Texture) game.getAssetManager().get("pause.png")));
        pauseButton = new ImageButton(pauseTRDrawable,pauseTRDrawable);
        pauseButton.setPosition(WORLD_WIDTH - pauseButton.getWidth()*1.2f, WORLD_HEIGHT - pauseButton.getHeight()*1.2f);

        musicButton  = new ImageButton(pauseTRDrawable,pauseTRDrawable);

        //Pause
        cameraPause = new OrthographicCamera();
        viewportPause = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, cameraPause);
        cameraPause.update();
        stagePause = new Stage(viewportPause);
        reini = new Texture(Gdx.files.internal("Botones/reiniciarsush.png"));
        menu = new Texture(Gdx.files.internal("Botones/menusushi.png"));
        reanu = new Texture(Gdx.files.internal("Botones/reanudarsushi.png"));

        ImageButton reanudar = new ImageButton(new TextureRegionDrawable(new TextureRegion(reanu)));
        reanudar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(state==STATE.PLAYING){
                    state = STATE.PAUSED;
                }else{
                    state = STATE.PLAYING;
                    Gdx.input.setInputProcessor(multiplexer);
                }
            }
        });

        ImageButton reiniciar = new ImageButton(new TextureRegionDrawable(new TextureRegion(reini)));
        reiniciar.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                music.stop();
                game.setScreen(new GameScreen(game));
            }
        });

        ImageButton mainMenu = new ImageButton(new TextureRegionDrawable(menu));
        mainMenu.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                music.stop();
                game.setScreen(new MainMenu(game));
                dispose();
            }
        });

        table2 = new Table();
        table2.row();
        table2.setFillParent(true);
        table2.add(reanudar).center().expandX().padTop(20f);
        table2.row();
        table2.add(reiniciar).center().expandX().padTop(20f);
        table2.row();
        table2.add(mainMenu).center().expandX().padTop(20f);
        table.padBottom(80f);
        stagePause.addActor(table2);

        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(state==STATE.PLAYING){
                    state = STATE.PAUSED;
                    Gdx.input.setInputProcessor(stagePause);
                }else{
                    state = STATE.PLAYING;
                }
            }
        });

        stageUI.addActor(pauseButton);

        table.add(imageLives).expandX().padTop(10f);
        table.add(imageScore).expandX().padTop(10f);
        table.row();
        table.add(livesLabel).expandX();
        table.add(scoreLabel);

        stageUI.addActor(table);

        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(stageUI);
        Gdx.input.setInputProcessor(multiplexer);
    }


    private void updateSam(float delta) {
        sam.update(delta);
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.justTouched()) {
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
        if(state == STATE.PLAYING) {
            super.render(delta);
            update(delta);
        }
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
        stageUI.draw();

        if(state == STATE.PAUSED){
            stagePause.draw();
        }
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
        viewportHUD.update(width, height);
        viewportPause.update(width,height);
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
        stageUI.dispose();
        stagePause.dispose();
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
                sam.setHit(true);
                hitSound.play();
                badItems.removeValue(obstacle, true);
                return true;
            }
        }
        return false;
    }

    public void addScore(int value) {
        score += value;
        scoreLabel.setText(String.format("%06d", score));

    }

    public void restLives(int value) {
        if(lives <=1){
            music.stop();
            game.setScreen(new GameOver(game));
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
                sam.setHit(true);
                powerSound.play();
                goodItems.removeValue(obstacle, true);
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
