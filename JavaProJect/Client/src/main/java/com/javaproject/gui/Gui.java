package com.javaproject.gui;

import org.joml.Vector2f;

import com.javaproject.io.Input;
import com.javaproject.io.Window;
import com.javaproject.render.*;
import java.awt.Button;

public class Gui {
    private Shader shader;
    private Camera camera;
    private TileSheet sheet;

    private Button temporary;

    public Gui(Window window) {
        shader = new Shader("gui");
        camera = new Camera(window.getWidth(), window.getHeight());
        sheet = new TileSheet("gui.png", 9);
    }

    public void resizeCamera(Window window) {
        camera.setProjection(window.getWidth(), window.getHeight());
    }

    public void update(Input input) {
        //temporary.update(input);
    }

    public void render() {
        shader.bind();
        //temporary.render(camera, sheet, shader);//버튼 화면에 표시
    }
}