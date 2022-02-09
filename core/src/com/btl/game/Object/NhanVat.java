package com.btl.game.Object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.btl.game.MainGame;
import com.btl.game.PlayScreen;


public class NhanVat extends Actor {
    private boolean die;
    private TextureRegion dinoDung;
    private TextureRegion dinoDie;
    private TextureRegion dinoDraw;

    private Animation chay;
    private float timer;
    private int vtoc;


    public World world;
    public Body bodyDino;
    private  boolean dangNhay;
    private  boolean nhay;



    public enum TrangThai{DUNG,CHAY,DIE};
    private TrangThai ttHienTai;
    public NhanVat(PlayScreen screen,float x ,float y){
        dinoDung = new TextureRegion(screen.getAtlas().findRegion("godzilla"),0,0,88,99);
        this.world =screen.getWorld();
        khoiTao(x,y);
        setSize(MainGame.PIM,MainGame.PIM);

        this.die = false;
        this.dangNhay = false;
        this.nhay = false;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i=1;i<4;i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("godzilla"),i*88,0,88,99));
        chay = new Animation(0.1f,frames);

        dinoDie = new TextureRegion(screen.getAtlas().findRegion("godzilla"),355,0,88,99);

        this.ttHienTai = TrangThai.DUNG;
        this.timer = 0;
        this.vtoc = 5;
        this.dangNhay = true;

    }
    private void khoiTao(float x ,float y){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x,y);

        bodyDef.type = BodyDef.BodyType.DynamicBody;// thành phẩn dộng;
        bodyDino = world.createBody(bodyDef);
        // tạo hình dạng

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.44f,0.45f);
        Fixture fixture = bodyDino.createFixture(shape,3);
        fixture.setUserData("nv");
        shape.dispose();

    }

    public void setNhay(boolean nhay) {
        this.nhay = nhay;
    }

    public void setDangNhay(boolean dangNhay) {
        this.dangNhay = dangNhay;
    }

    public boolean isDie() {
        return die;
    }


    public void setDie(boolean die) {
        this.die = die;
    }

    @Override
    public void act(float delta) {
        if(((Gdx.input.justTouched() && !dangNhay) || nhay)  ){
            if(!this.die){
                nhay = false;
                dangNhay = true;
                bodyDino.applyLinearImpulse(new Vector2(0,15f),bodyDino.getWorldCenter(),true);
                MainGame.manager.get("Music/jump.mp3", Sound.class).play();
            }

        }
        if(!this.die){
            if(((int)timer %6 == 0 && (int)timer >0 ) && !dangNhay){
                MainGame.vToc+= 0.5f;
            }
            bodyDino.setLinearVelocity(MainGame.vToc,bodyDino.getLinearVelocity().y);
        }
        else{
            //bodyDino.setLinearVelocity(0,bodyDino.getLinearVelocity().y);
            bodyDino.setActive(false);
        }

        if(dangNhay){
            bodyDino.applyForceToCenter(0,- MainGame.vToc*1.15f,true);
        }



    }
    private TrangThai getTrangThai(){
        if(isDie())
            return TrangThai.DIE;
        else if(bodyDino.getLinearVelocity().x !=0  && !dangNhay)
            return TrangThai.CHAY;
        else
            return TrangThai.DUNG;
    }
    public void upDate(float dt){
        ttHienTai = getTrangThai();
        switch (ttHienTai){

            case CHAY:
                dinoDraw = (TextureRegion)chay.getKeyFrame(timer,true);
                timer +=dt;
                break;
            case DIE:
                dinoDraw = dinoDie;
                timer =0;
                break;
            case DUNG:
            default:
                dinoDraw = dinoDung;
                timer = 0;
                break;
        }


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((bodyDino.getPosition().x -0.5f)*MainGame.PIM,(bodyDino.getPosition().y -0.5f)*MainGame.PIM);
        batch.draw(dinoDraw,getX(),getY(),getWidth(),getHeight());

    }
    public void dispose(){
        world.destroyBody(bodyDino);

    }
}
