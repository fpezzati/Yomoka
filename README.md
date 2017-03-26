Yomoka project
==============

[![Build Status](https://travis-ci.org/fpezzati/Yomoka.svg?branch=master)](https://travis-ci.org/fpezzati/Yomoka)

Yomoka is a sandbox where to mess with technologies I want to learn. Maybe one day I'll got something back by all this effort :D

What this project is about
--------------------------
Yomoka should be a platform where people can share goods by creating an Offer. An Offer indicates an amount of a good and a latitude longitude pair, so you can see what and where. About Tecniche avanzate di programmazione exam I create YoOffer maven module to focus on Offer type and an API to manage it.  
My focus is to learn how a test driven approach would work in an enterprise Java project.

Tecniques and technologies I choose to use
------------------------------------------
Since I want to learn TDD, JUnit and Mockito are almost mandatory. JUnit and Mockito are agnostic about enterprise technologies and I was curios about developing an enterprise application by TDD.

I am familiar with JavaEE, especially with JBoss Wildfly platform, so I choose Wildfly as platform and Resteasy which is JBoss JAX-RS implementation to build my little API.

I choose MongoDB to store my data because it offer some primitives about querying geospatial data. JPA is my favorite persistence framework. Recently Hibernate come out with a new release: Hibernate OGM. Hibernate OGM is a mature ORM for NoSQL databases.

Integration tests runs in a scenario where an application server and a database talks. I choose Docker to reproduce this scenario. Docker gives you the ability to create immutable artifacts that implements services as containers. Those artifacts work the same way everywhere there is a Docker instance running. Docker can be used with Maven using fabric8 maven-docker-plugin. This plugin permits to run multiple containers, it suits my needs perfectly because I have two containers to run: one for Wildfly with my web application and one for MongoDB.

I choose Git as version control system. Git is widely used and freely supported, I share Yomoka on GitHub and use other tools that easy integrates with it like travis-ci and codacy. I also used Sonar to check source quality.

eventuali scelte di design e implementazione
--------------------------------------------
As TDD says, I start to write tests before implement features but I wrote some spec on Trello for first.

When writing tests I follow a bottom up approach. Tests came with this order (this is also the storyline as git log shows):

- serialization and deserialization,
- Offer equals method,
- travis-ci configuration,
- jacoco configuration,
- Offer annotation tests,
- JPA tests with Fongo,
- restful web service OfferService tests,
- added custom exceptions,
- added Sonar profile to check source code quality,
- tests refactor,
- add RunWith feature to have CDI on tests,
- code refactor. I centralize exception handling by AOP because Sonar complains about code duplicated,
- major tests refactor. All test breaks were fixed,
- have a taste of wildfly-swarn and decide to stay with Wildfly,
- fixed some issues emerged on deploy phase, mainly about Hibernate OGM,
- first introduced Docker to run integration tests. Docker and fabric8 maven-docker-plugin gave me quite of a pain. Docs are not so clear about CMD and RUN,
- I faced some other problems about building a self-contained .war with Hibernate OGM. I decided to build my own Docker image about a Wildfly with embedded Hibernate OGM,
- I also got hard times about configuring images by maven and have two containers aware of each other. Docs were unclear because plugin was not a final release (0.19.0 version) and previous releases were a bit different,
- as integration tests runs, I focused on improving code quality and coverage,
- I added a simple swagger page.

Thanks to JPA I was easy to switch from test to production database by indicating a `persistence.xml` in `main/resources` and another `persistence.xml` in `test/resources`.

When Sonar told me there was a lot of duplicate code about exception handling I thought about AOP to centralize it. So I choose CDI and implemented an interceptor who care about exceptions raised by rest service simply by using an annotation. To make tests to run again I used the JUnit's `@RunWith` feature to launch tests by class which instantiated a CDI context.

eventuali difficoltà incontrate e come si sono superate
-------------------------------------------------------
Hibernate OGM is not natively supported in Wildfly so I have to build my own Wildfly and Hibernate OGM Docker image.

Hibernate OGM gives some issues about deployin the application. I have to bring libs in my .war and excludes Wildfly persistence subsystem.
