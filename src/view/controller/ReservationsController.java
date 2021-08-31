/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;


import communication.Communication;
import domain.Client;
import domain.Photographer;
import domain.PhotographyServices;
import domain.Reservation;
import domain.ReservationDetail;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
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
        
        switch(formMode){
            case ADD:
                prepareView();
                frmReservations.setLocationRelativeTo(ViewCordinator.getInstance().getFrmMain());
                frmReservations.setVisible(true);
                break;
            case EDIT:
                prepareViewEdit();
                frmReservations.setLocationRelativeTo(ViewCordinator.getInstance().getFrmMain());
                frmReservations.setVisible(true);
                fillCbServices();
                break;
        
        }
        
        
    }
     private void fillDefaultValues() {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy.");
        String currentDate = df.format(new Date());
        frmReservations.getTxtDate().setText(currentDate);
        frmReservations.getTxtSum().setText("0.0");
    }

    private void prepareView() {
        frmReservations.getBtnEdit().setVisible(false);
        frmReservations.getCmbReservation().setVisible(false);
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
            frmReservations.getCmbServices().setSelectedIndex(-1);
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
        
        frmReservations.addBtnAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReservationDetail rd=new ReservationDetail();
                rd.setCost(Double.valueOf(frmReservations.getTxtProductPrice().getText()));
                rd.setService((PhotographyServices) frmReservations.getCmbServices().getSelectedItem());
                ReservationDetailTableModel tbl=(ReservationDetailTableModel) frmReservations.getTblItems().getModel();
                tbl.addItem(rd.getService(), rd.getCost());
                frmReservations.getTxtSum().setText(String.valueOf(tbl.getInvoice().getCost()));
                
                
           }
        });
        
        
        frmReservations.addBtnMakeReservationActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DateFormat df = new SimpleDateFormat("dd.MM.yyyy.");
                ReservationDetailTableModel tblmodel=(ReservationDetailTableModel) frmReservations.getTblItems().getModel();
                Reservation res=tblmodel.getInvoice();
                try {
                    res.setDate(df.parse(frmReservations.getTxtDate().getText().trim()));
                    Client c=(Client) frmReservations.getCmbClient().getSelectedItem();
                    res.setClient(c);
                    res.setPhotographer((Photographer) ViewCordinator.getInstance().getParam("photographer"));
                    res.setPlace(frmReservations.getTxtPlace().getText());
                    try {
                        Communication.getInstance().addNewReservation(res);
                        
                        
                    JOptionPane.showMessageDialog(frmReservations, "Reservation is saved!");
                    } catch (Exception ex) {
                        System.out.println("Greska kod slanja rezervacije");
                        JOptionPane.showMessageDialog(frmReservations, "Could not save reservation..");
                        Logger.getLogger(ReservationsController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                } catch (ParseException ex) {
                    System.out.println("Greska kod parsiranja");
                    Logger.getLogger(ReservationsController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
          }
        });
        
        frmReservations.addBtnRemoveActLis(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeInvoiceItem();
            }

            private void removeInvoiceItem() {
                int rowIndex = frmReservations.getTblItems().getSelectedRow();
                ReservationDetailTableModel model =  (ReservationDetailTableModel) frmReservations.getTblItems().getModel();
                if (rowIndex >= 0) {
                    model.removeInvoiceItem(rowIndex);
                    double amount = model.getInvoice().getCost();
                    frmReservations.getTxtSum().setText(String.valueOf(amount));
                } else {
                    JOptionPane.showMessageDialog(frmReservations, "Reservation item is not selected!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        
    }

    private void prepareViewEdit() {
        frmReservations.getBtnSaveReservation().setVisible(false);
        frmReservations.getCmbReservation().setVisible(true);
        frmReservations.getCmbReservation().removeAllItems();
        try {
            List<Reservation>reservations=Communication.getInstance().getAllRes();
            for (Reservation reservation : reservations) {
                frmReservations.getCmbReservation().addItem(reservation);
            }
            
        } catch (Exception ex) {
            Logger.getLogger(ReservationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        frmReservations.getCmbReservation().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Reservation r=(Reservation) frmReservations.getCmbReservation().getSelectedItem();
                frmReservations.getTxtPlace().setText(r.getPlace());
                frmReservations.getTxtDate().setText(String.valueOf(r.getDate()));
                frmReservations.getTxtSum().setText(String.valueOf(r.getCost()));
                frmReservations.getCmbClient().removeAllItems();
                 
                
                try {
                    List<Client> clients=Communication.getInstance().getAllClients();
                    
                    for (Client client : clients) {
                      
                        if(client.getId().equals(r.getClient().getId()) ){
                          r.setClient(client);
                          
                    }}
                    frmReservations.getCmbClient().addItem(r.getClient());
                    
                } catch (Exception ex) {
                    Logger.getLogger(ReservationsController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                 ReservationDetailTableModel model=new ReservationDetailTableModel(new Reservation());
                    frmReservations.getTblItems().setModel(model);
                     List<ReservationDetail> details=r.getReservationDetails();
                     for (ReservationDetail detail : details) {
                    model.addItem(detail.getService(), detail.getCost());
                }
                     
                     
               
        
                
                
            }
        });
     
       
            
        
    }
    
}
