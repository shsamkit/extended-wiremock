FROM shsamkit/extended-wiremock:latest
ADD ./stubs/mappings /usr/local/var/wm-stub/mappings
ADD ./stubs/__files /usr/local/var/wm-stub/__files
ADD ./application.yml /usr/local/var/application.yml
ENV CONFIG_OVERRIDES="--spring.config.location=/usr/local/var/application.yml"
EXPOSE 8080/tcp