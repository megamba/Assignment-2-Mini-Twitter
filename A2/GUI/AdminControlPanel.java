/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A2.GUI;

import A2.GUI.Driver;
import A2.GroupsUser;
import A2.Observer;
import A2.SingleUser;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
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

    private JButton validateUsersButton;
    private JButton checkMostRecentUpdatedButton;
    
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
        addComponent(frame, openUserView, 1, 2, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, showInfo, 1, 4, 2, 2, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        
        //assignment 3
        addComponent(frame, validateUsersButton, 1, 3, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, checkMostRecentUpdatedButton, 2, 3, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
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
        
        /*Assignment 3 addition*/
        validateUsersButton = new JButton("Validate Users");
        setValidateUsersButtonActionListener();
        
        checkMostRecentUpdatedButton = new JButton("Check for recently updated");
        setCheckMostRecentUpdatedButtonActionListener();
    }

    private void formatFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.setSize(800, 400);
        frame.setVisible(true);
    }
    
        
    /*Assignment 3 addition*/
    
    private void setValidateUsersButtonActionListener() {
        validateUsersButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                InfoPopup popup;
                
                if(allUsers.size() <= 2) {
                    popup = new InfoPopup("Error!",
                            "Only 1 user exists." ,
                            JOptionPane.ERROR_MESSAGE);
                }else {
                    
                    for(int i = 0; i < allUsers.size() - 1; i++) {
                        
                        System.out.println(allUsers.get(i));
                        
                        for(int j = 0; j < allUsers.size(); j++) {
                            
                            if(j != i) {
                                if(((SingleUser) allUsers.get(i)).equals((SingleUser) allUsers.get(j))) {   //check for duplicate ID
                                    popup = new InfoPopup("Error!",
                                        "Duplicate user: " + allUsers.get(i),
                                        JOptionPane.ERROR_MESSAGE);
                                    break;
                                }
                            }
                        }
                            
                        for(int k = 0; k < ((User) allUsers.get(i)).getID().length() - 1; k++) {

                            if(((User) allUsers.get(k)).getID().substring(k, k + 1).equals(" ")) {
                                System.out.println("An ID has a space: " + allUsers.get(k));

                                popup = new InfoPopup("Error!",
                                "User with a space : " + allUsers.get(i).toString(),
                                JOptionPane.ERROR_MESSAGE);
                                break;
                            }
                            
                        } 
                    }   //end loop
                    
                }
                
            }
        }); 
    }   //end setValidateUsersButtonActionListener()

    private void setCheckMostRecentUpdatedButtonActionListener() {
        checkMostRecentUpdatedButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                InfoPopup popup;
                
                if(allUsers.isEmpty()) {
                    popup = new InfoPopup("Error!",
                            "No users." ,
                            JOptionPane.ERROR_MESSAGE);
                }else {
                    
                    long mostRecentUpdate = 0;
                    
                    for(int i = 0; i < allUsers.size(); i++) {
                        
                        if(((User) allUsers.get(i)).getLastUpdatedTime() > mostRecentUpdate) {  //if this user's update time is greated than mostRecentUpdate, replace it
                            mostRecentUpdate = ((User) allUsers.get(i)).getLastUpdatedTime();
                        }
                        
                    }
                    
                    System.out.println("Most recent update time is: " + mostRecentUpdate);
                }
                
                
                
            }
        }); 
    }   //end setValidateUsersButtonActionListener()

}
