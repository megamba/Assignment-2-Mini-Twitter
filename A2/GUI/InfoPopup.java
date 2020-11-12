/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A2.GUI;
import javax.swing.JOptionPane;
/**
 *
 * @author miche
 * pops up info and errors
 */

public class InfoPopup {

    public InfoPopup(String title, String message, int messageType) {
        JOptionPane.showMessageDialog(null, message, title, messageType);
    }

}