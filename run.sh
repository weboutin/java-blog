javac -cp \
./WEB-INF/lib/servlet-api.jar:\
./WEB-INF/lib/json-20201115.jar:.\
 Users.java \
&& mv *.class ./WEB-INF/classes/  \
&& ../../bin/shutdown.sh \
&& ../../bin/startup.sh \
&& tail -f ../../logs/catalina.out
