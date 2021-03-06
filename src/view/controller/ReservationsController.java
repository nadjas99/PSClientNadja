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
            
            List<PhotographyServices> services=Communication.getInstance().getAllServices();
            for (PhotographyServices service : services) {
              frmReservations.getCmbServices().addItem(service);
            }
            frmReservations.getCmbServices().setSelectedItem(services.get(0));
            frmReservations.getTxtProductPrice().setText(String.valueOf(services.get(0).getPrice()));
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
                        validateReservation(res);
                        try {
                        
                        Communication.getInstance().addNewReservation(res);
                        JOptionPane.showMessageDialog(frmReservations, "Reservation is saved!");
                        } catch (Exception ex) {
                        System.out.println("Greska kod slanja rezervacije");
                        JOptionPane.showMessageDialog(frmReservations, "Could not save reservation..");
                        Logger.getLogger(ReservationsController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frmReservations, "Fill in everything and try again");
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
        
        frmReservations.addBtnEditActLis(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               DateFormat df = new SimpleDateFormat("dd.MM.yyyy.");
                ReservationDetailTableModel tblmodel=(ReservationDetailTableModel) frmReservations.getTblItems().getModel();
                Reservation res=tblmodel.getInvoice();
                
                try {
                    res.setDate(df.parse(frmReservations.getTxtDate().getText().trim()));
                } catch (ParseException ex) {
                    Logger.getLogger(ReservationsController.class.getName()).log(Level.SEVERE, null, ex);
                }
                    res.setPlace(frmReservations.getTxtPlace().getText());
                try {
                    validateReservation(res);
                    try {
                         
                        Communication.getInstance().editReservation(res);
                        
                        
                    JOptionPane.showMessageDialog(frmReservations, "Reservation is edited!");
                    } catch (Exception ex) {
                        
                        JOptionPane.showMessageDialog(frmReservations, "Could not edit reservation..");
                        
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmReservations, "Could not edit reservation, fill in all fields!");
                    
                }
                    
                    
//                } catch (ParseException ex) {
//                    System.out.println("Greska kod parsiranja");
//                    Logger.getLogger(ReservationsController.class.getName()).log(Level.SEVERE, null, ex);
//                }
            
                   }});
        
        
    }
    
                private void validateReservation(Reservation res) throws Exception {
                if(frmReservations.getTxtDate().getText().equals("") || frmReservations.getTxtPlace().getText().equals("") || frmReservations.getTxtSum().getText().equals("") || frmReservations.getTxtSum().getText().equals("0.0" )){
                throw new Exception();
                
                
                }
            }

    private void prepareViewEdit() {
        
        fillCbServices();
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
                DateFormat df = new SimpleDateFormat("dd.MM.yyyy.");
        String currentDate = df.format(r.getDate());
        frmReservations.getTxtDate().setText(currentDate);
                frmReservations.getTxtId().setText(String.valueOf(r.getId()));
                frmReservations.getTxtPlace().setText(r.getPlace());
                
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
                    JOptionPane.showMessageDialog(frmReservations, ex);
                }
                try {
                    List<PhotographyServices> phs=Communication.getInstance().getAllServices();
                     
                     List<ReservationDetail> details=r.getReservationDetails();
                     for (ReservationDetail detail : r.getReservationDetails()) {
                         for (PhotographyServices service : phs) {
                             if(detail.getService().getId().equals(service.getId())){
                                 detail.setService(service);
                             }
                         }
                    
                }
                   
                } catch (Exception ex) {
                   JOptionPane.showMessageDialog(frmReservations, ex);
                }
                ReservationDetailTableModel model=new ReservationDetailTableModel(r);
                    frmReservations.getTblItems().setModel(model);
                
                
                  
                     
                     
               
        
                
                
            }
        });
     
       
            
        
    }
    
}
