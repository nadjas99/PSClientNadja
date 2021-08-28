/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;

import communication.Communication;
import domain.Client;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import view.coordinator.ViewCordinator;
import view.form.FrmClients;

/**
 *
 * @author Nadja
 */
public class ClientViewController {
    private final FrmClients frmClients;

    public ClientViewController(FrmClients frmClients) {
        this.frmClients = frmClients;
        ActionPerformed();
        
    }
     public void openForm(){
         prepareView();
         frmClients.setVisible(true);
         
     }
    private void ActionPerformed() {
       frmClients.addBtnDeleteActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               Client clientDelete=(Client) frmClients.getCmbClients().getSelectedItem();
                 int answer = JOptionPane.showConfirmDialog(frmClients, "Do you really want to delete this client?");
            if(answer!=0){
                return;
            }
               try {
                   Communication.getInstance().deleteClient(clientDelete);
               } catch (Exception ex) {
                   System.out.println("Neuspesno brisanje"+ex.getMessage());
               }
            ViewCordinator.getInstance().refreshClients();
            JOptionPane.showMessageDialog(frmClients, "Member deleted successfully!");
            frmClients.dispose();
               
               
         }
       });
       frmClients.addBtnEditActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent ae) {
               frmClients.getjPanel1().setVisible(true);
               Client c=(Client) frmClients.getCmbClients().getSelectedItem();
               frmClients.getjPanel1().setEnabled(true);
               frmClients.getjButton1().setVisible(true);
               frmClients.getTxtName().setText(c.getName());
               frmClients.getTxtSurname().setText(c.getSurname());
               frmClients.getTxtEmail().setText(c.getEmail());
               frmClients.getTxtAdress().setText(c.getAdress());
               
              
          }
       });
       
       frmClients.addBtnSaveChangesActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent ae) {
               Client c=(Client) frmClients.getCmbClients().getSelectedItem();
                if(frmClients.getTxtName().getText().trim().equals("") || frmClients.getTxtSurname().getText().trim().equals("") || frmClients.getTxtEmail().getText().trim().equals("") || frmClients.getTxtAdress().getText().trim().equals("")){
                        JOptionPane.showMessageDialog(frmClients, "Text fields can't be empty! Fill in all text fields and try again");
                        return;
                     } 
                
                c.setName(frmClients.getTxtName().getText().trim());
                c.setSurname(frmClients.getTxtSurname().getText().trim());
                c.setAdress(frmClients.getTxtAdress().getText().trim());
                c.setEmail(frmClients.getTxtEmail().getText().trim());
               try {
                   Communication.getInstance().editClient(c);
                   JOptionPane.showMessageDialog(frmClients, "Client successfully edited!");
               } catch (Exception ex) {
                   JOptionPane.showMessageDialog(frmClients, "Error while editing clients info, try again"+ex.getMessage());
                   System.out.println("Error while editing clients info, try again"+ex.getMessage());;
               }
               
               
          }
       });
       frmClients.addBtnViewActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent ae) {
               frmClients.getjPanel1().setVisible(true);
               Client c=(Client) frmClients.getCmbClients().getSelectedItem();
               frmClients.getTxtName().setText(c.getName());
               frmClients.getTxtSurname().setText(c.getSurname());
               frmClients.getTxtAdress().setText(c.getAdress());
               frmClients.getTxtEmail().setText(c.getEmail());
          }
       });
       
       
       
    }
    

    public void prepareView() {
        try {
            fillCbClients();
            frmClients.getjPanel1().setVisible(false);
            
            frmClients.getjButton1().setVisible(false);
        } catch (Exception ex) {
            Logger.getLogger(ClientViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

    private void fillCbClients() throws Exception {
        frmClients.getCmbClients().removeAllItems();
        List<Client> clients=Communication.getInstance().getAllClients();
        for(Client c : clients){
            System.out.println(c.getName());
            frmClients.getCmbClients().addItem(c);
            System.out.println("nesto");
        }
        
       
    }
       
    
    

    
    
}
