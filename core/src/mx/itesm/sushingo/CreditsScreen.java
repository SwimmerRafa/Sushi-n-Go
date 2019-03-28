package mx.itesm.sushingo;
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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class CreditsScreen extends ScreenAdapter {
    private final Game game;
    private static final float WORLD_WIDTH = 1280;
    private static final float WORLD_HEIGHT = 720;

    private Stage stage;

    private Texture backgroundTexture;

    private Texture menuTexture;
    private Texture menuPressed;
    private Table table;
    private Music music;

    public CreditsScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        stage = new Stage(new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture(Gdx.files.internal("Fondos/creditos.png"));
        Image background = new Image(backgroundTexture);
        stage.addActor(background);

        menuTexture = new Texture(Gdx.files.internal("Botones/menuBotonNormal.png"));
        menuPressed = new Texture(Gdx.files.internal("Botones/menuBoton.png"));
        ImageButton menu = new ImageButton(new TextureRegionDrawable(new TextureRegion(menuTexture)), new TextureRegionDrawable(new TextureRegion(menuPressed)));

        menu.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new MainMenu(game));
                dispose();
            }
        });

        table = new Table();

        table.row();
        table.setFillParent(true);
        table.add(menu).right().expandX();
        table.padBottom(450f);

        stage.addActor(table);

        music = Gdx.audio.newMusic(Gdx.files.internal("Audio/Menu01.mp3"));
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
        backgroundTexture.dispose();
        menuTexture.dispose();
        music.dispose();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
