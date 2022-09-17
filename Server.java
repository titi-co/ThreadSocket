import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    // Defines the default port
    final static int port = 3000;

    public static void main(String[] args) throws IOException {
        // Defines the ticket object
        Ticket ticket = new Ticket("BTS Concert", 2);

        // Initiates the socket server on port 3000
        ServerSocket server = new ServerSocket(port);

        // Infinite loop to get new clients
        while (true) {
            Socket socket = null;

            try {

                // The socket object receives the client request
                socket = server.accept();

                System.out.println("New client connected: " + socket);

                // in and out streams
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());

                System.out.println("Assigning thread for the client");

                // The thread is assigned with a TicketStore instance to work with
                Thread thread = new TicketStore(socket, input, output, ticket);

                // The thread is started
                thread.start();
            } catch (Exception e) {
                socket.close();
                e.printStackTrace();
                server.close();
            }
        }

    }
}
