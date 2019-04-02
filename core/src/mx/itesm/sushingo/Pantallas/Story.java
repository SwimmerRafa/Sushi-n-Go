package mx.itesm.sushingo.Pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class Story extends ScreenAdapter {
    private final Game game;
    private static final float WORLD_WIDTH = 1280;
    private static final float WORLD_HEIGHT = 720;
    private Stage stage;
    private Texture story1;
    private Texture story2;
    private Texture story3;
    private Texture story4;
    private Texture story5;
    private Texture story6;
    private Texture story7;

    public Story(Game game){
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        stage = new Stage(new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        story1 = new Texture(Gdx.files.internal("Story/1a.png"));
        story2 = new Texture(Gdx.files.internal("Story/2a.png"));
        story3 = new Texture(Gdx.files.internal("Story/3a.png"));
        story4 = new Texture(Gdx.files.internal("Story/4a.png"));
        story5 = new Texture(Gdx.files.internal("Story/5a.png"));
        story6 = new Texture(Gdx.files.internal("Story/6a.png"));
        story7 = new Texture(Gdx.files.internal("Story/7a.png"));


        Image image1 = new Image(story1);
        Image image2 = new Image(story2);
        Image image3 = new Image(story3);
        Image image4 = new Image(story4);
        Image image5 = new Image(story5);
        Image image6 = new Image(story6);
        Image image7 = new Image(story7);

        stage.addActor(image1);
        stage.addActor(image2);
        stage.addActor(image3);
        stage.addActor(image4);
        stage.addActor(image5);
        stage.addActor(image6);
        stage.addActor(image7);

        image1.addAction(sequence(fadeIn(1), fadeOut(1)));
        image2.addAction(sequence(delay(2),fadeIn(1), fadeOut(1)));

        image3.addAction(sequence(delay(4),fadeIn(1), fadeOut(1)));
        image4.addAction(sequence(delay(6),fadeIn(1), fadeOut(1)));
        image5.addAction(sequence(delay(8),fadeIn(1), fadeOut(1)));
        image6.addAction(sequence(delay(10),fadeIn(1), fadeOut(1)));

        image7.addAction(sequence(delay(1),fadeIn(1), fadeOut(1), run(new Runnable() {
            @Override
            public void run() {
                game.setScreen(new ScenariosScreen(game));
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
        story1.dispose();
        story2.dispose();
        story3.dispose();
        story4.dispose();
        story5.dispose();
        story6.dispose();
        story7.dispose();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b, Color.WHITE.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
