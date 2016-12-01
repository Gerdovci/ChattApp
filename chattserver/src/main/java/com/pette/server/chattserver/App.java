package com.pette.server.chattserver;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Hello world!
 *
 */
public class App {

	public static void main(String[] args) {
		System.out.println("WTF??");
		int nreq = 1;
		try {
			ServerSocket sock = new ServerSocket(8080);
			for (;;) {
				Socket newsock = sock.accept();
				System.out.println("Creating thread ...");
				ThreadHandler t = new ThreadHandler(newsock, nreq);
				t.start();
				t.run();
			}
		} catch (Exception e) {
			System.out.println("IO error " + e);
		}
		System.out.println("End!");
	}
}
