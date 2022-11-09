package compitoottria;

import java.net.*;
import java.util.ArrayList;

public class App 
{
    public static void main(String [] args) throws Exception
    {
        try(ServerSocket serverSocket = new ServerSocket(1723)){
            System.out.println("Server opened.");
            ArrayList<ClientHandler> handlerList = new ArrayList<ClientHandler>();
            while(true){
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket, handlerList);
                handlerList.add(clientHandler);
                clientHandler.start();
            }   
        }catch (Exception e){
            System.out.println(e);
        }
    }
}