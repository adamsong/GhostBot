docker build . -t ghcr.io/adamsong/ghostbot:latest -t ghcr.io/adamsong/ghostbot:v$1
docker push ghcr.io/adamsong/ghostbot:latest
docker push ghcr.io/adamsong/ghostbot:v$1
