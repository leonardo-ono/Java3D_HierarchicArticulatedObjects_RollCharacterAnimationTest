package main;

import br.ol.renderer3d.core.ViewCanvas;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author leonardo
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ViewCanvas viewCanvas = new ViewCanvas();
                JFrame frame = new JFrame();
                frame.setTitle("3D Hierachic Articulated Objects / Roll character animation test");
                frame.setSize(800, 600);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setResizable(false);
                frame.getContentPane().add(viewCanvas);
                frame.setVisible(true);
                viewCanvas.requestFocus();
                viewCanvas.init();
            }
        });
    }
    
}
