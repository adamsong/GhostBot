from discord.ext.commands import Cog
import re
import requests
import random

url_regex = re.compile("http[s]?://(?:[a-zA-Z]|[0-9]|[$-_@.&+]|[!*\\(\\),]|(?:%[0-9a-fA-F][0-9a-fA-F]))+")


class Scp(Cog):
    def __init__(self, bot):
        self.bot = bot

    @Cog.listener()
    async def on_message(self, message):
        image = False
        if links := url_regex.findall(message.content):
            for link in links:
                response = requests.get(link)
                image = image or response.headers['content-type'].startswith("image")
        for attachment in message.attachments:
            response = requests.get(attachment.url)
            image = image or response.headers['content-type'].startswith("image")
        randval = random.randint(0, self.bot.booba_chance)
        print(f"{image}, {self.bot.booba_chance}, {randval}")
        if image and randval == 0:
            await message.add_reaction(self.bot.get_emoji(765990605305937952))


def setup(bot):
    bot.add_cog(Scp(bot))
