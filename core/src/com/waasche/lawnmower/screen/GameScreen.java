package com.waasche.lawnmower.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Timer;
import com.waasche.lawnmower.controller.LawnmowerCameraController;
import com.waasche.lawnmower.data.*;
import com.waasche.lawnmower.main.MainClass;
import com.waasche.lawnmower.resources.Assets;
import com.waasche.lawnmower.resources.Sounds;
import com.waasche.lawnmower.services.LevelService;

import java.util.ArrayList;


public class GameScreen implements Screen {
    private static final int TARGET_WIDTH = 560;
    private static final float UNIT_TO_PIXEL = TARGET_WIDTH * 0.15f;
    public static final int COMPLEXITY_INDEX = 0;
    private int levelInd;
    private int boardSize;
    private OrthographicCamera cam;
    private SpriteBatch fieldSpritesBatch;
    private SpriteBatch bgBatch;
    private Sprite[][] sprites;
    final Matrix4 matrix = new Matrix4();
    final Plane xzPlane = new Plane(new Vector3(0, 1, 0), 0);
    final Vector3 intersection = new Vector3();
    private Lawnmower lawnmower;
    private Level currentLevel = null;
    FieldPoint incPosition = null;
    private MainClass mainClass = null;
    private boolean isMoving = false;
    private FieldLine currentLine;
    private LevelTypeMetaData levelTypeMetaData;
    private boolean isStopped = false;
    private Label levelLabel;
    private Label movesCount;
    private Label scoresLabel;


    public GameScreen(MainClass mainClass, LevelTypeMetaData levelPackMetaData, int levelInd) {
        this.mainClass = mainClass;
        this.levelInd = levelInd;
        this.boardSize = levelPackMetaData.getId();
        this.levelTypeMetaData = levelPackMetaData;
        this.currentLevel = Assets.levelsList.get(this.boardSize).getLevels().get(this.levelInd - 1);
        this.currentLevel.setPassedLines(new ArrayList<FieldLine>());
        this.currentLevel.setPassedPoints(new ArrayList<FieldPoint>());
        Gdx.graphics.setContinuousRendering(true);
        levelLabel = new Label(Assets.strings.get("level") + " " + levelTypeMetaData.getId() + "/" + levelInd, Assets.textStyle);
        movesCount = new Label(Assets.strings.get("moves") + " 0", Assets.textStyle);
        scoresLabel = new Label(Assets.strings.get("scores") + " 0", Assets.textStyle);
        show();
    }

    @Override
    public void show() {
        lawnmower = Assets.lawnmowerUpRight;
        Sounds.lwStarts(0.1f);
        cam = new OrthographicCamera(currentLevel.getSizeX() * 2, currentLevel.getSizeY() * 2 * (Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));
        cam.position.set(currentLevel.getSizeX(), currentLevel.getSizeY() * 0.5f, currentLevel.getSizeX());
        cam.direction.set(-1, -1, -1);
        cam.near = 1;
        cam.far = 50;
        matrix.setToRotation(new Vector3(1, 0, 0), 90);
        sprites = new Sprite[currentLevel.getSizeX()][currentLevel.getSizeY()];
        for (int z = 0; z < currentLevel.getSizeX(); z++) {
            for (int x = 0; x < currentLevel.getSizeY(); x++) {
                if (LevelService.isWall(currentLevel, new FieldPoint(x, z))) {
                    sprites[x][z] = new Sprite(Assets.wallSprite.getTexture());
                } else {
                    sprites[x][z] = new Sprite(Assets.grassFull.getTexture());
                }
                sprites[x][z].setPosition(x, z);
                sprites[x][z].setSize(1, 1);
            }
        }
        FieldPoint startPoint = new FieldPoint(currentLevel.getLevelTypes().get(COMPLEXITY_INDEX).getStart().getX(), currentLevel.getLevelTypes().get(COMPLEXITY_INDEX).getStart().getY());
        lawnmower.setLawnmowerPosition(startPoint);
        currentLine = new FieldLine(startPoint, null);
        currentLevel.addPassedPoints(startPoint);
        lawnmower.setSize(1, 1);
        fieldSpritesBatch = new SpriteBatch();
        bgBatch = new SpriteBatch();
        Gdx.input.setInputProcessor(new LawnmowerCameraController(cam));
    }

