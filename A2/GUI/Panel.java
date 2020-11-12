/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A2.GUI;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JPanel;

/**
 *
 * @author miche
 * abstract class as parent for main UI panels
 * 
 */

public abstract class Panel extends JPanel {

    private static final Insets insets = new Insets(0, 0, 0, 0);

    public Panel() {
        super(new GridBagLayout());
    }

    public static void addComponent(Container container, Component component, int gridx, int gridy,
            int gridwidth, int gridheight, int anchor, int fill) {
        GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 1.0, 1.0,
            anchor, fill, insets, 0, 0);
        container.add(component, gbc);
    }

}
