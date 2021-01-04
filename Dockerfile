FROM openjdk:8

COPY target/GithubRepoApiList-0.0.1-SNAPSHOT.jar github-repo-api-list.jar

ENV TZ 'America/Sao_Paulo'
    RUN echo $TZ > /etc/timezone && \
    apt-get update && apt-get install -y tzdata && \
    rm /etc/localtime && \
    ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && \
    dpkg-reconfigure -f noninteractive tzdata && \
    apt-get clean

CMD ["java", "-jar", "/github-repo-api-list.jar"]