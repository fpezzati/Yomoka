package edu.pezzati.yo.offer.util;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

public class WeldRunner extends BlockJUnit4ClassRunner {

	private WeldContainer weldContainer;

	public WeldRunner(Class<?> klass) throws InitializationError {
		super(klass);
		Weld weld = new Weld();
		weldContainer = weld.initialize();
		// Runtime.getRuntime().addShutdownHook(new Thread() {
		// @Override
		// public void run() {
		// weld.shutdown();
		// }
		// });
	}

	@Override
	protected Object createTest() throws Exception {
		final Class<?> test = getTestClass().getJavaClass();
		return weldContainer.instance().select(test).get();
	}
}
