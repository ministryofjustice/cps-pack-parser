FROM java

RUN addgroup --gid 2000 --system appgroup && \
    adduser --uid 2000 --system appuser --gid 2000

RUN mkdir -p /app
WORKDIR /app


COPY build/libs/cps-pack-parser-*.jar /app/cps-pack-parser.jar

COPY tessdata/ /tessdata/

RUN chown -R appuser:appgroup /app
RUN chown -R appuser:appgroup /tessdata

USER 2000

ENTRYPOINT ["/usr/bin/java", "-jar", "/app/cps-pack-parser.jar"]
