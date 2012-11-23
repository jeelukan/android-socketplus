package com.jeelu.android.Interfaces;

import com.jeelu.android.Enums.*;;

public interface IService {

	/**
	 * Start the service.
	 */
	public void startService();

	/**
	 * Stop the service
	 */
	public void stopService();

	/**
	 * @return ServiceState
	 */
	public ServiceState getServiceState();
}
