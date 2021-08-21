/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        frmMain.addjmiAddNewClientListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //ViewCordinator.getInstance().openAddMemberForm();
            }
        });
        
        frmMain.addjmiSeeAllClientsListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //ViewCordinator.getInstance().openViewMembers();
            }
        });
        
        frmMain.addjmiSeeAllReservationsListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //ViewCordinator.getInstance().openViewTaskGroups();
            }
        });
        
        frmMain.addjmiSeeAllServicesListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //ViewCordinator.getInstance().openProfileForm();
            }
        });
    }

    private void prepareView() {
        
    }

    public FrmMain getFrmMain() {
        return frmMain;
    }
    
    
    
    
}
