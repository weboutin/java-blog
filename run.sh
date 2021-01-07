javac -cp \
./WEB-INF/lib/servlet-api.jar:\
./WEB-INF/lib/mysql-connector-java-8.0.21.jar:.\
 Users.java \
&& mv *.class ./WEB-INF/classes/  \
&& ../../bin/shutdown.sh \
&& ../../bin/startup.sh \
&& tail -f ../../logs/catalina.out
