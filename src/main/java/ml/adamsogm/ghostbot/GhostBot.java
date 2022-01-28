package ml.adamsogm.ghostbot;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.event.domain.interaction.MessageInteractionEvent;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.object.entity.User;
import ml.adamsogm.ghostbot.listeners.MessageCommandListener;
import ml.adamsogm.ghostbot.listeners.SlashCommandListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class GhostBot {
	private static final Logger LOGGER = LoggerFactory.getLogger(GhostBot.class);
	
	public static void main(String[] args) {
		GatewayDiscordClient client = DiscordClientBuilder.create(
			System.getenv("BOT_TOKEN")).build().login().block();
		
		if (client == null) {
			System.err.println("Error making discord client");
			return;
		}
		
		//Call our code to handle creating/deleting/editing our global slash commands.
		try {
			new GlobalCommandRegistrar(client.getRestClient()).registerCommands();
		} catch (Exception e) {
			LOGGER.error("Error trying to register global slash commands", e);
		}
		
		client.on(ChatInputInteractionEvent.class, SlashCommandListener::handle).subscribe();
		client.on(MessageInteractionEvent.class, MessageCommandListener::handle).subscribe();
		
		client.on(ReadyEvent.class, event -> Mono.fromRunnable(() -> {
			final User self = event.getSelf();
			LOGGER.info("Logged in as {}#{}", self.getUsername(), self.getDiscriminator());
		})).then(client.onDisconnect()).block();
	}
}
