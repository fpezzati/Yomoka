package edu.pezzati.yo.offer.util;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.pezzati.yo.offer.exception.OfferNotFound;

@HandleServiceException
@Interceptor
public class HandleServiceExceptionImpl {

    private Logger log = LoggerFactory.getLogger(getClass());
    private static final String OFFER_NOT_FOUND = "Offer not found";
    private static final String INVALID_INPUT = "Invalid input";
    private static final String UNEXPECTED_ERROR = "Unexpected error";

    @AroundInvoke
    public Object intercept(InvocationContext invocationContext) throws Exception {
	log.info(invocationContext.getMethod().getName());
	try {
	    return invocationContext.proceed();
	} catch (IllegalArgumentException iae) {
	    log.error(INVALID_INPUT, iae);
	    return Response.status(400).entity(iae.getMessage()).build();
	} catch (OfferNotFound onf) {
	    log.error(OFFER_NOT_FOUND, onf);
	    return Response.status(404).entity(onf.getMessage()).build();
	} catch (Exception e) {
	    log.error(UNEXPECTED_ERROR, e);
	    return Response.status(500).entity(e.getMessage()).build();
	}
    }
}
