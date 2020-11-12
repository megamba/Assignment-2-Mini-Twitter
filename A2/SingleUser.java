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
 * SingleUser is LEAF of the *Composite pattern
 * SU both Subject and Observer of *observer pattern
 * SU accepts visitors from *visitor pattern
 * 
 * defines behavior for primitive objects in the composition
 */
import A2.Visitor.Visitor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class SingleUser extends User implements Subject {

    private Map<String, Observer> followers;
    private Map<String, Subject> following;
    private List<String> newsFeed;

    private String latestMessage;
    private int positiveMessageCount;
    private static final List<String> POSITIVE_WORDS = Arrays.asList("good", "great", "excellent", "exciting");

    public SingleUser(String id) {
        super(id);
        followers = new HashMap<String, Observer>();
        followers.put(this.getID(), this);
        following = new HashMap<String, Subject>();
        newsFeed = new ArrayList<String>();
    }

    public Map<String, Observer> getFollowers() {
        return followers;
    }

    public Map<String, Subject> getFollowing() {
        return following;
    }

    public List<String> getNewsFeed() {
        return newsFeed;
    }

    public void sendMessage(String message) {
        this.latestMessage = message;
        this.setMessageCount(this.getMessageCount() + 1);

        if (isPositiveMessage(message)) {
            ++positiveMessageCount;
        }

        notifyObservers();
    }

    public String getLatestMessage() {
        return this.latestMessage;
    }

    public int getPositiveMessageCount() {
        return positiveMessageCount;
    }
    

    /*Composite methods*/
    @Override
    public boolean contains(String id) {
        return this.getID().equals(id);
    }

    @Override
    public int getGroupUserCount() {
        return 0;
    }

    @Override
    public int getSingleUserCount() {
        return 1;
    }

    
    /*Observer methods*/

    @Override
    public void update(Subject subject) {
        newsFeed.add(0, (((SingleUser) subject).getID() + ": " + ((SingleUser) subject).getLatestMessage()));
    }

    
    /*Subject methods*/
    
    @Override
    public void attach(Observer observer) {
        addFollower(observer);
    }
    
    @Override
    public void notifyObservers() {
        for (Observer obs : followers.values()) {
            obs.update(this);
        }
    }
    

    /*Visitor methods*/

    @Override
    public void accept(Visitor visitor) {
        visitor.visitSingleUser(this);
    }

    
    /*Private methods*/

    private void addFollower(Observer user) {
        this.getFollowers().put(((User) user).getID(), user);
        ((SingleUser) user).addUserToFollow(this);
    }


    private void addUserToFollow(Subject toFollow){
        if (toFollow.getClass() == SingleUser.class) {
            getFollowing().put(((User) toFollow).getID(), toFollow);
        }
    }

    private boolean isPositiveMessage(String message) {
        boolean positive = false;
        message = message.toLowerCase();
        for (String word : POSITIVE_WORDS) {
            if (message.contains(word)) {
                positive = true;
            }
        }
        return positive;
    }

    
    

}
