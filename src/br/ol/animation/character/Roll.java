package br.ol.animation.character;

import br.ol.animation.bvh.Node;
import br.ol.animation.bvh.Skeleton;
import br.ol.math.Vec2;
import br.ol.math.Vec4;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import br.ol.renderer3d.core.Renderer;
import br.ol.renderer3d.core.Renderer.MatrixMode;
import br.ol.renderer3d.core.Time;
import br.ol.renderer3d.core.ViewCanvas;
import br.ol.mesh.wavefront.Obj;
import br.ol.mesh.wavefront.WavefrontParser;

/**
 * Roll character animation test
 * using 3D Hierarchic Articulated Object method
 * 
 * @author leonardo
 */
public class Roll {

    private Skeleton skeleton;
    private final Map<String, List<Obj>> meshes = new HashMap<String, List<Obj>>();
    private final Map<String, List<Obj>> meshesArticulation = new HashMap<String, List<Obj>>();
    private boolean animate = true; // false = T position
    private double frameIndex;
    
    public Roll() {
        loadSkeleton();
        loadMeshes();
    }
    
    private void loadSkeleton() {
        skeleton = new Skeleton("137_23.bvh"); // 
        
        //skeleton = new Skeleton("137_37.bvh"); // 
        //skeleton = new Skeleton("137_38.bvh"); // 
        //skeleton = new Skeleton("144_03.bvh"); // 
        
        //skeleton = new Skeleton("144_01.bvh"); // <-- estrela
        
       // skeleton = new Skeleton("144_03.bvh"); // <-- walking
        
        //skeleton = new Skeleton("144_05.bvh"); // <-- kick
        //skeleton = new Skeleton("144_06.bvh"); // <-- kick 2
        
        //skeleton = new Skeleton("144_30.bvh"); // <-- exercicios 
        //skeleton = new Skeleton("135_01.bvh"); // <-- luta artes marciais
        
        //skeleton = new Skeleton("124_01.bvh"); // <-- baseball jogar bola
        //skeleton = new Skeleton("124_07.bvh"); // <-- baseball rebater
        
        //skeleton = new Skeleton("120_22.bvh"); // <-- zombie
        //skeleton = new Skeleton("105_05.bvh"); // <-- mummy
        //skeleton = new Skeleton("105_08.bvh"); // <-- mummy
        
        // skeleton = new Skeleton("105_24.bvh"); // <-- hurt leg walking
        
        //skeleton = new Skeleton("104_41.bvh"); // <-- zombie
        //skeleton = new Skeleton("104_43.bvh"); // <-- zombie cycle
        
        //skeleton = new Skeleton("94_05.bvh"); // <-- indian dance
        
        // skeleton = new Skeleton("90_02.bvh"); // <-- estrela
        
        //skeleton = new Skeleton("90_05.bvh"); // <-- jump kick
        //skeleton = new Skeleton("90_28.bvh"); // <-- break dance
        
        // skeleton = new Skeleton("02_08.bvh"); // <-- sword / parece rebatendo tennis
        // skeleton = new Skeleton("02_09.bvh"); // <-- sword / parece rebatendo tennis com a outra mao
        
        //skeleton = new Skeleton("01_06.bvh"); 
    }
    
    private void loadMeshes() {
        try {
            meshes.put("Head", new ArrayList<Obj>(WavefrontParser.load("/res/Roll_Head.obj", 6)));
            meshes.put("Neck1", new ArrayList<Obj>(WavefrontParser.load("/res/Roll_Neck.obj", 6)));
            meshes.put("Hips", new ArrayList<Obj>(WavefrontParser.load("/res/Roll_Hip.obj", 6)));
            meshes.put("Spine", new ArrayList<Obj>(WavefrontParser.load("/res/Roll_Spine.obj", 6)));
            meshes.put("RightUpLeg", new ArrayList<Obj>(WavefrontParser.load("/res/Roll_RightUpLeg.obj", 6)));
            meshes.put("LeftUpLeg", new ArrayList<Obj>(WavefrontParser.load("/res/Roll_LeftUpLeg.obj", 6)));
            meshes.put("RightLeg", new ArrayList<Obj>(WavefrontParser.load("/res/Roll_RightLeg.obj", 6)));
            meshes.put("LeftLeg", new ArrayList<Obj>(WavefrontParser.load("/res/Roll_LeftLeg.obj", 6)));
            meshes.put("RightFoot", new ArrayList<Obj>(WavefrontParser.load("/res/Roll_RightFoot.obj", 6)));
            meshes.put("LeftFoot", new ArrayList<Obj>(WavefrontParser.load("/res/Roll_LeftFoot.obj", 6)));
            meshes.put("RightArm", new ArrayList<Obj>(WavefrontParser.load("/res/Roll_RightArm.obj", 6)));
            meshes.put("LeftArm", new ArrayList<Obj>(WavefrontParser.load("/res/Roll_LeftArm.obj", 6)));
            meshes.put("RightForeArm", new ArrayList<Obj>(WavefrontParser.load("/res/Roll_RightForeArm.obj", 6)));
            meshes.put("LeftForeArm", new ArrayList<Obj>(WavefrontParser.load("/res/Roll_LeftForeArm.obj", 6)));
            meshes.put("RightHand", new ArrayList<Obj>(WavefrontParser.load("/res/Roll_RightHand.obj", 6)));
            meshes.put("LeftHand", new ArrayList<Obj>(WavefrontParser.load("/res/Roll_LeftHand.obj", 6)));
            
            // attemp to hide imperfection between
            meshesArticulation.put("Neck1", new ArrayList<Obj>(WavefrontParser.load("/res/Roll_LegArticulation.obj", 4)));
            meshesArticulation.put("RightLeg", new ArrayList<Obj>(WavefrontParser.load("/res/Roll_LegArticulation.obj", 6)));
            meshesArticulation.put("LeftLeg", new ArrayList<Obj>(WavefrontParser.load("/res/Roll_LegArticulation.obj", 6)));
            meshesArticulation.put("RightForeArm", new ArrayList<Obj>(WavefrontParser.load("/res/Roll_ArmArticulation.obj", 6)));
            meshesArticulation.put("LeftForeArm", new ArrayList<Obj>(WavefrontParser.load("/res/Roll_ArmArticulation.obj", 6)));
            
        } catch (Exception ex) {
            Logger.getLogger(ViewCanvas.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
    }
    
    public void setPose(int frameIndex) {
        skeleton.setPose(frameIndex);
    }

    public int getFrameSize() {
        return skeleton.getFrameSize();
    }

    public void update() {
        if (animate) {
            setPose((int) frameIndex);
        }
        else {
            setPose(-1); // T pose
        }
        frameIndex += (skeleton.getMotion().getFrameTime() * Time.delta * 0.00002);
        if (frameIndex > getFrameSize() - 1) {
            frameIndex = 0;
        }
    }
    
    public void draw(Renderer renderer) {
        renderer.setMatrixMode(MatrixMode.MODEL);
        for (int nodeIndex = 0; nodeIndex < skeleton.getNodes().size(); nodeIndex++) {
            Node node = skeleton.getNodes().get(nodeIndex);
            drawMesh(renderer, meshes, node);
            drawMesh(renderer, meshesArticulation, node);
        }
    }
    
    private void drawMesh(Renderer renderer, Map<String, List<Obj>> meshes, Node node) {
        List<Obj> mesh = meshes.get(node.getName());
        if (mesh == null) {
            return;
        }
        renderer.setIdentity();
        renderer.getCurrentTranform().getMatrix().multiply(node.getTransform());
        for (Obj obj : mesh) {
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
    
}
