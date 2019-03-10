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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MainMenu extends ScreenAdapter {

    private final Game game;
    private static final float WORLD_WIDTH = 1280;
    private static final float WORLD_HEIGHT = 720;

    private Stage stage;

    private Texture backgroundTexture;
    private Texture titleTexture;
    private Table table;

    private Texture playTexture;
    private Texture playPressed;
    private Texture charactersTexture;
    private Texture charactersPressed;
    private Texture tutorialTexture;
    private Texture tutorialPressed;
    private Texture creditsTexture;
    private Texture creditsPressed;

    public MainMenu(Game game) {
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

        playTexture = new Texture(Gdx.files.internal(""));
        playPressed = new Texture(Gdx.files.internal(""));
        ImageButton play = new ImageButton(new TextureRegionDrawable(new TextureRegion(playTexture)), new TextureRegionDrawable(new TextureRegion(playPressed)));
        play.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new ScenariosScreen(game));
                dispose();
            }
        });

        charactersTexture = new Texture(Gdx.files.internal(""));
        charactersPressed = new Texture(Gdx.files.internal(""));
        ImageButton characters = new ImageButton(new TextureRegionDrawable(new TextureRegion(charactersTexture)), new TextureRegionDrawable(new TextureRegion(charactersPressed)));
        characters.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new CharactersScreen(game));
                dispose();
            }
        });

        tutorialTexture= new Texture(Gdx.files.internal(""));
        tutorialPressed = new Texture(Gdx.files.internal(""));
        ImageButton tutorial = new ImageButton(new TextureRegionDrawable(new TextureRegion(tutorialTexture)), new TextureRegionDrawable(new TextureRegion(tutorialPressed)));
        tutorial.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new TutorialScreen(game));
                dispose();
            }
        });

        creditsTexture = new Texture(Gdx.files.internal(""));
        creditsPressed = new Texture(Gdx.files.internal(""));
        ImageButton credits = new ImageButton(new TextureRegionDrawable(new TextureRegion(creditsTexture)), new TextureRegionDrawable(new TextureRegion(creditsPressed)));
        credits.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new CreditsScreen(game));
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
        titleTexture.dispose();
        playTexture.dispose();
        playPressed.dispose();

    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

}
