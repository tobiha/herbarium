package at.thammerer.herbarium.spring;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.io.ResourceLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class SpringServiceApplication extends SpringApplication {
	protected static final AtomicBoolean entered = new AtomicBoolean(false);
	protected static final int EXIT_CODE_ERROR = 70; // inspired by BSD's EX_SOFTWARE error code

	private static Object[] adjustSources(Object... sources) {
		System.setProperty("com.rise.spring.boot.SpringServiceApplication.standalone", "true");
		return sources;
	}

	@SuppressWarnings("unused")
	public SpringServiceApplication(ResourceLoader resourceLoader, Object... sources) {
		super(resourceLoader, adjustSources(sources));
		setDefaults();
	}

	@SuppressWarnings("unused")
	public SpringServiceApplication(Object... sources) {
		super(adjustSources(sources));
		setDefaults();
	}

	private void setDefaults() {
		this.setShowBanner(false);
		this.addListeners(new ApplicationListener<ApplicationEvent>() {
			@Override
			public void onApplicationEvent(ApplicationEvent event) {
				if (event instanceof ApplicationFailedEvent) {
					exitOnFailed((ApplicationFailedEvent) event);
				}
				if (event instanceof ApplicationEnvironmentPreparedEvent) {
					ConfigurableEnvironment env = ((ApplicationEnvironmentPreparedEvent) event).getEnvironment();
					configureLogging(env);
					configureInfoProperties(env);
				}
			}
		});
	}


	@Override
	public ConfigurableApplicationContext run(String... args) {
		if (entered.get() && System.getProperty("os.name").startsWith("Windows")) {
			// Assume call is stop request from Apache commons-daemon/procrun
			System.exit(0);
		}
		entered.set(true);

		try {
			return super.run(args);
		} catch (Exception e) {
			LoggerFactory.getLogger(SpringServiceApplication.class).error("Startup failed, exiting", e);

			System.exit(EXIT_CODE_ERROR);
			return null;
		}
	}

	@SuppressWarnings("unused")
	public static void configureWebApp(SpringApplicationBuilder springApplicationBuilder) {
		configure(springApplicationBuilder);
	}

	public static SpringApplicationBuilder configure(SpringApplicationBuilder springApplicationBuilder) {
		springApplicationBuilder.showBanner(false);
		springApplicationBuilder.listeners(new ApplicationListener<ApplicationEvent>() {
			@Override
			public void onApplicationEvent(ApplicationEvent event) {
				if (event instanceof ApplicationFailedEvent) {
					exitOnFailed((ApplicationFailedEvent) event);
				} else if (event instanceof ApplicationEnvironmentPreparedEvent) {
					ConfigurableEnvironment env = ((ApplicationEnvironmentPreparedEvent) event).getEnvironment();
					configureLogging(env);
					configureInfoProperties(env);
				}
			}
		});
		return springApplicationBuilder;
	}

	protected static void exitOnFailed(ApplicationFailedEvent event) {
		LoggerFactory.getLogger(SpringServiceApplication.class).error("ApplicationFailedEvent", event.getException());
		System.exit(EXIT_CODE_ERROR);
	}

	protected static void configureLogging(Environment environment) {
		String logFile = System.getProperty("LOG_FILE");
		if (logFile == null) {
			logFile = System.getenv("LOG_FILE");
		}

		if (logFile != null) {
			ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger)
				LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
			root.detachAppender("STDOUT");
			System.out.println("Logging to '" + logFile + "'");
		} else {
			LoggerFactory.getLogger(SpringServiceApplication.class)
				.info("No LOG_FILE set, logging to STDOUT");
		}

	}

	protected static void configureInfoProperties(ConfigurableEnvironment env) {
		SystemInformation systemInformation = new SystemInformation();

		Map<String, Object> infoMap = new HashMap<>();
		infoMap.put("info.fileTimestamp", systemInformation.getDeploymentDateAsString());
		infoMap.put("info.hostname", systemInformation.getHostname());

		env.getPropertySources().addFirst(new MapPropertySource("INFO_MAP", infoMap));
	}


}
