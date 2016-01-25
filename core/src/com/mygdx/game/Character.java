package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;


/**
 * Created by Justin on 2015-11-02.
 */
public class Character implements ApplicationListener {
    SpriteBatch batch;
    TextureAtlas taBomberman;
    Sprite[] spBomberman;
    int i = 0, nCurrentIndex;
    float stateTime, fCharacterVelocityX = 0, fCharacterVelocityY = 0, fCharacterX, fCharacterY, fCharacterWidth, fOldX, fOldY;
    int nVelocityX, nVelocityY;
    boolean[] arbDirection = new boolean[4];//0=up, 1=down, 2=right, 3=left
    boolean bStop = true, bCollidedX, bCollidedY;
    Sprite sprChar;
    int nSHeight, nSWidth, nLayerCount, nC = 0, nTileHeight, nTileWidth, nYtiles, nCharacterWidth;
    Map map;
    String sID;
    Stage stage;
    Actor actor;
    float fCharacterHeight=0;
    private Array<Rectangle> tiles;
    Array<Rectangle> collisionRects = new Array<Rectangle>();
    MapObjects collisionObjects;

    private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
        @Override
        protected Rectangle newObject () {
            return new Rectangle();
        }
    };

    public void setMap(Map _map, int _nYtiles) {
        nYtiles = _nYtiles;
        map = _map;
    }

    public void create() {
        collisionObjects = new MapObjects();
        collisionObjects = map.getCollisionLayer();

        //collisionObjects =  map.tiledMap.getLayers().get("collision").getObjects();
     //   MapObjects objects = map.tiledMap.getLayers().get("collision").getObjects();
        int tileWidth = 32; // whatever your tile width is
        int tileHeight = 32; // whatever your tile height is
        for (int i = 0; i < collisionObjects.getCount(); i++) {
            RectangleMapObject obj = (RectangleMapObject) collisionObjects.get(i);
            Rectangle rect = obj.getRectangle();
            //collisionRects.add(new Rectangle(rect.x / nTileWidth, rect.y / nTileHeight, rect.width / nTileWidth, rect.height / nTileHeight));
            collisionRects.add(new Rectangle(rect.x/nTileWidth, rect.y/nTileHeight, rect.width/nTileWidth, rect.height/nTileHeight));
        }

      //  tiles = new Array<Rectangle>();
        stage = new Stage();
        actor = new Actor();
//        collisionObjects = map.tiledMap.getLayers().get("CollisionLayer").getObjects();
        // cam = map.getCam();
        nSHeight = Gdx.graphics.getHeight(); //use to make scaling
        nSWidth = Gdx.graphics.getWidth();
        nVelocityX = nSWidth * 10 / 1794;
        nVelocityY = nSHeight * 10 / 1080;
        fCharacterWidth = nSWidth * 110 / 1794;
        arbDirection[0] = true;
        batch = new SpriteBatch();
        taBomberman = new TextureAtlas(Gdx.files.internal("bomberchar.pack"));
        //txChar = new Texture(new TextureRegion(taBomberman.findRegion("frame-0.png")));
        //    txChar= new Texture(taBomberman.findRegion("frame-0.png"));
        spBomberman = new Sprite[4];
        sprChar = new Sprite();
        fCharacterX = (map.nMapWidth - 175);
        fCharacterY = map.nMapHeight - 350;
        nTileHeight = (int) map.collisionLayer.getTileHeight();
        nTileWidth = (int) map.collisionLayer.getTileWidth();
        sprChar = new Sprite(taBomberman.findRegion("frame-0"));
        sprChar.setBounds(fCharacterX, fCharacterY, 1, 1);

      //  nCharacterWidth = nSWidth * 110 / 1794;
        fCharacterWidth = sprChar.getWidth();
        fCharacterHeight = sprChar.getHeight();
        actor.setBounds(sprChar.getX(), sprChar.getY(), fCharacterWidth, fCharacterHeight);
        actor.setTouchable(Touchable.enabled);
        stage.addActor(actor);
    }

    @Override
    public void resize(int width, int height) {
    }


    public void setCharacterVelocity(int _nVx, int _nVy) {
        fCharacterVelocityX = nVelocityX * _nVx;
        fCharacterVelocityY = nVelocityY * _nVy;
    }



    public void render() {

        nC++;
        fOldX = fCharacterX;
        fOldY = fCharacterY;
        bCollidedX = false;
        bCollidedY = false;
        //Gdx.gl.glClearColor(0, 0, 0, 1);
        // Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        fCharacterX += fCharacterVelocityX / 2;
        fCharacterY += fCharacterVelocityY / 2;

     //   sID = getTileID(fCharacterX, fCharacterY, nCharacterWidth);
/*        if (sID.equalsIgnoreCase("block")) {
            fCharacterX = fOldX;
            fCharacterY = fOldY;
        }
        System.out.println(sID);
        if (nC % 15 == 0) {
            System.out.println("X: " + (int) fCharacterX / nTileWidth + " Y: " + (int) (nYtiles - (fCharacterY / nTileHeight)));
        }*/


     /*   Rectangle playerRect = rectPool.obtain();
        playerRect.set(fCharacterX, fCharacterY + sprChar.getHeight()*0.1f, sprChar.getWidth(),sprChar.getHeight());

        int startX, startY, endX, endY;
        if (fCharacterVelocityX > 0) {
            startX = endX = (int)(fCharacterX + sprChar.getWidth() + fCharacterVelocityX);
        } else {
            startX = endX = (int)(fCharacterVelocityX + fCharacterVelocityX);
        }

        startY = (int)(fCharacterY);
        endY = (int)(fCharacterY+ sprChar.getHeight());
        getTiles(startX, startY, endX, endY, tiles);



        for (Rectangle tile : tiles) {

            if (playerRect.overlaps(tile)) {
                System.out.println("collsion");
                if(player.getVelocity().x > 0){
                    player.getPosition().x = tile.x - TILEWIDTH - TILEWIDTH * 0.40f;
                }else if(player.getVelocity().x < 0){
                    player.getPosition().x = tile.x + TILEWIDTH + TILEWIDTH * 0.05f;
                }

                player.getVelocity().x = 0;

                break;
            }
            if(playerRect.overlaps(tile)){

            }
        }
*/
        for(int i=0;i<collisionRects.size;i++){
            if(collisionRects.get(i).overlaps(sprChar.getBoundingRectangle())){
                System.out.println("collision");


               // collisionRects.get(i).getPosition();
            }
            System.out.println("rect X: "+collisionRects.get(i).getX()+"rect Y "+collisionRects.get(i).getY());
        }
        if(nC%60==0){
          //  System.out.println("X "+collisionRects.get(1).getX()+" Y "+ collisionRects.get(1).getY());
            System.out.println(" char x "+fCharacterX+" char y "+fCharacterY);
        }

        batch.begin();
        batch.draw(sprChar, fCharacterX, fCharacterY);
        batch.end();
    }

    private void getTiles (int startX, int startY, int endX, int endY, Array<Rectangle> tiles) {
        TiledMapTileLayer layer = (TiledMapTileLayer)map.tiledMap.getLayers().get(0);
        rectPool.freeAll(tiles);
        tiles.clear();
        for (int y = startY; y <= endY; y++) {
            for (int x = startX; x <= endX; x++) {
                System.out.println("got tile");
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell != null) {
                    Rectangle rect = rectPool.obtain();
                    rect.set(x, y, 1, 1);
                    tiles.add(rect);
                }
            }
        }
    }
    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        sprChar.getTexture().dispose();
    }

    public void getBoolsBack(boolean[] _arbDirection, boolean _bStop, int _nCurrentIndex) {
        arbDirection = _arbDirection;
        bStop = _bStop;
        nCurrentIndex = _nCurrentIndex;
    }
}
