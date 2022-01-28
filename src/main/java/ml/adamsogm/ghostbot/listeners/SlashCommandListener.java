package ml.adamsogm.ghostbot.listeners;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import ml.adamsogm.ghostbot.commands.GreetCommand;
import ml.adamsogm.ghostbot.commands.ISlashCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class SlashCommandListener {
	private static final List<ISlashCommand> commands = new ArrayList<>();
	
	static {
		commands.add(new GreetCommand());
	}
	
	public static Mono<Void> handle(ChatInputInteractionEvent event) {
		return Flux.fromIterable(commands)
			.filter(command -> command.getName().equals(event.getCommandName()))
			.next()
			.flatMap(command -> command.handle(event));
	}
}
