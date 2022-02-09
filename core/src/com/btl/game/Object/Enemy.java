package com.btl.game.Object;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.btl.game.MainGame;
import com.btl.game.PlayScreen;


public class Enemy extends Actor {
    private Animation bay;
    private float timer;
    private TextureRegion textureDraw;
    private boolean die;
    public World world;
    public Body body;
    public Enemy(PlayScreen screen, float x , float y){
        this.world =screen.getWorld();
        khoiTao(x,y);
        setSize(MainGame.PIM,MainGame.PIM);
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i=0;i<2;i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("offline-sprite-2x-santa"),i*91,4,91,79));
        bay = new Animation(0.2f,frames);
        this.timer = 0;
        textureDraw =new TextureRegion(screen.getAtlas().findRegion("offline-sprite-2x-santa"),0,4,91,79);
    }
    private void khoiTao(float x, float y){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x,y);

        bodyDef.type = BodyDef.BodyType.DynamicBody;// thành phẩn dộng;
        body = world.createBody(bodyDef);
        // tạo hình dạng

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.3f,0.3f);
        Fixture fixture = body.createFixture(shape,1);
        fixture.setUserData("chim");
        shape.dispose();

    }

    @Override
    public void act(float delta) {
        if(this.die)
            body.setActive(false);

    }
    public void upDate(float dt){
        timer +=dt;
        textureDraw = (TextureRegion)bay.getKeyFrame(timer,true);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x -0.5f)*MainGame.PIM,(body.getPosition().y -0.5f)*MainGame.PIM);
        batch.draw(textureDraw,getX(),getY(),getWidth(),getHeight());
    }
    public void dispose(){

        world.destroyBody(body);

    }

    public void setDie(boolean die) {
        this.die = die;
    }
}
