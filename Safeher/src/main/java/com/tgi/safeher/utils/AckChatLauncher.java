package com.tgi.safeher.utils;

import java.io.IOException;

import com.corundumstudio.socketio.AckCallback;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.VoidAckCallback;
import com.corundumstudio.socketio.listener.DataListener;
import com.tgi.safeher.beans.ChatObject;

public class AckChatLauncher extends Thread {

	private Configuration config = null;
    private SocketIOServer server = null;

	public AckChatLauncher(int port) throws IOException {
		if(config == null || server == null){
			config = new Configuration();
			config.setPort(port);
			config.setHostname("192.168.0.141");
	        server = new SocketIOServer(config);	
		}
	}

	public void run() {
		int i = 0;
		while (true) {
//			try {
				config.setMaxFramePayloadLength(1024 * 1024);
				config.setMaxHttpContentLength(1024 * 1024);
				
				final SocketIOServer server = new SocketIOServer(config);
				
				server.addEventListener("msg", byte[].class,
						new DataListener<byte[]>() {
							@Override
							public void onData(SocketIOClient client,
									byte[] data, AckRequest ackRequest) {
								System.out.println("Client IP Address: "+client.getRemoteAddress());
								client.sendEvent("Hello client: "+client.getRemoteAddress(), data);
							}
						});

				server.start();

				System.out.println("Waiting for client on port "
						+ config.getPort() + "...");
				System.out.println("Sizeeeeeeeeeeeeeeeeeeeeeeeeeeeee: "+i);
//				Thread.sleep(Integer.MAX_VALUE);
				server.stop();
				i++;
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}

		}
	}

	public static void main(String[] args) {

//		try {
//			Thread t = new AckChatLauncher(6661);
//			t.start();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		  Configuration config = new Configuration();
	        config.setHostname("192.168.0.141");
	        config.setPort(6661);

	        final SocketIOServer server = new SocketIOServer(config);
	        server.addEventListener("ackevent1", ChatObject.class, new DataListener<ChatObject>() {
	            @Override
	            public void onData(final SocketIOClient client, ChatObject data, final AckRequest ackRequest) {

	                // check is ack requested by client,
	                // but it's not required check
	                if (ackRequest.isAckRequested()) {
	                    // send ack response with data to client
	                    ackRequest.sendAckData("client message was delivered to server!", "yeah!");
	                }
	                System.out.println("userName: "+data.getUserName());
	                System.out.println("userMessage: "+data.getMessage());
	                // send message back to client with ack callback WITH data
	                ChatObject ackChatObjectData = new ChatObject(data.getUserName(), "fawad");
	                client.sendEvent("ackevent2", new AckCallback<String>(String.class) {
	                    @Override
	                    public void onSuccess(String result) {
	                        System.out.println("ack from client: " + client.getRemoteAddress() + " data: " + result);
	                    }
	                }, ackChatObjectData);

	                ChatObject ackChatObjectData1 = new ChatObject(data.getUserName(), "message with void ack");
	                client.sendEvent("ackevent3", new VoidAckCallback() {

	                    protected void onSuccess() {
	                        System.out.println("void ack from: " + client.getRemoteAddress());
	                    }

	                }, ackChatObjectData1);
	            }
	        });

	        server.start();

	        try {
				Thread.sleep(Integer.MAX_VALUE);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        server.stop();
		
	}

}