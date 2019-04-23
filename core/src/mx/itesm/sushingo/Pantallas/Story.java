package mx.itesm.sushingo.Pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import mx.itesm.sushingo.SushinGo;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class Story extends ScreenAdapter {
    private final SushinGo game;
    private static final float WORLD_WIDTH = 1280;
    private static final float WORLD_HEIGHT = 720;
    private Stage stage;
    private Texture story1;
    private Texture story2;
    private Texture story3;
    private Texture story4;
    private Texture story5;
    private Texture story6;
    private Texture story7;
    private Music music;
    private Texture menuTexture;
    private Texture menuPressed;
    private Table table;

    public Story(SushinGo game){
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        stage = new Stage(new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        story1 = new Texture(Gdx.files.internal("Story/1a.png"));
        story2 = new Texture(Gdx.files.internal("Story/2a.png"));
        story3 = new Texture(Gdx.files.internal("Story/3a.png"));
        story4 = new Texture(Gdx.files.internal("Story/4a.png"));
        story5 = new Texture(Gdx.files.internal("Story/5a.png"));
        story6 = new Texture(Gdx.files.internal("Story/6a.png"));
        story7 = new Texture(Gdx.files.internal("Story/7a.png"));

        menuTexture = new Texture(Gdx.files.internal("Botones/menuho.png"));
        menuPressed = new Texture(Gdx.files.internal("Botones/mmenuhorlu.png"));
        ImageButton menu = new ImageButton(new TextureRegionDrawable(new TextureRegion(menuTexture)), new TextureRegionDrawable(new TextureRegion(menuPressed)));
        menu.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new MainMenu(game));
                dispose();
            }
        });

        Image image1 = new Image(story1);
        Image image2 = new Image(story2);
        Image image3 = new Image(story3);
        Image image4 = new Image(story4);
        Image image5 = new Image(story5);
        Image image6 = new Image(story6);
        Image image7 = new Image(story7);

        stage.addActor(image7);

        image7.addAction(sequence(delay(24),fadeIn(2), fadeOut(0.19f), run(new Runnable() {
            @Override
            public void run() {
                game.setScreen(new MainMenu(game));
                dispose();
            }
        })));

        stage.addActor(image6);
        image6.addAction(sequence(delay(20),fadeIn(2), fadeOut(2)));
        stage.addActor(image5);
        image5.addAction(sequence(delay(16),fadeIn(2), fadeOut(2)));
        stage.addActor(image4);
        image4.addAction(sequence(delay(12),fadeIn(2), fadeOut(2)));
        stage.addActor(image3);
        image3.addAction(sequence(delay(8),fadeIn(2), fadeOut(2)));
        stage.addActor(image2);
        image2.addAction(sequence(delay(4),fadeIn(2), fadeOut(2)));
        stage.addActor(image1);
        image1.addAction(sequence(fadeIn(2), fadeOut(2)));

        table = new Table();

        table.row();
        table.setFillParent(true);
        table.add(menu).right().expandX();
        table.padBottom(600f);

        stage.addActor(table);

        music = Gdx.audio.newMusic(Gdx.files.internal("Audio/Story.mp3"));
        music.setLooping(true);
        music.setVolume(.3f);
        music.play();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        clearScreen();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
        music.dispose();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b, Color.WHITE.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}