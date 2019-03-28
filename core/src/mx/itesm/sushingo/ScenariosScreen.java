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
    private Table table;
    private Music music;

    public ScenariosScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        stage = new Stage(new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture(Gdx.files.internal("Fondos/ecenarios.png"));
        Image background = new Image(backgroundTexture);
        stage.addActor(background);

        scenario1Texture = new Texture(Gdx.files.internal("Botones/Moshio.png"));
        scenario1Pressed = new Texture(Gdx.files.internal("Botones/moshiover.png"));
        ImageButton scenario1 = new ImageButton(new TextureRegionDrawable(new TextureRegion(scenario1Texture)), new TextureRegionDrawable(new TextureRegion(scenario1Pressed)));
        scenario1.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new StoryScreen(game));
                dispose();
            }
        });

        scenario2Texture = new Texture(Gdx.files.internal("Botones/Pasele.png"));
        scenario2Pressed = new Texture(Gdx.files.internal("Botones/paseleama.png"));
        ImageButton scenario2 = new ImageButton(new TextureRegionDrawable(new TextureRegion(scenario2Texture)), new TextureRegionDrawable(new TextureRegion(scenario2Pressed)));
        scenario2.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new StoryScreen(game));
                dispose();
            }
        });

        scenario3Texture= new Texture(Gdx.files.internal("Botones/kamikaze.png"));
        scenario3Pressed = new Texture(Gdx.files.internal("Botones/kamikazero.png"));
        ImageButton scenario3 = new ImageButton(new TextureRegionDrawable(new TextureRegion(scenario3Texture)), new TextureRegionDrawable(new TextureRegion(scenario3Pressed)));
        scenario3.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new StoryScreen(game));
                dispose();
            }
        });

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

        table = new Table();

        table.row();
        table.setFillParent(true);
        table.add(menu).colspan(10).expand().right();
        table.row();
        table.add(scenario1).expandX().center();
        table.add(scenario2).expandX().center();
        table.add(scenario3).expandX().center();
        table.padBottom(50f);

        table.setFillParent(true);
        table.pack();

        stage.addActor(table);

        music = Gdx.audio.newMusic(Gdx.files.internal("Audio/Menu.mp3"));
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
        music.dispose();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

}
