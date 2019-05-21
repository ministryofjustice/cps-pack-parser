FROM java

COPY build/libs/cps-pack-parser-*.jar /root/cps-pack-parser.jar

COPY tessdata/ /tessdata/

ENTRYPOINT ["/usr/bin/java", "-jar", "/root/cps-pack-parser.jar"]
