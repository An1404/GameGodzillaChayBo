package com.btl.game.Object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.btl.game.MainGame;
import com.btl.game.PlayScreen;


public class Ground  extends Actor {
    public World world;
    public Body body;
    private Texture textureGround;
    Fixture fixture ;
    public Ground(PlayScreen screen, float x, float y , float width){
        textureGround = new Texture("nen.png");

        this.world = screen.getWorld();
        khoiTao( x, y,width);
        setSize(width* MainGame.PIM,MainGame.PIM);
        setPosition(x *MainGame.PIM,(y-1)*MainGame.PIM);

    }
    private void khoiTao(float x, float y,float width){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x+width/2,y-0.5f);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);
        // tạo hình dạng

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2,0.5f);
        fixture = body.createFixture(shape,1);
        fixture.setUserData("dat");
        shape.dispose();

    }
    public void dispose(){
        body.destroyFixture(fixture);
        world.destroyBody(body);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(textureGround,getX(),getY(),getWidth(),getHeight());
    }
}
