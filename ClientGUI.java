import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ClientGUI implements ActionListener {
    final static int port = 3000;
    private InetAddress ip;
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    private JFrame frame;
    private JPanel panel;
    private JButton orderButton;
    private JLabel label;

    public ClientGUI() throws IOException {
        // Gets the ip adress from the localhost
        ip = InetAddress.getByName("localhost");

        // Opens the socket connection
        socket = new Socket(ip, port);

        // Open the data input and output streams
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());

        // Creates the screen components
        frame = new JFrame();
        panel = new JPanel();

        panel.setBackground(new java.awt.Color(110, 56, 197));

        orderButton = new JButton("Order Ticket");
        orderButton.addActionListener(this);
        orderButton.setPreferredSize(new Dimension(250, 100));
        label = new JLabel("");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setForeground(Color.white);

        panel.setBorder(BorderFactory.createEmptyBorder(100, 200, 100, 200));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(orderButton);
        panel.add(label);

        frame.add(panel, BorderLayout.CENTER);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                // Closes everything on window close
                try {
                    output.writeUTF("2");
                    output.close();
                    input.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.exit(1);
            }
        });
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("BTS Concert");
        frame.pack();
        frame.setVisible(true);

    }

    public static void main(String args[]) throws IOException {
        new ClientGUI();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        try {
            output.writeUTF("1");

            label.setText(input.readUTF());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
