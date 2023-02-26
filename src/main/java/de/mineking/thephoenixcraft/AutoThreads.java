package de.mineking.thephoenixcraft;

import net.dv8tion.jda.api.entities.channel.attribute.IThreadContainer;
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.lang3.StringUtils;

public class AutoThreads extends ListenerAdapter {
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if(event.getChannel().getIdLong() != Main.config.picvid) {
			return;
		}

		if(!(event.getChannel() instanceof IThreadContainer channel)) {
			return;
		}

		channel.createThreadChannel(StringUtils.abbreviate(event.getMessage().getContentRaw(), ThreadChannel.MAX_NAME_LENGTH), event.getMessageIdLong()).queue();
	}
}
