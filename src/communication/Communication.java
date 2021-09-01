/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;


import domain.Client;
import domain.Photographer;
import domain.PhotographyServices;
import domain.Reservation;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

/**
 *
 * @author FON
 */
public class Communication {
    private static Communication instance;
    private Socket socket;
    private Receiver receiver;
    private Sender sender;
    
    private Communication(){
    }
    
    public void connect() throws Exception{
        try{
            socket = new Socket("localhost", 9000);
            receiver = new Receiver(socket);
            sender = new Sender(socket);
        }catch(SocketException se){
            throw new Exception("Server is not connected!");
            
        }catch(Exception e){
            
        }
    }
    
    public static Communication getInstance(){
        if(instance==null){
            instance = new Communication();
        }
        return instance;
    }

    public Receiver getReceiver() {
        return receiver;
    }
    
   
    
    public Photographer login(String username, String password) throws Exception{
        Photographer ph=new Photographer();
        ph.setUsername(username);
        ph.setPassword(password);
        Request request = new Request(Operation.LOGIN, ph);
        sender.send(request);
        
        Response response = (Response) receiver.receive();
        if(response.getException()==null){
            return (Photographer) response.getResult();
        } else throw response.getException();
         
    }
    public void addNewClient(Client c) throws Exception {
        Request request = new Request(Operation.ADD_NEW_CLIENT,c);
        sender.send(request);
        
        Response response = (Response) receiver.receive();
        if(response.getException()==null){
            
        } else throw response.getException();
    }

    public List<Client> getAllClients() throws Exception {
        Request request = new Request(Operation.GET_ALL_CLIENTS,null);
        sender.send(request);
        
        Response response = (Response) receiver.receive();
        if(response.getException()==null){
            return (List<Client>) response.getResult();
        } else throw response.getException();
    }

    public void editClient(Client c) throws Exception {
        Request request = new Request(Operation.EDIT_CLIENT,c);
        sender.send(request);
        
        Response response = (Response) receiver.receive();
        if(response.getException()==null){
            
        } else throw response.getException();
    }

    public void deleteClient(Client c) throws Exception{
        Request request = new Request(Operation.DELETE_CLIENT,c);
        sender.send(request);
        
        Response response = (Response) receiver.receive();
        if(response.getException()==null){
            
        } else throw response.getException();
    }

    public List<PhotographyServices> getAllServices() throws Exception {
         Request request = new Request(Operation.GET_ALL_SERVICES,null);
        sender.send(request);
        
        Response response = (Response) receiver.receive();
        if(response.getException()==null){
            return (List<PhotographyServices>) response.getResult();
        } else throw response.getException();
    }
     public void addNewReservation(Reservation r) throws Exception {
        Request request = new Request(Operation.ADD_NEW_RESERVATION,r);
        sender.send(request);
        
        Response response = (Response) receiver.receive();
        if(response.getException()==null){
            
        } else throw response.getException();
    }
     
      public List<Reservation> getAllRes() throws Exception {
        Request request = new Request(Operation.GET_ALL_RESERVATIONS,null);
        sender.send(request);
        
        Response response = (Response) receiver.receive();
        if(response.getException()==null){
            return (List<Reservation>) response.getResult();
        } else throw response.getException();
    }
        public void editReservation(Reservation r) throws Exception {
        Request request = new Request(Operation.UPDATE_RESERVATION,r);
        sender.send(request);
        
        Response response = (Response) receiver.receive();
        if(response.getException()==null){
            
        } else throw response.getException();
    }
        
          public Socket logout(Photographer p) throws Exception {
        Request request = new Request(Operation.LOGOUT,p);
        sender.send(request);
        
        Response response = (Response) receiver.receive();
        if(response.getException()==null){
            return socket;
        } else throw response.getException();
    }
    







}

   
