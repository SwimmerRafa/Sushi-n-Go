package mx.itesm.sushingo.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import mx.itesm.sushingo.SushinGo;

public class PauseScreen extends ScreenAdapter {
    private final SushinGo game;
    private static final float WORLD_WIDTH = 800;
    private static final float WORLD_HEIGHT = 480;

    private static final int MONEY_DEFAULT = 0;
    private static final int STARS_DEFAULT = 0;
    private static final float MUSIC_VOLUME_DEFAULT = 100f;
    private static final float EFFECTS_VOLUME_DEFAULT = 100f;
    private static final boolean MUSIC_ON_DEFAULT = true;
    private static final boolean EFFECTS_ON_DEFAULT = true;

    private Preferences preferences;

    private Stage stage;
    private Texture backgroundTexture;
    private Texture backTexture;
    private Texture backPressedTexture;
    private ImageButton backButton;
    private Texture scoresTexture;
    private Texture scoresPressedTexture;
    private ImageButton scoresButton;
    private Texture resetTexture;
    private Texture resetPressedTexture;
    private ImageButton resetButton;
    private Texture musicTexture;
    private Texture musicPressedTexture;
    private ImageButton musicButton;
    private Texture effectsTexture;
    private Texture effectsPressedTexture;
    private ImageButton effectsButton;
    private Texture sliderBackground;
    private Texture sliderKnob;
    private Slider musicSlider;
    private Slider effectsSlider;

    private BitmapFont myFont;
    private Label moneyLabel;
    private Label starsLabel;

    private int money;
    private int stars;
    private float musicVolume;
    private float effectsVolume;
    private boolean musicOn;
    private boolean effectsOn;


    public PauseScreen(SushinGo game) {
        this.game = game;
    }

