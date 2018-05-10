package com.waasche.lawnmower.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.waasche.lawnmower.data.*;
import com.waasche.lawnmower.main.MainClass;
import com.waasche.lawnmower.resources.Assets;
import com.waasche.lawnmower.resources.Sounds;
import com.waasche.lawnmower.services.LevelService;

import java.util.ArrayList;


public class GameScreen implements Screen {
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
    private FieldPoint incPosition = null;
    private MainClass mainClass = null;
    private boolean isMoving = false;
    private FieldLine currentLine;
    private LevelTypeMetaData levelTypeMetaData;
    private boolean isStopped = false;
    private Label levelLabel;
    private Label attemptsLabel;
    private Label movesCount;
    private Label scoresLabel;
    private Sprite levelTextRectangle = Assets.levelTextRectangle;
    private Sprite attemptTextRectangle = Assets.attemptTextRectangle;
    private Actor startFromBeginningActor;
    private Actor leftButtonActor;
    private Actor rightButtonActor;
    private Actor downButtonActor;
    private Actor upButtonActor;
    private Stage stage;
    private boolean isStartFromBeginning = false;
    private Moves move = Moves.NONE;
    private boolean isPaused;


    public GameScreen(MainClass mainClass, LevelTypeMetaData levelPackMetaData, int levelInd) {
        Sounds.playBackgroundMusic(0);
        this.mainClass = mainClass;
        this.levelInd = levelInd;
        this.boardSize = levelPackMetaData.getId();
        this.levelTypeMetaData = levelPackMetaData;
        this.currentLevel = Assets.levelsList.get(this.boardSize).getLevels().get(levelInd - 1);
        this.currentLevel.setPassedLines(new ArrayList<FieldLine>());
        this.currentLevel.setPassedPoints(new ArrayList<FieldPoint>());
        GameProgress.setAttempt("" + levelPackMetaData.getId(), "" + levelInd);
        Gdx.graphics.setContinuousRendering(true);
        prepareStage(levelInd);
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);
        show();
    }

    private void prepareStage(int levelInd) {
        Assets.labelStyle.font = Assets.fontLarge;
        Assets.mediumLableStyle.font = Assets.fontMedium;
        Assets.smallLabelStyle.font = Assets.fontSmall;
        levelLabel = new Label(Assets.strings.get("level") + " " + levelTypeMetaData.getId() + "/" + levelInd, Assets.labelStyle);
        levelLabel.setX(6f * Assets.SCREEN_UNIT);
        levelLabel.setY(1.1f * Assets.SCREEN_UNIT);
        attemptsLabel = new Label(Assets.strings.get("attempt") + " " + GameProgress.getAttempt("" + levelTypeMetaData.getId(), "" + levelInd), Assets.mediumLableStyle);
        attemptsLabel.setX(65f*Assets.SCREEN_UNIT);
        attemptsLabel.setY(2.4f*Assets.SCREEN_UNIT);
        attemptsLabel.setX(65f*Assets.SCREEN_UNIT);
        attemptsLabel.setY(1.0f*Assets.SCREEN_UNIT);
        levelTextRectangle.setOrigin(levelLabel.getOriginX(), levelLabel.getOriginY());
        attemptTextRectangle.setX(attemptsLabel.getX() + 10f*Assets.SCREEN_UNIT);
        attemptTextRectangle.setY(attemptsLabel.getY() + 0.2f*Assets.SCREEN_UNIT);
        movesCount = new Label(Assets.strings.get("moves") + " 0", Assets.labelStyle);
        scoresLabel = new Label(Assets.strings.get("scores") + " 0", Assets.labelStyle);
        stage = new Stage();
        startFromBeginningActor = new Image(Assets.spriteButtonBack);
        startFromBeginningActor.setPosition(Assets.ANDROID_WIDTH - Assets.SCREEN_UNIT * 20f, Assets.ANDROID_HEIGHT * 0.7f);
        startFromBeginningActor.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                isStartFromBeginning = true;
                restartCurrentLevel();
            }
        });
        leftButtonActor = new Image(Assets.spriteButtonArrowLeft);
        leftButtonActor.setPosition(Assets.ANDROID_WIDTH - Assets.SCREEN_UNIT * 23f, Assets.ANDROID_HEIGHT * 0.2f);
        leftButtonActor.setRotation(-45);
        leftButtonActor.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                move = Moves.LEFT;
            }
        });
        rightButtonActor = new Image(Assets.spriteButtonArrowRight);
        rightButtonActor.setPosition(Assets.ANDROID_WIDTH - Assets.SCREEN_UNIT * 15f, Assets.ANDROID_HEIGHT * 0.2f);
        rightButtonActor.setRotation(-45);
        rightButtonActor.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                move = Moves.RIGHT;
            }
        });
        upButtonActor = new Image(Assets.spriteButtonArrowUp);
        upButtonActor.setRotation(45);
        upButtonActor.setPosition(Assets.ANDROID_WIDTH - Assets.ANDROID_WIDTH * 0.9f, Assets.ANDROID_HEIGHT * 0.36f);
        upButtonActor.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                move = Moves.UP;
            }
        });
        downButtonActor = new Image(Assets.spriteButtonArrowDown);
        downButtonActor.setRotation(-135);
        downButtonActor.setPosition(Assets.ANDROID_WIDTH - Assets.ANDROID_WIDTH * 0.9f, Assets.ANDROID_HEIGHT * 0.4f);
        downButtonActor.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                move = Moves.DOWN;
            }
        });

        stage.addActor(startFromBeginningActor);
        stage.addActor(leftButtonActor);
        stage.addActor(rightButtonActor);
        stage.addActor(upButtonActor);
        stage.addActor(downButtonActor);
    }

    private void startNextLevel(){
        Sounds.continueMusic();
        GameProgress.setCompleted("" + boardSize, "" + levelInd, true);
        mainClass.setCurrentScreen(new GameScreen(mainClass, levelTypeMetaData, levelInd + 1));
    }

    @Override
    public void show() {
        lawnmower = Assets.lawnmowerUpRight;
        Sounds.playBackgroundMusic(0.1f);
        cam = new OrthographicCamera(currentLevel.getSizeX() * 2, currentLevel.getSizeY() * 2 * (Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));
        cam.position.set(currentLevel.getSizeX(), currentLevel.getSizeY() * 0.5f, currentLevel.getSizeX());
        cam.direction.set(-1, -1, -1);
        cam.near = 1;
        cam.far = 50;
        matrix.setToRotation(new Vector3(1, 0, 0), 90);
        sprites = new Sprite[currentLevel.getSizeY()][currentLevel.getSizeX()];
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
    }

    @Override
    public void render(float delta) {
        drawBgBatch();
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && isPaused){
            isPaused = false;
            startNextLevel();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            backToMenu();
            return;
        }
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
            if (LevelService.isAllowToMove(currentLevel, movePosition) && !isStartFromBeginning) {
                currentLine.addPoint(movePosition);
                currentLevel.addPassedPoints(movePosition);
                lawnmower.setLawnmowerPosition(movePosition);
                movesCount.setText(Assets.strings.get("moves") + " " + currentLevel.getPassedPoints().size());
                scoresLabel.setText(Assets.strings.get("scores") + " " + currentLevel.getPassedPoints().size() * 124);
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
                if(!isStartFromBeginning) {
                    isMoving = false;
                    if (!isStopped && !isPaused) {
                        Sounds.playStopSound(0.1f);
                        isStopped = true;
                    }
                    if (LevelService.isOver(currentLevel, COMPLEXITY_INDEX)) {
                        moveToNextLevel();
                    }
                }
            }
        }
        cam.update();
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

    private void drawBgBatch() {
        bgBatch.begin();
        Assets.imageBackground.draw(bgBatch);
        levelTextRectangle.draw(bgBatch);
        attemptTextRectangle.draw(bgBatch);
        levelLabel.draw(bgBatch, 0.8f);
        attemptsLabel.draw(bgBatch, 0.8f);
        bgBatch.end();
        stage.draw();
        stage.act();

    }

    private void moveToNextLevel() {
        if (levelInd == Assets.levelsList.get(boardSize).getLevels().size()) {
            GameProgress.setCompleted("" + boardSize, "" + levelInd, true);
            backToMenu();
        } else {
            Sounds.stopMusic();
            if(!isPaused) {
                isStartFromBeginning = true;
                Sounds.playLevelFinished(0);
            }
            isPaused = true;
        }
        GameProgress.setFirstSuccess("" + levelTypeMetaData.getId(),"" + levelInd);
    }


    private void restartCurrentLevel() {
        mainClass.setCurrentScreen(new GameScreen(mainClass, levelTypeMetaData, levelInd));
        this.dispose();
    }

    private void backToMenu() {
        if (!isStartFromBeginning) {
            mainClass.setCurrentScreen(new LevelSelectScreen(mainClass, levelTypeMetaData));
            mainClass.showCurrentScreen();
            this.dispose();
        }
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
