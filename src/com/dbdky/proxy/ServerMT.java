package com.dbdky.proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.concurrent.*;

public class ServerMT {
	private ServerSocket serverSocket;
	private ExecutorService executorServer;
	private final int POOL_SIZE = 10;
	
	public ServerMT() throws IOException {
		serverSocket = new ServerSocket(ConfigUtil.getProxyPort());
		executorServer = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*POOL_SIZE);
		System.out.println("服务器启动");
	}
	
	public void service() {
		while (true) {
			Socket socket = null;
			try {
				socket = serverSocket.accept();
				executorServer.execute(new Handler(socket));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		new ServerMT().service();
	}
}

class Handler implements Runnable {
	private Socket socket;
	public Handler(Socket socket) {
		this.socket = socket;
	}
	
	private PrintWriter getWriter(Socket socket) throws IOException {
		OutputStream socketOut = socket.getOutputStream();
		return new PrintWriter(socketOut, true);
	}
	
	private BufferedReader getReader(Socket socket) throws IOException {
		return new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	public String echo(String msg) {
		return "echo:" + msg;
	}
	
	public void run() {
		try {
			System.out.println("New connection accepted" + socket.getInetAddress()+":" + socket.getPort());
			BufferedReader br = getReader(socket);
			PrintWriter pw = getWriter(socket);
			String msg = null;
			
			while ((msg = br.readLine()) != null) {
				System.out.println(msg);
				//pw.println(echo(msg));
				pw.println("0");
				if (msg.equals("bye")) {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}