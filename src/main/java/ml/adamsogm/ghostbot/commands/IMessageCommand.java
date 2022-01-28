package ml.adamsogm.ghostbot.commands;

import discord4j.core.event.domain.interaction.MessageInteractionEvent;
import reactor.core.publisher.Mono;

public interface IMessageCommand {
	String getName();
	Mono<Void> handle(MessageInteractionEvent event);
}
