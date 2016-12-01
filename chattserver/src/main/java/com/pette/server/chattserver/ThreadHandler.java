package com.pette.server.chattserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadHandler extends Thread {
	Socket newsock;
	int n;

	ThreadHandler(Socket s, int v) {
		newsock = s;
		n = v;
	}

	@Override
	public void run() {
		try {
			System.out.println("Running..");
			PrintWriter outp = new PrintWriter(newsock.getOutputStream(), true);
			BufferedReader inp = new BufferedReader(new InputStreamReader(newsock.getInputStream()));

			outp.println("Hello :: enter QUIT to exit \n");
			boolean more_data = true;
			String line;

			while (more_data) {
				line = inp.readLine();
				System.out.println("Message '" + line + "' echoed back to client.");
				if (line == null) {
					System.out.println("line = null");
					more_data = false;
				} else {
					outp.println("From server: " + line + ". \n");
					if (line.trim().equals("QUIT"))
						more_data = false;
				}
			}
			newsock.close();
			System.out.println("Disconnected from client number: " + n);
		} catch (Exception e) {
			System.out.println("IO error " + e);
		}

	}
}
