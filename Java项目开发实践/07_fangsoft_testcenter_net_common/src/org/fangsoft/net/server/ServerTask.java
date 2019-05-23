package org.fangsoft.net.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.fangsoft.util.SocketUtil;

public abstract class ServerTask<R, T> implements Runnable {
	private Socket socket;
	public ServerTask(Socket socket) {
		this.socket = socket;
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	protected abstract T handle(R request);
	public void run() {
		this.doProcess();
	}
	@SuppressWarnings("unchecked")
	public final void doProcess() {
		InputStream is;
		ObjectOutputStream oos;
		try {
			is = this.socket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			R request = (R)ois.readObject();
			this.socket.shutdownInput();
			T response = this.handle(request);
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(response);
		}catch (IOException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			SocketUtil.close(this.socket);
		}
	}
}
