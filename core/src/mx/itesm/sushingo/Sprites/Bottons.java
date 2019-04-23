package mx.itesm.sushingo.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Bottons extends Actor {
    private Animation bottonAnimation;
    private Texture texture;
    private float x = 0;
    private float y = 0;

    public Bottons(Texture texture) {
        this.texture = texture;
        bottonAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);
    }

    public void update(float dt){
        bottonAnimation.update(dt);
    }

    public TextureRegion getTexture() {
        return bottonAnimation.getFrame();
    }

    public void dispose (){
        texture.dispose();
    }

    public void draw (SpriteBatch sb){
        getTexture();
        sb.draw(getTexture(), x, y);
    }

    public void setPosition (float x, float y){
        this.x = x;
        this.y = y;
    }

    public void act(float dt){
        super.act(dt);
    }
}
