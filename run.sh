mvn package -f ./pom.xml \
&& mv ./target/sbs-impl.war ../ROOT.war \
&& ../../bin/shutdown.sh \
&& ../../bin/startup.sh \
&& tail -f ../../logs/catalina.out