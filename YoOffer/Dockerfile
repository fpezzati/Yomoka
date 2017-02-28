FROM fpezzati/wildflyogm
ENV yodbhost 'localhost'
ENV yodbport '27017'
ENV yocommand '/opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0 -Dyodbhost=$yodbhost -Dyodbport=$yodbport'
ENV java_arg_yodbhost '-Dyodbhost=$yodbhost'
ENV java_arg_yodbport '-Dyodbport=$yodbport'
ARG webapp
COPY $webapp /opt/jboss/wildfly/standalone/deployments/
EXPOSE 8080
#CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "echo $java_arg_yodbhost", "echo $java_arg_yodbport"]
#RUN '/opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0 -Dyodbhost=$yodbhost -Dyodbport=$yodbport'
#RUN ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "echo $java_arg_yodbhost", "echo $java_arg_yodbport"]
#RUN /bin/bash -c '/opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0 -Dyodbhost=$yodbhost -Dyodbport=$yodbport'
#CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "echo ${java_arg_yodbhost}", "echo ${java_arg_yodbport}"]
#CMD 'echo ${yocommand}'
CMD $yocommand
