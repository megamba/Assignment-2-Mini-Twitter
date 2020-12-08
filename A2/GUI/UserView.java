/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A2.GUI;

import A2.GroupsUser;
import A2.Observer;
import A2.SingleUser;
import A2.Subject;
import A2.User;
import static A2.GUI.Panel.addComponent;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.tree.DefaultMutableTreeNode;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * @author miche
 * This Panel pops up when Admin activates Open User View Button
 * This is the User View
 */
public class UserView extends Panel {
    private static JFrame frame;
    private GridBagConstraints constraints;
    private JTextField toFollow;

    private JTextArea tweetMessageTextArea;
    private JTextArea currentFollowing;
    private JTextArea newsFeedTextArea;
    /*Assignment 3 addition*/
    private JLabel creationTimeLabel;
    private JLabel updateTimeLabel;
    
    private JButton followUserButton;
    private JButton postTweetButton;
    
    private Subject user;
    private Map<String, Observer> allUsers;
    private Map<String, JPanel> openPanels;

    private JScrollPane messageScrollPane;
    private JScrollPane currentFollowingScrollPane;
    private JScrollPane newsFeedScrollPane;

    public UserView(Map<String, Observer> allUsers, Map<String, JPanel> allPanels, DefaultMutableTreeNode user) {
        super();

        this.user = (Subject) user;
        this.allUsers = allUsers;
        this.openPanels = allPanels;
        setComponents();
        addComponents();
    }

    private void addComponents() {
        addComponent(frame, toFollow, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, followUserButton, 1, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, currentFollowing, 0, 1, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, messageScrollPane, 0, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, postTweetButton, 1, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, newsFeedScrollPane, 0, 3, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        
        /*Assignment 3 addition*/
        addComponent(frame, creationTimeLabel, 0, 0, 0, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    }

    private void setComponents() {
        frame = new JFrame("User View");
        formatFrame();

        constraints = new GridBagConstraints();
        constraints.ipady = 100;

        toFollow = new JTextField();
        toFollow.setForeground(Color.GRAY);
        followUserButton = new JButton("Follow User");
        setFollowUserButtonActionListener();

        currentFollowing = new JTextArea("Current Following: ");
        formatTextArea(currentFollowing);
        currentFollowingScrollPane = new JScrollPane(currentFollowing);
        currentFollowingScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        tweetMessageTextArea = new JTextArea("Message PLaceholder");
        tweetMessageTextArea.setForeground(Color.GRAY);
        messageScrollPane = new JScrollPane(tweetMessageTextArea);
        messageScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        postTweetButton = new JButton("Post Tweet");
        setPostTweetButtonActionListener();

        newsFeedTextArea = new JTextArea("News Feed");
        formatTextArea(newsFeedTextArea);
        newsFeedScrollPane = new JScrollPane(newsFeedTextArea);
        newsFeedScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        /*Assignment 3 addition*/
        creationTimeLabel = new JLabel("Creation Time: " + ((User) user).getCreationTime());



        updateCurrentFollowingTextArea();

        updateNewsFeedTextArea();
    }

    private void formatTextArea(JTextArea textArea) {
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setRows(8);
        textArea.setEditable(false);
    }

    private void formatFrame() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.setSize(800, 400);
        frame.setVisible(true);
        frame.setTitle(((User) user).getID());
        frame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                openPanels.remove(((User) user).getID());
            }
        });
    }
    
    private void setFollowUserButtonActionListener() {
        followUserButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                User userToFollow = (User) allUsers.get(toFollow.getText());

                if (!allUsers.containsKey(toFollow.getText())) {
                    InfoPopup popup = new InfoPopup("Error!",
                            "User does not exist",
                            JOptionPane.ERROR_MESSAGE);

                } else if (userToFollow.getClass() == GroupsUser.class) {
                    InfoPopup popup = new InfoPopup("Error!",
                            "Can't follow a group",
                            JOptionPane.ERROR_MESSAGE);
                } else if (allUsers.containsKey(toFollow.getText())) {
                    ((Subject) userToFollow).attach((Observer) user);
                }

                
                updateCurrentFollowingTextArea();
            }
        });
    }
    
    private void updateCurrentFollowingTextArea() {
        String list = "Current Following: \n";
        for (String following : ((SingleUser) user).getFollowing().keySet()) {
            list += " - " + following + "\n";
        }
        currentFollowing.setText(list);
        currentFollowing.setCaretPosition(0);
    }
    
  
    private void setPostTweetButtonActionListener() {
        postTweetButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ((SingleUser) user).sendMessage(tweetMessageTextArea.getText());

                for (JPanel panel : openPanels.values()) {
                    ((UserView) panel).updateNewsFeedTextArea();
                }
            }
        });
    }
    
    private void updateNewsFeedTextArea() {
        String list = "News Feed: \n";
 
        for (String news : ((SingleUser) user).getNewsFeed()) {
            list += " - " + news + "\n";
        }
        newsFeedTextArea.setText(list);
        newsFeedTextArea.setCaretPosition(0);
    }
    

 
}
