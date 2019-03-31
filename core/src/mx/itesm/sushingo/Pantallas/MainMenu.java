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

public class MainMenu extends ScreenAdapter {

    private final Game game;
    private static final float WORLD_WIDTH = 1280;
    private static final float WORLD_HEIGHT = 720;

    private Stage stage;

    private Texture backgroundTexture;
    private Table table;

    private Texture playTexture;
    private Texture playPressed;
    private Texture charactersTexture;
    private Texture charactersPressed;
    private Texture tutorialTexture;
    private Texture tutorialPressed;
    private Texture creditsTexture;
    private Texture creditsPressed;
    private Music music;

    public MainMenu(Game game) {
        this.game = game;
    }


    @Override
    public void show(){
        super.show();
        stage = new Stage(new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        backgroundTexture =new Texture(Gdx.files.internal("Fondos/Menu.png"));
        Image background = new Image(backgroundTexture);
        stage.addActor(background);

        playTexture = new Texture(Gdx.files.internal("Botones/Jugar.png"));
        playPressed = new Texture(Gdx.files.internal("Botones/JugarLuz.png"));
        ImageButton play = new ImageButton(new TextureRegionDrawable(new TextureRegion(playTexture)), new TextureRegionDrawable(new TextureRegion(playPressed)));

        play.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new ScenariosScreen(game));
                dispose();
            }
        });

        charactersTexture = new Texture(Gdx.files.internal("Botones/Personajes.png"));
        charactersPressed = new Texture(Gdx.files.internal("Botones/PerLuz.png"));

        ImageButton characters = new ImageButton(new TextureRegionDrawable(new TextureRegion(charactersTexture)), new TextureRegionDrawable(new TextureRegion(charactersPressed)));

        characters.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new CharactersScreen(game));
                dispose();
            }
        });

        tutorialTexture= new Texture(Gdx.files.internal("Botones/Tutorial.png"));
        tutorialPressed = new Texture(Gdx.files.internal("Botones/TutorialLuz.png"));
        ImageButton tutorial = new ImageButton(new TextureRegionDrawable(new TextureRegion(tutorialTexture)), new TextureRegionDrawable(new TextureRegion(tutorialPressed)));

        tutorial.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new TutorialScreen(game));
                dispose();
            }
        });

        creditsTexture = new Texture(Gdx.files.internal("Botones/Créditos.png"));
        creditsPressed = new Texture(Gdx.files.internal("Botones/CréditosLuz.png"));
        ImageButton credits = new ImageButton(new TextureRegionDrawable(new TextureRegion(creditsTexture)), new TextureRegionDrawable(new TextureRegion(creditsPressed)), new TextureRegionDrawable(new TextureRegion(creditsPressed)));

        credits.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new CreditsScreen(game));
                dispose();
            }
        });

        table = new Table();

        table.row();
        table.setFillParent(true);
        table.add(play).expandX();
        table.add(characters).expandX();
        table.add(tutorial).expandX();
        table.add(credits).expandX();
        table.padBottom(80f);

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

    public void dispose() {
        super.dispose();
        stage.dispose();
        backgroundTexture.dispose();
        playTexture.dispose();
        playPressed.dispose();
        music.dispose();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

}
