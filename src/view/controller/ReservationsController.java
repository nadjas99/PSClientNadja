/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;

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
    }

   
    
    
    public void openForm(FormMode formMode){
        frmReservations.setLocationRelativeTo(ViewCordinator.getInstance().getFrmMain());
        frmReservations.setVisible(true);
        
    }
    
}
