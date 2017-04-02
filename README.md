Yomoka project
==============

[![Build Status](https://travis-ci.org/fpezzati/Yomoka.svg?branch=master)](https://travis-ci.org/fpezzati/Yomoka)

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/22e01821f71b4a15a93ea8977afd1636)](https://www.codacy.com/app/fpezzati/Yomoka?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=fpezzati/Yomoka&amp;utm_campaign=Badge_Grade)

Yomoka is a sandbox where to mess with technologies I want to learn.

What this project is about
--------------------------
Yomoka should be a platform where people can share goods by creating an Offer. An Offer indicates a description of a good, an amount and a latitude longitude pair, so you can see what, how many and where. About *Tecniche avanzate di programmazione* exam I create YoOffer maven module to focus Test Driven Developement. YoOffer module is about Offer type and an API to manage it. My focus is to learn TDD and how a test driven approach would work in a JavaEE project.

Tecniques and technologies I choose to use
------------------------------------------
Because I want to learn TDD, JUnit Maven and Mockito are almost mandatory if you want to use Java. JUnit and Mockito are agnostic about enterprise technology.

I am familiar with JBoss's [Wildfly](http://wildfly.org/) application server, so I choose Wildfly as platform and Resteasy which is JBoss JAX-RS implementation to build my little API.

I choose [MongoDB](www.mongodb.com) to store my data because it offers some primitives about querying geospatial data. JPA is my favorite persistence framework. Recently Hibernate come out with a new release: [Hibernate OGM](http://hibernate.org/ogm/).

Integration tests will runs in a scenario where the web application is deployed on Wildfly and interacts with a MongoDB istance. I choose [Docker](https://www.docker.com/) to reproduce this scenario. Docker gives you the ability to create immutable artifacts that implements services as containers. Those artifacts work the same way everywhere Docker runs. Docker can be used with Maven using [fabric8 maven-docker-plugin](https://github.com/fabric8io/docker-maven-plugin). This plugin permits to run multiple containers, it suits my needs perfectly because I have two containers to run: one for Wildfly and one for MongoDB.

I choose Git as version control system. Git is widely used and supported. As you can see I share Yomoka on GitHub and use other tools that easy integrates with it like travis-ci and codacy. I also used Sonar to check source quality.

Implementation and design choiches
----------------------------------
When I figured out what the service is about, I decided about a small set of classes:
```
edu.pezzati.yo.offer.OfferPersistenceService
edu.pezzati.yo.offer.OfferService
edu.pezzati.yo.offer.model.Offer
```
of course that was what I had in mind. As TDD says, I started to write tests before implement features. I wrote tests from the easier to the more complex. So first comes tests about data model, the Offer: JSON serialization, deserialization, test about equals method and so on.

### Annotation tests
Then comes tests about annotation. Annotations play a significant role in JavaEE. In this project I use annotations to implement the webservice, offer entity and do validation. Change annotation and you change your service behavior, so I decided to implement tests that check if annotations change. Annotation tests check that a specific method has some annotations with expected values. Here is an example:
```
@Test
public void offerLonMustHaveDigitsAndDecimalMaxAndDecimalMinAnnotations() throws Exception {
	String fieldName = "lon";
	Field field = Offer.class.getDeclaredField(fieldName);
	Digits digits = (Digits) AnnotationResolver.getFieldAnnotationByType(field, Digits.class);
	Assert.assertEquals(3, digits.integer());
	Assert.assertEquals(8, digits.fraction());

	DecimalMax decimalMax = (DecimalMax) AnnotationResolver.getFieldAnnotationByType(field, DecimalMax.class);
	Assert.assertEquals("90.00", decimalMax.value());

	DecimalMin decimalMin = (DecimalMin) AnnotationResolver.getFieldAnnotationByType(field, DecimalMin.class);
	Assert.assertEquals("-90.00", decimalMin.value());
}
```
The test check that attribute `lon` is annotated with `@DecimalMax` and with `@DecimalMin` and their values are `90.00` and `-90.00`. If you change annotation's value test will break, also if you change specifications (and tests are specifications) test will break and you know you have to modify annotations.

### Persistence tests
Persistence service gives an interface to do create, search, update and delete operation about the Offer model. Persistence service is also responsible about validating models as they arrives. Persistence tests indicates how the service should behaves against null or invalid data, search for existing or non existing data and so on.
In this tests I use an in-memory database, Fongo, so these tests are not pure unit tests because they rely on a database. I choose to use Fongo because using a database while writing unit tests, even if it is not exactly the same database that will be used in production, gives me confidence that also integration tests will behave the same. Fongo is not too much resources consuming, tests perform quite fast.

### Web service tests
Web service tests focus on how the web service behave and responds. Most of tests check that HTTP response's status code is consistent with data passed as argument. Most of these unit tests inspired integration tests.

While implementing tests I occasionally run Yomoka agains Sonar to check code smells or duplications. At a point Sonar shows some code duplications about `OfferPersistenceService` error handling. To avoid code duplications I introduced CDI interceptor to centralize error handling and proper feedback. This caused a major refactor of that class. Tests were changed too to handle CDI. I made a JUnit runner to use [Weld](http://weld.cdi-spec.org/) and have CDI in a JavaSE environment. Here is the runner:
```
public class WeldRunner extends BlockJUnit4ClassRunner {

	private WeldContainer weldContainer;

	public WeldRunner(Class<?> klass) throws InitializationError {
		super(klass);
		Weld weld = new Weld();
		weldContainer = weld.initialize();
	}

	@Override
	protected Object createTest() throws Exception {
		final Class<?> test = getTestClass().getJavaClass();
		return weldContainer.instance().select(test).get();
	}
}
```

### Integration tests
Integration tests are strongly inspirated by existing unit tests about web service. As expected, integration tests run fine very early. The real effort here was to configure Maven to run Docker containers to run tests against.

Obstacles and how I overcome them
---------------------------------
I only had a bit of a headache with Hibernate OGM. Hibernate OGM is not natively supported in Wildfly. This is not really an issue you must add it to Wildfly as module if you want to use it in your webapp. So my yaml file to build the Wildfly docker image would be more complex than expected. I overcome this problem by creating my own Wildfly and Hibernate OGM Docker image, which can be found [here](https://hub.docker.com/r/fpezzati/wildflyogm/) on the Docker Hub.
>>>>>>> feature/tap-report
