FROM  nacos/nacos-server as base

FROM  registry.cn-hangzhou.aliyuncs.com/lzk90s/openjdk11-openj9:alpine-jre as runtime

ENV MODE="cluster" \
    PREFER_HOST_MODE="ip"\
    BASE_DIR="/home/nacos" \
    CLASSPATH=".:/home/nacos/conf:$CLASSPATH" \
    CLUSTER_CONF="/home/nacos/conf/cluster.conf" \
    FUNCTION_MODE="all" \
    JAVA_HOME="/opt/java/openjdk/" \
    NACOS_USER="nacos" \
    JAVA="/opt/java/openjdk/bin/java" \
    JVM_XMS="1g" \
    JVM_XMX="1g" \
    JVM_XMN="512m" \
    JVM_MS="128m" \
    JVM_MMS="320m" \
    NACOS_DEBUG="n" \
    TOMCAT_ACCESSLOG_ENABLED="false" \
    TIME_ZONE="Asia/Shanghai"

WORKDIR $BASE_DIR

COPY --from=base /home/nacos /home/nacos

ENTRYPOINT ["sh", "bin/docker-startup.sh"]