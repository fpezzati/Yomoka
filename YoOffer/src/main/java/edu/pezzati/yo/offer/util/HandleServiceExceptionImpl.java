package edu.pezzati.yo.offer.util;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@HandleServiceException
@Interceptor
public class HandleServiceExceptionImpl {

	private Logger log = LoggerFactory.getLogger(getClass());

	@AroundInvoke
	public Object intercept(InvocationContext invocationContext) throws Exception {
		log.info("GOT IT!");
		return invocationContext.proceed();
	}
}
