docker build . -t userevents
docker run -d -p 5432:5432 --name userevents userevents