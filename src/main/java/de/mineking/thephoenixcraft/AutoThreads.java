package de.mineking.thephoenixcraft;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.attribute.IThreadContainer;
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class AutoThreads extends ListenerAdapter {
	public static final Pattern URL_PATTERN = Pattern.compile("(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]", Pattern.CASE_INSENSITIVE);

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if(event.getChannel().getIdLong() != Main.config.picvid) return;
		if(!(event.getChannel() instanceof IThreadContainer channel)) return;

		if(event.getMessage().getAttachments().isEmpty() && !URL_PATTERN.asPredicate().test(event.getMessage().getContentRaw())) {
			event.getMessage().delete().queue();
			return;
		}

		channel.createThreadChannel(
				StringUtils.abbreviate(
						getThreadName(event.getMessage()),
						ThreadChannel.MAX_NAME_LENGTH
				),
				event.getMessageIdLong()
		).queue();
	}

	public String getThreadName(Message message) {
		var content = message.getContentRaw()
				.replaceAll(URL_PATTERN.pattern(), "")
				.replaceAll("<a?:\\w+:(\\d+)>", "")
				.trim();

		if(content.isEmpty()) {
			if(!message.getEmbeds().isEmpty()) {
				var title = message.getEmbeds().get(0).getTitle();
				if (title != null) return title;
			}

			return message.getAuthor().getGlobalName();
		}

		return content;
	}
}

