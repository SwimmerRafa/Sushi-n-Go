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

import mx.itesm.sushingo.States.LoadingScreen;
import mx.itesm.sushingo.SushinGo;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class StoryGame1 extends ScreenAdapter {
    private final SushinGo game;
    private static final float WORLD_WIDTH = 1280;
    private static final float WORLD_HEIGHT = 720;
    private Stage stage;
    private Texture story1;
    private Texture story2;
    private Texture story3;
    private Texture story4;
    private Texture story5;
    private Music music;
    private Texture menuTexture;
    private Texture menuPressed;
    private Table table;

    public StoryGame1(SushinGo game){
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        stage = new Stage(new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        story1 = new Texture(Gdx.files.internal("Intermedio/1-1.png"));
        story2 = new Texture(Gdx.files.internal("Intermedio/1-2.png"));
        story3 = new Texture(Gdx.files.internal("Intermedio/1-3.png"));
        story4 = new Texture(Gdx.files.internal("Intermedio/1-4.png"));
        story5 = new Texture(Gdx.files.internal("Intermedio/1-5.png"));

        menuTexture = new Texture(Gdx.files.internal("Botones/skip.png"));
        menuPressed = new Texture(Gdx.files.internal("Botones/skiplu.png"));
        ImageButton menu = new ImageButton(new TextureRegionDrawable(new TextureRegion(menuTexture)), new TextureRegionDrawable(new TextureRegion(menuPressed)));
        menu.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new LoadingScreen(game));
                dispose();
            }
        });

        Image image1 = new Image(story1);
        Image image2 = new Image(story2);
        Image image3 = new Image(story3);
        Image image4 = new Image(story4);
        Image image5 = new Image(story5);


        stage.addActor(image5);
        image5.addAction(sequence(delay(18),fadeIn(3), fadeOut(3), run(new Runnable() {
            @Override
            public void run() {
                game.setScreen(new LoadingScreen(game));
                dispose();
            }
        })));

        stage.addActor(image4);
        image4.addAction(sequence(delay(14),fadeIn(2), fadeOut(2)));
        stage.addActor(image3);
        image3.addAction(sequence(delay(10),fadeIn(2), fadeOut(2)));
        stage.addActor(image2);
        image2.addAction(sequence(delay(6),fadeIn(2), fadeOut(2)));
        stage.addActor(image1);
        image1.addAction(sequence(delay(2),fadeIn(2), fadeOut(2)));

        table = new Table();

        table.row();
        table.setFillParent(true);
        table.add(menu).expandX().right().padBottom(20);
        table.padBottom(400f);

        stage.addActor(table);

        music = Gdx.audio.newMusic(Gdx.files.internal("Audio/Mo_Shio.mp3"));
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