    public void create() {

        //se inicializan las preferencias
        preferences = Gdx.app.getPreferences(SushinGo.class.getName());
        loadPreferences();

        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture(Gdx.files.internal("bg2.png"));
        Image background = new Image(backgroundTexture);
        stage.addActor(background);

        backTexture = new Texture(Gdx.files.internal("backbtn.png"));
        backPressedTexture = new Texture(Gdx.files.internal("backbtn_pressed.png"));
        backButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(backTexture)), new TextureRegionDrawable(new TextureRegion(backPressedTexture)));
        backButton.setPosition(30, 260);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                money += 1;
                Gdx.app.log("EVENT", "increased money: " + money);
            };
        });
        stage.addActor(backButton);

        scoresTexture = new Texture(Gdx.files.internal("scores.png"));
        scoresPressedTexture = new Texture(Gdx.files.internal("scores_pressed.png"));
        scoresButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(scoresTexture)), new TextureRegionDrawable(new TextureRegion(scoresPressedTexture)));
        scoresButton.setPosition(30, 145);
        scoresButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stars +=1;
                Gdx.app.log("EVENT", "increased stars: " + stars);
            };
        });
        stage.addActor(scoresButton);

        resetTexture = new Texture(Gdx.files.internal("reset.png"));
        resetPressedTexture = new Texture(Gdx.files.internal("reset_pressed.png"));
        resetButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(resetTexture)), new TextureRegionDrawable(new TextureRegion(resetPressedTexture)));
        resetButton.setPosition(30, 25);
        resetButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resetPreferences();
                Gdx.app.log("EVENT", "preferences have been reset");
            };
        });
        stage.addActor(resetButton);

        musicTexture = new Texture(Gdx.files.internal("music_off.png"));
        musicPressedTexture = new Texture(Gdx.files.internal("music_on.png"));
        musicButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(musicTexture)), new TextureRegionDrawable(new TextureRegion(musicTexture)), new TextureRegionDrawable(new TextureRegion(musicPressedTexture)));
        musicButton.setPosition(210, 200);
        musicButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                musicOn = !musicOn;
                Gdx.app.log("EVENT", "music button toggled: " + musicOn);
            };
        });
        stage.addActor(musicButton);

        effectsTexture = new Texture(Gdx.files.internal("effects_off.png"));
        effectsPressedTexture = new Texture(Gdx.files.internal("effects_on.png"));
        effectsButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(effectsTexture)), new TextureRegionDrawable(new TextureRegion(effectsTexture)), new TextureRegionDrawable(new TextureRegion(effectsPressedTexture)));
        effectsButton.setPosition(210, 130);
        effectsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                effectsOn = !effectsOn;
                Gdx.app.log("EVENT", "effects button toggled: " + effectsOn);
            };
        });
        stage.addActor(effectsButton);

        Slider.SliderStyle ss = new Slider.SliderStyle();
        sliderBackground = new Texture(Gdx.files.internal("bar.png"));
        ss.background = new TextureRegionDrawable(new TextureRegion(sliderBackground));
        sliderKnob = new Texture(Gdx.files.internal("slider.png"));
        ss.knob = new TextureRegionDrawable(new TextureRegion(sliderKnob));

        musicSlider = new Slider(0f, 100f, 1f, false, ss);
        musicSlider.setPosition(285,210);
        musicSlider.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                musicVolume = musicSlider.getValue();
                Gdx.app.log("EVENT", "slider changed to " + musicSlider.getValue());
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return true;
            };
        });
        stage.addActor(musicSlider);

        effectsSlider = new Slider(0f, 100f, 1f, false, ss);
        effectsSlider.setPosition(285,145);
        effectsSlider.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                effectsVolume = effectsSlider.getValue();
                Gdx.app.log("EVENT", "slider changed to " + effectsSlider.getValue());
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return true;
            };
        });
        stage.addActor(effectsSlider);

        myFont = new BitmapFont(Gdx.files.internal("myfont.fnt"));
        Label.LabelStyle ls = new Label.LabelStyle();
        ls.font = myFont;
        moneyLabel = new Label("000", ls);
        moneyLabel.setPosition(620, 210);
        stage.addActor(moneyLabel);

        starsLabel = new Label("000", ls);
        starsLabel.setPosition(620, 145);
        stage.addActor(starsLabel);
    }

    public void render () {

        updateWidgets();

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void pause(){
        //se guardan las preferencias antes de salir
        savePreferences();
    }

    @Override
    public void dispose () {
        backgroundTexture.dispose();
        backTexture.dispose();
        backPressedTexture.dispose();
        scoresTexture.dispose();
        scoresPressedTexture.dispose();
        resetTexture.dispose();
        resetPressedTexture.dispose();
        musicTexture.dispose();
        musicPressedTexture.dispose();
        effectsTexture.dispose();
        effectsPressedTexture.dispose();
        sliderBackground.dispose();
        sliderKnob.dispose();
        myFont.dispose();
        stage.dispose();
    }

    private void updateWidgets() {
        //se actualizan las etiquetas por si cambian los valores
        moneyLabel.setText(money);
        starsLabel.setText(stars);
        musicButton.setChecked(musicOn);
        effectsButton.setChecked(effectsOn);
        musicSlider.setValue(musicVolume);
        effectsSlider.setValue(effectsVolume);

    }

    private void resetPreferences() {
        money = MONEY_DEFAULT;
        stars = STARS_DEFAULT;
        musicVolume = MUSIC_VOLUME_DEFAULT;
        effectsVolume = EFFECTS_VOLUME_DEFAULT;
        musicOn = MUSIC_ON_DEFAULT;
        effectsOn = EFFECTS_ON_DEFAULT;
    }

    private void loadPreferences(){

        Gdx.app.log("LOG:","===================");
        Gdx.app.log("LOG:","Loaded preferences");
        Gdx.app.log("LOG:","===================");

        musicVolume = preferences.getFloat("musicVolume", MUSIC_VOLUME_DEFAULT);
        Gdx.app.log("LOG:","Music volume: " +  musicVolume + "/100");

        effectsVolume = preferences.getFloat("effectsVolume", EFFECTS_VOLUME_DEFAULT);
        Gdx.app.log("LOG:","Effects volume: " + effectsVolume + "/100");

        musicOn = preferences.getBoolean("musicOn", MUSIC_ON_DEFAULT);
        Gdx.app.log("LOG:","Music On: " + musicOn);

        effectsOn = preferences.getBoolean("effectsOn", EFFECTS_ON_DEFAULT);
        Gdx.app.log("LOG:","Effects On: " + effectsOn);

        money = preferences.getInteger("money", MONEY_DEFAULT);
        Gdx.app.log("LOG:","Money available: " +  money);

        stars = preferences.getInteger("stars", STARS_DEFAULT);
        Gdx.app.log("LOG:","Stars available: " +  stars);

    }

    private void savePreferences(){

        Gdx.app.log("LOG:","===================");
        Gdx.app.log("LOG:","Saved preferences");
        Gdx.app.log("LOG:","===================");

        preferences.putFloat("musicVolume", musicVolume);
        Gdx.app.log("LOG:","Music volume: " +  musicVolume + "/100");

        preferences.putFloat("effectsVolume", effectsVolume);
        Gdx.app.log("LOG:","Effects volume: " + effectsVolume + "/100");

        preferences.putBoolean("musicOn", musicOn);
        Gdx.app.log("LOG:","Music On: " + musicOn);

        preferences.putBoolean("effectsOn", effectsOn);
        Gdx.app.log("LOG:","Effects On: " + effectsOn);

        preferences.putInteger("money", money);
        Gdx.app.log("LOG:","Money available: " +  money);

        preferences.putInteger("stars", stars);
        Gdx.app.log("LOG:","Stars available: " +  stars);

        preferences.flush();
    }

}
