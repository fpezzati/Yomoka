package edu.pezzati.yo.offer;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

public class OfferPersistenceFactory {

    @Produces
    public OfferPersistenceService createPersistenceService(InjectionPoint injectionPoint) {
	PersistenceUnit persistenceUnit = injectionPoint.getAnnotated().getAnnotation(PersistenceUnit.class);
	return new OfferPersistenceServiceImpl(persistenceUnit.name());
    }

    public void close(@Disposes OfferPersistenceService persistenceService) {
	persistenceService.dispose();
    }
}
