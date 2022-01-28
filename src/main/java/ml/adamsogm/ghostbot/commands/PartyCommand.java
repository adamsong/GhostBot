package ml.adamsogm.ghostbot.commands;

import discord4j.core.event.domain.interaction.MessageInteractionEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.reaction.ReactionEmoji;
import reactor.core.publisher.Mono;

import java.util.Collections;

public class PartyCommand implements IMessageCommand {
	
	@Override
	public String getName() {
		return "Party";
	}
	
	@Override
	public Mono<Void> handle(MessageInteractionEvent event) {
		return event.getTargetMessage().flatMap(message ->
			message.getGuild().map(Guild::getEmojis).flatMap(guildEmojiFlux ->
				guildEmojiFlux.collectList().flatMap(guildEmojis -> {
					Mono<Void> reply = event.reply().withEphemeral(true).withContent("Starting the party!");
					Collections.shuffle(guildEmojis);
					int count = Math.min(20, guildEmojis.size());
					for(int i = 0; i < count; i++) {
						reply = reply.and(message.addReaction(ReactionEmoji.custom(guildEmojis.get(i))));
					}
					return reply;
				})
			)
		);
	}
}
