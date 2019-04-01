package mx.itesm.sushingo;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import mx.itesm.sushingo.Pantallas.MainMenu;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class TransitionScreen extends ScreenAdapter {
    private final Game game;
    private static final float WORLD_WIDTH = 1280;
    private static final float WORLD_HEIGHT = 720;
    private Stage stage;
    private Texture backgroundTexture;
    private Texture logoTexture;



    public TransitionScreen(Game game){
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        stage = new Stage(new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture(Gdx.files.internal("tec.png"));
        logoTexture = new Texture(Gdx.files.internal("logo.png"));

        Image background = new Image(backgroundTexture);
        Image logo = new Image(logoTexture);

        stage.addActor(background);
        stage.addActor(logo);
        logo.getColor().a = 0f;

        background.addAction(sequence(delay(1),fadeIn(1), fadeOut(2)));
        logo.addAction(sequence(delay(4), fadeIn(2),delay(2), fadeOut(2), run(new Runnable() {
            @Override
            public void run() {
                game.setScreen(new MainMenu(game));
                dispose();
            }
        })));

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
        logoTexture.dispose();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b, Color.WHITE.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

}
