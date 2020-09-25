from discord.ext import commands
from discord.ext.commands import Cog

scp_help = """
SCP Lookup
 - Type a message containing a string of the format SCP-1234 and the bot will respond with a link to that scp.
"""

party_help = """
Party
 - &party - Add fun emotes to this message
 - &party <messageid> - Add fun emotes to message <messageid> in this channel
 - &party <channelid> <messageid> - Add fun emote to message <messageid> in channel <channelid>
"""


class Help(Cog):
    def __init__(self, bot):
        self.bot = bot

    @commands.group(pass_context=True)
    async def help(self, ctx):
        if ctx.invoked_subcommand is None:
            help_text = "```"
            help_text += scp_help
            help_text += party_help
            help_text += "```"
            await ctx.send(help_text)

    @help.command(pass_context=True)
    async def scp(self, ctx):
        help_text = "```"
        help_text += scp_help
        help_text += "```"
        await ctx.send(help_text)

    @help.command(pass_context=True)
    async def party(self, ctx):
        help_text = "```"
        help_text += party_help
        help_text += "```"
        await ctx.send(help_text)


def setup(bot):
    bot.add_cog(Help(bot))
