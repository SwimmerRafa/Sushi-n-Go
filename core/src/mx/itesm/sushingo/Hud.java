package mx.itesm.sushingo;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Hud implements Disposable {

    private static final float WORLD_WIDTH = 1280;
    private static final float WORLD_HEIGHT = 720;
    public Stage stage;
    private Viewport viewport;

    private static Integer lives;
    private static Integer score;

    private Label livesLabel;
    private static Label scoreLabel;
    private Label samLabel;
    private Label scoreL;

    public Hud (SpriteBatch sb){
        lives = 3;
        score = 0;

        viewport = new FitViewport(WORLD_WIDTH,WORLD_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table= new Table();
        table.top();
        table.setFillParent(true);

        livesLabel = new Label(String.format("%03d",lives),new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        scoreLabel =new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        scoreL = new Label("SCORE", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        scoreL.setSize(500,500);
        samLabel = new Label("LIVES", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        scoreL.setSize(500,500);

        table.add(samLabel).expandX().padTop(10f);
        table.add(scoreL).expandX().padTop(10f);
        table.row();
        table.add(livesLabel).expandX();
        table.add(scoreLabel);

        stage.addActor(table);
    }

    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }

    public static void restLives(int value){
        lives -= value;
        scoreLabel.setText(String.format("%03d", lives));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
