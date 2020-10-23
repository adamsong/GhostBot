docker stop ghost
docker rm ghost
docker run -v $(pwd)/config:/config --name=ghost --restart unless-stopped -d docker.pkg.github.com/adamsong/ghostbot/ghostbot:latest
