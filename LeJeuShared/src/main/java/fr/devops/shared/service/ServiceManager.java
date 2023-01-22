package fr.devops.shared.service;

import java.util.HashMap;
import java.util.Map;

public class ServiceManager {

	private static final Map<Class<? extends IService>, Object> serviceMap = new HashMap<>();

	public static final <U extends IService, V extends U> void registerAs(Class<U> asType, V service) {
		serviceMap.put(asType, service);
	}
	
	public static final <U extends IService> void register(U service) {
		serviceMap.put(service.getClass(), service);
	}
	
	public static final <U extends IService> U get(Class<U> serviceType) {
		return serviceType.cast(serviceMap.get(serviceType));
	}
	
}
