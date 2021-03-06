package com.almasb.fxglgames.shooter;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.ecs.Entity;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.almasb.fxgl.service.Input;
import com.almasb.fxgl.settings.GameSettings;
import javafx.scene.input.MouseButton;
import javafx.util.Duration;

import java.util.Map;
//////

/**
 * Created by Book on 2017/5/20.
 */
public class ShooterApp extends GameApplication{
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setTitle("Shooter game");
        settings.setVersion("0.1");
        settings.setProfilingEnabled(false);
        settings.setIntroEnabled(false);
        settings.setCloseConfirmation(false);
        settings.setMenuEnabled(false);
        settings.setIntroEnabled(false);
        settings.setApplicationMode(ApplicationMode.RELEASE);

    }
    @Override
    protected void initInput(){
        Input input = getInput();
        input.addAction(new UserAction("Shoot") {
            @Override
            protected void onActionBegin() {
                // super.onActionBegin();
                getGameWorld().spawn("Bullet",input.getMouseXWorld(),getHeight()-10);

            }
        }, MouseButton.PRIMARY);
    }

    @Override
    protected void initGameVars(Map<String,Object> vars){
        vars.put("enemies",0);
    }

    @Override
    protected void initGame(){
        initBackground();
getGameWorld().spawn("Wall",0,1);
        getMasterTimer().runAtInterval(
                ()-> {
                    int numEnemies = getGameState().getInt("enemies");
                    if (numEnemies<1000){
                        getGameWorld().spawn("Enemy",
                                FXGLMath.random(0,(int)getWidth()-40),
                                FXGLMath.random(0,(int)getHeight()-40)
                        );
                        getGameState().increment("enemies",1);

                    }
                }, Duration.seconds(0.1)
        );
    }


    private void initBackground(){
        GameEntity bg = new GameEntity();
        bg.getViewComponent().setTexture("background.png");
        getGameWorld().addEntity(bg);
    }
    protected void initPhysics() {
        PhysicsWorld physicsWorld = getPhysicsWorld();
        physicsWorld.addCollisionHandler(new CollisionHandler(ShooterType.BULLET, ShooterType.ENEMY) {
            @Override
            protected void onCollisionBegin(Entity bullet, Entity enemy) {
                enemy.removeFromWorld();
                bullet.removeFromWorld();

                getGameState().increment("enemies", -1);
            }
        });
        physicsWorld.addCollisionHandler(new CollisionHandler(ShooterType.BULLET,ShooterType.WALL) {
            @Override
            protected void onCollisionBegin(Entity bullet, Entity wall) {
             bullet.removeFromWorld();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}



