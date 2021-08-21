/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.coordinator;

import java.util.HashMap;
import java.util.Map;

import view.controller.LoginController;
import view.controller.MainController;

import view.form.FrmLogin;
import view.form.FrmMain;


/**
 *
 * @author Nadja
 */
public class ViewCordinator {
    private static ViewCordinator instance;
    MainController mainController;
    
    private Map<String,Object> params;
 
    private ViewCordinator() {
        params = new HashMap<>();
    }
    
    public static ViewCordinator getInstance(){
        if(instance==null){
            instance=  new ViewCordinator();
        }
        return instance;
    }
    
    public void addParam(String s, Object o){
        params.put(s, o);
    }
    
    public Object getParam(String s){
        return params.get(s);
    }
    
    public void openLoginForm(){
        LoginController frmLogin = new LoginController(new FrmLogin());
        frmLogin.openForm();
    }
    
    public void openMainForm(){
         mainController = new MainController(new FrmMain());
        mainController.openForm();
    }
    
   

    public FrmMain getFrmMain() {
        return mainController.getFrmMain();
    }
    
    
    
    
    
    
    
}
