/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A2.GUI;

import A2.User;
import A2.Visitor.MessageVisitors;
import A2.Visitor.PositiveMessageVisitors;
import A2.Visitor.GroupVisitors;
import A2.Visitor.UserVisitors;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author miche
 * 
 * Panel for Info buttons: Show User Total, Show Group Total, Show Messages Total, and Show Positive PErcentage 
 */
public class ShowInfoPanel extends Panel {
    private JButton usersButton;
    private JButton groupsButton;
    private JButton messagesButton;
    private JButton positiveButton;
    private JPanel treePanel;

    public ShowInfoPanel(JPanel treePanel) {
        super();

        this.treePanel = treePanel;
        initializeComponents();
        addComponents();
    }

    private void addComponents() {
        addComponent(this, usersButton, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, groupsButton, 1, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, messagesButton, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, positiveButton, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    }

    private void initializeComponents() {
        usersButton = new JButton("Show User Total");
        setUserTotalButtonActionListener();

        groupsButton = new JButton("Show Group Total");
        setGroupTotalButtonActionListener();

        messagesButton = new JButton("Show Messages Total");
        setMessagesTotalButtonActionListener();

        positiveButton = new JButton("Show Positive Percentage");
        setPositivePercentageButtonActionListener();
    }

    private DefaultMutableTreeNode getSelectedNode() {
        JTree tree = ((TreeView) treePanel).getTree();
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
        if (!((TreeView) treePanel).getRoot().equals(selectedNode)) {
            selectedNode = (DefaultMutableTreeNode) selectedNode.getUserObject();
        }

        return selectedNode;
    }

    private void setUserTotalButtonActionListener() {
        usersButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // get User selected in TreeView
                DefaultMutableTreeNode selectedNode = getSelectedNode();

                UserVisitors visitor = new UserVisitors();
                ((User) selectedNode).accept(visitor);
                String message = "Total single users in "
                        + ((User) selectedNode).getID() + ": "
                        + Integer.toString(visitor.visitUser(((User) selectedNode)));

                InfoPopup popup = new InfoPopup(((User) selectedNode).getID() + " information",
                        message, JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void setGroupTotalButtonActionListener() {
        groupsButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // get User selected in TreeView
                DefaultMutableTreeNode selectedNode = getSelectedNode();

                GroupVisitors visitor = new GroupVisitors();
                ((User) selectedNode).accept(visitor);
                String message = "Total groups in "
                        + ((User) selectedNode).getID() + ": "
                        + Integer.toString(visitor.visitUser(((User) selectedNode)));

                InfoPopup popup = new InfoPopup(((User) selectedNode).getID() + " information",
                        message, JOptionPane.INFORMATION_MESSAGE);

            }
        });
    }

    private void setMessagesTotalButtonActionListener() {
        messagesButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // get User selected in TreeView
                DefaultMutableTreeNode selectedNode = getSelectedNode();

                MessageVisitors visitor = new MessageVisitors();
                ((User) selectedNode).accept(visitor);
                String message = "Total number of messages sent by "
                        + ((User) selectedNode).getID() + ": "
                        + Integer.toString(visitor.visitUser(((User) selectedNode)));

                InfoPopup popUp = new InfoPopup(((User) selectedNode).getID() + " information",
                        message, JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
    
    private void setPositivePercentageButtonActionListener() {
        positiveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // get User selected in TreeView
                DefaultMutableTreeNode selectedNode = getSelectedNode();

                PositiveMessageVisitors positiveCountVisitor = new PositiveMessageVisitors();
                ((User) selectedNode).accept(positiveCountVisitor);
                int positiveCount = positiveCountVisitor.visitUser(((User) selectedNode));

                MessageVisitors messageCountVisitor = new MessageVisitors();
                ((User) selectedNode).accept(messageCountVisitor);
                int messageCount = messageCountVisitor.visitUser(((User) selectedNode));
                
                double percentage = 0;
                if (messageCount > 0) {
                    percentage = ((double) positiveCount / messageCount) * 100;
                }
                String percentageString = String.format("%.2f", percentage);

                String message = "Percentage of positive messages sent by "
                        + ((User) selectedNode).getID() + ": "
                        + percentageString + "%";

                InfoPopup popUp = new InfoPopup(((User) selectedNode).getID() + " information",
                        message, JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

}
