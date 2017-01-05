package edu.pezzati.yo.offer;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

public class OfferPersistenceFactory {

	@Produces
	public OfferPersistenceService createPersistenceService(InjectionPoint injectionPoint) {
		PersistenceUnit persistenceUnit = injectionPoint.getBean().getBeanClass().getAnnotation(PersistenceUnit.class);
		return new OfferPersistenceServiceImpl(persistenceUnit.name());
	}
}
