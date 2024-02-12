import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try(Socket socket = new Socket("localhost", 300)){
            Scanner scanner = new Scanner(System.in);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
            
            boolean giocatore1 = "1".equals(reader.readLine()) ? true : false;

            if(giocatore1){
                System.out.println("Inserisci due valori X ed Y / X -> [1,3] ed Y -> [1,3]");
                String giocata = scanner.nextLine();
                writer.writeBytes(giocata + "\n");
            }

            partita: while(true){
                String risposta = reader.readLine();
                if(!risposta.contains("[")){
                    System.out.println(risposta);
                    break partita;
                }
                if(risposta.contains("[ERRORE]")) System.out.println(risposta);
                System.out.println("Inserisci due valori X ed Y / X -> [1,3] ed Y -> [1,3]");
                String giocata = scanner.nextLine();
                writer.writeBytes(giocata + "\n");
            }

            scanner.close();
            reader.close();
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}