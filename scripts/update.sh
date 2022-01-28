docker stop ghost
docker rm ghost
docker run --name=ghost --restart unless-stopped -d docker.pkg.github.com/adamsong/ghostbot/ghostbot:latest
