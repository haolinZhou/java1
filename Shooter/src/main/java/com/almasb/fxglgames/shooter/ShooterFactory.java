package com.almasb.fxglgames.shooter;


import com.almasb.fxgl.annotation.SetEntityFactory;
import com.almasb.fxgl.annotation.Spawns;
import com.almasb.fxgl.ecs.Entity;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.CollidableComponent;
import com.almasb.fxgl.entity.control.ProjectileControl;
import javafx.geometry.Point2D;

/**
 * Created by Book on 2017/5/20.
 */
@SetEntityFactory
public class ShooterFactory implements EntityFactory {
    @Spawns("Bullet")
    public Entity newBullet(SpawnData data){
        return Entities.builder()
                .from(data)
                .type(ShooterType.BULLET)
                //.viewFromNodeWithBBox(new Rectangle(10,2, Color.BLUE))
                .viewFromTextureWithBBox("Bullet.png")
                .with(new CollidableComponent(true))
                .with(new ProjectileControl(new Point2D(0,-1),300))
                .build();
    }
    @Spawns("Enemy")
    public Entity newEnemy(SpawnData data){
        return Entities.builder()
                .from(data)
                .type(ShooterType.ENEMY)
               // .viewFromNodeWithBBox(new Rectangle(40,40,Color.RED))
                .viewFromTextureWithBBox("Seeker.png")
                .with(new CollidableComponent(true))
                .build();
    }
}
