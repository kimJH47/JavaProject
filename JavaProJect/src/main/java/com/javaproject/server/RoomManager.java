package com.javaproject.server;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.javaproject.protocol.*;

public class RoomManager {
    //main 서버에서 받은 메시지 정보를 각 room 에 해당하는 곳으로 전달하기

    Queue<ClientHandler> clientList = new LinkedList<>();//대기열
    Set<Room> roomList = new HashSet<>();
    private int room_number = 0;
    static int users = 0;


    public RoomManager() {
        System.out.println("RoomManager created");

    }
    public void broadcasting() {
        //Queue 에 있는 user 전부대상

    }

    public void searchRoom(int roomNum, Protocol data) {
        for(Iterator<Room> iter = roomList.iterator();iter.hasNext();) {
            Room room = iter.next();
            if(room.getRoomNum()==roomNum) {
                room.broadcasting(data);
            }

        }


    }

    synchronized public void addClient(ClientHandler client) {

        this.clientList.offer(client);
        System.out.println("(RM)add client!: " + client.getName() + client.socket.getInetAddress());
        users++;
        if (users % 2 == 0)
            createRoom(clientList);
    }
    synchronized public void createRoom(Queue<ClientHandler> clientList) {
        //방만들기 수정필요
        roomList.add(new Room(room_number++, clientList));

    }
}