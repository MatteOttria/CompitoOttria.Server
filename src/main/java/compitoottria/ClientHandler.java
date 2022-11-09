package compitoottria;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread{
    private static Socket socket;
    PrintWriter printWriter;
    static int biglietti = 3;
    static private ArrayList<ClientHandler> handlerList = new ArrayList<ClientHandler>();
    public ClientHandler(Socket socket,  ArrayList<ClientHandler> handlerList){
        this.socket = socket;
        this.handlerList = handlerList;
    }

    Socket getSocket(){
        return socket; 
    }

    @Override
    public void run() {
        try {
            System.out.println("Client connected.");
            InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
            BufferedReader bufferReader = new BufferedReader(streamReader);
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("You are connect to the server. Comandi disponibili: 'D' -> richiesta disponibilità; 'A' ->  acquista biglietto; 'Q' -> disconnessione");
            gestore(printWriter, bufferReader);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void gestore(PrintWriter printWriter, BufferedReader bufferedReader){
        try {
            while (true) {
                if (biglietti <= 0) {
                    for(ClientHandler client : handlerList) {
                        System.out.println("Client Disconnected");
                        client.printWriter.println("Disconnessione in corso. Biglietti terminati.");
                        client.printWriter.println("@");
                    }
                    return;
                }else{
                    String str = bufferedReader.readLine();
                    if (str.equals("D")) {
                        disponibilità(printWriter);
                    }else if(str.equals("A")){
                        acquista(printWriter);
                    }else if(str.equals("Q")){
                        disconetti(printWriter);
                    }else{
                        printWriter.println("Comando: " + str + " non riconosciuto.");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void disponibilità(PrintWriter printWriter){
        printWriter.println("I biglietti rianenti sono " + biglietti);
    }

    public static void acquista(PrintWriter printWriter){
        biglietti = biglietti -1;
        printWriter.println("Biglietto acquistato.");
    }

    public static void disconetti(PrintWriter printWriter) throws Exception{
        printWriter.println("Disconnessione");
        socket.close();
    }
}