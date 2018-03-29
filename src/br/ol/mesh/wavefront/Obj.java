package br.ol.mesh.wavefront;

import java.util.ArrayList;
import java.util.List;
import br.ol.renderer3d.core.Material;
import br.ol.mesh.wavefront.WavefrontParser.Face;

/**
 *
 * @author leonardo
 */
public class Obj {
    
    public List<Face> faces = new ArrayList<Face>();
    public Material material;
    
}
