import re

from discord.ext.commands import Cog

scp_regex = re.compile('\\bSCP-\\d{3,4}J?\\b')


class Scp(Cog):
    def __init__(self, bot):
        self.bot = bot

    @Cog.listener()
    async def on_message(self, message):
        if message.author.id == self.bot.user.id \
                or message.content.startswith(self.bot.command_prefix):
            return
        if len(results := scp_regex.findall(message.content.upper())) != 0:
            response = "\n".join(["http://www.scpwiki.com/" + x for x in results])
            await message.channel.send(response)


def setup(bot):
    bot.add_cog(Scp(bot))
