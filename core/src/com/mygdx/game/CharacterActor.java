package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Justin on 2016-01-12.
 */
public class CharacterActor extends Actor implements ActionListener {
    Texture texture = new Texture(Gdx.files.internal(""));
    float actorX = 0, actorY = 0;
    public boolean started = false;

    public CharacterActor(){
        setBounds(actorX,actorY,texture.getWidth(),texture.getHeight());
        addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                ((CharacterActor)event.getTarget()).started = true;
                return true;
            }
        });
    }

    public void draw(Batch batch, float alpha){
        batch.draw(texture,actorX,actorY);
    }

    @Override
    public void act(float delta){
        if(started){
            actorX+=5;
        }
    }

    private Stage stage;

    public void create() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        CharacterActor myActor = new CharacterActor();
        myActor.setTouchable(Touchable.enabled);
        stage.addActor(myActor);
    }
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
    public void resize(int width, int height) {
    }
    public void pause() {
    }
    public void resume() {
    }
    public void dispose() {
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
