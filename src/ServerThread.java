import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class ServerThread extends Thread{
    Socket socketP1, socketP2;
    Tris game;
    public ServerThread(Socket socketP1, Socket socketP2){
        this.socketP1 = socketP1;
        this.socketP2 = socketP2;
        game = new Tris();
    }

    @Override
    public void run(){
        try{
            BufferedReader readerP1 = new BufferedReader(new InputStreamReader(socketP1.getInputStream()));
            DataOutputStream writerP1 = new DataOutputStream(socketP1.getOutputStream());
            BufferedReader readerP2 = new BufferedReader(new InputStreamReader(socketP2.getInputStream()));
            DataOutputStream writerP2 = new DataOutputStream(socketP2.getOutputStream());
            Scanner scanner = new Scanner(System.in);
            
            writerP1.writeBytes("1\n");
            writerP2.writeBytes("2\n");

            partita: while(true){
                String giocataP1 = readerP1.readLine();
                try {
                    game.inserisciPunto(true, intValue(giocataP1.charAt(0)), intValue(giocataP1.charAt(1)));
                    writerP2.writeBytes("[OK]\n");
                    System.out.println(game);
                    giocoP2: while(true){ 
                        try{
                            String giocataP2 = readerP2.readLine();
                            game.inserisciPunto(false, intValue(giocataP2.charAt(0)), intValue(giocataP2.charAt(1)));
                            System.out.println(game);
                            break giocoP2;
                        }catch(TrisException e){
                            switch(e.message){
                                case "1":
                                    writerP2.writeBytes("[ERRORE]: valori sbagliati\n");
                                    break;
                                case "2":
                                    writerP2.writeBytes("[ERRORE]: casella gia utlizzata\n");
                                    break;
                                case "V2":
                                    writerP2.writeBytes("HAI VINTO\n");
                                    writerP1.writeBytes("HAI PERSO\n");
                                    break partita;
                                default: writerP2.writeBytes("[ERRORE] sconosciuto\n");
                            }
                        }
                    }
                    writerP1.writeBytes("[OK]\n");
                } catch (TrisException e) {
                    switch(e.message){
                        case "1":
                            writerP1.writeBytes("[ERRORE]: valori sbagliati\n");
                            break;
                        case "2":
                            writerP1.writeBytes("[ERRORE]: casella gia utlizzata\n");
                            break;
                        case "V1":
                            writerP1.writeBytes("HAI VINTO\n");
                            writerP2.writeBytes("HAI PERSO\n");
                            break partita;
                        case "P":
                            writerP1.writeBytes("PAREGGIO\n");
                            writerP2.writeBytes("PAREGGIO\n");
                            break partita;
                        default: writerP1.writeBytes("[ERRORE] sconosciuto\n");
                    }
                }
            }

            scanner.close();
            readerP1.close();
            writerP1.close();
            readerP2.close();
            writerP2.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private int intValue(char c){
        return Integer.parseInt(Character.toString(c));
    }
}