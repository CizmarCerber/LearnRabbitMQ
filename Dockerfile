FROM rabbitmq:3.13-management

USER root
RUN apt-get update && \
    apt-get install -y --no-install-recommends wget unzip && \
    rm -rf /var/lib/apt/lists/*

RUN mkdir -p /opt/rabbitmq/plugins

ARG PLUGIN_VERSION=0.7.1
ARG PLUGIN_ZIP_URL="https://github.com/noxdafox/rabbitmq-message-deduplication/releases/download/${PLUGIN_VERSION}/plugins-rmqv3.13.x-erl26.2-elx1.16.zip"

RUN wget -O /tmp/plugins.zip "${PLUGIN_ZIP_URL}" && \
    unzip -o /tmp/plugins.zip -d / && \
    \
    mv /rabbitmq_message_deduplication-*.ez /opt/rabbitmq/plugins/ && \
    mv /elixir-*.ez /opt/rabbitmq/plugins/ && \
    \
    rm /tmp/plugins.zip && \
    ls -lh /opt/rabbitmq/plugins/

RUN rabbitmq-plugins enable --offline rabbitmq_message_deduplication

USER rabbitmq

EXPOSE 5672
EXPOSE 15672

CMD ["rabbitmq-server"]