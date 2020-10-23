from discord.ext import commands
from discord.ext.commands import Cog

import random


class Party(Cog):
    def __init__(self, bot):
        self.bot = bot

    @commands.command()
    async def party(self, ctx, *args):
        emojis = self.bot.emojis
        random.shuffle(emojis)
        channel = ctx.channel
        message = ctx.message

        channel_id = 0
        message_id = 0

        if len(args) == 2:
            channel_id = args[0]
            message_id = args[1]
        elif len(args) == 1:
            message_id = args[0]

        if channel_id != 0:
            try:
                channel_id = int(channel_id)
            except ValueError:
                await ctx.send("Channel id is invalid")
                return
            channel = ctx.guild.get_channel(int(channel_id))
            if channel is None:
                await ctx.send(f"Channel {channel_id} is not found")
                return
        if message_id != 0:
            try:
                message_id = int(message_id)
            except ValueError:
                await ctx.send("Message id is invalid")
                return
            message = await channel.fetch_message(message_id)
            if message is None:
                await ctx.send(f"Message {message_id} is not found")
                return

        count = 0
        for emoji in emojis:
            await message.add_reaction(emoji)
            count += 1
            if count >= 20:
                break


def setup(bot):
    bot.add_cog(Party(bot))
