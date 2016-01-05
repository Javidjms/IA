/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Vue3;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;

/**
 * Classe qui permet de dessiner un équivalent à un JPanel avec une image en fond
 */
public class JImagePanel extends JComponent {
    private BufferedImage image;
    
    public JImagePanel() {
        
    }
    
    public JImagePanel(BufferedImage image) {
        this.image = image;
    }
    
    public void setImage(BufferedImage image) {
        this.image = image;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();  
        int height = getHeight();  
        for (int x = 0; x < width; x += image.getWidth()) {  
            for (int y = 0; y < height; y += image.getHeight()) {  
                g.drawImage(image, x, y, this);  
            }  
        } 
    }
}
