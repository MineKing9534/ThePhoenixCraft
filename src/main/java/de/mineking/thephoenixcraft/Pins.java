package de.mineking.thephoenixcraft;

import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Pins extends ListenerAdapter {
	@Override
	public void onMessageReactionAdd(MessageReactionAddEvent event) {
		if(!event.getEmoji().equals(Emoji.fromFormatted("\uD83D\uDCCC"))) return;

		event.getChannel().asGuildMessageChannel().clearReactionsById(event.getMessageIdLong(), event.getEmoji()).queue();

		event.getChannel().retrieveMessageById(event.getMessageIdLong())
				.flatMap(message ->
						message.isPinned()
								? message.unpin()
								: message.pin()
				)
				.queue();
	}
}
