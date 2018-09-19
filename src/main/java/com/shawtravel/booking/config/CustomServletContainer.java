package com.shawtravel.booking.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.stereotype.Component;

@Component
public class CustomServletContainer implements EmbeddedServletContainerCustomizer {
	@Autowired
	private ApplicationConfiguration config;

	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		container.setPort(config.getServerPort());
	}

}
