FROM fpezzati/wildflyogm
#ARG yodbhost=-Dyodbhost=localhost
#ARG yodbport=-Dyodbport=27017
#ENV yodbhost ${yodbhost}
#ENV yodbport ${yodbport}
ARG webapp
COPY $webapp /opt/jboss/wildfly/standalone/deployments/
EXPOSE 8080
#CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-Dyodbhost=$yodbhost", "-Dyodbport=$yodbport"]
#CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "$yodbhost", "$yodbport"]
ENTRYPOINT ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "${yodbhost}", "${yodbport}"]
