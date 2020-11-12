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
 * CONCRETE VISITOR 3
 * gets total number of messages
 */

public class MessageVisitors implements Visitor {

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
        return ((SingleUser) user).getMessageCount();
    }

    @Override
    public int visitGroupsUser(User user) {
        int count = 0;

        for (User u : ((GroupsUser) user).getGroupUsers().values()) {
            count += visitUser(u);
        }

        return count;
    }

}