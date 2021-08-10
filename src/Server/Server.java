package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private ServerSocket server;
    private Socket socket;
    private final int PORT = 8189;

    private List<ClientHandler> clients;
    private AuthService authService;

    public Server() {
        clients = new CopyOnWriteArrayList<>();
        authService = new SimpleAuthService();
        try {
            server = new ServerSocket(PORT);
            System.out.println("Server started!");

            while (true) {
                socket = server.accept();
                System.out.println("Client connected");
                new ClientHandler(socket, this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void broadcastMsg(ClientHandler sender, String msg) {
        String message = String.format("[%s]: %s", sender.getNickname(), msg);
//        System.out.println(message);

        for (ClientHandler c : clients) {
                c.sendMsg(message);
        }
    }

    public void broadcastMsgSingleUser(ClientHandler sender, String msg, String recipient) {
        String message = String.format("[%s]: %s", sender.getNickname(), msg);
//        System.out.println(message);
        for (ClientHandler c : clients) {
                if (recipient.equals(c.getNickname()) || sender.getNickname().equals(c.getNickname())) {
                    c.sendMsg(message);
                }
        }
    }

    public AuthService getAuthService() {
        return authService;
    }

    public void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
    }

    public void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }

}

