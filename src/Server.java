import java.io.IOException;
import java.net.ServerSocket;

public class Server{
    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(300)){
            while(true){
                new ServerThread(serverSocket.accept(), serverSocket.accept()).start();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}