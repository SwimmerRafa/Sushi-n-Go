package mx.itesm.sushingo.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Sam extends Actor {
    private static final int GRAVITY = -15;
    private Vector3 position;
    private Vector3 velocity;

    private Texture sam;

    public Sam (int x, int y){
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        sam = new Texture(Gdx.files.internal("Ejemplo/Sam.png"));
    }

    public void update(float dt){
        velocity.add(0,GRAVITY,0);
        velocity.scl(dt);
        position.add(0,velocity.y,0);

        velocity.scl(1/dt);
    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return sam;
    }


    /*private static final int TILE_WIDTH = 118;
    private static final int TILE_HEIGHT = 118;

    private static final float FRAME_DURATION = 0.25F;

    private static final float DIVE_ACCEL = 0.30F;
    private static final float FLY_ACCEL = 5F;
    private static final float COLLISION_RADIUS = 24f;
    private final Circle collisionCircle;

    private float x = 0;
    private float y = 0;

    private float ySpeed = 0;

    private float animationTimer = 0;

    private final Animation animation;

    public Sam(TextureRegion flappeeTexture) {
        TextureRegion[][] flappeeTextures = new TextureRegion(flappeeTexture).split(TILE_WIDTH, TILE_HEIGHT);

        animation = new Animation(FRAME_DURATION,flappeeTextures[0][0], flappeeTextures[0][1]);
        animation.setPlayMode(Animation.PlayMode.LOOP);

        collisionCircle = new Circle(x, y, COLLISION_RADIUS);
    }

    public Circle getCollisionCircle() {
        return collisionCircle;
    }

    public void update(float delta) {
        animationTimer += delta;
        ySpeed -= DIVE_ACCEL;
        setPosition(x, y + ySpeed);
    }

    public void flyUp() {
        ySpeed = FLY_ACCEL;
        setPosition(x, y + ySpeed);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        updateCollisionCircle();
    }

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    public void draw(SpriteBatch batch) {
        TextureRegion flappeeTexture = (TextureRegion)animation.getKeyFrame(animationTimer);
        float textureX = collisionCircle.x - flappeeTexture.getRegionWidth() / 2;
        float textureY = collisionCircle.y - flappeeTexture.getRegionHeight() / 2;
        batch.draw(flappeeTexture, textureX, textureY);
    }

    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.circle(collisionCircle.x, collisionCircle.y, collisionCircle.radius);
    }

    private void updateCollisionCircle() {
        collisionCircle.setX(x);
        collisionCircle.setY(y);
    }*/
}
