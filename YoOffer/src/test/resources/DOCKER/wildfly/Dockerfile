FROM jboss/wildfly
#ARG webapp
#ADD $webapp $JBOSS_HOME/standalone/deployments
RUN echo $webapp
RUN cp $webapp $JBOSS_HOME/standalone/deployments
EXPOSE 8080
