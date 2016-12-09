package com.tgi.safeher.service.manager.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import redis.clients.jedis.Client;

import com.corundumstudio.socketio.AckCallback;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.VoidAckCallback;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DefaultExceptionListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.google.maps.model.LatLng;
import com.tgi.safeher.API.thirdParty.GoogleWrapper;
import com.tgi.safeher.beans.AppUserBean;
import com.tgi.safeher.beans.DistanceAPIBean;
import com.tgi.safeher.beans.PreRideRequestBean;
import com.tgi.safeher.beans.ResponseObject;
import com.tgi.safeher.beans.RideBean;
import com.tgi.safeher.beans.RideQuickInfoBean;
import com.tgi.safeher.beans.RideRequestResponseBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.enumeration.PushNotificationStatus;
import com.tgi.safeher.common.enumeration.ReturnStatusEnum;
import com.tgi.safeher.common.exception.GenericException;
import com.tgi.safeher.dao.AppUserDao;
import com.tgi.safeher.entity.RideCriteriaEntity;
import com.tgi.safeher.map.beans.MapBean;
import com.tgi.safeher.map.service.IMapService;
import com.tgi.safeher.map.service.iml.MapService;
import com.tgi.safeher.repository.ActiveDriverStatusRepository;
import com.tgi.safeher.repository.ArrivalTimeRepository;
import com.tgi.safeher.repository.CriteriaRepository;
import com.tgi.safeher.repository.DriverNavigationRepository;
import com.tgi.safeher.repository.IRideColorManagementRepository;
import com.tgi.safeher.repository.RequestStatusRepository;
import com.tgi.safeher.repository.SocketClientUUIDRepository;
import com.tgi.safeher.rws.SingletonSession;
import com.tgi.safeher.service.IRideRequestResponseService;
import com.tgi.safeher.service.impl.AsyncServiceImpl;
import com.tgi.safeher.service.manager.IRideManager;
import com.tgi.safeher.service.safeHerMapService.IGoogleMapsAPIService;
import com.tgi.safeher.utils.ApplicationContaxtAwareUtil;
import com.tgi.safeher.utils.DataBeanFactory;
import com.tgi.safeher.utils.DateUtil;
import com.tgi.safeher.utils.DecoratorUtil;
import com.tgi.safeher.utils.SocketIO;
import com.tgi.safeher.utils.StringUtil;

@Service
@Scope("prototype")
public class SocketManager {

//	@Autowired
	private DecoratorUtil decoratorUtil = new DecoratorUtil();
	
//	@Autowired
//	private static IMapService iMapService;
	
	private SocketIO socketIO = null;
	
	private static final Logger logger_c = Logger
			.getLogger(SocketManager.class);

	public void testServiceMethod() {
		Date date = new Date();

		logger_c.info("test service method invoked: " + date.toString());
	}
	
	public void saveDriverLocationSocket() throws IOException{
		logger_c.info("**************Entering in saveDriverLocationSocket ************");
		socketIO = new SocketIO(52984);
		
		socketIO.getServer().addConnectListener(new ConnectListener() {
	        @Override
	        public void onConnect(SocketIOClient client) {
	           
	        	System.out.println("hello" + client.getSessionId());
	        	System.out.println("socket clients size: "+socketIO.getServer().getAllClients().size());
	        	//client.
	        	logger_c.info("socket clients size: "+socketIO.getServer().getAllClients().size());
	        	
	        }
	    });
		
		
		socketIO.getServer().addDisconnectListener(new DisconnectListener() {
	        @Override
	        public void onDisconnect(SocketIOClient client) {
	        	System.out.println("bye" + client.getSessionId());
	        	System.out.println("socket clients size: "+socketIO.getServer().getAllClients().size());
	        	client.disconnect();
	        	logger_c.info("socket clients size: "+socketIO.getServer().getAllClients().size());
	        }
	    });
		
		socketIO.getServer().addEventListener("savePassangerDriver", MapBean.class, new DataListener<MapBean>() {
            @Override
            public void onData(final SocketIOClient client, MapBean data, final AckRequest ackRequest) {

	            // check is ack requested by client,
	            // but it's not required check
//                if (ackRequest.isAckRequested()) {
//                    // send ack response with data to client
//                    ackRequest.sendAckData("client message was delivered to server!", "yeah!");
//                }
            	logger_c.info("**************Entering in onData of saveDriverLocationSocket with mapBean "+data+" *********");
        		long lStartTime = new Date().getTime();
        		SafeHerDecorator decorator = new SafeHerDecorator();
        		decorator.setDataBean(data);

            	System.out.println("apUserIdddddddddddddddddd: "+data.getAppUserId());
            	System.out.println("directionnnnnnnnnnnnnnnnnnnnnnn1: "+
        				data.getDirection());
//            	socketIO.getSocketMap().put(data.getAppUserId(), data);
//              System.out.println("mapSIzeeeeeeeeeeeeeeeeeee: "+socketIO.getSocketMap().size());
        		
        		IMapService iMapService = (IMapService)
        				ApplicationContaxtAwareUtil.getApplicationContext().getBean(IMapService.class);
        		AsyncServiceImpl asyncServiceImpl = (AsyncServiceImpl)
        				ApplicationContaxtAwareUtil.getApplicationContext().getBean(AsyncServiceImpl.class);
                try {	
        			if (decorator.getResponseMessage() == null
        					|| decorator.getResponseMessage().length() <= 0) {
        				/*iMapService.savePassangerDriver(decorator);*/
//        				iMapService.savePassangerDriverMongo(decorator);
                        asyncServiceImpl.saveDriverLocationTrackListIntoMongo(data);
        			}
        		} catch (GenericException e) {
        			decorator.setResponseMessage(e.getMessage());
        			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
        			logger_c.info("**************Exiting  from onData with exception "+e.getMessage() +" ********");
        		}
        		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
        		
                // send message back to client with ack callback WITH data
        		ResponseObject responseObject = decoratorUtil.responseToClient(decorator);
                client.sendEvent("savePassangerDriverResponse", new AckCallback<String>(String.class) {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("ack from client: " + client.getRemoteAddress() + " data: " + result);
                    }
                }, responseObject);

        		ResponseObject responseObject2 = decoratorUtil.responseToClient(decorator);
                client.sendEvent("savePassangerDriver3", new VoidAckCallback() {

                    protected void onSuccess() {
                        System.out.println("void ack from: " + client.getRemoteAddress());
                    }

                }, responseObject2);
            }
            
            
        });

