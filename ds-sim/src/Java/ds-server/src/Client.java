import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {

    class Server { // class to store and retrive server info
        private String type;
        private int cores;
        private int id;
        private int limit;
        private double hourlyRate;
        private int memory;
        private int diskSpace;

        public Server(String type, int id, int limit, double hourlyRate, int cores, int memory, int diskSpace) {
            this.type = type;
            this.cores = cores;
            this.id = id;
            this.limit = limit;
            this.hourlyRate = hourlyRate;
            this.cores = cores;
            this.memory = memory;
            this.diskSpace = diskSpace;
        }

        public String getType() {
            return type;
        }

        public int getCores() {
            return cores;
        }

        public int getId() {
            return id;
        }

        public int getLimit() {
            return limit;
        }

        public int getMemory() {
            return memory;
        }

        public double getHourlyRate() {
            return hourlyRate;
        }

        public int getDiskSpace() {
            return diskSpace;
        }
    }

    public static void main(String[] args) throws Exception {

        String HELO = "HELO\n";
        String AUTH = "AUTH 47478969\n";
        String REDY = "REDY\n";
        String SCHD = "SCHD\n";
        Socket clientSocket = new Socket("127.0.0.1", 50000); // creating the socket
        String message = "";
        DataOutputStream dataOut = new DataOutputStream(clientSocket.getOutputStream()); // dataout for writing to the
                                                                                         // server
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // bufferreader
                                                                                                                  // to
                                                                                                                  // read
                                                                                                                  // from
                                                                                                                  // theserver
        Server largestServer;
        ArrayList<Server> servers = new ArrayList<Server>();
        dataOut.write(HELO.getBytes()); // Handshake begins
        dataOut.flush();
        message = bufferedReader.readLine();
        System.out.println("The server is saying:" + message);
        dataOut.write(AUTH.getBytes());
        dataOut.flush();
        message = bufferedReader.readLine();
        System.out.println("The server is saying:" + message); // Handshake ends

        while (!message.equals("NONE")) {
            dataOut.write(REDY.getBytes()); // Alerting the server that client is REDY
            dataOut.flush();
            message = bufferedReader.readLine();
            System.out.println("The server is saying: " + message);
            if (message.equals("JOBN")) {
                dataOut.write(("GETS All\n").getBytes());
                dataOut.flush();
                message = bufferedReader.readLine();
                System.out.println("The server is saying: " + message);
                dataOut.write(("OK").getBytes());
                dataOut.flush();
                servers.add(bufferedReader.readLine()); // adding all servers to a list

                dataOut.write(("OK").getBytes());
                message = bufferedReader.readLine();
                if (message.equals(".")) {

                }

            }

        }

        dataOut.close();
        bufferedReader.close();
        clientSocket.close();

    }

    private Server largestServer() {
        int largestindex = 0;
        int maxCores = 0;
        for (int i = 0; i < servers.size(); i++) {
            if (servers.get(i).getCores() > maxCores) {
                maxCores = server.get(i).getCores();
                largestindex = i;
            }
        }
        return servers.get(largestindex);
    }

}
