FROM fpezzati/wildflyogm
ENV yodbhost=localhost
ENV yodbport=27017
ARG webapp
COPY $webapp /opt/jboss/wildfly/standalone/deployments/
EXPOSE 8080
CMD /opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0 -Dyodbhost=$yodbhost -Dyodbport=$yodbport