    @Override
    public void render(float delta) {
        Moves move = getMoveByTouchedTile();
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP) && !isMoving || move.equals(Moves.UP)) {
            incPosition = Moves.UP.getFieldPoint();
            lawnmower.upTexture();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN) && !isMoving || move.equals(Moves.DOWN)) {
            incPosition = Moves.DOWN.getFieldPoint();
            lawnmower.downTexture();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT) && !isMoving || move.equals(Moves.LEFT)) {
            incPosition = Moves.LEFT.getFieldPoint();
            lawnmower.leftTexture();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT) && !isMoving || move.equals(Moves.RIGHT)) {
            incPosition = Moves.RIGHT.getFieldPoint();
            lawnmower.rightTexture();
        }
        if (incPosition != null) {
            FieldPoint movePosition = new FieldPoint((int) lawnmower.getX() + incPosition.getX(), (int) lawnmower.getY() + incPosition.getY());
            if (LevelService.isAllowToMove(currentLevel, movePosition)) {
                currentLine.addPoint(movePosition);
                currentLevel.addPassedPoints(movePosition);
                lawnmower.setLawnmowerPosition(movePosition);
                movesCount.setText(Assets.strings.get("moves") + " " + currentLevel.getPassedPoints().size());
                scoresLabel.setText(Assets.strings.get("scores") + " " + currentLevel.getPassedPoints().size()*124);
                if (LevelService.isFail(currentLevel, currentLine)) {
                    Sounds.playCrashSound(0.1f);
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                          backToMenu();
                        }
                    }, Assets.DELAY_TIME_IN_SECONDS);
                }
                if (currentLine.getEnd() != null) {
                    currentLevel.addLineToPassedLines(currentLine);
                    currentLine = new FieldLine(movePosition, null);
                }
                if (LevelService.isOver(currentLevel, COMPLEXITY_INDEX)) {
                    moveToNextLevel();
                }
                isStopped = false;
            } else {
                isMoving = false;
                if(!isStopped) {
                    Sounds.playTickSound(0.1f);
                    isStopped = true;
                }
                if (LevelService.isOver(currentLevel, COMPLEXITY_INDEX)) {
                    moveToNextLevel();
                }
            }
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();
        bgBatch.begin();
        Assets.spriteBackground.draw(bgBatch);
        levelLabel.draw(bgBatch, 1f);
        movesCount.setPosition(Assets.ANDROID_HEIGHT - Assets.SCREEN_UNIT, 0.0f);
        scoresLabel.setPosition(0f, Assets.SCREEN_UNIT*2f);
        movesCount.draw(bgBatch, 1f);
        scoresLabel.draw(bgBatch,1f);
        bgBatch.end();
        fieldSpritesBatch.setProjectionMatrix(cam.combined);
        fieldSpritesBatch.setTransformMatrix(matrix);
        fieldSpritesBatch.begin();
        for (int z = 0; z < currentLevel.getSizeX(); z++) {
            for (int x = 0; x < currentLevel.getSizeY(); x++) {
                sprites[x][z].draw(fieldSpritesBatch);
            }
        }
        sprites[lawnmower.getLawnmowerPosition().getX()][lawnmower.getLawnmowerPosition().getY()].setTexture(Assets.grassEmpty.getTexture());
        sprites[lawnmower.getLawnmowerPosition().getX()][lawnmower.getLawnmowerPosition().getY()].setSize(1, 1);
        lawnmower.draw(fieldSpritesBatch);
        fieldSpritesBatch.end();
    }

    private void moveToNextLevel() {
        if (levelInd == Assets.levelsList.get(boardSize).getLevels().size()) {
            GameProgress.setCompleted("" + boardSize, "" + levelInd, true);
            backToMenu();
        } else {
            GameProgress.setCompleted("" + boardSize, "" + levelInd, true);
            mainClass.setCurrentScreen(new GameScreen(mainClass, levelTypeMetaData, levelInd + 1));
            this.dispose();
        }
    }

    private void backToMenu() {
        mainClass.setCurrentScreen(new LevelSelectScreen(mainClass, levelTypeMetaData));
        mainClass.showCurrentScreen();
        this.dispose();
    }

    private Moves getMoveByTouchedTile() {
        if (Gdx.input.justTouched()) {
            Ray pickRay = cam.getPickRay(Gdx.input.getX(), Gdx.input.getY());
            Intersector.intersectRayPlane(pickRay, xzPlane, intersection);
            int x = (int) intersection.x;
            int z = (int) intersection.z;
            if (lawnmower.getLawnmowerPosition().getX() - x < 0) {
                return Moves.RIGHT;
            }
            if (lawnmower.getLawnmowerPosition().getX() - x > 0) {
                return Moves.LEFT;
            }
            if (lawnmower.getLawnmowerPosition().getY() - z < 0) {
                return Moves.DOWN;
            }
            if (lawnmower.getLawnmowerPosition().getY() - z > 0) {
                return Moves.UP;
            }
        }
        return Moves.NONE;
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
        dispose();
    }

    @Override
    public void dispose() {

    }
}
