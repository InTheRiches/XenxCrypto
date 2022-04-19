package net.riches.web.server;

import java.net.Socket;

public interface HttpServer
{
	/**
	 * Starts the configured webserver.
	 */
	public void start();

	/**
	 * Shuts down the webserver.
	 */
	public void stop();

	/**
	 * Dispatches an incoming socket connection to an appropriate handler.
	 * 
	 * @param socket
	 */
	public void dispatchRequest(Socket socket);

	/**
	 * Returns the signature of the webserver.
	 * 
	 * @return
	 */
	public String getServerSignature();

}
