package ml.adamsogm.ghostbot.commands;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import reactor.core.publisher.Mono;

public class GreetCommand implements ISlashCommand {
	
	@Override
	public String getName() {
		return "greet";
	}
	
	@Override
	public Mono<Void> handle(ChatInputInteractionEvent event) {
		String name = event.getOption("name")
			.flatMap(ApplicationCommandInteractionOption::getValue)
			.map(ApplicationCommandInteractionOptionValue::asString)
			.orElse(""); // Required arguments should never be null, but the warning was annoying
		return event.reply().withEphemeral(true).withContent("Hello, " + name);
	}
}
