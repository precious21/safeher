package com.tgi.safeher.utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import com.corundumstudio.socketio.AckCallback;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.VoidAckCallback;
import com.corundumstudio.socketio.listener.DataListener;
import com.tgi.safeher.beans.ChatObject;

public class AckChatLauncher2 extends Thread {

	private Configuration config = null;
    private SocketIOServer server = null;
    private List<ChatObject> list = null;

	public AckChatLauncher2(int port) throws IOException {
		if(config == null || server == null){
			config = new Configuration();
			config.setPort(port);
			config.setHostname("192.168.0.141");
	        server = new SocketIOServer(config);
	        list = new ArrayList<ChatObject>();
		}
	}
	
	public static void main(String[] args) throws IOException {
		
		final AckChatLauncher2 chatLauncher2 = new AckChatLauncher2(6661);
		
		chatLauncher2.server.addEventListener("ackevent1", ChatObject.class, new DataListener<ChatObject>() {
            @Override
            public void onData(final SocketIOClient client, ChatObject data, final AckRequest ackRequest) {

                // check is ack requested by client,
                // but it's not required check
//                if (ackRequest.isAckRequested()) {
//                    // send ack response with data to client
//                    ackRequest.sendAckData("client message was delivered to server!", "yeah!");
//                }
                chatLauncher2.list.add(data);
                System.out.println("jssonnnnnnnnnnnnnnnnnnnnn: "+data.getJson());
                System.out.println("listSIzeeeeeeeeeeeeeeeeee: "+chatLauncher2.list.size());
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

		chatLauncher2.server.start();

        try {
			Thread.sleep(Integer.MAX_VALUE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        chatLauncher2.server.stop();
		
	}

	public Configuration getConfig() {
		return config;
	}

	public void setConfig(Configuration config) {
		this.config = config;
	}

	public SocketIOServer getServer() {
		return server;
	}

	public void setServer(SocketIOServer server) {
		this.server = server;
	}

	public List<ChatObject> getList() {
		return list;
	}

	public void setList(List<ChatObject> list) {
		this.list = list;
	}
	
//	public static void main(String[] args) {
//
////		try {
////			Thread t = new AckChatLauncher(6661);
////			t.start();
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
//		  Configuration config = new Configuration();
//	        config.setHostname("192.168.0.141");
//	        config.setPort(6661);
//	        final List<ChatObject> list = new ArrayList<ChatObject>();
//
//	        final SocketIOServer server = new SocketIOServer(config);
//	        server.addEventListener("ackevent1", ChatObject.class, new DataListener<ChatObject>() {
//	            @Override
//	            public void onData(final SocketIOClient client, ChatObject data, final AckRequest ackRequest) {
//
//	                // check is ack requested by client,
//	                // but it's not required check
//	                if (ackRequest.isAckRequested()) {
//	                    // send ack response with data to client
//	                    ackRequest.sendAckData("client message was delivered to server!", "yeah!");
//	                }
//	                list.add(data);
//	                System.out.println("jssonnnnnnnnnnnnnnnnnnnnn: "+data.getJson());
//	                System.out.println("listSIzeeeeeeeeeeeeeeeeee: "+list.size());
//	                // send message back to client with ack callback WITH data
//	                ChatObject ackChatObjectData = new ChatObject(data.getUserName(), "fawad");
//	                client.sendEvent("ackevent2", new AckCallback<String>(String.class) {
//	                    @Override
//	                    public void onSuccess(String result) {
//	                        System.out.println("ack from client: " + client.getRemoteAddress() + " data: " + result);
//	                    }
//	                }, ackChatObjectData);
//
//	                ChatObject ackChatObjectData1 = new ChatObject(data.getUserName(), "message with void ack");
//	                client.sendEvent("ackevent3", new VoidAckCallback() {
//
//	                    protected void onSuccess() {
//	                        System.out.println("void ack from: " + client.getRemoteAddress());
//	                    }
//
//	                }, ackChatObjectData1);
//	            }
//	        });
//
//	        server.start();
//
//	        try {
//				Thread.sleep(Integer.MAX_VALUE);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//	        server.stop();
//		
//	}

}