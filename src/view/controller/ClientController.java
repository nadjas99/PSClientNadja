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
import javax.swing.JOptionPane;
import view.coordinator.ViewCordinator;
import view.form.FrmAddClient;

/**
 *
 * @author Nadja
 */
public class ClientController {
    private final FrmAddClient frmAddClient;

    public ClientController(FrmAddClient frmAddClient) {
        this.frmAddClient = frmAddClient;
        addActionListener();
    }

    public void openForm() {
        frmAddClient.setVisible(true);
        
    }
    
    private void addActionListener(){
        
        frmAddClient.addBtnAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                
                try {
                   if(frmAddClient.getTxtName().getText().trim().equals("") || frmAddClient.getTxtSurname().getText().trim().equals("") || frmAddClient.getTxtEmail().getText().trim().equals("") || frmAddClient.getTxtAdress().getText().trim().equals("")){
                        JOptionPane.showMessageDialog(frmAddClient, "Text fields can't be empty! Fill in all text fields and try again");
                        return;
                     } 
                   Client client=new Client();
                   client.setName(frmAddClient.getTxtName().getText().trim());
                   client.setSurname(frmAddClient.getTxtSurname().getText().trim());
                   client.setAdress(frmAddClient.getTxtAdress().getText().trim());
                   client.setEmail(frmAddClient.getTxtEmail().getText().trim());
                   validateClient(client);
                   Communication.getInstance().addNewClient(client);
                   JOptionPane.showMessageDialog(frmAddClient, "New client added successfully!");
             } catch (Exception e) {
                    JOptionPane.showMessageDialog(frmAddClient, "Unsuccessfully adding member: "+e.getMessage());
                }
                
            }});
    }

            
        
        private void validateClient(Client client) throws Exception {
                if(!client.getEmail().contains("@gmail.com")){
                    throw new Exception("Email not valid! Email needs to be gmail for purposes of using google drive.");
            }
            }
    
    
}
