/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A2.GUI;

import A2.GUI.Driver;
import A2.GroupsUser;
import A2.Observer;
import A2.User;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.awt.GridBagConstraints;
/**
 *
 * @author miche
 * SINGLETON
 * Entrance to the program!
 * this is the main interface which has tree view, add user, open user, and show *something*
 */


public class AdminControlPanel extends Panel {

    private static AdminControlPanel INSTANCE;

    private static JFrame frame;
    private JPanel treeView;
    private JPanel addUser;
    private JPanel openUserView;
    private JPanel showInfo;

    private DefaultMutableTreeNode root;
    private Map<String, Observer> allUsers;

    public static AdminControlPanel getInstance() {
        if (INSTANCE == null) {
            synchronized (Driver.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AdminControlPanel();
                }
            }
        }
        return INSTANCE;
    }

    private AdminControlPanel() {
        super();

        setComponents();
        addComponents();
    }

    private void addComponents() {
        addComponent(frame, treeView, 0, 0, 1, 6, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, addUser, 1, 0, 2, 2, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, openUserView, 1, 2, 2, 2, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, showInfo, 1, 4, 2, 2, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    }

    private void setComponents() {
        frame = new JFrame("Assignment 2 : Mini Twitter App");
        formatFrame();

        allUsers = new HashMap<String, Observer>();
        root = new GroupsUser("Root");
        allUsers.put(((User) root).getID(), (Observer) this.root);

        treeView = new TreeView(root);
        addUser = new AddUsersPanel(treeView, allUsers);
        
        openUserView = new OpenUserViewPanel(treeView, allUsers);
        showInfo = new ShowInfoPanel(treeView);
    }

    private void formatFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.setSize(800, 400);
        frame.setVisible(true);
    }

}
