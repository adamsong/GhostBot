FROM docker.pkg.github.com/adamsong/ghostbot/ghostbot-deps:v2.0.0
COPY bot /bot
  
ENTRYPOINT [ "/usr/local/bin/python", "-mbot.bot" ]