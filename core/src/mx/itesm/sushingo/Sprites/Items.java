package mx.itesm.sushingo.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Items {

    private Texture arroz, machete, jalapeño, dango;
    private Rectangle collision;
    private static final float DISTANCE_BETWEEN_FLOOR_AND_CEILING = 225F;
    private float x= 0;
    private final float y;
    private static final float HEIGHT_OFFSET = -400f;
    private static final float COLLISION_RECTANGLE_WIDTH = 13f;
    private static final float COLLISION_RECTANGLE_HEIGHT = 447f;
    private static final float MAX_SPEED_PER_SECOND = 100F;
    private static final float COLLISION_CIRCLE_RADIUS = 33f;
    public static final float WIDTH = COLLISION_CIRCLE_RADIUS * 2;


    public Items(){
        arroz = new Texture(Gdx.files.internal("Items/arrozc.png"));
        //machete = new Texture(Gdx.files.internal("Items/"));
        jalapeño = new Texture(Gdx.files.internal("Items/chile2.png"));
        dango = new Texture(Gdx.files.internal("Items/dango.png"));

        this.y = MathUtils.random(HEIGHT_OFFSET);
        this.collision = new Rectangle(x, y, COLLISION_RECTANGLE_WIDTH, COLLISION_RECTANGLE_HEIGHT);
    }

    public void update(float dt){
        setPosition(x - (MAX_SPEED_PER_SECOND * dt));
    }

    public void setPosition(float x){
        this.x = x;
        updateCollisionRectangle();
    }

    public float getX(){
        return x;
    }

    private void updateCollisionRectangle(){
        collision.setX(x);
    }

    public void draw(SpriteBatch batch){
        drawArroz(batch);
        //drawMachete(batch);
        drawJalapeño(batch);
        drawDango(batch);
    }

    private void drawArroz(SpriteBatch batch){
        float textureX = arroz.getWidth();
        float textureY = collision.getY();
        batch.draw(arroz,textureX,textureY);
    }

    /*private void drawMachete(SpriteBatch batch){
        float textureX = machete.getWidth();
        float textureY = collision.getY();
        batch.draw(machete,textureX,textureY);
    }*/

    private void drawJalapeño(SpriteBatch batch){
        float textureX = jalapeño.getWidth();
        float textureY = collision.getY();
        batch.draw(jalapeño,textureX,textureY);
    }

    private void drawDango(SpriteBatch batch){
        float textureX = dango.getWidth();
        float textureY = collision.getY();
        batch.draw(dango,textureX,textureY);
    }

    public boolean isSamColliding( Sam sam){
        Circle samCollisionCircle = sam.getCollisionCircle();
        return
                Intersector.overlaps(samCollisionCircle,collision);
    }

}
