package org.encuestame.core.integration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;

public class MailMessageService {
	public Message<?> processMessage(String bites) {
		MessageBuilder<?> messageBuilder = MessageBuilder.withPayload(bites);
		Pattern p = Pattern
				.compile("(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
		Matcher matcher = p.matcher(bites);
		if (matcher.find()) {
			String url = matcher.group(0);
			messageBuilder = MessageBuilder.withPayload(url);
			messageBuilder.setHeader("MESSAGE_TYPE", "URL");
		} else {
			messageBuilder = MessageBuilder.withPayload(bites);
			messageBuilder.setHeader("MESSAGE_TYPE", "TEXT");
		}
		return messageBuilder.build();

	}
}