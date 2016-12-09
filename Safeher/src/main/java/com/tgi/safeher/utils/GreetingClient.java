package com.tgi.safeher.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import org.omg.CORBA.portable.InputStream;

public class GreetingClient {
	public static void main(String[] args) {
		// String serverName = args[0];
		// int port = Integer.parseInt(args[1]);
		try {
			System.out.println("Client Message: Connecting to " + "localhost" + " on port "
					+ "6666");
			Socket client = new Socket("localhost", 6666);
			System.out.println("Client Message: Just connected to "
					+ client.getRemoteSocketAddress());
			OutputStream outToServer = client.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);
			out.writeUTF("Client Message: Hello from " + client.getLocalSocketAddress());
			java.io.InputStream inFromServer = client.getInputStream();
			DataInputStream in = new DataInputStream(inFromServer);
			System.out.println("Client Message: Server says " + in.readUTF());
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
