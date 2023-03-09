import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(6000);

        Socket clientmsg = serverSocket.accept();

        DataInputStream DataIN = new DataInputStream(clientmsg.getInputStream());
        DataOutputStream DataOut = new DataOutputStream(clientmsg.getOutputStream());

        String msg = "";
        while (!msg.toLowerCase().equals("bye")) {
            msg = DataIN.readUTF();
            System.out.println(msg);
            if (msg.equals("HELO")) {
                DataOut.writeUTF("G'DAY");
            } else if (msg.equals("BYE")) {
                DataOut.writeUTF("BYE");
            }
        }

        serverSocket.close();
        DataIN.close();
        DataOut.close();
    }
}
