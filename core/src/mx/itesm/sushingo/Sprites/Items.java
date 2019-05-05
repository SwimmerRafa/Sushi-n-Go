package mx.itesm.sushingo.Sprites;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Items {
    private static final float COLLISION_SQUARE_WIDTH = 50F;
    private static final float MAX_SPEED_PER_SECOND = 130F;

    public static final float WIDTH = COLLISION_SQUARE_WIDTH;
    private Texture texture;
    private final Rectangle collisionRectangle;
    private float x = 0;
    private float y = 0;

    protected boolean toDestroy;
    protected boolean destroyed;

    public Items(Texture texture){
        this.collisionRectangle = new Rectangle(x, y, COLLISION_SQUARE_WIDTH, COLLISION_SQUARE_WIDTH);
        this.texture = texture;
        toDestroy = false;
        destroyed = false;
    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
        updateCollisionRectangle();
    }

    public void updateCollisionRectangle(){
        collisionRectangle.setX(x);
        collisionRectangle.setY(y);
    }

    public boolean isSamColliding(Sam sam){

        Circle samCollisionCircle = sam.getCollisionCircle();
        return Intersector.overlaps(samCollisionCircle, collisionRectangle);
    }

    public boolean isCamColliding(Cam cam){

        Circle samCollisionCircle = cam.getCollisionCircle();
        return Intersector.overlaps(samCollisionCircle, collisionRectangle);
    }

    public boolean isPaulColliding(Paul paul){

        Circle samCollisionCircle = paul.getCollisionCircle();
        return Intersector.overlaps(samCollisionCircle, collisionRectangle);
    }

    public void update (float delta){
        if(toDestroy && ! destroyed){
            destroyed = true;
        }
        setPosition(x - (MAX_SPEED_PER_SECOND * delta), y);
    }

    public float getX(){
        return x;
    }

    public void draw(SpriteBatch sb){
        if(!destroyed) {
            sb.draw(this.texture, x, y);
        }
    }

    public void dispose(){
        texture.dispose();
    }

}
