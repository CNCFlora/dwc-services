FROM cncflora/java8 

RUN wget http://repo1.maven.org/maven2/org/eclipse/jetty/jetty-runner/9.2.2.v20140723/jetty-runner-9.2.2.v20140723.jar -O /root/jetty.jar

ADD start.sh /root/start.sh
RUN chmod +x /root/start.sh

ADD supervisord.conf /etc/supervisor/conf.d/dwc.conf

EXPOSE 8080
EXPOSE 9001

CMD ["supervisord"]

ADD target/dwc-services-0.0.15-standalone.war /root/dwc-services.war

