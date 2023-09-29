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
		if (event.getChannel().getIdLong() != Main.config.picvid) {
			return;
		}

		ThreadChannel channel = (ThreadChannel) event.getChannel(); // Cast to ThreadChannel

		if (event.getMessage().getAttachments().isEmpty() && !URL_PATTERN.asPredicate().test(event.getMessage().getContentRaw())) {
			event.getMessage().delete().queue();
			return;
		}

		String threadName = getThreadName(event.getMessage());


		threadName = threadName.replaceAll("<a:\\w+:(\\d+)>", ""); // For animated emojis
		threadName = threadName.replaceAll("<:\\w+:(\\d+)>", ""); // For regular emojis


		threadName = StringUtils.abbreviate(threadName, ThreadChannel.MAX_NAME_LENGTH);

		channel.createThreadChannel(threadName, event.getMessageIdLong()).queue();
	}

	public String getThreadName(Message message) {
		var content = message.getContentRaw().replaceAll(URL_PATTERN.pattern(), "").trim();

		if (content.isEmpty()) {
			if (!message.getEmbeds().isEmpty()) {
				var title = message.getEmbeds().get(0).getTitle();

				if (title != null) {
					return title;
				}
			}

			return message.getAuthor().getGlobalName();
		}

		return content;
	}
}

