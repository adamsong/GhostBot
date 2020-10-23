docker build . -f Dockerfile-deps -t docker.pkg.github.com/adamsong/ghostbot/ghostbot-deps:v$1
docker push docker.pkg.github.com/adamsong/ghostbot/ghostbot-deps:v$1
