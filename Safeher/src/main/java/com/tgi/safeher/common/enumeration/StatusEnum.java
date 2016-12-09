package com.tgi.safeher.common.enumeration;

public enum StatusEnum {

	
	Active(1), Accepted(2) ,Final(3), Rejected(4), Ready(5), Ignored(6), Intial(7), MoveToPickUp(8), PassengerColorVerify(9),DriverColorVerify(10)
	,DriverArrival(11),PassengerArrival(12),DriverJustArrived(13),DistributionDue(14),Distributed(15),DistributedPartial(16),Deduct(17),Due(18),InProcess(19),Complete(20)
	,NoReceive(21),Receive(22),Consumed(23),Denied(24),Expire(25),New(26),Proved(27),MatchColor(28),Checked(29),ReChecked(30),Verified(31),InValid(32);
	
	private int	value;
	StatusEnum(int code)
	{
		this.value = code;
	}
	public int getValue( ) {

		return value;
	}
}
