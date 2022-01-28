package ml.adamsogm.ghostbot.listeners;

import discord4j.core.event.domain.interaction.MessageInteractionEvent;
import ml.adamsogm.ghostbot.commands.IMessageCommand;
import ml.adamsogm.ghostbot.commands.PartyCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class MessageCommandListener {
	private static final List<IMessageCommand> commands = new ArrayList<>();
	
	static {
		commands.add(new PartyCommand());
	}
	
	public static Mono<Void> handle(MessageInteractionEvent event) {
		return Flux.fromIterable(commands)
			.filter(command -> command.getName().equals(event.getCommandName()))
			.next()
			.flatMap(command -> command.handle(event));
	}
}
