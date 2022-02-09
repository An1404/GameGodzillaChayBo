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

public class Tree  extends Actor {
    private ArrayList<TextureRegion>  textureTree;

    private World world;
    private Body body;
    private Fixture fixture ;
    private int vt;
    public  Tree(PlayScreen screen, float x, float y){
        textureTree = new ArrayList<TextureRegion>();
        textureTree.add(new TextureRegion(screen.getAtlas().findRegion("nha"),0,0,34,73));
        textureTree.add(new TextureRegion(screen.getAtlas().findRegion("nha"),0,0,68,73));
        textureTree.add(new TextureRegion(screen.getAtlas().findRegion("nha"),0,0,102,73));
        textureTree.add(new TextureRegion(screen.getAtlas().findRegion("nha"),68,0,68,73));
       /*textureTree.add(new TextureRegion(screen.getAtlas().findRegion("nha2"),0,0,50,105));
       textureTree.add(new TextureRegion(screen.getAtlas().findRegion("nha2"),0,0,100,105));
       textureTree.add(new TextureRegion(screen.getAtlas().findRegion("nha"),100,0,202,105));
       textureTree.add(new TextureRegion(screen.getAtlas().findRegion("nha"),100,0,100,105));*/

        Random random = new Random();
        vt = random.nextInt(4);



        this.world =screen.getWorld();
        khoiTao(x,y);



         switch (vt){
             case 1:
             case 3:
                 setSize(MainGame.PIM*1.4f,MainGame.PIM);
                 break;
             case 2:
                 setSize(MainGame.PIM*1.6f,MainGame.PIM);
                 break;


             case 0:
             default:
                 setSize(MainGame.PIM,MainGame.PIM);
                 break;

         }

    }

    private void khoiTao(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x,y+0.5f);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);
        // tạo hình dạng

        CircleShape shape = new CircleShape();// hình cầu
        switch (vt){
            case 1:
            case 3:
                shape.setRadius(0.4f);
                break;
            case 2:
                shape.setRadius(0.45f);
                break;


            case 0:
            default:
                shape.setRadius(0.36f);
                break;

        }

        fixture = body.createFixture(shape,1);
        fixture.setUserData("cay");

        shape.dispose();
    }

    @Override
    public void act(float delta) {
        //setX(getX()-250*delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x-0.5f)* MainGame.PIM,(body.getPosition().y-0.6f)*MainGame.PIM);

        batch.draw(textureTree.get(vt),getX(),getY(),getWidth(),getHeight());
    }
    public void dispose(){
        body.destroyFixture(fixture);
        world.destroyBody(body);

    }

}
