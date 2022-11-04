
splunk-otel-javaagent.jar:
	curl -L https://github.com/signalfx/splunk-otel-java/releases/latest/download/splunk-otel-javaagent.jar -o $@

jetty-run: splunk-otel-javaagent.jar
	OTEL_SERVICE_NAME='MyServlet' \
	OTEL_RESOURCE_ATTRIBUTES='deployment.environment=MyServlet' \
	OTEL_EXPORTER_OTLP_ENDPOINT='http://localhost:4317' \
	MAVEN_OPTS="-javaagent:./$^" \
	mvn jetty:run

clean:
	rm splunk-otel-javaagent.jar
	rm -rf target
