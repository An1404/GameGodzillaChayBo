package com.btl.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.btl.game.Object.Enemy;
import com.btl.game.Object.Ground;
import com.btl.game.Object.Nha;
import com.btl.game.Object.NhanVat;
import com.btl.game.Object.Sky;
import com.btl.game.Object.Tree;


import java.util.ArrayList;

public class PlayScreen implements Screen {
    private  MainGame game;
    private Stage stage;
    private World world;
    // nhân vật
    private NhanVat dino;
    private Ground ground;

    private ArrayList<Enemy> enemies;


    private TextureAtlas atlas;
    private Music music;
    private ImageButton button;
    private boolean dkCam;
    private  int value;


    private  float score;
    private Label scoreLb;
    private Label hScoreLb;
    private Label high;
    private float highScore;
    private Preferences pres;



    public PlayScreen(MainGame mainGame){
        this.game = mainGame;
        // load nv
        atlas = new TextureAtlas("godzilla.pack");
        stage =new Stage(new FillViewport(640,360));
        world = new World(new Vector2(0,-10),true);// trọng lục


        enemies = new ArrayList<Enemy>();

        world.setContactListener(new ContactListener() {
            private boolean ktObj(Contact contact, String userA, String userB){
                return  (contact.getFixtureA().getUserData().equals(userA) && contact.getFixtureB().getUserData().equals(userB) ||
                        contact.getFixtureB().getUserData().equals(userA) && contact.getFixtureA().getUserData().equals(userB) );
            }
            @Override
            public void beginContact(Contact contact) {
                if(ktObj(contact,"nv","dat")){
                    dino.setDangNhay(false);
                    if(Gdx.input.isTouched()){

                        dino.setNhay(true);
                    }
                }
                if(ktObj(contact,"nv","cay") || ktObj(contact,"nv","chim")){
                    dino.setDie(true);
                    music.stop();
                    MainGame.manager.get("Music/die.mp3", Sound.class).play();
                    gameOver();
                    for(Enemy enemy : enemies){
                        enemy.setDie(true);

                    }
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
        this.music = MainGame.manager.get("Music/song.mp3", Music.class);
        music.setLooping(true);
        music.play();
        pres = Gdx.app.getPreferences("data");
        this.highScore = pres.getInteger("HighScore",0);

        scoreLb = new Label(String.format("%06d",(int)score),new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        hScoreLb = new Label(String.format("%06d",(int)highScore),new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        high = new Label("Hi",new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        high.setPosition(stage.getWidth()/1.5f, stage.getHeight()-50);
        hScoreLb.setPosition(stage.getWidth()/1.5f, stage.getHeight()-50);
        scoreLb.setPosition(stage.getWidth()/1.5f, stage.getHeight()-50);
        score = 0;

        stage.addActor(scoreLb);
        stage.addActor(hScoreLb);
        stage.addActor(high);






        //stage.setDebugAll(true);
        this.dkCam = true;
        this.value = 15;







    }
    public TextureAtlas getAtlas(){
        return atlas;
    }
    public World getWorld(){
        return world;
    }
    public void gameOver(){

            Texture texture = new Texture("Anh/replay.png");

            Drawable drawable = new TextureRegionDrawable(new TextureRegion(texture));

            button = new ImageButton(drawable);
            button.setSize(80,80);
            button.setPosition(stage.getCamera().position.x-button.getWidth()/2, stage.getHeight()/2);
            button.addCaptureListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {

                    game.setScreen(new PlayScreen(game));
                    MainGame.vToc = 5;

                }
            });

            stage.addActor(button);

            Gdx.input.setInputProcessor(stage);
            if(highScore < score){
                pres.putInteger("HighScore",(int)score);
                pres.flush();
            }



    }

    @Override
    public void show() {
        dino = new NhanVat(this,15f,1.5f);
        ground = new Ground(this,0,1,1000);

        stage.addActor(dino);
        stage.addActor(ground);




    }

    @Override
    public void render(float delta) {
        if(Gdx.input.isTouched())
            dkCam = false;
        if(!dino.isDie() && !dkCam){
            score +=0.2f;
            scoreLb.setText(String.format("%06d",(int)score));
            if(score< 1000f)
                value += 21;
            else{

                value += 26;
            }
            Sky sky = new Sky(this,value+5,5);
            Tree tree = new Tree(this,value,1);
            Nha nha = new Nha(this,value+11,1);
            stage.addActor(tree);
            stage.addActor(nha);
            stage.addActor(sky);
            enemies.add(new Enemy(this,((value+11f)+5)*7,2.5f));
                for(Enemy enemy : enemies){
                    stage.addActor(enemy);

                }



        }





        Gdx.gl.glClearColor(0.2f,1,1,1);// xoá mh
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        if(!dino.isDie()){
            if(dkCam){

                stage.getCamera().position.set(19*MainGame.PIM,stage.getCamera().position.y,0);

            }
            else{
                float vToc =  MainGame.vToc;
                float vtSau = (vToc -0.05f)*delta*MainGame.PIM;
                stage.getCamera().translate(vtSau,0,0);
                //stage.getCamera().position.set(stage.getCamera().position.x +)
                high.setX(stage.getCamera().position.x);
                scoreLb.setX(stage.getCamera().position.x+150);
                hScoreLb.setX(stage.getCamera().position.x+30);

            }
            for(Enemy enemy : enemies){
                enemy.upDate(delta);
            }

        }

        if(!dkCam)
             stage.act();
        world.step(delta,6,2);
        dino.upDate(delta);



        stage.draw();



    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dino.dispose();
        dino.remove();
        ground.dispose();
        ground.remove();



    }

    @Override
    public void dispose() {
        world.dispose();
        stage.dispose();

    }
}
