FROM postgres:latest

ENV POSTGRES_DB=events
ENV POSTGRES_USER=user
ENV POSTGRES_PASSWORD=pass

COPY init.sql /docker-entrypoint-initdb.d/