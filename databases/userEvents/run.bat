docker build . -t userEvents
docker run -d -p 5432:5432 --name userEvents userEvents