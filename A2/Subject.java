/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A2;
/**
 *
 * @author miche
 * OBSERVER PATTERN
 * interface for observer pattern
 */

public interface Subject {

    public void attach(Observer observer);
    public void notifyObservers();

}