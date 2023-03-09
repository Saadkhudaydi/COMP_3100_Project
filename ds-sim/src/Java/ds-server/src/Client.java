import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws Exception {

        Socket clientSocket = new Socket("localhost", 6000);

        DataInputStream datain = new DataInputStream(clientSocket.getInputStream());
        DataOutputStream dataOut = new DataOutputStream(clientSocket.getOutputStream());

        String message = "HELO";

        while (!message.toLowerCase().equals("bye")) {

            dataOut.writeUTF(message);

            message = datain.readUTF();
            System.out.println(message);
            if (message.equals("G'DAY")) {
                dataOut.writeUTF("BYE");

            }

        }
        dataOut.close();
        datain.close();
        clientSocket.close();
    }
}
