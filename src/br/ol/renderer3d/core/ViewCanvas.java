package br.ol.renderer3d.core;

import br.ol.animation.character.Roll;
import br.ol.math.Vec2;
import br.ol.math.Vec4;
import br.ol.mesh.wavefront.Obj;
import br.ol.mesh.wavefront.WavefrontParser;
import static br.ol.renderer3d.core.Renderer.MatrixMode.*;
import br.ol.renderer3d.shader.GouraudShaderWithTexture;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author leo
 */
public class ViewCanvas extends Canvas {
    
    private boolean running = false;
    private BufferStrategy bs;

    private Renderer renderer;
    private Thread thread;
    
    private Shader gouraudShader = new GouraudShaderWithTexture();
    
    private Light light = new Light();

    private List<Obj> room;
    private Roll roll;
    
    public ViewCanvas() {
        addKeyListener(new KeyHandler());
    }
    
    public void init() {
        createBufferStrategy(2);
        bs = getBufferStrategy();
        renderer = new Renderer(440, 330);
        
        try {
            room = new ArrayList<Obj>(WavefrontParser.load("/res/crystal.obj", 40));
        } catch (Exception ex) {
            Logger.getLogger(ViewCanvas.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
        roll = new Roll();
        
        // light
        light.diffuse.set(1, 1, 1, 1);
        renderer.addLight(light);

        // shader
        renderer.setShader(gouraudShader);
        
        renderer.setMatrixMode(PROJECTION);
        renderer.setPerspectiveProjection(Math.toRadians(90));
        renderer.setClipZNear(-1);

        running = true;
        thread = new Thread(new MainLoop());
        thread.start();
        
        MouseHandler mouseHandler = new MouseHandler();
        addMouseMotionListener(mouseHandler);
    }
    
    private double cameraTargetAngleX = 0;
    private double cameraTargetAngleY = 0;

    private double cameraAngleX = 0;
    private double cameraAngleY = 0;
    private Vec4 cameraPosition = new Vec4(0, 50, 100, 1);
    
    private void moveCamera(double d, double dAngle) {
        double s = Math.sin(cameraAngleY + dAngle);
        double c = Math.cos(cameraAngleY + dAngle);
        cameraPosition.x += (c * d);
        cameraPosition.z += (s * d);
    }
    
    public void update() {
        cameraTargetAngleX = Mouse.y;
        cameraTargetAngleY = Mouse.x;
        
        cameraAngleX += (Math.toRadians(cameraTargetAngleX * 0.5) - cameraAngleX) * 0.1;
        cameraAngleY += (Math.toRadians(cameraTargetAngleY * 0.5) - cameraAngleY) * 0.1;
        
        double velocity = 1;
        
        if (Keyboard.keyDown[38]) {
            moveCamera(-velocity, Math.toRadians(90));
        }
        else if (Keyboard.keyDown[40]) {
            moveCamera(velocity, Math.toRadians(90));
        }
        
        if (Keyboard.keyDown[37]) {
            moveCamera(-velocity, 0);
        }
        else if (Keyboard.keyDown[39]) {
            moveCamera(velocity, 0);
        }

        if (Keyboard.keyDown[87]) {
            cameraPosition.y -= velocity;
        }
        else if (Keyboard.keyDown[83]) {
            cameraPosition.y += velocity;
        }
        
        updateCharacterAnimation();

        renderer.clearAllBuffers(); 

        renderer.setBackfaceCullingEnabled(true);
        drawRoom();
        renderer.setBackfaceCullingEnabled(false);
        drawRollCharacter();
    }
    
    private void updateCharacterAnimation() {
        roll.update();
    }

    private void drawRoom() {
        renderer.setMatrixMode(VIEW);
        renderer.setIdentity();
        renderer.rotateX(-cameraAngleX);
        renderer.rotateY(cameraAngleY);
        renderer.translate(-cameraPosition.x, -cameraPosition.y, -cameraPosition.z);
        
        renderer.setMatrixMode(Renderer.MatrixMode.MODEL);
        renderer.setIdentity();

        for (Obj obj : room) {
            renderer.setMaterial(obj.material);
            renderer.begin();
            for (WavefrontParser.Face face : obj.faces) {
                for (int f=0; f<3; f++) {
                    Vec4 v = face.vertex[f];
                    Vec4 n = face.normal[f];
                    Vec2 t = face.texture[f];
                    renderer.setTextureCoordinates(t.x, t.y);
                    renderer.setNormal(n.x, n.y, n.z);
                    renderer.setVertex(v.x, v.y, v.z);
                }
            }
            renderer.end();            
        }
    }
    
    private void drawRollCharacter() {
        renderer.setMatrixMode(VIEW);
        renderer.setIdentity();
        renderer.rotateX(-cameraAngleX);
        renderer.rotateY(cameraAngleY);
        renderer.translate(-cameraPosition.x, -cameraPosition.y, -cameraPosition.z);

        roll.draw(renderer);
    }
    
    public void draw(Graphics2D g) {
        Graphics2D g2d = (Graphics2D) renderer.getColorBuffer().getColorBuffer().getGraphics();
        g2d.setColor(Color.WHITE);
        g2d.drawString("FPS: " + Time.fps, 10, 20);
        
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);        
        g2d.drawImage(renderer.getColorBuffer().getColorBuffer(), 0, 0, 400, 300, 0, 0, 440, 330, null);
        
        g.drawImage(renderer.getColorBuffer().getColorBuffer(), 0, 0, 800, 600, 0, 0, 400, 300, null);
    }
    
    private class MainLoop implements Runnable {

        @Override
        public void run() {
            while (running) {
                Time.update();
                update();
                Graphics2D g = (Graphics2D) bs.getDrawGraphics();
                draw(g);
                g.dispose();
                bs.show();
            }
        }
        
    }

    private class MouseHandler extends MouseAdapter {

        @Override
        public void mouseMoved(MouseEvent e) {
            Mouse.x = e.getX() - getWidth() * 0.5;
            Mouse.y = getHeight() * 0.5 - e.getY();
        }
        
    }

    private class KeyHandler extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            Keyboard.keyDown[e.getKeyCode()] = true;
        }

        @Override
        public void keyReleased(KeyEvent e) {
            Keyboard.keyDown[e.getKeyCode()] = false;
        }
        
    }
    
}
