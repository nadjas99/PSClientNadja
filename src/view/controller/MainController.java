/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;


import domain.Photographer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.net.SocketException;
import javax.swing.JOptionPane;
import view.coordinator.ViewCordinator;
import view.form.FrmMain;

/**
 *
 * @author Nadja
 */
public class MainController {
    private final FrmMain frmMain;

    public MainController(FrmMain frmMain) {
        this.frmMain = frmMain;
        addActionListener();
    }
    
    public void openForm(){
        prepareView();
        frmMain.setVisible(true);
        
    }

    private void addActionListener() {
        frmMain.getJmiMakeReservation().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewCordinator.getInstance().openAddReservationForm(); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        frmMain.addjmiAddNewClientListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                ViewCordinator.getInstance().openAddClientForm();
            }
        });
        
        frmMain.addjmiSeeAllClientsListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                ViewCordinator.getInstance().openViewClientsForm();
            }
        });
        
        frmMain.addjmiSeeAllReservationsListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                ViewCordinator.getInstance().openEditReservationForm();
            }
        });
        
        frmMain.addjmiSeeAllServicesListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                ViewCordinator.getInstance().openServicesForm();
            }
        });
        frmMain.addBtnLogoutActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                 try{
            Photographer cm = (Photographer) ViewCordinator.getInstance().getParam("photographer");
            Socket socket = communication.Communication.getInstance().logout(cm);
            frmMain.dispose();
           
            socket.close();
            ViewCordinator.getInstance().openLoginForm();
        }catch(SocketException se){
            JOptionPane.showMessageDialog(frmMain, "Server is closed, Goodbye");
            System.exit(0);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(frmMain, "Goodbye!"+e.getMessage());
        }
            }
        });
    }

    private void prepareView() {
        Photographer p = (Photographer) ViewCordinator.getInstance().getParam("photographer");
        frmMain.getLblUser().setText(p.toString());
    }

    public FrmMain getFrmMain() {
        return frmMain;
    }
    
    
    
    
}
