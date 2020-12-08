/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A2;

/**
 *
 * @author miche
 * COMPONENT of COMPOSITE pattern
 * declares the interface for objects in the composition
 */
import A2.Visitor.Visitor;
import A2.Subject;
import javax.swing.tree.DefaultMutableTreeNode;

public abstract class User extends DefaultMutableTreeNode implements Observer {

    private String id;
    private int messageCount;
    
    /*Assignment 3 additions*/
    private long creationTime = System.currentTimeMillis();
    private long lastUpdatedTime = 0;


    public abstract int getSingleUserCount();
    public abstract int getGroupUserCount();
    public abstract boolean contains(String id);

    public User(String id) {
        super(id);
        this.id = id;
        this.setMessageCount(0);
    }

    public String getID() {
        return id;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }
    
    /*Assignment 3 additions*/
    public long getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public long getCreationTime() {
        return creationTime;
    }
   

    
    /* Visitor methods*/
    public abstract void accept(Visitor visitor);

}
