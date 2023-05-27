import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Client {

    private static final String HELO = "HELO\n"; // declearing static Variables
    private static final String AUTH = "AUTH saad\n";
    private static final String REDY = "REDY\n";
    private static final String SCHD = "SCHD ";
    private static final String NONE = "NONE\n";
    private static final String QUIT = "QUIT\n";
    private static final String GETS_CAPABLE = "GETS Capable ";
    private static final String GETS_AVAIL = "GETS Avail ";
    private static final String OK = "OK\n";
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 50000;
    private static String LARGEST_TYPE;
    private static String FIRST_SERVER_TYPE;
    private static int LARGEST_CORES;
    private static Socket socket; // creating the socket
    private static DataOutputStream dataOut; // for outputing to the server
    private static InputStreamReader dataIn; // to receive messages from server
    private static BufferedReader bufferedReader; // to receive messages from server

    public static void main(String[] args) throws Exception {
        initConnection();
        startProcessing();
        socket.setSoTimeout(1000);
        closeConnection();
    }

    private static void startProcessing() {
        Map<String, List<ServerInfo>> servers = new HashMap();
        String response = sendMessage(HELO); // Handshake begins
        sendMessage(AUTH); // Handshake ends
        while (!response.isBlank()) {
            response = sendMessage(REDY); // Alerting the server that client is REDY
            if (response.contains("NONE")) {
                sendMessage(QUIT);
                break;
            }

            else if (response.contains("JOBN")) {
                jobnHandler(response, servers);
            }
        }
    }

    private static void initConnection() {
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            dataOut = new DataOutputStream(socket.getOutputStream());
            dataIn = new InputStreamReader(socket.getInputStream());
            bufferedReader = new BufferedReader(dataIn);
        } catch (UnknownHostException hostException) {
            System.out.println("Host unknown exception detected " + hostException.getMessage());
        } catch (IOException ioException) {
            System.out.println("IO exception detected " + ioException.getMessage());
        }
    }

    private static void closeConnection() {
        try {
            bufferedReader.close();
            dataIn.close();
            dataOut.close();
            socket.close();
        } catch (IOException ioException) {
            System.out.println("IO exception detected " + ioException.getMessage());
        }
    }

    private static void jobnHandler(String response, Map<String, List<ServerInfo>> servers) {
        Job job = new Job(response.split(" "));
        int changeCommand;
        response = sendMessage(GETS_AVAIL + job.getRequiredResources());
        int numOfServers = Integer.parseInt(response.split(" ")[1]); // Store Num of servers

        if (numOfServers == 0) {
            System.out.println("No available servers");
            sendMessage(OK);
            changeCommand = Integer.parseInt(response.split(" ")[1]);
            if (changeCommand == 0) {
                response = sendMessage(GETS_CAPABLE + job.getRequiredResources());
                numOfServers = Integer.parseInt(response.split(" ")[1]);
                response = sendMessage(OK);

                try {
                    servers = getServers(response, numOfServers);
                } catch (IOException ioException) {
                    System.out.println("IO exception detected " + ioException.getMessage());
                    return;
                }
                response = sendMessage(OK);
                if (response.equals(".")) {

                    // System.out.println(servers.get(key));
                    Schedule schedule = new Schedule(job, servers.get(FIRST_SERVER_TYPE));
                    response = sendMessage(schedule.scheduleJob(SCHD));

                }
            }

        } else {
            response = sendMessage(OK);
            try {
                servers = getServers(response, numOfServers);
            } catch (IOException ioException) {
                System.out.println("IO exception detected " + ioException.getMessage());
                return;
            }

            response = sendMessage(OK);
            if (response.equals(".")) {

                // System.out.println(servers.get(key));
                Schedule schedule = new Schedule(job, servers.get(FIRST_SERVER_TYPE));
                response = sendMessage(schedule.scheduleJob(SCHD));

            }
        }
    }

    private static String sendMessage(String message) {

        System.out.print("Sending Message: " + message);

        try {
            dataOut.write(message.getBytes());
            String response = bufferedReader.readLine();
            System.out.println("Recieved Message: " + response);

            return response;

        } catch (IOException e) {
            System.out.println("Exception occured when sending message " + e.getMessage());
        }
        return "";
    }

    private static Map<String, List<ServerInfo>> getServers(String firstServer, int numOfServers) throws IOException {
        Map<String, List<ServerInfo>> serversMap = new LinkedHashMap(); // Hashtable with the keys being the server type
        int currentLargestCore = 0;
        String currentLargestType = "";
        String line = firstServer;
        FIRST_SERVER_TYPE = firstServer.split(" ")[0];
        for (int i = 0; i < numOfServers; i++) { // looping through the servers

            if (i != 0) {
                line = bufferedReader.readLine();
            }

            ServerInfo serverInfo = new ServerInfo(line.split(" "));

            // add the servers info by splitting the spaces from the server's response
            if (currentLargestCore <= serverInfo.getCore()) { // searching for largest core
                currentLargestCore = serverInfo.getCore(); // fillter the largest servers
                currentLargestType = serverInfo.getType();
            }
            List<ServerInfo> servers = serversMap.get(serverInfo.getType());
            // GET ALL SERVERS THAT BELONG TO THE SAME TYPE OF THE OBJECT
            if (servers == null) {
                servers = new ArrayList<>();
            }
            servers.add(serverInfo);
            // add the servers to the list
            serversMap.put(serverInfo.getType(), servers); // assigning keys to the lists and storing each server
                                                           // arraylist

        }
        if (LARGEST_CORES <= currentLargestCore) {
            // putting a condition in order to keep the first largest server if both have
            // the same core count
            LARGEST_CORES = currentLargestCore; // assigning the static varibales to be used in scheduling
            LARGEST_TYPE = currentLargestType;
        }

        return serversMap;
    }

}
