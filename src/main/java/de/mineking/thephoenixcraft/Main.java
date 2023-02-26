package de.mineking.thephoenixcraft;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main {
	public final static Config config = Config.read("config");
	public static JDA jda;

	public static void main(String[] args) {
		jda = JDABuilder.createDefault(config.token)
				.enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
				.addEventListeners(new Pins(), new AutoThreads())
				.setStatus(OnlineStatus.ONLINE)
				.setActivity(Activity.playing("mit deinem Leben"))
				.build();
	}
}