		socketCommon();

		socketIO.getServer().start();
		
//        try {
//			Thread.sleep(Integer.MAX_VALUE);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//
//        socketIO.getServer().stop();
	}
	
	public void socketCommon() throws IOException{
		
//		final SocketIO socketIO = new SocketIO(6666);
		logger_c.info("**************Entering in socketCommon *********");
		socketIO.getServer().addEventListener("activeDrivers", DistanceAPIBean.class, new DataListener<DistanceAPIBean>() {
            @Override
            public void onData(final SocketIOClient client, DistanceAPIBean data, final AckRequest ackRequest) {
            	logger_c.info("**************Entering in onData of socketCommon with DistanceAPIBEan "+data+" *********");
        		long lStartTime = new Date().getTime();
        		SafeHerDecorator decorator = new SafeHerDecorator();
        		decorator.setDataBean(data);

            	System.out.println("lattttttttttttttttttttttttttttttt: "+data.getLatDestinations());
        		
            	GoogleWrapper googleWraper = (GoogleWrapper)
        				ApplicationContaxtAwareUtil.getApplicationContext().getBean(GoogleWrapper.class);
                try {	
        			if (decorator.getResponseMessage() == null
        					|| decorator.getResponseMessage().length() <= 0) {
        				//TODO:Check
        				googleWraper.getActiveDriversListFromQueryMongo(decorator);
        				/*googleWraper.getActiveDriversListFromQuery(decorator);*/
        			}
        		} catch (Exception e) {
        			decorator.setResponseMessage(e.getMessage());
        			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
        			logger_c.info("**************Exiting from onData of socketCommon with Exception "+e.getMessage() +" *********");
        		}
        		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
        		
                // send message back to client with ack callback WITH data
        		ResponseObject responseObject = decoratorUtil.responseToClient(decorator);
                client.sendEvent("activeDriversResponse", new AckCallback<String>(String.class) {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("ack from client: " + client.getRemoteAddress() + " data: " + result);
                    }
                }, responseObject);

        		ResponseObject responseObject2 = decoratorUtil.responseToClient(decorator);
                client.sendEvent("activeDriversDriver3", new VoidAckCallback() {

                    protected void onSuccess() {
                        System.out.println("void ack from: " + client.getRemoteAddress());
                    }

                }, responseObject2);
            }
        });
		
		
		socketIO.getServer().addEventListener("saveNavigationV1", MapBean.class, new DataListener<MapBean>() {
            @Override
            public void onData(final SocketIOClient client, MapBean data, final AckRequest ackRequest) {
            	logger_c.info("**************Method onData saveNavigation map Bean "+data+" **********");
            	System.out.println("Saving navigation of driver: "+data.getAppUserId());
        		long lStartTime = new Date().getTime();
        		SafeHerDecorator decorator = new SafeHerDecorator();
        		AsyncServiceImpl asyncServiceImpl = (AsyncServiceImpl)
        				ApplicationContaxtAwareUtil.getApplicationContext().getBean(AsyncServiceImpl.class);
        		decorator.setDataBean(data);

        		if(data.getAppUserId() != null || 
            			data.getLat() != null || data.getLng() != null){
            		//we are storing data in singleton map but in future we will shift to rades (in memory database) 
                	MapBean bean = SingletonSession.getNavigationMap()
                			.get(data.getAppUserId());
            		
            		//implemented redis
            		//fetching driver navigation from redis
//            		DriverNavigationRepository driverNavigationRepository = (DriverNavigationRepository)
//            				ApplicationContaxtAwareUtil.getApplicationContext().getBean(DriverNavigationRepository.class);
//            		Map<Object, Object> list = driverNavigationRepository.findAllDriverNavigation();
//            		if(list != null){
//                		System.out.println("this is all drivers navigation found size: "+list.size());	
//            		}
//            		MapBean bean = driverNavigationRepository.findDriverNavigation(data.getAppUserId());
                	if(bean != null){
                		data.setPreLat(bean.getLat());
                		data.setPreLng(bean.getLng());
                	}else{
                		data.setPreLat("0.0");
                		data.setPreLng("0.0");
                	}
            		SingletonSession.getNavigationMap().put(data.getAppUserId(), data);
            		asyncServiceImpl.saveActiveDriverIntoDb(data);
            		//implemented redis
            		//saving driver navigation into redis
//                	driverNavigationRepository.saveRideRequestResponseResultDetail(data.getAppUserId(), bean);
					decorator.setResponseMessage("Successfully saved current location");
					decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
            	}else{
        			decorator.setResponseMessage("please provide valid information");
        			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
            	}

            	// send message back to client with ack callback WITH data
        		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
        		ResponseObject responseObject = decoratorUtil.responseToClient(decorator);
                client.sendEvent("saveNavigationResponseV1", new AckCallback<String>(String.class) {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("ack from client: " + client.getRemoteAddress() + " data: " + result);
                    }
                }, responseObject);

        		ResponseObject responseObject2 = decoratorUtil.responseToClient(decorator);
                client.sendEvent("saveNavigation2V1", new VoidAckCallback() {

                    protected void onSuccess() {
                        System.out.println("void ack from: " + client.getRemoteAddress());
                    }

                }, responseObject2);
            }
        });
		
		socketIO.getServer().addEventListener("getNavigation", MapBean.class, new DataListener<MapBean>() {
            @Override
            public void onData(final SocketIOClient client, MapBean data, final AckRequest ackRequest) {
            	logger_c.info("**************Method onData getNavigation map Bean "+data+" **********");
        		System.out.println("Fetching navigation of driver: "+data.getAppUserId());
        		long lStartTime = new Date().getTime();
        		SafeHerDecorator decorator = new SafeHerDecorator();
        		decorator.setDataBean(data);
        		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
        		
            	if(data.getAppUserId() != null){
                	MapBean bean = SingletonSession.getNavigationMap()
                			.get(data.getAppUserId());
            		
            		//implemented redis
            		//fetching driver navigation from redis
//            		DriverNavigationRepository driverNavigationRepository = (DriverNavigationRepository)
//            				ApplicationContaxtAwareUtil.getApplicationContext().getBean(DriverNavigationRepository.class);
//            		Map<Object, Object> list = driverNavigationRepository.findAllDriverNavigation();
//            		if(list != null){
//                		System.out.println("this is all drivers navigation found size: "+list.size());	
//                		MapBean bean = (MapBean) list.get(0);
//                		System.out.println(bean.getAppUserId());
//            		}
//            		MapBean bean = driverNavigationRepository.findDriverNavigation(data.getAppUserId());
            		if(bean != null){
            			if(bean.getPreLat().equals("0.0") || 
            					bean.getPreLng().equals("0.0")){
            				bean.setPreLat(bean.getLat());
            				bean.setPreLng(bean.getLng());
            			}
    					decorator.getResponseMap().put("data", bean);
    					decorator.setResponseMessage("Successfully get driver navigation");
    					decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
            		}else{
            			decorator.setResponseMessage("Driver not found");
            			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
            		}
            	}else{
        			decorator.setResponseMessage("please provide valid information");
        			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
            	}

            	// send message back to client with ack callback WITH data
        		ResponseObject responseObject = decoratorUtil.responseToClient(decorator);
                client.sendEvent("getNavigationResponse", new AckCallback<String>(String.class) {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("ack from client: " + client.getRemoteAddress() + " data: " + result);
                    }
                }, responseObject);
        		ResponseObject responseObject2 = decoratorUtil.responseToClient(decorator);
                client.sendEvent("getNavigation2", new VoidAckCallback() {

                    protected void onSuccess() {
                        System.out.println("void ack from: " + client.getRemoteAddress());
                    }

                }, responseObject2);
            }
        });
		
		socketIO.getServer().addEventListener("checkForColorScreen", PreRideRequestBean.class,
				new DataListener<PreRideRequestBean>() {
            @Override
            public void onData(final SocketIOClient client, PreRideRequestBean data, final AckRequest ackRequest) {
        		SafeHerDecorator decorator = new SafeHerDecorator();
        		long lStartTime = new Date().getTime();
        		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
            	logger_c.info("**************Method onData checkForColorScreen PreRideRequestBean "+data+" **********");
        		System.out.println("Mathcing color of isDriver: "+data.getIsDriver());        		

        		RequestStatusRepository requestStatusRepository = (RequestStatusRepository)
        				ApplicationContaxtAwareUtil.getApplicationContext().getBean(RequestStatusRepository.class);
    			String requestStatus = requestStatusRepository.
    					findRequestStatus(data.getRequestNo());
    			if(requestStatus != null){
					decorator.getResponseMap().put("canOpenColorScreen", "1");
					decorator.setResponseMessage("You can open color screen");
					decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
    			}else{
					decorator.getResponseMap().put("canOpenColorScreen", "0");
					decorator.setResponseMessage("You can not open color screen");
        			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
    			}
            	// send message back to client with ack callback WITH data
        		ResponseObject responseObject = decoratorUtil.responseToClient(decorator);
                client.sendEvent("checkForColorScreenResponse", new AckCallback<String>(String.class) {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("ack from client: " + client.getRemoteAddress() + " data: " + result);
                    }
                }, responseObject);
        		ResponseObject responseObject2 = decoratorUtil.responseToClient(decorator);
                client.sendEvent("checkForColorScreen2", new VoidAckCallback() {

                    protected void onSuccess() {
                        System.out.println("void ack from: " + client.getRemoteAddress());
                    }

                }, responseObject2);
            }
        });
		
		socketIO.getServer().addEventListener("checkForColorVerification", PreRideRequestBean.class,
				new DataListener<PreRideRequestBean>() {
            @Override
            public void onData(final SocketIOClient client, PreRideRequestBean data, final AckRequest ackRequest) {
        		SafeHerDecorator decorator = new SafeHerDecorator();
        		long lStartTime = new Date().getTime();
        		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
            	logger_c.info("**************Method onData checkForColorVerification PreRideRequestBean "+data+" **********");
        		System.out.println("Mathcing color of isDriver: "+data.getIsDriver());        		

        		IRideColorManagementRepository iRideColorManagementRepository = (IRideColorManagementRepository)
        				ApplicationContaxtAwareUtil.getApplicationContext().getBean(IRideColorManagementRepository.class);
    			PreRideRequestBean bean = iRideColorManagementRepository.
    					findColorStatus(data.getRequestNo());
    			if(bean != null){
    				if(bean.getIsDriverColorVerified() != null && 
    						bean.getIsPassengerColorVerified() != null && 
    						bean.getDriverVerificationAttemps() != null && 
    						bean.getPassengerVerificationAttemps() != null){
    					
        				if(bean.getIsDriverColorVerified().equals("1") && 
        						bean.getIsPassengerColorVerified().equals("1")){
        					
        					decorator.getResponseMap().put("colorVerified", "1");
        					decorator.setResponseMessage("Color Verified");
        					decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());	
        					
        				}else if(bean.getPassengerVerificationAttemps().equals("2") && 
                							bean.getIsDriverColorVerified().equals("0") ){
        					
        					decorator.getResponseMap().put("passengerCanceled", "1");
        					decorator.setResponseMessage("Ride Canceled");
        					decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());	
        					
        				}else if(bean.getDriverVerificationAttemps().equals("2") && 
    							bean.getIsPassengerColorVerified().equals("0") ){
    					
	    					decorator.getResponseMap().put("driverCanceled", "1");
	    					decorator.setResponseMessage("Color Verified");
	    					decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());	
	    					
	    				}else if(bean.getIsPassengerColorVerified().equals("1") ){
    					
	    					decorator.getResponseMap().put("driverVerfied", "1");
	    					decorator.getResponseMap().put("verifyColor", "1");
	    					decorator.setResponseMessage("Color Verified");
	    					decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());	
	    					
	    				}else if(bean.getIsDriverColorVerified().equals("1") ){
    					
	    					decorator.getResponseMap().put("passengerVerfied", "1");
	    					decorator.getResponseMap().put("verifyColor", "1");
	    					decorator.setResponseMessage("Color Verified");
	    					decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());	
	    				}	
    				}
    			}else{
					decorator.getResponseMap().put("verifyColor", "1");
					decorator.getResponseMap().put("driverVerfied", "0");
					decorator.getResponseMap().put("passengerVerfied", "0");
					decorator.setResponseMessage("Please verify your color");
        			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
    			}
            	// send message back to client with ack callback WITH data
        		ResponseObject responseObject = decoratorUtil.responseToClient(decorator);
                client.sendEvent("checkForColorVerificationResponse", new AckCallback<String>(String.class) {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("ack from client: " + client.getRemoteAddress() + " data: " + result);
                    }
                }, responseObject);
        		ResponseObject responseObject2 = decoratorUtil.responseToClient(decorator);
                client.sendEvent("checkForColorVerification2", new VoidAckCallback() {

                    protected void onSuccess() {
                        System.out.println("void ack from: " + client.getRemoteAddress());
                    }

                }, responseObject2);
            }
        });

        //here sockets start from accept request by passenger to end ride
        socketIO.getServer().addEventListener("saveClientUUID", AppUserBean.class, new DataListener<AppUserBean>() {
            @Override
            public void onData(final SocketIOClient client, AppUserBean appUser, final AckRequest ackRequest) {

            	logger_c.info("**************Method onData saveClientUUID AppUserBean "+appUser+" **********");
        		System.out.println("saving client,s UUID to socket where appUserId: "+appUser.getAppUserId());
            	SafeHerDecorator decorator = new SafeHerDecorator();
        		long lStartTime = new Date().getTime();
        		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
        		
        		//save client UUID into redis
        		SocketClientUUIDRepository socketClientUUIDRepository = (SocketClientUUIDRepository)
        				ApplicationContaxtAwareUtil.getApplicationContext().getBean(SocketClientUUIDRepository.class);
        		if(appUser.getAppUserId() != null){
        			socketClientUUIDRepository.saveClientSocketUUID(
            				appUser.getAppUserId(), client.getSessionId().toString());
//        			SingletonSession.getClientUUIDMap().put(
//        					appUser.getAppUserId(), client.getSessionId().toString());
        		}
        		System.out.println("socket clients size: "+socketIO.getServer().getAllClients().size());
//        		Collection<SocketIOClient> list = socketIO.getServer().getAllClients();
//        		if(list != null){
//        			for(SocketIOClient cl : list){
//        				System.out.println(cl.getSessionId());
//        			}
//        		}
            }
        });
		
		socketIO.getServer().addEventListener("acceptRequestByPassenger", RideRequestResponseBean.class,
				new DataListener<RideRequestResponseBean>() {
            @Override
            public void onData(final SocketIOClient client, RideRequestResponseBean data, final AckRequest ackRequest) {
            	logger_c.info("**************Method onData acceptRequestByPassenger RideRequestResponseBean "+data+" **********");
        		SafeHerDecorator decorator = new SafeHerDecorator();
        		long lStartTime = new Date().getTime();
        		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
        		decorator.setDataBean(data);
            	IRideRequestResponseService iRideRequestResponseService = (IRideRequestResponseService)
        				ApplicationContaxtAwareUtil.getApplicationContext().getBean(IRideRequestResponseService.class);
                try {	
        			if (decorator.getResponseMessage() == null
        					|| decorator.getResponseMessage().length() <= 0) {
        				/*iRideRequestResponseService.acceptRequestV2(decorator);*/
        				iRideRequestResponseService.acceptRequestV3(decorator);
        			}
        		} catch (Exception e) {
        			decorator.setResponseMessage(e.getMessage());
        			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
        			logger_c.info("**************Exiting from onData of socketCommon with Exception "+e.getMessage() +" *********");
        		}
        		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
    			
            	// send message back to client with ack callback WITH data
        		ResponseObject responseObject = decoratorUtil.responseToClient(decorator);
                client.sendEvent("acceptRequestByPassengerResponse", new AckCallback<String>(String.class) {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("ack from client: " + client.getRemoteAddress() + " data: " + result);
                    }
                }, responseObject);
        		ResponseObject responseObject2 = decoratorUtil.responseToClient(decorator);
                client.sendEvent("acceptRequestByPassenger2", new VoidAckCallback() {

                    protected void onSuccess() {
                        System.out.println("void ack from: " + client.getRemoteAddress());
                    }

                }, responseObject2);
                
                //send message to receiver
//        		String clientUUIDId = SingletonSession.getClientUUIDMap().get(data.getReceiverId());
        		String clientUUIDId = findClinetUUid(data.getReceiverId());
        		if(clientUUIDId != null){
        			RideRequestResponseBean rideBean = (RideRequestResponseBean) decorator.getDataBean();
            		RideQuickInfoBean bean = populateRideQurickInfoBean(rideBean, new RideQuickInfoBean());
            		socketIO.getServer().getClient(UUID.fromString(clientUUIDId)).sendEvent("message", bean);
        			
        		}
            }
        });
		
		socketIO.getServer().addEventListener("startPreRideByDriver", PreRideRequestBean.class,
				new DataListener<PreRideRequestBean>() {
            @Override
            public void onData(final SocketIOClient client, PreRideRequestBean data, final AckRequest ackRequest) {
            	logger_c.info("**************Method onData startPreRideByDriver PreRideRequestBean "+data+" **********");
        		SafeHerDecorator decorator = new SafeHerDecorator();
        		long lStartTime = new Date().getTime();
        		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
        		decorator.setDataBean(data);
            	IRideManager iRideManager = (IRideManager)
        				ApplicationContaxtAwareUtil.getApplicationContext().getBean(IRideManager.class);
                try {	
        			if (decorator.getResponseMessage() == null
        					|| decorator.getResponseMessage().length() <= 0) {
        				iRideManager.startPerRide(decorator);
        			}
        		} catch (Exception e) {
        			decorator.setResponseMessage(e.getMessage());
        			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
        			logger_c.info("**************Exiting from onData of socketCommon with Exception "+e.getMessage() +" *********");
        		}
        		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
    			
            	// send message back to client with ack callback WITH data
        		ResponseObject responseObject = decoratorUtil.responseToClient(decorator);
                client.sendEvent("startPreRideByDriverResponse", new AckCallback<String>(String.class) {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("ack from client: " + client.getRemoteAddress() + " data: " + result);
                    }
                }, responseObject);
        		ResponseObject responseObject2 = decoratorUtil.responseToClient(decorator);
                client.sendEvent("startPreRideByDriver2", new VoidAckCallback() {

                    protected void onSuccess() {
                        System.out.println("void ack from: " + client.getRemoteAddress());
                    }

                }, responseObject2);
                
                //send message to receiver
//        		String clientUUIDId = SingletonSession.getClientUUIDMap().get(data.getReceiverId());
        		String clientUUIDId = findClinetUUid(data.getReceiverId());
        		if(clientUUIDId != null){
        			PreRideRequestBean preBean = (PreRideRequestBean) decorator.getDataBean();
            		RideQuickInfoBean bean = populateRideQurickInfoBeanFromPreRideBean(preBean, new RideQuickInfoBean());
            		socketIO.getServer().getClient(UUID.fromString(clientUUIDId)).sendEvent("message", bean);
        			
        		}
            }
        });  
		
