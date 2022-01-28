docker build . -t docker.pkg.github.com/adamsong/ghostbot/ghostbot:latest -t docker.pkg.github.com/adamsong/ghostbot/ghostbot:v$1
docker push docker.pkg.github.com/adamsong/ghostbot/ghostbot:latest
docker push docker.pkg.github.com/adamsong/ghostbot/ghostbot:v$1
