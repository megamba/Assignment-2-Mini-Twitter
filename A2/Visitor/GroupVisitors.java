/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A2.Visitor;

import A2.GroupsUser;
import A2.SingleUser;
import A2.User;
import A2.Visitor.Visitor;

/**
 *
 * @author miche
 * CONCRETE VISITOR 2
 * gets number of GroupsUser for User
 * 
 * For each type of visitor all the visit methods, declared in abstract visitor, must be implemented. Each Visitor will be responsible for different operations.
 */

public class GroupVisitors implements Visitor {

    @Override
    public int visitUser(User user) {
        int count = 0;

        if (user.getClass() == SingleUser.class) {
            count += visitSingleUser(user);
        } else if (user.getClass() == GroupsUser.class) {
            count += visitGroupsUser(user);
        }

        return count;
    }

    @Override
    public int visitSingleUser(User user) {
        return 0;
    }

    @Override
    public int visitGroupsUser(User user) {
        int count = 0;

        for (User u : ((GroupsUser) user).getGroupUsers().values()) {
            if (u.getClass() == GroupsUser.class) {
                ++count;
            }
            count += visitUser(u);
        }

        return count;
    }

}

