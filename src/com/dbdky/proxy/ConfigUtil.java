package com.dbdky.proxy;

public class ConfigUtil {
	public static int getProxyPort() {
		//TODO;
		return 9876;
	}
	
	private static void setProxyPort(int port) {
		port_ = port;
	}
	
	private static int port_;
}