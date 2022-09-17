import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class TicketStore extends Thread {
    final DataInputStream input;
    final DataOutputStream output;
    final Socket socket;

    Ticket tickets;

    public TicketStore(Socket socket, DataInputStream input, DataOutputStream output, Ticket tickets) {
        this.socket = socket;
        this.input = input;
        this.output = output;

        this.tickets = tickets;
    }

    @Override
    public void run() {
        String clientAnswer;

        while (true) {
            try {

                // Reads the answer from the specific client
                clientAnswer = input.readUTF();

                if (clientAnswer.equals("2")) {
                    this.socket.close();
                    System.out.println("Client " + this.socket + " closed connection");
                    break;
                }

                // Does the action and sends it to the client
                switch (clientAnswer) {
                    case "1":
                        System.out.println("Client " + this.socket + "is buying tickets");

                        if (this.tickets.n > 0) {
                            this.tickets.n--;
                            System.out.println("Ticket Sold for the Client " + this.socket);
                            output.writeUTF("\n\nTicket Bought successfully!");

                        } else {
                            System.out.println("No more tickets available!");
                            output.writeUTF("\n\nTicket could not be bought!");
                        }
                        break;
                    default:
                        output.writeUTF("Comando inválido!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            // Close the in and out streams
            this.input.close();
            this.output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
