/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A2;

/**
 *
 * @author miche
 * COMPOSITE PATTERN!
 * GroupsUser is COMPOSITE of *Composite design pattern.
 * GroupsUser is Observer of *Observer design pattern.
 * GruopsUser accepts visitors of *Visitor design pattern.
 * 
 * stores child components and implements child related operations in the component interface.
 */
import A2.Visitor.Visitor;
import java.util.HashMap;
import java.util.Map;
import A2.Subject;


public class GroupsUser extends User implements Subject{

    private Map<String,User> groupUsers;
    private Map<String, Observer> followers;
    
    /*Assignment 3 addition*/
    private long creationTime = System.currentTimeMillis();

    public GroupsUser(String id) {
        super(id);
        groupUsers = new HashMap<String,User>();
        
        System.out.println(getCreationTime());  //assignment 31
    }

    public Map<String,User> getGroupUsers() {
        return groupUsers;
    }

    public User addUserInGroup(User user) {
        if (!this.contains(user.getID())) {
            this.groupUsers.put(user.getID(), user);
        }
        return this;
    }
    
    /*Assignment 3 addition*/
    public long getCreationTime() {
        return creationTime;
    }


    /*Composite methods*/

    @Override
    public int getSingleUserCount() {
        int count = 0;
        for (User user : this.groupUsers.values()) {
            count += user.getSingleUserCount();
        }
        return count;
    }

    @Override
    public int getGroupUserCount() {
        int count = 0;
        for (User user : this.groupUsers.values()) {
            if (user.getClass() == GroupsUser.class) {
                ++count;
                count += user.getGroupUserCount();
            }
        }
        return count;
    }
    
    @Override
    public boolean contains(String id) {
        boolean contains = false;
        for (User user : groupUsers.values()) {
            if (user.contains(id)) {
                contains = true;
            }
        }
        return contains;
    }

    @Override
    public int getMessageCount() {
        int msgCount = 0;
        for (User user : this.groupUsers.values()) {
            msgCount += user.getMessageCount();
        }
        return msgCount;
    }
    


    
    /*Observer methods*/

    @Override
    public void update(Subject subject) {
        for (User user : groupUsers.values()) {
            ((Observer) user).update(subject);
        }
        
    }

    
    /*Subject Methods*/
    @Override
    public void notifyObservers() {
        for (Observer obs : followers.values()) {
            obs.update(this);
        }
    }
   
    
    /* Visitor methods*/

    @Override
    public void accept(Visitor visitor) {
        for (User user : groupUsers.values()) {
            user.accept(visitor);
        }
        visitor.visitGroupsUser(this);
    }

    
    
    /* Private methods*/

    private boolean containsGroupUser() {
        boolean containsGroup = false;
        for (User user : this.groupUsers.values()) {
            if (user.getClass() == GroupsUser.class) {
                containsGroup = true;
            }
        }
        return containsGroup;
    }

    @Override
    public void attach(Observer observer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }





}
