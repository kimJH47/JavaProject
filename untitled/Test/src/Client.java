package Lobby;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
	Socket socket;

	// 클라이언트 동작 메서드
	public void startClient(String IP, int port) {

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					socket = new Socket(IP, port);
					System.out.println("클라이언트 소켓 생성");
					receive();
				} catch (Exception e) {
					stopClient();
					e.printStackTrace();
				}

			}
		});
		Thread tr2 = new Thread(new Runnable() {
			@Override
			public void run() {

				while (true) {
					try {
						BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
						send(bufferedReader.readLine());

					} catch (IOException e) {
						e.printStackTrace();
						break;
					}

				}
			}
		});
		thread.start();
		tr2.start();

	}

	// 클라이언트 종료 메서드
	public void stopClient() {
		try {
			if (socket != null && !socket.isClosed()) {
				socket.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 서버로 부터 메세지를 전달받는 메서드
	public void receive() {
		while (true) {
			try {
				InputStream in = socket.getInputStream();
				byte[] buffer = new byte[512];
				int length = in.read(buffer);
				if (length == -1)
					throw new IOException();
				String message = new String(buffer, 0, length, "UTF-8");
				System.out.printf("%s", message);

			} catch (Exception e) {
				stopClient();
				e.printStackTrace();
				break;

			}
		}

	}

	// 전송메서드
	public void send(String message) {
		Thread thread2 = new Thread() {
			@Override
			public void run() {
				try {
					OutputStream out = socket.getOutputStream();
					byte[] buffer;
					buffer = message.getBytes("UTF-8");
					out.write(buffer);
					System.out.println("message send");
					out.flush();

				} catch (Exception e) {
					e.printStackTrace();
					stopClient();

				}

			}
		};
		thread2.start();

	}
}
