/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;

import communication.Communication;
import domain.PhotographyServices;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.DocFlavor;
import view.form.FrmServices;

/**
 *
 * @author Nadja
 */
public class ServicesController {
    
    private final FrmServices frmServices;

    public ServicesController(FrmServices frmServices) {
        this.frmServices = frmServices;
        ActionPerformed();
    }
     public void openForm(){
         prepareView();
         frmServices.setVisible(true);
     }

    private void prepareView() {
        try {
            fillCmbBox();
        } catch (Exception ex) {
            System.out.println("Error while filling in the combo box");
        }
    }

    private void ActionPerformed() {
        frmServices.addBtnDisplayActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PhotographyServices ph=(PhotographyServices) frmServices.getCmbServices().getSelectedItem();
                frmServices.getTxtDesc().setText(ph.getDescription());
                frmServices.getTxtName().setText(ph.getName());
                frmServices.getTxtPrice().setText(String.valueOf(ph.getPrice()));
                
                
                
           }
        });
    }

    private void fillCmbBox() throws Exception {
        List<PhotographyServices> services=Communication.getInstance().getAllServices();
        frmServices.getCmbServices().removeAllItems();
        for (PhotographyServices service : services) {
            frmServices.getCmbServices().addItem(service);
        }
        
    }
    
    
    
}
