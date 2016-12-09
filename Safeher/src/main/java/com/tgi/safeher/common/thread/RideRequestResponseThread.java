package com.tgi.safeher.common.thread;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tgi.safeher.API.thirdParty.IOS.PushNotificationsIOS;
import com.tgi.safeher.beans.RideRequestResponseBean;
import com.tgi.safeher.common.decorator.SafeHerDecorator;
import com.tgi.safeher.common.exception.GenericException;

@Component
@Scope("singleton")
public class RideRequestResponseThread implements Runnable, Serializable {

	private List<RideRequestResponseBean> passengerRequest = new ArrayList<RideRequestResponseBean>();
	HashMap passengerRequestMap = new HashMap<String, ArrayList<RideRequestResponseBean>>();
	private Thread t;
	private SafeHerDecorator decorator;
	private boolean isWait = false;
	
	public HashMap getPassengerRequestMap() {
		return passengerRequestMap;
	}

	public void setPassengerRequestMap(HashMap passengerRequestMap) {
		this.passengerRequestMap = passengerRequestMap;
	}

	public RideRequestResponseThread(){
	}

	public List<RideRequestResponseBean> getPassengerRequest() {
		return passengerRequest;
	}

	public void setPassengerRequest(List<RideRequestResponseBean> passengerRequest) {
		this.passengerRequest = passengerRequest;
	}


	public void run() {
		try {
			synchronized (decorator) {
//				int i = 1;
//				System.out.println("Thread: "
//						+ ((RideRequestResponseBean) decorator.getDataBean())
//								.getAppUserId() + " Index:" + i + " Running.");
				try {
					//PushNotificationsIOS.pushIOS(
						//	"3c6c9856b1da6fa06525148eb9e5a44d7ab0acdcf09dd557488f95cd94833df1", "Driver", "dev");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				t.sleep(5000);
				isWait = true;
//				System.out.println("Thread: "
//						+ ((RideRequestResponseBean) decorator.getDataBean())
//								.getAppUserId() + " Index:" + i + " exiting.");
//				i++;
			}
			// Let the thread sleep for a while.
		} catch (InterruptedException e) {
			System.out.println("Thread interrupted.");
		}
	}

	public void start(SafeHerDecorator decorator) {
		this.decorator = decorator;
		if (t == null) {
			t = new Thread(this);
		}
		t.start();
	}

}
