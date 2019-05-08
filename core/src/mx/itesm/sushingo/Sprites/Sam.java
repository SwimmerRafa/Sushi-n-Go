package mx.itesm.sushingo.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Sam {

    private Rectangle bounds;
    private Animation birdAnimation;
    private Texture texture;
    private Sound flap;
    public boolean colliding;
    private float x = 0;
    private float y = 0;

    private final Circle collisionCircle;
    private static final float COLLISION_RADIUS = 24f;
    private float ySpeed = 0;
    private static final float DIVE_ACCEL = 0.30F;
    private static final float FLY_ACCEL = 5F;
    private enum state {NORMAL, HIT};
    private float hitTimer = 1;
    private boolean isHit = false;

    public Sam(){

        texture = new Texture(Gdx.files.internal("Personajes/pez.png"));
        birdAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);
        bounds = new Rectangle(x, y, texture.getWidth()/3, texture.getHeight());
        flap = Gdx.audio.newSound(Gdx.files.internal("Audio/sam.wav"));
        colliding = false;
        collisionCircle = new Circle(x, y, COLLISION_RADIUS);
    }

    public void update(float dt){
        birdAnimation.update(dt);
        ySpeed -= DIVE_ACCEL;
        setPosition(x, y + ySpeed);

        if (isHit()){
            hitTimer -= dt;
            if(hitTimer<=0){
                isHit = false;
                hitTimer = 1;
            }
        }
    }

    public TextureRegion getTexture() {
        return birdAnimation.getFrame();
    }

    public void jump(){
        ySpeed = FLY_ACCEL;
        setPosition(x,y+ySpeed);
        flap.play(0.5f);
    }

    public void dispose(){
        texture.dispose();
        flap.dispose();
    }

    public void draw(SpriteBatch batch){
        getTexture();
        float textureX = collisionCircle.x - getTexture().getRegionWidth() / 2;
        float textureY = collisionCircle.y - getTexture().getRegionHeight() / 2;
        batch.draw(getTexture(), textureX,textureY);
    }

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        updateCollisionCircle();
    }

    public Circle getCollisionCircle() {
        return collisionCircle;
    }

    private void updateCollisionCircle() {
        collisionCircle.setX(x);
        collisionCircle.setY(y);
    }

    public float getHeight(){
        return COLLISION_RADIUS;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }
}