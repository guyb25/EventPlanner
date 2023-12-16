FROM postgres:latest

ENV POSTGRES_DB=userevents
ENV POSTGRES_USER=user
ENV POSTGRES_PASSWORD=pass

EXPOSE 5432

COPY init.sql /docker-entrypoint-initdb.d/