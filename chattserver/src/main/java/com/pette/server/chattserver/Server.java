package com.pette.server.chattserver;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	ServerSocket serverSocket;

	static final int socketServerPORT = 8080;
	StringBuilder convo = new StringBuilder();
	ExecutorService execs = Executors.newFixedThreadPool(10);

	public Server() {
		Thread socketServerThread = new Thread(new SocketServerThread());
		System.out.println("Starting server");
		socketServerThread.start();
	}

	public static void main(String[] args) {
		Server server = new Server();
	}

	public int getPort() {
		return socketServerPORT;
	}

	public void onDestroy() {
		if (serverSocket != null) {
			try {
				serverSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private class SocketServerThread extends Thread {

		int count = 0;

		@Override
		public void run() {

			try {
				// create ServerSocket using specified port
				serverSocket = new ServerSocket(socketServerPORT);

				while (true) {
					System.out.println("Running");
					// block the call until connection is created and return
					// Socket object

					Socket socket = serverSocket.accept();
					count++;
					String ip = socket.getInetAddress().toString();

					SocketServerReplyThread socketServerReplyThread = new SocketServerReplyThread(socket, count, convo);
					socketServerReplyThread.run();

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private class SocketServerReplyThread extends Thread {
		String message = "";
		private Socket hostThreadSocket;
		int cnt;

		SocketServerReplyThread(Socket socket, int c, StringBuilder convo) {
			hostThreadSocket = socket;
			cnt = c;
		}

		@Override
		public void run() {
			OutputStream outputStream;

			try {

				String messageString = "";
				outputStream = hostThreadSocket.getOutputStream();
				PrintStream printStream = new PrintStream(outputStream);
				printStream.print(convo.toString());
				BufferedInputStream test = new BufferedInputStream(hostThreadSocket.getInputStream());
				byte[] buffer = new byte[1024]; // If you handle
												// larger data
				// use a bigger buffer size
				int read;

				if (test.available() != -1) {
					while ((read = test.read(buffer)) != -1) {
						System.out.println("Reading data");
						messageString += new String(buffer).substring(0, read);
						break;
					}
				}

				System.out.println("MESSAGE: " + messageString);
				String msgReply = messageString + " #" + cnt;

				if (messageString.length() != 0) {
					convo.append(messageString + "\n");
				}

				printStream.print(convo.toString());
				printStream.close();

				// outputStream = hostThreadSocket.getOutputStream();
				// PrintStream printStream = new PrintStream(outputStream);
				// printStream.print(convo.toString());
				// printStream.close();

				message += "replayed: " + msgReply + "\n";

				System.out.println("Client " + message);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				message += "Something wrong! " + e.toString() + "\n";
			}

			System.out.println("Client " + message);
		}

	}

	public String getIpAddress() {
		String ip = "";
		try {
			Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface.getNetworkInterfaces();
			while (enumNetworkInterfaces.hasMoreElements()) {
				NetworkInterface networkInterface = enumNetworkInterfaces.nextElement();
				Enumeration<InetAddress> enumInetAddress = networkInterface.getInetAddresses();
				while (enumInetAddress.hasMoreElements()) {
					InetAddress inetAddress = enumInetAddress.nextElement();

					if (inetAddress.isSiteLocalAddress()) {
						ip += "Server running at : " + inetAddress.getHostAddress();
					}
				}
			}

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ip += "Something Wrong! " + e.toString() + "\n";
		}
		return ip;
	}
}