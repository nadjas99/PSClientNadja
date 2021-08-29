/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;

import com.oracle.jrockit.jfr.DataType;
import communication.Communication;
import domain.Client;
import domain.PhotographyServices;
import domain.Reservation;
import domain.ReservationDetail;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import view.coordinator.ViewCordinator;
import view.form.FrmReservations;
import view.form.mode.FormMode;
import view.table.model.ReservationDetailTableModel;

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
     private void fillDefaultValues() {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy.");
        String currentDate = df.format(new Date());
        frmReservations.getTxtDate().setText(currentDate);
        frmReservations.getTxtSum().setText("0.0");
    }

    private void prepareView() {
       fillCbClients();
       fillCbServices();
       fillDefaultValues();
        ReservationDetailTableModel model=new ReservationDetailTableModel(new Reservation());
        frmReservations.getTblItems().setModel(model);
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
        frmReservations.getCmbServices().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                PhotographyServices phs=(PhotographyServices) frmReservations.getCmbServices().getSelectedItem();
                frmReservations.getTxtProductPrice().setText(String.valueOf(phs.getPrice()));
            }
        });
    }

    private void ActionPerformed() {
        frmReservations.addBtnMakeReservationActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        frmReservations.addBtnAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReservationDetail rd=new ReservationDetail();
                rd.setCost(Double.valueOf(frmReservations.getTxtProductPrice().getText()));
                rd.setService((PhotographyServices) frmReservations.getCmbServices().getSelectedItem());
                ReservationDetailTableModel tbl=(ReservationDetailTableModel) frmReservations.getTblItems().getModel();
                tbl.addItem(rd.getService(), rd.getCost());
                
                
           }
        });
        
    }
    
}
