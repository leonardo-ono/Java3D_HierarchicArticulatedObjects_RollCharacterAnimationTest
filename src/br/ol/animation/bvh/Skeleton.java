package br.ol.animation.bvh;

import java.util.ArrayList;
import java.util.List;

/**
 * Skeleton class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Skeleton {
    
    private Node rootNode;
    private Motion motion;
    private final List<Node> nodes = new ArrayList<Node>();

    public Skeleton(String resource) {
        Parser parser = new Parser();
        parser.load(resource);
        rootNode = new Node(parser);
        motion = new Motion(parser);
        rootNode.fillNodesList(nodes);
        parser.close();
    }

    public Node getRootNode() {
        return rootNode;
    }

    public Motion getMotion() {
        return motion;
    }

    public List<Node> getNodes() {
        return nodes;
    }
    
    public int getFrameSize() {
        return motion.getFrameSize();
    }
    
    public void setPose(int frameIndex) {
        if (frameIndex < 0) {
            rootNode.setPose(null);
        }
        else {
            rootNode.setPose(motion.getData(frameIndex));
        }
    }
    
}
