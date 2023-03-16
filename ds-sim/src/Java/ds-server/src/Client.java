import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws Exception {

        Socket clientSocket = new Socket("127.0.0.1", 50000);

        //DataInputStream datain = new DataInputStream(clientSocket.getInputStream());
        
        //ByteArrayOutputStream baos = new ByteArrayOutputStream();
        

        String message = "";

        // while (!message.toLowerCase().equals("bye")) {

            

            
          //  System.out.println(message);
          //  if (message.equals("G'DAY")) {
               // dataOut.write(("BYE\n").getBytes());

           // }
            
                

       // }
              try{  
                DataOutputStream dataOut = new DataOutputStream(clientSocket.getOutputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                dataOut.write(("HELO\n").getBytes());
                dataOut.flush();
                //dataOut.write(message.getBytes());
                message = bufferedReader.readLine();
               System.out.println("The server is saying:"+message);
              // String username = "47478969";
               dataOut.write(("AUTH 47478969\n").getBytes());
               dataOut.flush();
               message = bufferedReader.readLine();
               System.out.println("The server is saying:"+message);
               dataOut.write(("REDY\n").getBytes());
               dataOut.flush();
               message = bufferedReader.readLine();
               System.out.println("The server is saying:"+message);
               dataOut.write(("QUIT\n").getBytes());
               dataOut.flush();
               message = bufferedReader.readLine();
               System.out.println("The server is saying:"+message);

        dataOut.close();
        //datain.close();
        bufferedReader.close();
        clientSocket.close();
    }
    catch(Exception e){System.out.println(e);}
    }}

