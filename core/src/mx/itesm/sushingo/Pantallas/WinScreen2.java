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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import mx.itesm.sushingo.States.GameScreen;
import mx.itesm.sushingo.States.GameScreen2;
import mx.itesm.sushingo.States.GameScreen3;
import mx.itesm.sushingo.SushinGo;

public class WinScreen2 extends ScreenAdapter {
    private final SushinGo game;
    private static final float WORLD_WIDTH = 1280;
    private static final float WORLD_HEIGHT = 720;

    private Stage stage;

    private Texture backgroundTexture;
    private Texture menuTexture;
    private Texture reiniTexture;
    private Texture avanzarTexture;
    private Texture menuPress, avanzarPress, reiniPress;
    private Music music;

    public WinScreen2(SushinGo game) {
        this.game = game;
    }

    private Table table;

    @Override
    public void show() {
        super.show();
        stage = new Stage(new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture(Gdx.files.internal("Fondos/win3.png"));
        Image background = new Image(backgroundTexture);
        stage.addActor(background);

        menuTexture = new Texture(Gdx.files.internal("Botones/MENCOL.png"));
        menuPress = new Texture(Gdx.files.internal("Botones/MENUCOL2.png"));
        ImageButton menu = new ImageButton(new TextureRegionDrawable(new TextureRegion(menuTexture)), new TextureRegionDrawable(new TextureRegion(menuPress)), new TextureRegionDrawable(new TextureRegion(menuPress)));
        menu.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                music.stop();
                game.setScreen(new MainMenu(game));
                dispose();
            }
        });

        avanzarTexture = new Texture(Gdx.files.internal("Botones/AVANZARCOL.png"));
        avanzarPress = new Texture(Gdx.files.internal("Botones/AVANZARCOL2.png"));
        ImageButton avanzar = new ImageButton(new TextureRegionDrawable(new TextureRegion(avanzarTexture)), new TextureRegionDrawable(new TextureRegion(avanzarPress)), new TextureRegionDrawable(new TextureRegion(avanzarPress)));
        avanzar.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                music.stop();
                game.setScreen(new GameScreen3(game));
                dispose();
            }
        });

        reiniTexture = new Texture(Gdx.files.internal("Botones/REINICOL.png"));
        reiniPress = new Texture(Gdx.files.internal("Botones/REINICOL3.png"));
        ImageButton reiniciar = new ImageButton(new TextureRegionDrawable(new TextureRegion(reiniTexture)), new TextureRegionDrawable(new TextureRegion(reiniPress)), new TextureRegionDrawable(new TextureRegion(reiniPress)));
        reiniciar.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                music.stop();
                game.setScreen(new GameScreen2(game));
                dispose();
            }
        });


        table = new Table();

        table.row();
        table.setFillParent(true);
        table.add(avanzar).padTop(20f).right().expandX();
        table.padBottom(20f);
        table.row();
        table.add(reiniciar).padTop(20f).right().expandX();
        table.padBottom(20f);
        table.row();
        table.add(menu).padTop(20f).right().expandX();
        table.padBottom(20f);
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
        reiniTexture.dispose();
        music.dispose();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}

