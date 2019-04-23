package mx.itesm.sushingo.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.itesm.sushingo.SushinGo;

public class LoadingScreen2 extends ScreenAdapter {
    private static final float WORLD_WIDTH = 1280;
    private static final float WORLD_HEIGHT = 720;
    private static final float PROGRESS_BAR_WIDTH = 500;
    private static final float PROGRESS_BAR_HEIGHT = 100;
    private ShapeRenderer shapeRenderer;
    private Viewport viewport;
    private Camera camera;
    private float progress = 0;
    private final SushinGo game;

    public LoadingScreen2(SushinGo game) {
        this.game = game;
    }


    public void resize(int width, int height) {
        viewport.update(width, height);
    }


    public void show() {
        camera = new OrthographicCamera();
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        camera.update();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        shapeRenderer = new ShapeRenderer();


        game.getAssetManager().load("Personajes/pez.png", Texture.class);
        //

        game.getAssetManager().load("play.png", Texture.class);
        game.getAssetManager().load("pause.png", Texture.class);

    }


    public void render(float delta) {
        update();
        clearScreen();
        draw();
    }


    public void dispose() {
        shapeRenderer.dispose();
    }

    private void update() {
        if (game.getAssetManager().update()) {
            game.setScreen(new GameScreen2(game));
        } else {
            progress = game.getAssetManager().getProgress();
        }
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void draw() {
        shapeRenderer.setProjectionMatrix(camera.projection);
        shapeRenderer.setTransformMatrix(camera.view);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(
                (WORLD_WIDTH  - PROGRESS_BAR_WIDTH) / 2, (WORLD_HEIGHT  - PROGRESS_BAR_HEIGHT) / 2,progress * PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT);
        shapeRenderer.end();
    }
}


