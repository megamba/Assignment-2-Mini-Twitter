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
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author miche
 * Text Fields and Buttons for adding SingleUser and GroupsUser
 */


public class AddUsersPanel extends Panel {

    private JPanel treePanel;
    private Map<String, Observer> allUsers;
    private JButton addUserButton;
    private JButton addGroupButton;
    private JTextField userId;
    private JTextField groupId;

    public AddUsersPanel(JPanel treePanel, Map<String, Observer> allUsers) {
        super();
        this.treePanel = treePanel;
        this.allUsers = allUsers;

        setComponents();
        addComponents();
    }

    private void addComponents() {
        addComponent(this, userId, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, addUserButton, 1, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, groupId, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, addGroupButton, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    }

    private void setComponents() {
        userId = new JTextField("User ID Placeholder");
        userId.setForeground(Color.GRAY);
        groupId = new JTextField("Group ID Placeholder");
        groupId.setForeground(Color.GRAY);

        addUserButton = new JButton("Add User");
        setAddUserButtonActionListener();

        addGroupButton = new JButton("Add Group");
        setAddGroupButtonActionListener();
    }

    private void setAddUserButtonActionListener() {
        addUserButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // check if user ID already exists
                if (allUsers.containsKey(userId.getText())) {
                    InfoPopup popup = new InfoPopup("Error!",
                            "User already exists",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    Observer child = new SingleUser(userId.getText());

                    allUsers.put(((User) child).getID(), child);
                    ((TreeView) treePanel).addSingleUser((DefaultMutableTreeNode) child);
                }
            }
        });
    }

    private void setAddGroupButtonActionListener() {
        addGroupButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // check if user ID already exists
                if (allUsers.containsKey(groupId.getText())) {
                    InfoPopup popup = new InfoPopup("Error!",
                            "User already exists",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    Observer child = new GroupsUser(groupId.getText());

                    allUsers.put(((User) child).getID(), child);
                    ((TreeView) treePanel).addGroupUser((DefaultMutableTreeNode) child);
                }
            }
        });
    }

}
