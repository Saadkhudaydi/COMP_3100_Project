import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.Line;

// JOBN submitTime jobID estRuntime core memory disk
public class Client {

    private static final String HELO = "HELO\n"; // declearing static Variables
    private static final String AUTH = "AUTH 47478969\n";
    private static final String REDY = "REDY\n";
    private static final String SCHD = "SCHD\n";
    private static final String OK = "OK\n";
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 50000;

    public static void main(String[] args) throws Exception {
        Socket clientSocket = new Socket(SERVER_IP, SERVER_PORT); // creating the socket
        DataOutputStream dataOut = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String message = "";

        sendMessage(HELO, dataOut);  // Handshake begins
        message = receiveMessage(bufferedReader);
        
        sendMessage(AUTH, dataOut);
        message = receiveMessage(bufferedReader); // Handshake ends

        while (!message.equals("NONE")) {
            
            sendMessage(REDY, dataOut); // Alerting the server that client is REDY
            message = receiveMessage(bufferedReader);

            if (message.contains("JOBN")) {
                String[] jobnInfo = message.split(" ");
                dataOut.write(
                        ("GETS Capable " + jobnInfo[4] + " " + jobnInfo[5] + " " + jobnInfo[6] + "\n").getBytes());
                        sendMessage("GETS Capable " + jobnInfo[4] + " " + jobnInfo[5] + " " + jobnInfo[6] + "\n", dataOut);
                        sendMessage("GETS All\n", dataOut);

                message = receiveMessage(bufferedReader);
                
                dataOut.write(OK.getBytes());
                System.out.println(bufferedReader.readLine());
                servers.addAll(getServers(bufferedReader));

                dataOut.write(OK.getBytes());
                message = bufferedReader.readLine();
                if (message.equals(".")) {

                }

            }

        }

        dataOut.close();
        bufferedReader.close();
        clientSocket.close();

    }

    private static void sendMessage(String message, DataOutputStream dataOut) {
        System.out.println("Sending Message: " + message);
        try {
            dataOut.write(message.getBytes());
        } catch (IOException e) {
            System.out.println("Exception occured when sending message" + e.getMessage());
        }
    }

    private static String receiveMessage(BufferedReader bufferedReader) {
        try {
            String message = bufferedReader.readLine();
            System.out.println("Recieved Message: "+message);
            return message;
        } catch (IOException e) {
            System.out.println("Exception occured when receiving message" + e.getMessage());
        }
        return "";
    }

    private static List<ServerInfo> getServers(BufferedReader buffer) throws IOException {
        List<ServerInfo> servers = new ArrayList<>();
        String line = buffer.readLine();
        while (line != null) {
            servers.add(new ServerInfo(line.split(" "))); // add the servers info by splitting the spaces from the
                                                          // server's response
            line = buffer.readLine();
        }
        return servers;

    }

    private static String[] 

}
