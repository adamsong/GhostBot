package ml.adamsogm.ghostbot.commands;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import reactor.core.publisher.Mono;

public interface ISlashCommand {
	String getName();
	Mono<Void> handle(ChatInputInteractionEvent event);
}
