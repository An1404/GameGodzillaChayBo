package com.btl.game.Object;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.btl.game.MainGame;
import com.btl.game.PlayScreen;

import java.util.ArrayList;
import java.util.Random;

public class Sky  extends Actor {
    private ArrayList<TextureRegion> textureSky;


    private World world;
    private Body body;
    private Fixture fixture ;
    private int vt;
    public  Sky(PlayScreen screen, float x, float y){
        textureSky = new ArrayList<TextureRegion>();
        for(int i =0;i<4;i++){
            for(int j =0;j<3;j++){
                textureSky.add(new TextureRegion(screen.getAtlas().findRegion("sky"),0,0,150,95));
            }
        }







        Random random = new Random();
        vt = random.nextInt(12);

        this.world =screen.getWorld();
        khoiTao(x,y);
        setSize(MainGame.PIM*1.5f,MainGame.PIM*1.2f);



    }

    private void khoiTao(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x,y+0.5f);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);
        // tạo hình dạng

        CircleShape shape = new CircleShape();// hình cầu

        shape.setRadius(0.36f);
        fixture = body.createFixture(shape,1);
        fixture.setUserData("sky");

        shape.dispose();
    }



    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x-0.5f)* MainGame.PIM,(body.getPosition().y-0.6f)*MainGame.PIM);

        batch.draw(textureSky.get(vt),getX(),getY(),getWidth(),getHeight());


    }
    public void dispose(){
        body.destroyFixture(fixture);
        world.destroyBody(body);

    }
}
