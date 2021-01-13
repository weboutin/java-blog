javac -cp \
./src/main/webapp/WEB-INF/lib/servlet-api.jar:\
./src/main/webapp/WEB-INF/lib/json-20201115.jar:.\
 ./src/main/webapp/servlets/Users.java \
&& mv ./src/main/webapp/servlets/*.class ./src/main/webapp/WEB-INF/classes/  \
&& mvn package -f ./pom.xml \
&& mv ./target/sbs-impl.war ../ROOT.war \
&& ../../bin/shutdown.sh \
&& ../../bin/startup.sh \
&& tail -f ../../logs/catalina.out