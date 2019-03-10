package mx.itesm.sushingo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class ScenariosScreen extends ScreenAdapter {
    private final Game game;
    private static final float WORLD_WIDTH = 1280;
    private static final float WORLD_HEIGHT = 720;

    private Stage stage;

    private Texture backgroundTexture;

    private Texture scenario1Texture;
    private Texture scenario1Pressed;
    private Texture scenario2Texture;
    private Texture scenario2Pressed;
    private Texture scenario3Texture;
    private Texture scenario3Pressed;
    private Texture menuTexture;
    private Texture menuPressed;

    public ScenariosScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture(Gdx.files.internal(""));
        Image background = new Image(backgroundTexture);
        stage.addActor(background);

        scenario1Texture = new Texture(Gdx.files.internal(""));
        scenario1Pressed = new Texture(Gdx.files.internal(""));
        ImageButton scenario1 = new ImageButton(new TextureRegionDrawable(new TextureRegion(scenario1Texture)), new TextureRegionDrawable(new TextureRegion(scenario1Pressed)));
        scenario1.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new StoryScreen(game));
                dispose();
            }
        });

        scenario2Texture = new Texture(Gdx.files.internal(""));
        scenario2Pressed = new Texture(Gdx.files.internal(""));
        ImageButton scenario2 = new ImageButton(new TextureRegionDrawable(new TextureRegion(scenario2Texture)), new TextureRegionDrawable(new TextureRegion(scenario2Pressed)));
        scenario2.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new StoryScreen(game));
                dispose();
            }
        });

        scenario3Texture= new Texture(Gdx.files.internal(""));
        scenario3Pressed = new Texture(Gdx.files.internal(""));
        ImageButton scenario3 = new ImageButton(new TextureRegionDrawable(new TextureRegion(scenario3Texture)), new TextureRegionDrawable(new TextureRegion(scenario3Pressed)));
        scenario3.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new StoryScreen(game));
                dispose();
            }
        });

        menuTexture = new Texture(Gdx.files.internal(""));
        menuPressed = new Texture(Gdx.files.internal(""));
        ImageButton menu = new ImageButton(new TextureRegionDrawable(new TextureRegion(menuTexture)), new TextureRegionDrawable(new TextureRegion(menuPressed)));
        menu.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new MainMenu(game));
                dispose();
            }
        });

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

    public void dispose() {
        super.dispose();
        stage.dispose();
        backgroundTexture.dispose();
        scenario1Texture.dispose();
        scenario1Pressed.dispose();
        scenario2Texture.dispose();
        scenario2Pressed.dispose();
        scenario3Texture.dispose();
        scenario3Pressed.dispose();
        menuTexture.dispose();
        menuPressed.dispose();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

}
