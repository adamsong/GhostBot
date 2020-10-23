import configparser
import logging
import os
import shutil
import sys

from discord.ext import commands

logging.basicConfig(level=logging.INFO)

if not os.path.isfile("bot/config-default.ini") and not os.path.isfile("config/config.ini"):
    logging.error(
        "Cannot find config default or an active config, please refer to the documentation for the config file.")
    logging.error(f"Current working directory {os.getcwd()}")
    sys.exit(1)

if not os.path.isfile("config/config.ini"):
    logging.info(f"Cannot find config file at {os.path.abspath('config/config.ini')}, copying default")
    try:
        os.makedirs("config/")
        shutil.copyfile("bot/config-default.ini", "config/config.ini")
    except IOError as e:
        logging.error("Unable to create config:")
        logging.error(f"{e}")
        sys.exit(1)

config = configparser.ConfigParser()
config.read('config/config.ini')

bot = commands.Bot(command_prefix="&")
bot.owner_id = int(config['bot-data']['owner'])
bot.remove_command("help")
bot.booba_chance = int(config['bot']['booba_chance'])
testing = config['bot']['testing'] in ['true', '1', 't', 'y', 'yes', 'yeah', 'yup', 'certainly', 'uh-huh']

extensions = [
    "bot.help",
    "bot.scp",
    "bot.party",
    "bot.booba"
]

for extension in extensions:
    try:
        bot.load_extension(extension)
    except Exception as e:
        exc = f"{type(e).__name__}: {e}"
        logging.error(f"Failed to load extension {extension}\n{exc}")

logging.info("Running bot")
bot.run(config['bot-data']['api_key'])
