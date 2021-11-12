package com.javaproject.client.main;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import com.javaproject.protocol.*;
import com.javaproject.client.gui.*;

public class Client {
    private Socket socket;
    private String ID;
    private int roomNum = -1;
    private final String IP = "127.0.0.1";
    private final int port = 9001;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Protocol data ;

    //
    public Client() {


        startClient();
    }

    public void startClient() {

        try {
            socket = new Socket();
            SocketAddress socketAddress = new InetSocketAddress(IP, port);
            socket.connect(socketAddress);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

        } catch (Exception e) {
            System.out.println("�������� ����");
            e.printStackTrace();
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    receive();
                } catch (Exception e) {
                    stopClient();
                    e.printStackTrace();
                }

            }
        });
        thread.start();
    }

    //  exit client
    public void stopClient() {
        try {
            if (socket != null && !socket.isClosed()) {
                System.out.println("��������");
                socket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Waiting for the server response
    public void receive() {
        System.out.println("receive start");
        while (true) {
            try {
                data = (Protocol) in.readObject();//???? �� �������� write �� ��ü�� �ƴ϶� ���� ������ ���� ��ü�� �ٽ� �޾ƿ���??
                System.out.println("receive: " + data);
                if(data instanceof ChatData) {
                    analysisChatData((ChatData) data);
                }
                if (data instanceof JoinData) {
                    System.out.println("join protocol");
                    analysisJoinData((JoinData) data);
                } else if (data instanceof LoginData) {
                    System.out.println(((LoginData) data).getID());
                    System.out.println(((LoginData) data).getPassWord());
                    System.out.println("test");
                }
            } catch (Exception e) {
                stopClient();
                e.printStackTrace();
                break;

            }
        }

    }

    // ���۸޼���
    public void sendData(Protocol data) {
        try {
            out.writeObject(data);
            out.flush();
            System.out.println(data + " send ");

        } catch (Exception e) {
            stopClient();
            e.printStackTrace();

        }

    }
    public void analysisLoginData(LoginData data) {

    }

    public void analysisChatData(ChatData data) {

        if(data.getProtocol()==ChatData.SEND_TEXT) {
            //print
            System.out.println(data);
        }

    }


    public void analysisJoinData(JoinData data) {

        try {
            if (data.getProtocol() == JoinData.LOGIN_ACCESS) {
                System.out.println("login acess");
                new LobbyForm().setVisible(true);
            } else if (data.getProtocol() == JoinData.LOGIN_FAILED) {
                System.out.println("login failed");
            } else if (data.getProtocol() == JoinData.JOIN_ROOM) {
                roomNum = Integer.parseInt(data.getMessage());
                System.out.printf("%d Room join!\n", roomNum);

            } else {
                System.out.println("Unkown JoinProtocol!");
            }

        } catch (Exception e) {
            try {
                e.printStackTrace();

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}
