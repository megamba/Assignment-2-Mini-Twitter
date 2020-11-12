/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A2.Visitor;

import A2.User;

/**
 *
 * @author miche
 * VISITOR PATTERN
 * visitor interface for User 
 * 
 * an interface or an abstract class used to declare the visit operations for all the types of visitable classes.
 */


public interface Visitor {
    public int visitUser(User user);
    public int visitSingleUser(User user);
    public int visitGroupsUser(User user);

}