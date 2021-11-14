package com.javaproject.server;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import com.javaproject.protocol.*;

public class ClientHandler {

    Socket socket;
    private Room room;
    private String ID = ""; // 직렬화로 추가하기
    private String name = new String("");
    private boolean joinFlag = false; // 대기열입장여부
    private Protocol data;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }
        receive();

    }

    public void enterRoom(Room room) {
        // 방입장
        this.room = room;

    }

    public void exitRoom() {
        // 자신이 가지오있는 room 객체의 clients 리스트중 본인 삭제
        room.clientList.remove(this);
        room = null;

    }

    public void wait_queue() {
        // 미구현
        while (room == null) {
        }
    }

    public void receive() {
        // 클라이언트로 메세지를 받는 메서드
        // 수정필요
        Runnable thread = new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        try {
                            data = (Protocol) in.readObject();
                            System.out.println("[message received]" + socket.getRemoteSocketAddress() + ":"
                                    + Thread.currentThread().getName() + ":" + data);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (EOFException e) {
                            continue;
                        } catch (SocketException e) {
                            break;
                        }
                        if (data instanceof ChatData) {
                            analysisChatData((ChatData) data);

                        } else if (data instanceof LoginData) {
                            System.out.println(socket.getRemoteSocketAddress() + ":로그인 연결요청");
                            analysisLoginData((LoginData) data);

                        } else if (data instanceof SignUpData) {
                            analysisSignUpData((SignUpData) data);

                        } else if (data instanceof JoinData) {
                            analysisJoinData((JoinData) data);
                        }
                    }
                } catch (Exception e) {
                    try {
                        System.out.println("[message receiving error]" + socket.getRemoteSocketAddress() + ":"
                                + Thread.currentThread().getName());
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        };
        Main.threadPool.submit(thread);

    }
    public void analysisSignUpData(SignUpData data){
        if(data.getProtocol() ==SignUpData.SIGNUP_REQUEST){
            try{
                if(Main.DAO.signUp(data.getID(),data.getPW(),data.getName())){
                    sendData(new SignUpData(SignUpData.SIGNUP_SUCCESS));
                }else{
                    sendData(new SignUpData(SignUpData.SIGNUP_FAILED));
                }
            }catch (Exception e){
                sendData(new SignUpData(SignUpData.SIGNUP_FAILED));
                e.printStackTrace();
            }

        }

    }
    public void analysisChatData(ChatData data) {
        try {
            if (data.getProtocol() == ChatData.SEND_TEXT) {
                // join request
                Main.roomManager.searchRoom(data.getRoomNum(),data);
            }
        } catch (Exception e) {
            sendData(new JoinData(JoinData.JOIN_FAILED));
            e.printStackTrace();
        }

    }

    public void analysisJoinData(JoinData data) {
        try {
            if (data.getProtocol() == JoinData.JOIN_REQUEST) {
                // join request
                Main.roomManager.addClient(this);
                sendData(new JoinData(JoinData.JOIN_ACCESS));
            }
        } catch (Exception e) {
            sendData(new JoinData(JoinData.JOIN_FAILED));
            e.printStackTrace();
        }
    }

    public void analysisLoginData(LoginData data) {

        String ID = data.getID();
        String passWord = data.getPassWord();
        System.out.println("login protocol....");
        if (data.getProtocol() == LoginData.LOGIN_REQUEST) {
            // login request
            try {
                if (Main.DAO.signIn(ID, passWord)) {
                    sendData(new JoinData(JoinData.LOGIN_ACCESS));
                    System.out.println("성공");

                } else {
                    sendData(new JoinData(JoinData.LOGIN_FAILED));
                    System.out.println("실패");

                }
            } catch (Exception e) {
                try {
                    sendData(new JoinData(JoinData.LOGIN_FAILED));
                    e.printStackTrace();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }

    }

    public void sendData(Protocol protocol) {
        // 클라이언트에게 메세지를 전송하는 메서드
        Runnable thread = new Runnable() {
            @Override
            public void run() {
                try {
                    out.writeObject(protocol);
                    out.flush();
                    System.out.println("send client:" + socket.getRemoteSocketAddress() + ":" + protocol);
                } catch (Exception e) {
                    try {
                        System.out.println("[message send error]" + socket.getRemoteSocketAddress() + ":"
                                + Thread.currentThread().getName());
                        //room.clientList.remove(Client.this);
                    } catch (Exception e2) {
                        e2.printStackTrace();

                    }

                }
            }
        };
        Main.threadPool.submit(thread);
    }

    public String getName() {
        return name;
    }
}
