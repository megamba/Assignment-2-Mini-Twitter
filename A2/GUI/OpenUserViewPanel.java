/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A2.GUI;

import A2.GroupsUser;
import A2.Observer;
import A2.SingleUser;
import A2.User;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author miche
 * 
 * has Open User View Button and sends Admin to UserViewPanel
 */
public class OpenUserViewPanel extends Panel {
    private JButton openUserViewButton;
    private JPanel blankPanel;
    private JPanel treeViewPanel;
    private Map<String, Observer> allUsers;
    private Map<String, JPanel> openPanels;
    
    public OpenUserViewPanel(JPanel treeViewPanel, Map<String, Observer> allUsers){
        super();
        this.treeViewPanel = treeViewPanel;
        this.allUsers = allUsers;
        setComponents();
        addComponents();
    }
    
    private void addComponents(){
        addComponent(this, openUserViewButton,1,2,2,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, blankPanel, 1, 3, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    }
    
    private void setComponents(){
        openPanels = new HashMap<String, JPanel>();
        openUserViewButton = new JButton("Open User View");
        setOpenUserViewActionListener();
        blankPanel = new JPanel();
    }

    private void setOpenUserViewActionListener() {
        openUserViewButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // get User selected in TreePanel
                DefaultMutableTreeNode selectedNode = getSelectedNode();

                // open user view UI on click, only open one window per User
                if (!allUsers.containsKey(((User) selectedNode).getID())) {
                    InfoPopup popup = new InfoPopup("Error!",
                            "That user doesn't exist.",
                            JOptionPane.ERROR_MESSAGE);
                } else if (selectedNode.getClass() == GroupsUser.class) {
                    InfoPopup popup = new InfoPopup("Error!",
                            "Can only open User View for single user.",
                            JOptionPane.ERROR_MESSAGE);
                } else if (openPanels.containsKey(((User) selectedNode).getID())) {
                    InfoPopup popup = new InfoPopup("Error!",
                            "User view is already open for " + ((User) selectedNode).getID() + "!",
                            JOptionPane.ERROR_MESSAGE);
                } else if (selectedNode.getClass() == SingleUser.class) {
                    UserView userView = new UserView(allUsers, openPanels, selectedNode);
                    openPanels.put(((User) selectedNode).getID(), userView);
                }
            }
        });
    }
    
    private DefaultMutableTreeNode getSelectedNode() {
        JTree tree = ((TreeView) treeViewPanel).getTree();
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
        if (!((TreeView) treeViewPanel).getRoot().equals(selectedNode)) {
            selectedNode = (DefaultMutableTreeNode) selectedNode.getUserObject();
        }

        return selectedNode;
    }
}
