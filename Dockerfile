FROM java

COPY build/libs/cps-pack-parser-*.jar /root/cps-pack-parser.jar

ENTRYPOINT ["/usr/bin/java", "-jar", "/root/cps-pack-parser.jar"]
