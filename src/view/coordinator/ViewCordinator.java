/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.coordinator;

import communication.Operation;
import communication.Request;
import communication.Response;
import domain.Client;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import view.controller.ClientController;
import view.controller.ClientViewController;

import view.controller.LoginController;
import view.controller.MainController;
import view.controller.ReservationsController;
import view.controller.ServicesController;
import view.form.FrmAddClient;
import view.form.FrmClients;

import view.form.FrmLogin;
import view.form.FrmMain;
import view.form.FrmReservations;
import view.form.FrmServices;
import view.form.mode.FormMode;


/**
 *
 * @author Nadja
 */
public class ViewCordinator {
    private static ViewCordinator instance;
    MainController mainController;
    ClientController clientController;
    ClientViewController frmClients;
    
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

    public void openAddReservationForm() {
        ReservationsController frmReservationsController= new ReservationsController(new FrmReservations(getFrmMain(), true));
        frmReservationsController.openForm(FormMode.ADD);
        
    }
     public void openEditReservationForm() {
        ReservationsController frmReservationsController= new ReservationsController(new FrmReservations(getFrmMain(), true));
        frmReservationsController.openForm(FormMode.EDIT);
        
    }
    public void openAddClientForm(){
        ClientController frmAddClientController=new ClientController(new FrmAddClient(getFrmMain(), true));
        frmAddClientController.openForm();
    }

    public void openViewClientsForm() {
      frmClients=new ClientViewController(new FrmClients(getFrmMain(), true));
        frmClients.openForm();
    }

    public void refreshClients() {
        frmClients.prepareView();
    }

    public void openServicesForm() {
        ServicesController frmServices=new ServicesController(new FrmServices(getFrmMain(), true));
        frmServices.openForm();
    }
 

    
    
    
    
    
    
    
}
