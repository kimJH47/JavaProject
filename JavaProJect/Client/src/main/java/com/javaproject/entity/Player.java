package com.javaproject.entity;


import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import com.javaproject.io.Window;
import com.javaproject.render.Animation;
import com.javaproject.render.Camera;
import com.javaproject.world.World;

public class Player extends Entity {
    public static final int ANIM_IDLE = 0;
    public static final int ANIM_WALK = 1;
    public static final int ANIM_SIZE = 2;

    private int PlayerNumber = 1; // 사용자의 번호
    int PlayerSlot; // 해당 캐릭터 자리

    public Player(Transform transform,int ps) {
        super(ANIM_SIZE, transform);
        this.PlayerSlot=ps;
        setAnimation(ANIM_IDLE, new Animation(4, 2, "player/idle"));
        setAnimation(ANIM_WALK, new Animation(4, 2, "player/walking"));
    }

    @Override
    public void update(float delta, Window window, Camera camera, World world) {
        if(PlayerSlot==PlayerNumber)
        {
            Vector2f movement = new Vector2f();

            if (window.getInput().isKeyDown(GLFW.GLFW_KEY_A)) movement.add(-10 * delta, 0);

            if (window.getInput().isKeyDown(GLFW.GLFW_KEY_D)) movement.add(10 * delta, 0);

            if (window.getInput().isKeyDown(GLFW.GLFW_KEY_W)) movement.add(0, 10 * delta);

            if (window.getInput().isKeyDown(GLFW.GLFW_KEY_S)) movement.add(0, -10 * delta);

            move(movement);

            if (movement.x != 0 || movement.y != 0)
                useAnimation(ANIM_WALK);
            else useAnimation(ANIM_IDLE);

            camera.getPosition().lerp(transform.pos.mul(-world.getScale(), new Vector3f()), 0.05f);
        }
        //플레이어 위치 따라다님
    }
}