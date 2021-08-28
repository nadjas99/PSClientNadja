/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;

import communication.Communication;
import domain.Client;
import domain.PhotographyServices;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import view.coordinator.ViewCordinator;
import view.form.FrmReservations;
import view.form.mode.FormMode;

/**
 *
 * @author Nadja
 */
public class ReservationsController {
    private final FrmReservations frmReservations;

    public ReservationsController(FrmReservations frmReservations) {
        this.frmReservations = frmReservations;
        ActionPerformed();
    }

   
    
    
    public void openForm(FormMode formMode){
        prepareView();
        frmReservations.setLocationRelativeTo(ViewCordinator.getInstance().getFrmMain());
        frmReservations.setVisible(true);
        
    }

    private void prepareView() {
       fillCbClients();
       fillCbServices();
    }

    private void fillCbClients() {
        try {
            frmReservations.getCmbClient().removeAllItems();
            List<Client> clients=Communication.getInstance().getAllClients();
            for (Client client : clients) {
                frmReservations.getCmbClient().addItem(client);
            }
        } catch (Exception ex) {
            Logger.getLogger(ReservationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void fillCbServices() {
        try {
            frmReservations.getCmbServices().removeAllItems();
            List<PhotographyServices> services=Communication.getInstance().getAllServices();
            for (PhotographyServices service : services) {
              frmReservations.getCmbServices().addItem(service);
            }
        } catch (Exception ex) {
            Logger.getLogger(ReservationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void ActionPerformed() {
        frmReservations.addBtnMakeReservationActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
    
}
