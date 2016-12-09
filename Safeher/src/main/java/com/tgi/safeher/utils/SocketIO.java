package com.tgi.safeher.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.corundumstudio.socketio.AckCallback;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.VoidAckCallback;
import com.corundumstudio.socketio.listener.DataListener;
import com.tgi.safeher.beans.AppUserBean;
import com.tgi.safeher.beans.ChatObject;
import com.tgi.safeher.beans.PreRideRequestBean;
import com.tgi.safeher.beans.ResponseObject;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.ReturnStatusEnum;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.map.beans.MapBean;
import com.tgi.safeher.map.service.IMapService;
import com.tgi.safeher.map.service.iml.MapService;
import com.tgi.safeher.repository.IRideColorManagementRepository;
import com.tgi.safeher.rws.SingletonSession;

public class SocketIO {
	
	private DecoratorUtil decoratorUtil = new DecoratorUtil();

	private DataBeanFactory dataBeanFactory = new DataBeanFactory();
	
	private static MapService mapService = new MapService();
	
	private Configuration config = null;
    private SocketIOServer server = null;
    private List<MapBean> list = null;
    private Map<String, MapBean> socketMap = null;

	public SocketIO(int port) throws IOException {
		if(config == null || server == null){
			//local server
//			String ipAddress = Common.getValueFromSpecificPropertieFile(
//					"/properties/ipAddress.properties", "SERVER_IP_ADDRESS_LOCAL");
			String ipAddress = Common.getValueFromSpecificPropertieFile(
					"/properties/ipAddress.properties", "SERVER_IP_ADDRESS_LOCAL");
			//live Linux
			/*String ipAddress = "10.0.0.13";*/
			config = new Configuration();
			config.setPort(port);
			config.setHostname(ipAddress);
		/*	config.setPingTimeout(6000);*/
	        server = new SocketIOServer(config);
	        list = new ArrayList<MapBean>();
	        socketMap = new HashMap<String, MapBean>();
		}
	}
	
	public static void main(String[] args) throws IOException {
		
//		final SocketIO socketIO = new SocketIO(6661);
		
		 Configuration config = new Configuration();
	        config.setHostname("192.168.0.141");
	        config.setPort(6661);

	        final SocketIOServer server = new SocketIOServer(config);
	        server.addEventListener("chatevent", ChatObject.class, new DataListener<ChatObject>() {
	        	
	            @Override
	            public void onData(SocketIOClient client, ChatObject data, AckRequest ackRequest) {
////	                server.getBroadcastOperations().sendEvent("chatevent", data);
//	                System.out.println(server.getAllClients().size());
//	                System.out.println(server.getClient(client.getSessionId()));
//	                client = server.getClient(client.getSessionId());
//	                System.out.println(client.getRemoteAddress());

	        		SafeHerDecorator decorator = new SafeHerDecorator();
	        		DecoratorUtil decoratorUtil = new DecoratorUtil();
	            	// send message back to client with ack callback WITH data
	        		ResponseObject responseObject = decoratorUtil.responseToClient(decorator);
	                client.sendEvent("getNavigationResponse", new AckCallback<String>(String.class) {
	                    @Override
	                    public void onSuccess(String result) {
	                       // System.out.println("ack from client: " + client.getRemoteAddress() + " data: " + result);
	                    }
	                }, responseObject);
	        		ResponseObject responseObject2 = decoratorUtil.responseToClient(decorator);
	                client.sendEvent("getNavigation2", new VoidAckCallback() {

	                    protected void onSuccess() {
	                        //System.out.println("void ack from: " + client.getRemoteAddress());
	                    }

	                }, responseObject2);

        			String clientId = SingletonSession.getClientUUIDMap().get("12335");
        			server.getClient(UUID.fromString(clientId)).sendEvent("chatevent", data);
        			
//        			server.getClient(cl.getSessionId()).sendEvent("chatevent", data);
//	            	Collection<SocketIOClient> abc = server.getAllClients();
//	            	for(SocketIOClient cl: abc){
//	            		System.out.println(cl.getRemoteAddress().toString());
//	            		if(cl.getRemoteAddress().toString().contains("141")){
//	            			String clientId = SingletonSession.getClientUUIDMap().get("123");
//	            			server.getClient(UUID.fromString(clientId)).sendEvent("chatevent", data);
////	            			server.getClient(cl.getSessionId()).sendEvent("chatevent", data);
////	            			if(!client.getRemoteAddress().toString().equals("/192.168.0.141:54367")){
////		            			server.getClient(client.getSessionId()).sendEvent("chatevent", data);
////	            				
////	            			}
//	            		}
//	            	}
	                
	                
	            }
	        });
	        
	        server.addEventListener("saveClientUUID", AppUserBean.class, new DataListener<AppUserBean>() {
	            @Override
	            public void onData(final SocketIOClient client, AppUserBean appUser, final AckRequest ackRequest) {

	            	SafeHerDecorator decorator = new SafeHerDecorator();
	            	DecoratorUtil decoratorUtil = new DecoratorUtil();
	        		long lStartTime = new Date().getTime();
	        		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
	        		
	        		if(appUser.getAppUserId() != null){
	        			SingletonSession.getClientUUIDMap().put(
	        					appUser.getAppUserId(), client.getSessionId().toString());
	        		}else{
						decorator.setResponseMessage("Please provide appUserId");
	        			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
	        		}
	            	
	        		ResponseObject responseObject = decoratorUtil.responseToClient(decorator);
	                client.sendEvent("saveClientUUIDResponse", new AckCallback<String>(String.class) {
	                    @Override
	                    public void onSuccess(String result) {
	                        System.out.println("ack from client: " + client.getRemoteAddress() + " data: " + result);
	                    }
	                }, responseObject);
	        		ResponseObject responseObject2 = decoratorUtil.responseToClient(decorator);
	                client.sendEvent("saveClientUUID2", new VoidAckCallback() {

	                    protected void onSuccess() {
	                        System.out.println("void ack from: " + client.getRemoteAddress());
	                    }

	                }, responseObject2);
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

	public DecoratorUtil getDecoratorUtil() {
		return decoratorUtil;
	}
	
	public void setDecoratorUtil(DecoratorUtil decoratorUtil) {
		this.decoratorUtil = decoratorUtil;
	}
	
	public DataBeanFactory getDataBeanFactory() {
		return dataBeanFactory;
	}
	
	public void setDataBeanFactory(DataBeanFactory dataBeanFactory) {
		this.dataBeanFactory = dataBeanFactory;
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
	
	public List<MapBean> getList() {
		return list;
	}
	
	public void setList(List<MapBean> list) {
		this.list = list;
	}
	
	public Map<String, MapBean> getSocketMap() {
		return socketMap;
	}
	
	public void setSocketMap(Map<String, MapBean> socketMap) {
		this.socketMap = socketMap;
	}
}
