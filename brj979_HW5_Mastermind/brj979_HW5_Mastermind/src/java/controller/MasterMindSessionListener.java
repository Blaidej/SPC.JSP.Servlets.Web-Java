/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import model.MasterMind;

/**
 * Web application life Cycle listener.
 *
 * @author CompSciStudent
 */
public class MasterMindSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setAttribute("game", new MasterMind());
        
        
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
    }
}
