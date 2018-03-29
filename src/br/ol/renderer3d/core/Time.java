package br.ol.renderer3d.core;

/**
 *
 * @author leonardo
 */
public class Time {

    public static long delta = 0;
    public static int fps = 0;
    
    private static int fpsCount;
    private static long fpsTime;
    private static long lastTime;
    
    public static void update() {
        long currentTime = System.nanoTime();
        delta = currentTime - lastTime;
        fpsTime += delta;
        if (fpsTime > 1000000000) {
            fps = fpsCount;
            fpsTime = fpsCount = 0;
        }
        else {
            fpsCount++;
        }
        lastTime = currentTime;
    }
    
}
