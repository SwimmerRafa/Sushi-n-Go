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

public class CharactersScreen extends ScreenAdapter {
    private final Game game;
    private static final float WORLD_WIDTH = 1280;
    private static final float WORLD_HEIGHT = 720;

    private Stage stage;

    private Texture backgroundTexture;

    private Texture fishTexture;
    private Texture fishPressed;
    private Texture octopusTexture;
    private Texture octopusPressed;
    private Texture shrimpTexture;
    private Texture shrimpPressed;

    private Texture menuTexture;
    private Texture menuPressed;

    public CharactersScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show(){
        super.show();
        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        backgroundTexture =new Texture(Gdx.files.internal(""));
        Image background = new Image(backgroundTexture);
        stage.addActor(background);

        fishTexture = new Texture(Gdx.files.internal(""));
        fishPressed = new Texture(Gdx.files.internal(""));
        ImageButton fish = new ImageButton(new TextureRegionDrawable(new TextureRegion(fishTexture)), new TextureRegionDrawable(new TextureRegion(fishPressed)));
        fish.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new MainMenu(game));
                dispose();
            }
        });

        octopusTexture = new Texture(Gdx.files.internal(""));
        octopusPressed = new Texture(Gdx.files.internal(""));
        ImageButton octopus = new ImageButton(new TextureRegionDrawable(new TextureRegion(octopusTexture)), new TextureRegionDrawable(new TextureRegion(octopusPressed)));
        octopus.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new MainMenu(game));
                dispose();
            }
        });

        shrimpTexture= new Texture(Gdx.files.internal(""));
        shrimpPressed = new Texture(Gdx.files.internal(""));
        ImageButton shrimp = new ImageButton(new TextureRegionDrawable(new TextureRegion(shrimpTexture)), new TextureRegionDrawable(new TextureRegion(shrimpPressed)));
        shrimp.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new MainMenu(game));
                dispose();
            }
        });

        menuTexture = new Texture(Gdx.files.internal(""));
        menuPressed = new Texture(Gdx.files.internal(""));
        ImageButton credits = new ImageButton(new TextureRegionDrawable(new TextureRegion(menuTexture)), new TextureRegionDrawable(new TextureRegion(menuPressed)));
        credits.addListener(new ActorGestureListener() {
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

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
        backgroundTexture.dispose();
        menuTexture.dispose();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}