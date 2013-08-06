package com.dbdky.proxy;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
	public static void main(String[] args) {
		try {
			ServerSocket server = null;
			try {
				server = new ServerSocket(ConfigUtil.getProxyPort());
			} catch (Exception e) {
				System.out.println("Can't listen to:" + e);
			}
			
			Socket socket = null;
			
			try {
				socket = server.accept();
			} catch (Exception e) {
				System.out.println("Error:" + e);
			}
			
			//StringBuilder line = new StringBuilder();
			String line;
			BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter os = new PrintWriter(socket.getOutputStream());
			
			BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
			line = sin.readLine();
			line = is.readLine();
		
			//String tempStr;
			//while ((tempStr = is.readLine()) != null) {
			//	line.append(tempStr);
			//}
			
			//System.out.println("From Client: " + line.toString());
			
			//os.println("0");
			//os.flush();
			while (!line.equals("bye")) {
			  os.println(line);
			  os.flush();
			  System.out.println("Server:" + line);
			  System.out.println("Client:" + is.readLine());
			  
			  //line = sin.readLine();
			}
			
			os.close();
			is.close();
			
			socket.close();
			server.close();
			
		} catch (Exception e) {
			System.out.println("Error:" + e);
		}
	}
}