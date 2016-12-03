package com.pette.server.chattserver;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.pette.server.common.ChatMessage;

public class Server {
    ServerSocket serverSocket;

    static final int socketServerPORT = 8080;
    List<ChatMessage> messages = new ArrayList<ChatMessage>();
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

		    SocketServerReplyThread socketServerReplyThread = new SocketServerReplyThread(
			    socket, count, messages);
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

	SocketServerReplyThread(Socket socket, int c, List<ChatMessage> messages) {
	    hostThreadSocket = socket;
	    cnt = c;
	}

	@Override
	public void run() {

	    try {

		String messageString = "";
		OutputStream outputStream = hostThreadSocket.getOutputStream();

		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
			outputStream);

		InputStream inputStream = hostThreadSocket.getInputStream();
		// ObjectInputStream objectInputStream = new
		// ObjectInputStream(inputStream);

		// TODO: READ OBJECTS INSTEAD

		BufferedInputStream test = new BufferedInputStream(
			hostThreadSocket.getInputStream());
		byte[] buffer = new byte[1024]; // If you handle
						// larger data
		// use a bigger buffer size
		int read;

		if (test.available() != 1) {
		    while ((read = test.read(buffer)) != 1) {
			System.out.println("Reading data");
			messageString += new String(buffer).substring(0, read);
			break;
		    }
		}

		// messageString = messageString.replaceAll("\\s+$", "");

		System.out.println(messageString);

		if (messageString.contains("GET UPDATECONVO")) {
		    objectOutputStream.writeObject(messages);

		} else {
		    if (messageString.length() != 0) {
			if (messages.size() > 100) {
			    messages.remove(0);
			}
			// hack to get current index for sender
			System.out.println("what==" + messageString);

			String[] split = messageString.split("!#");
			int count = 0;

			System.out.println("==" + split[1]);
			if (split.length > 1) {
			    messageString = split[0];
			    System.out.println("==" + split[1]);
			    split[1] = split[1].replaceAll("\\D+", "");
			    count = Integer.parseInt(split[1]);
			} else {
			    System.out
				    .println("Sorry your are not authorized!!! BITCH");
			    hostThreadSocket.close();
			}

			System.out.println(split[0]);

			ChatMessage chatMessage = new ChatMessage("send",
				"recv", messageString, "ID", false);

			// Add missing messages

			List<ChatMessage> temp = new ArrayList<ChatMessage>();
			for (int i = count; i < messages.size(); i++) {
			    temp.add(messages.get(i));
			}

			temp.add(chatMessage);
			messages.add(chatMessage);
			objectOutputStream.writeObject(temp);
		    }
		}

		message += "replayed: " + messageString + "#" + cnt + "\n";

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
	    Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
		    .getNetworkInterfaces();
	    while (enumNetworkInterfaces.hasMoreElements()) {
		NetworkInterface networkInterface = enumNetworkInterfaces
			.nextElement();
		Enumeration<InetAddress> enumInetAddress = networkInterface
			.getInetAddresses();
		while (enumInetAddress.hasMoreElements()) {
		    InetAddress inetAddress = enumInetAddress.nextElement();

		    if (inetAddress.isSiteLocalAddress()) {
			ip += "Server running at : "
				+ inetAddress.getHostAddress();
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