//		//for sendEvent to client about driver navigation
//		socketIO.getServer().addEventListener("getNavigationResponse", 
//				SafeHerDecorator.class, new DataListener<SafeHerDecorator>() {
//            @Override
//            public void onData(SocketIOClient client, 
//            		SafeHerDecorator safeHerDecorator, AckRequest ackRequest) {
//            }
//	    });
		
		socketIO.getServer().addEventListener("saveNavigation", MapBean.class,
				new DataListener<MapBean>() {
            @Override
            public void onData(final SocketIOClient client, MapBean data, final AckRequest ackRequest) {
            	logger_c.info("**************Method onData saveNavigation MapBean "+data+" **********");
            	System.out.println("Saving navigation of driver: "+data.getAppUserId());
        		long lStartTime = new Date().getTime();
        		SafeHerDecorator decorator = new SafeHerDecorator();
        		decorator.setDataBean(data);
        		
        		AsyncServiceImpl asyncServiceImpl = (AsyncServiceImpl)
        				ApplicationContaxtAwareUtil.getApplicationContext().getBean(AsyncServiceImpl.class);
        		DriverNavigationRepository driverNavigationRepository = (DriverNavigationRepository)
        				ApplicationContaxtAwareUtil.getApplicationContext().getBean(DriverNavigationRepository.class);
        		ActiveDriverStatusRepository activeDriverStatusRepository = (ActiveDriverStatusRepository)
        				ApplicationContaxtAwareUtil.getApplicationContext().getBean(ActiveDriverStatusRepository.class);
        		ArrivalTimeRepository arrivalTimeRepository = (ArrivalTimeRepository)
        				ApplicationContaxtAwareUtil.getApplicationContext().getBean(ArrivalTimeRepository.class);

        		if(data.getAppUserId() != null || 
            			data.getLat() != null || data.getLng() != null){
        			
        			//save arrivalTimeFlag
        			//please delete this on end ride from redis
        			//please delete this from redis on cancel
        			//please tell passenger and driver to send passengerId on cancel or Late reason request to delete arrival from redis

					if(StringUtil.isNotEmpty(data.getRideNo()) && 
							data.getRideNo().contains("RE")){
	        			long saveCount = 0;
	        			Long count = arrivalTimeRepository.findArrivalTimeFlag(
	        					data.getReceiverId());
	        			if(count == null){
	        				saveCount = 1;
	        				//calculate arrival time from google
	        				data.setArrivelTime(getArrivalTime(data));
	        				if(data.getArrivelTime() == null){
	        					data.setArrivelTime("");
	        				}else{
	        					String[] splitTime = data.getArrivelTime().split(":");
	        					if(splitTime != null && splitTime.length > 1){
	        						if(new Integer(splitTime[0]) <= 1){
	        							data.setArrivelTime("02:00");
	        						}
	        					}
	        				}
	        			}else{
	        				saveCount = count + 1;
	        				if(count % 30 == 0){
	        					//calculate arrival time from google
	            				data.setArrivelTime(getArrivalTime(data));
	        				}else{
	        					data.setArrivelTime("");
	        				}
	        			}
	        			arrivalTimeRepository.saveArrivalTimeFlag(
	        					saveCount, data.getReceiverId());
					}else{
    					data.setArrivelTime("");
					}
            		
//                	save live navigation into redis
            		MapBean bean = driverNavigationRepository.
            				findDriverNavigation(data.getAppUserId());
                	if(bean == null){
                		data.setPreLat(data.getLat());
                		data.setPreLng(data.getLng());
                	}else{
                		data.setPreLat(bean.getLat());
                		data.setPreLng(bean.getLng());
                	}
                	
                	driverNavigationRepository.saveDriverNavigation(
                			data.getAppUserId(), data);
                	//asynch saving latlng into db in driver location trak table
                	if(data !=null){
	                	if(data.getRequestNo() != null){
	                    	String activeDriverStatus = activeDriverStatusRepository.
	                    			findRequestStatus(data.getRequestNo());
	                    	if(activeDriverStatus == null){
	                        	System.out.println("Saving navigation of driver: "+data.getAppUserId()+" into sql db");
//	                    		asyncServiceImpl.saveActiveDriverIntoDb(data);
	                        	asyncServiceImpl.saveDriverLocationTrackIntoMongo(data);
	                    	}
	                	}
                	}
            		decorator.getResponseMap().put("data", data);
					decorator.setResponseMessage("Successfully saved current location");
					decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.toString());
            	}else{
        			decorator.setResponseMessage("please provide valid information");
        			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
            	}

//            	// send message back to client with ack callback WITH data
//        		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
//        		ResponseObject responseObject = decoratorUtil.responseToClient(decorator);
//                client.sendEvent("saveNavigationResponse", new AckCallback<String>(String.class) {
//                    @Override
//                    public void onSuccess(String result) {
//                        System.out.println("ack from client: " + client.getRemoteAddress() + " data: " + result);
//                    }
//                }, responseObject);
//
//        		ResponseObject responseObject2 = decoratorUtil.responseToClient(decorator);
//                client.sendEvent("saveNavigation2", new VoidAckCallback() {
//
//                    protected void onSuccess() {
//                        System.out.println("void ack from: " + client.getRemoteAddress());
//                    }
//
//                }, responseObject2);

                //send message to receiver
        		ResponseObject responseObject = decoratorUtil.responseToClient(decorator);
                
//        		String clientUUIDId = SingletonSession.getClientUUIDMap().get(data.getReceiverId());
        		String clientUUIDId = findClinetUUid(data.getReceiverId());
        		if(clientUUIDId != null){
        			SocketIOClient cl = socketIO.getServer().getClient(UUID.fromString(clientUUIDId));
//        			socketIO.getServer().getClient(UUID.fromString(clientUUIDId)).sendEvent("getNavigationResponse", responseObject);
        			if(cl != null){
                		cl.sendEvent("getNavigationResponse", responseObject);	
                		System.out.println("sending navigation of driver: "+data.getAppUserId()+
                        		" to passenger: "+data.getReceiverId());
            			System.out.println("Driver Arrival Time: "+data.getArrivelTime());
        			}
        			
        		}
            }
        });
		
		socketIO.getServer().addEventListener("passengerOrDriverNotReached", RideRequestResponseBean.class,
				new DataListener<RideRequestResponseBean>() {
            @Override
            public void onData(final SocketIOClient client, RideRequestResponseBean data, final AckRequest ackRequest) {
            	logger_c.info("**************Method onData passengerOrDriverNotReached RideRequestResponseBean "+data+" **********");
        		SafeHerDecorator decorator = new SafeHerDecorator();
        		long lStartTime = new Date().getTime();
        		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
        		decorator.setDataBean(data);
            	IRideRequestResponseService iRideRequestResponseService = (IRideRequestResponseService)
        				ApplicationContaxtAwareUtil.getApplicationContext().getBean(IRideRequestResponseService.class);
                try {	
        			if (decorator.getResponseMessage() == null
        					|| decorator.getResponseMessage().length() <= 0) {
        				iRideRequestResponseService.passengerOrDriverNotReached(decorator);
        			}
        		} catch (Exception e) {
        			decorator.setResponseMessage(e.getMessage());
        			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
        			logger_c.info("**************Exiting from onData of socketCommon with Exception "+e.getMessage() +" *********");
        		}
        		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
    			
            	// send message back to client with ack callback WITH data
        		ResponseObject responseObject = decoratorUtil.responseToClient(decorator);
                client.sendEvent("passengerOrDriverNotReachedResponse", new AckCallback<String>(String.class) {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("ack from client: " + client.getRemoteAddress() + " data: " + result);
                    }
                }, responseObject);
        		ResponseObject responseObject2 = decoratorUtil.responseToClient(decorator);
                client.sendEvent("passengerOrDriverNotReached2", new VoidAckCallback() {

                    protected void onSuccess() {
                        System.out.println("void ack from: " + client.getRemoteAddress());
                    }

                }, responseObject2);
                
                //send message to receiver
//        		String clientUUIDId = SingletonSession.getClientUUIDMap().get(data.getReceiverId());
        		String clientUUIDId = findClinetUUid(data.getReceiverId());
        		if(clientUUIDId != null){
            		RideQuickInfoBean bean = new RideQuickInfoBean();
            		RideRequestResponseBean rideBean = (RideRequestResponseBean) decorator.getDataBean();
            		bean.setNotificationType(rideBean.getNotificationType());
            		bean.setNotificationMessage(rideBean.getNotificationMessage());
            		socketIO.getServer().getClient(UUID.fromString(clientUUIDId)).sendEvent("message", bean);
        			
        		}
            }
        });
		
		socketIO.getServer().addEventListener("cancelReasonRequest", RideRequestResponseBean.class,
				new DataListener<RideRequestResponseBean>() {
            @Override
            public void onData(final SocketIOClient client, RideRequestResponseBean data, final AckRequest ackRequest) {
            	logger_c.info("**************Method onData cancelReasonRequest RideRequestResponseBean "+data+" **********");
        		SafeHerDecorator decorator = new SafeHerDecorator();
        		long lStartTime = new Date().getTime();
        		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
        		decorator.setDataBean(data);
            	IRideRequestResponseService iRideRequestResponseService = (IRideRequestResponseService)
        				ApplicationContaxtAwareUtil.getApplicationContext().getBean(IRideRequestResponseService.class);
                try {	
                	if (decorator.getResponseMessage() == null
        					|| decorator.getResponseMessage().length() <= 0) {
                		iRideRequestResponseService.cancelOrLateReasonRequest(decorator);
                		
        			}
        		} catch (Exception e) {
        			decorator.setResponseMessage(e.getMessage());
        			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
        			logger_c.info("**************Exiting from onData of socketCommon with Exception "+e.getMessage() +" *********");
        		}
        		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
    			
            	// send message back to client with ack callback WITH data
        		ResponseObject responseObject = decoratorUtil.responseToClient(decorator);
                client.sendEvent("cancelReasonRequestResponse", new AckCallback<String>(String.class) {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("ack from client: " + client.getRemoteAddress() + " data: " + result);
                    }
                }, responseObject);
        		ResponseObject responseObject2 = decoratorUtil.responseToClient(decorator);
                client.sendEvent("cancelReasonRequest2", new VoidAckCallback() {

                    protected void onSuccess() {
                        System.out.println("void ack from: " + client.getRemoteAddress());
                    }

                }, responseObject2);
                
                //send message to receiver
//        		String clientUUIDId = SingletonSession.getClientUUIDMap().get(data.getReceiverId());
        		String clientUUIDId = findClinetUUid(data.getReceiverId());
        		if(clientUUIDId != null){
            		RideQuickInfoBean bean = new RideQuickInfoBean();
            		RideRequestResponseBean rideBean = (RideRequestResponseBean) decorator.getDataBean();
            		bean.setNotificationType(rideBean.getNotificationType());
            		bean.setNotificationMessage(rideBean.getNotificationMessage());
            		socketIO.getServer().getClient(UUID.fromString(clientUUIDId)).sendEvent("message", bean);
        			
        		}
            }
        });
		
		socketIO.getServer().addEventListener("colorMatch", PreRideRequestBean.class,
				new DataListener<PreRideRequestBean>() {
            @Override
            public void onData(final SocketIOClient client, PreRideRequestBean data, final AckRequest ackRequest) {
        		SafeHerDecorator decorator = new SafeHerDecorator();
        		long lStartTime = new Date().getTime();
        		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
            	logger_c.info("**************Method onData colorMatch PreRideRequestBean "+data+" **********");
        		System.out.println("Mathcing color of isDriver: "+data.getIsDriver());        		
        		try {
            		IRideManager iRideManager = (IRideManager)
            				ApplicationContaxtAwareUtil.getApplicationContext().getBean(IRideManager.class);
            		decorator.setDataBean(data);
        			if (decorator.getResponseMessage() == null
        					|| decorator.getResponseMessage().length() <= 0) {
        				iRideManager.getColorMatchV2(decorator);
        			}
        		} catch (Exception e) {
        			decorator.setResponseMessage(e.getMessage());
        			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
        			logger_c.info("******Exiting from colorMatchRequest using socket with json "+ data 
        					+ "and exception "+e.getMessage()+"******");
        		}
            	// send message back to client with ack callback WITH data
        		ResponseObject responseObject = decoratorUtil.responseToClient(decorator);
                client.sendEvent("colorMatchResponse", new AckCallback<String>(String.class) {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("ack from client: " + client.getRemoteAddress() + " data: " + result);
                    }
                }, responseObject);
        		ResponseObject responseObject2 = decoratorUtil.responseToClient(decorator);
                client.sendEvent("colorMatch2", new VoidAckCallback() {

                    protected void onSuccess() {
                        System.out.println("void ack from: " + client.getRemoteAddress());
                    }

                }, responseObject2);
                
                //send message to receiver
//        		String clientUUIDId = SingletonSession.getClientUUIDMap().get(data.getReceiverId());
        		String clientUUIDId = findClinetUUid(data.getReceiverId());
        		if(clientUUIDId != null){
        			PreRideRequestBean preBean = (PreRideRequestBean) decorator.getDataBean();
            		RideQuickInfoBean bean = new RideQuickInfoBean();
            		bean.setNotificationType(preBean.getNotificationType());
            		bean.setNotificationMessage(preBean.getNotificationMessage());
            		socketIO.getServer().getClient(UUID.fromString(clientUUIDId)).sendEvent("message", bean);
            		//send message to sender
        			if (preBean
							.getNotificationType()
							.equals(PushNotificationStatus.ColorVerificationFailed
									.getValue())
							|| preBean
									.getNotificationType()
									.equals(PushNotificationStatus.RideCancelByEmergency
											.getValue())
							|| preBean
									.getNotificationType()
									.equals(PushNotificationStatus.SuccessfullVerification
											.getValue())) {
        				
            			String appUserId = "";
            			if(data.getIsDriver().equals("1")){
            				appUserId = data.getDriverappUserId();
            			}else{
            				appUserId = data.getPassengerappUserId();
            			}
//            			String senderClientUUIDId = SingletonSession.getClientUUIDMap().get(appUserId);
                		String senderClientUUIDId = findClinetUUid(data.getReceiverId());
            			socketIO.getServer().getClient(UUID.fromString(senderClientUUIDId)).sendEvent("message", bean);
            		}
        			
        		}
            }
        });
		
		socketIO.getServer().addEventListener("startFinalRide", RideBean.class,
				new DataListener<RideBean>() {
            @Override
            public void onData(final SocketIOClient client, RideBean data, final AckRequest ackRequest) {
            	logger_c.info("**************Method onData startFinalRide RideBean "+data+" **********");
        		SafeHerDecorator decorator = new SafeHerDecorator();
        		long lStartTime = new Date().getTime();
        		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
        		decorator.setDataBean(data);
            	IRideManager rideManager = (IRideManager)
        				ApplicationContaxtAwareUtil.getApplicationContext().getBean(IRideManager.class);
                try {	
                	if (decorator.getResponseMessage() == null
        					|| decorator.getResponseMessage().length() <= 0) {
        				rideManager.startRide(decorator);
        			}
        		} catch (Exception e) {
        			decorator.setResponseMessage(e.getMessage());
        			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
        			logger_c.info("**************Exiting from onData of socketCommon with Exception "+e.getMessage() +" *********");
        		}
        		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
    			
            	// send message back to client with ack callback WITH data
        		ResponseObject responseObject = decoratorUtil.responseToClient(decorator);
                client.sendEvent("startFinalRideResponse", new AckCallback<String>(String.class) {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("ack from client: " + client.getRemoteAddress() + " data: " + result);
                    }
                }, responseObject);
        		ResponseObject responseObject2 = decoratorUtil.responseToClient(decorator);
                client.sendEvent("startFinalRide2", new VoidAckCallback() {

                    protected void onSuccess() {
                        System.out.println("void ack from: " + client.getRemoteAddress());
                    }

                }, responseObject2);
                
                //send message to receiver
//        		String clientUUIDId = SingletonSession.getClientUUIDMap().get(data.getReceiverId());
        		String clientUUIDId = findClinetUUid(data.getReceiverId());
        		if(clientUUIDId != null){
            		RideQuickInfoBean bean = new RideQuickInfoBean();
            		RideBean rideBean = (RideBean) decorator.getDataBean();
            		bean.setNotificationType(rideBean.getNotificationType());
            		bean.setRideNo(rideBean.getRideNo());
            		bean.setNotificationMessage(rideBean.getNotificationMessage());
            		socketIO.getServer().getClient(UUID.fromString(clientUUIDId)).sendEvent("message", bean);
        			
        		}
            }
        });
		
		socketIO.getServer().addEventListener("endRide", RideBean.class,
				new DataListener<RideBean>() {
            @Override
            public void onData(final SocketIOClient client, RideBean data, final AckRequest ackRequest) {
            	logger_c.info("**************Method onData endRide RideBean "+data+" **********");
        		SafeHerDecorator decorator = new SafeHerDecorator();
        		long lStartTime = new Date().getTime();
        		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
        		decorator.setDataBean(data);
            	IRideManager rideManager = (IRideManager)
        				ApplicationContaxtAwareUtil.getApplicationContext().getBean(IRideManager.class);
                try {	
                	if (decorator.getResponseMessage() == null
        					|| decorator.getResponseMessage().length() <= 0) {
        				rideManager.endRideV2(decorator);
        			}
        		} catch (Exception e) {
        			decorator.setResponseMessage(e.getMessage());
        			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
        			logger_c.info("**************Exiting from onData of socketCommon with Exception "+e.getMessage() +" *********");
        		}
        		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
    			
            	// send message back to client with ack callback WITH data
        		ResponseObject responseObject = decoratorUtil.responseToClient(decorator);
                client.sendEvent("endRideResponse", new AckCallback<String>(String.class) {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("ack from client: " + client.getRemoteAddress() + " data: " + result);
                    }
                }, responseObject);
        		ResponseObject responseObject2 = decoratorUtil.responseToClient(decorator);
                client.sendEvent("endRide2", new VoidAckCallback() {

                    protected void onSuccess() {
                        System.out.println("void ack from: " + client.getRemoteAddress());
                    }

                }, responseObject2);
                
                //send message to receiver
//        		String clientUUIDId = SingletonSession.getClientUUIDMap().get(data.getReceiverId());
        		String clientUUIDId = findClinetUUid(data.getReceiverId());
        		if(clientUUIDId != null){
            		RideBean rideBean = (RideBean) decorator.getDataBean();
            		RideQuickInfoBean bean = new RideQuickInfoBean();
            		if(rideBean.getNotificationType().equals(
            				PushNotificationStatus.PaymentCancel.getValue())){
                		bean.setNotificationType(rideBean.getNotificationType());
                		bean.setNotificationMessage(rideBean.getNotificationMessage());
            		}else if(rideBean.getNotificationType().equals(
                				PushNotificationStatus.InvoiceRequest.getValue())){
                		bean = populateInvoiceInfo(rideBean, new RideQuickInfoBean());
                	}
            		socketIO.getServer().getClient(UUID.fromString(clientUUIDId)).sendEvent("message", bean);
        			
        		}
            }
        });
		
		socketIO.getServer().addEventListener("midEndRide", RideBean.class,
				new DataListener<RideBean>() {
            @Override
            public void onData(final SocketIOClient client, RideBean data, final AckRequest ackRequest) {
            	logger_c.info("**************Method onData midEndRide RideBean "+data+" **********");
        		SafeHerDecorator decorator = new SafeHerDecorator();
        		long lStartTime = new Date().getTime();
        		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
        		decorator.setDataBean(data);
            	IRideManager rideManager = (IRideManager)
        				ApplicationContaxtAwareUtil.getApplicationContext().getBean(IRideManager.class);
                try {	
                	if (decorator.getResponseMessage() == null
        					|| decorator.getResponseMessage().length() <= 0) {
        				rideManager.midEndRide(decorator);
        			}
        		} catch (Exception e) {
        			decorator.setResponseMessage(e.getMessage());
        			decorator.setReturnCode(ReturnStatusEnum.FAILURE.toString());
        			logger_c.info("**************Exiting from onData of socketCommon with Exception "+e.getMessage() +" *********");
        		}
        		decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
    			
            	// send message back to client with ack callback WITH data
        		ResponseObject responseObject = decoratorUtil.responseToClient(decorator);
                client.sendEvent("midEndRideResponse", new AckCallback<String>(String.class) {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("ack from client: " + client.getRemoteAddress() + " data: " + result);
                    }
                }, responseObject);
        		ResponseObject responseObject2 = decoratorUtil.responseToClient(decorator);
                client.sendEvent("midEndRide2", new VoidAckCallback() {

                    protected void onSuccess() {
                        System.out.println("void ack from: " + client.getRemoteAddress());
                    }

                }, responseObject2);
                
                //send message to receiver
//        		String clientUUIDId = SingletonSession.getClientUUIDMap().get(data.getReceiverId());
        		String clientUUIDId = findClinetUUid(data.getReceiverId());
        		if(clientUUIDId != null){
            		RideQuickInfoBean bean = new RideQuickInfoBean();
            		RideBean rideBean = (RideBean) decorator.getDataBean();
            		bean.setNotificationType(rideBean.getNotificationType());
            		bean.setNotificationMessage(rideBean.getNotificationMessage());
            		socketIO.getServer().getClient(UUID.fromString(clientUUIDId)).sendEvent("message", bean);
        			
        		}
            }
        });
		//here sockets end from accept request by passenger to end ride

