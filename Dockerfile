FROM alpine:latest
ARG home=/home/code

RUN apk update
RUN apk upgrade
RUN apk add --no-cache bash \
    maven \
    openjdk17-jdk \
    --update util-linux

RUN mkdir ${home}
WORKDIR home









