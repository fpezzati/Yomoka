package edu.pezzati.yo.offer;

public interface PersistenceService {

	public <T> T create(T entity) throws IllegalArgumentException;
}
