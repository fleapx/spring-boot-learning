package com.yangbingdong.springboot.common.config.shutdown;

import io.undertow.server.HandlerWrapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 优雅停机配置类，通过注册{@link WebServerFactoryCustomizer}自定义关机动作
 *
 * @author ybd
 * @date 18-4-19
 * @contact yangbingdong1994@gmail.com
 */
@Configuration
@ConditionalOnClass(HandlerWrapper.class)
public class GracefulShutdownConfiguration {

	@Bean
	public GracefulShutdownWrapper gracefulShutdownWrapper() {
		return new GracefulShutdownWrapper();
	}

	@Bean
	public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> gracefulWebServerFactoryCustomizer() {
		return factory -> {
			if (factory instanceof UndertowServletWebServerFactory) {
				UndertowServletWebServerFactory undertowServletWebServerFactory = (UndertowServletWebServerFactory) factory;
				undertowServletWebServerFactory
						.addDeploymentInfoCustomizers(deploymentInfo ->
								deploymentInfo.addOuterHandlerChainWrapper(gracefulShutdownWrapper()));
//				undertowServletWebServerFactory.addBuilderCustomizers(builder -> builder.setServerOption(UndertowOptions.ENABLE_STATISTICS, true));
			}
		};
	}

	@Bean
	public GracefulShutdownListener gracefulShutdown() {
		return new GracefulShutdownListener(gracefulShutdownWrapper());
	}
}
