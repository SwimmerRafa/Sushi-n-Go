package mx.itesm.sushingo;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class StoryScreen extends ScreenAdapter {

    private final Game game;
    private static final float WORLD_WIDTH = 1280;
    private static final float WORLD_HEIGHT = 720;

    private Stage stage;

    private Texture backgroundTexture;

    private Texture nextTexture;
    private Texture nextPressed;
    private Texture skipTexture;
    private Texture skipPressed;
    private Texture menuTexture;
    private Texture menuPressed;

    public StoryScreen(Game game) {
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

        nextTexture = new Texture(Gdx.files.internal(""));
        nextPressed = new Texture(Gdx.files.internal(""));
        ImageButton next = new ImageButton(new TextureRegionDrawable(new TextureRegion(nextTexture)), new TextureRegionDrawable(new TextureRegion(nextPressed)));
        next.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new ScenariosScreen(game));
                dispose();
            }
        });


        skipTexture= new Texture(Gdx.files.internal(""));
        skipPressed = new Texture(Gdx.files.internal(""));
        ImageButton skip = new ImageButton(new TextureRegionDrawable(new TextureRegion(skipTexture)), new TextureRegionDrawable(new TextureRegion(skipPressed)));
        skip.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new GameScreen());
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
}
