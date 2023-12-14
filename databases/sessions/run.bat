docker build . -t sessions
docker run -d -p 6379:6379 --name sessions sessions