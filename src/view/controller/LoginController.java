/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import jdk.nashorn.internal.objects.NativeString;
import view.form.FrmLogin;

/**
 *
 * @author Nadja
 */
public class LoginController {
    private final FrmLogin frmLogin;

    public LoginController(FrmLogin frmLogin) {
        this.frmLogin = frmLogin;
        addActionListener();
    }
    public void openForm() {
        frmLogin.setVisible(true);
    }
    

    private void addActionListener() {
        frmLogin.loginAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                login(ae);
            }

            private void login(ActionEvent ae) {
                
                try {
               String username= frmLogin.getTxtUsername().getText().trim();
               String password=String.copyValueOf(frmLogin.getTxtPassword().getPassword()).trim();
               validateInput();
               
                    
                } catch (Exception e) {
                }
               
            
            }

            
    });}
    private void validateInput() throws Exception{
               String username= frmLogin.getTxtUsername().getText().trim();
               String password=String.copyValueOf(frmLogin.getTxtPassword().getPassword()).trim();
               
            if(username.equals("")){
                frmLogin.getLblUsernameValidation().setText("Username cannot be empty!");
                frmLogin.getLblUsernameValidation().setForeground(Color.red);
                throw new Exception("Empty username");
            }
            if(password.equals("")){
                frmLogin.getLblPasswordValidation().setText("Password cannot be empty!");
                frmLogin.getLblPasswordValidation().setForeground(Color.red);
                throw new Exception("Empty password");
            }
            
            frmLogin.getLblUsernameValidation().setText("good");
            frmLogin.getLblPasswordValidation().setText("good");
            
            
    }
    
    
}
