/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;


import domain.Photographer;
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
        Request request = new Request(Operation.LOGIN, new Photographer(null, null,username,password));
        sender.send(request);
        
        Response response = (Response) receiver.receive();
        if(response.getException()==null){
            return (Photographer) response.getResult();
        } else throw response.getException();
         
    }}

   