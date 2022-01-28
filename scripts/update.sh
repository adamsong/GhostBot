docker stop ghost
docker rm ghost
docker run --name=ghost --restart unless-stopped --env-file .env -d ghcr.io/adamsong/ghostbot:latest