//		socketIO.getServer().start();
//
//        try {
//			Thread.sleep(Integer.MAX_VALUE);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//
//        socketIO.getServer().stop();
	}
	
	private RideQuickInfoBean populateRideQurickInfoBean(
			RideRequestResponseBean rideBean, RideQuickInfoBean quickBean){
		RideQuickInfoBean bean = new RideQuickInfoBean();
		bean.setDriverAppId(rideBean.getDriverId());
		bean.setPassengerAppId(rideBean.getPassengerId());
		bean.setPreRideId(rideBean.getPreRideId());
		bean.setRideReqResId(rideBean.getRideReqResId());
		bean.setNotificationType(rideBean.getNotificationType());
		bean.setNotificationMessage(rideBean.getNotificationMessage());
		
		return bean;
	}
	
	private RideQuickInfoBean populateRideQurickInfoBeanFromPreRideBean(
			PreRideRequestBean preBean, RideQuickInfoBean quickBean){
		RideQuickInfoBean bean = new RideQuickInfoBean();
		bean.setDriverAppId(preBean.getDriverappUserId());
		bean.setPassengerAppId(preBean.getPassengerappUserId());
		bean.setPreRideId(preBean.getPreRideId());
		bean.setEstimatedTime(preBean.getEstimatedTime());
		bean.setNotificationType(preBean.getNotificationType());
		bean.setNotificationMessage(preBean.getNotificationMessage());
		
		return bean;
	}
	
	private RideQuickInfoBean populateInvoiceInfo(
			RideBean rideBean, RideQuickInfoBean quickBean){
		RideQuickInfoBean bean = new RideQuickInfoBean();
		bean.setRideNo(rideBean.getRideNo());
		bean.setDiscount(rideBean.getDiscount());
		bean.setInvoiceNo(rideBean.getInvoiceNo());
		bean.setPaymentDone(rideBean.getPaymentDone());
		bean.setNotificationType(rideBean.getNotificationType());
		bean.setNotificationMessage(rideBean.getNotificationMessage());
		
		return bean;
	}
	
	@SuppressWarnings("unused")
	private String findClinetUUid(String appUserId){
		SocketClientUUIDRepository socketClientUUIDRepository = (SocketClientUUIDRepository)
				ApplicationContaxtAwareUtil.getApplicationContext().getBean(SocketClientUUIDRepository.class);
		return socketClientUUIDRepository.findClientSocketUUID(appUserId);
	}
	
	@SuppressWarnings("unused")
	private String getArrivalTime(MapBean data){

		CriteriaRepository criteriaRepository = (CriteriaRepository)
				ApplicationContaxtAwareUtil.getApplicationContext().getBean(CriteriaRepository.class);
		IGoogleMapsAPIService iGoogleMapsAPIService = (IGoogleMapsAPIService)
				ApplicationContaxtAwareUtil.getApplicationContext().getBean(IGoogleMapsAPIService.class);
		
		RideCriteriaEntity rideCriteriaEntity = criteriaRepository.
				findCriteria(data.getReceiverId());
		if(rideCriteriaEntity != null){
			LatLng driver = new LatLng(new Double(data.getLat()),
					new Double(data.getLng()));
			LatLng passenger = new LatLng(new Double(rideCriteriaEntity.getSourceLat()),
					new Double(rideCriteriaEntity.getSourceLong()));
			String time = iGoogleMapsAPIService.getArriveTime(driver, passenger);
			if(time != null){
				return DateUtil.getMinutesAndSecs(new Double(time));
			}
		}
		return null;
	}

}