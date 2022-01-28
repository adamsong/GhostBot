package ml.adamsogm.ghostbot;

import discord4j.common.JacksonResources;
import discord4j.discordjson.json.ApplicationCommandRequest;
import discord4j.rest.RestClient;
import discord4j.rest.service.ApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Handle the management of discord commands from JSON files
 * Original found in the Discord4J examples https://github.com/Discord4J/example-projects/blob/master/gradle-simple-bot/src/main/java/com/novamaday/d4j/gradle/simplebot/GlobalCommandRegistrar.java
 */
public class GlobalCommandRegistrar {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	private final RestClient restClient;
	
	public GlobalCommandRegistrar(RestClient restClient) {
		this.restClient = restClient;
	}
	
	private static List<String> getCommandsJson() throws IOException {
		//The name of the folder the commands json is in, inside our resources folder
		final String commandsFolderName = "commands/";
		
		//Get the folder as a resource
		URL url = GlobalCommandRegistrar.class.getClassLoader().getResource(commandsFolderName);
		Objects.requireNonNull(url, commandsFolderName + " could not be found");
		
		File folder;
		try {
			folder = new File(url.toURI());
		} catch (URISyntaxException e) {
			folder = new File(url.getPath());
		}
		
		//Get all the files inside this folder and return the contents of the files as a list of strings
		List<String> list = new ArrayList<>();
		File[] files = Objects.requireNonNull(folder.listFiles(), folder + " is not a directory");
		
		for (File file : files) {
			String resourceFileAsString = getResourceFileAsString(commandsFolderName + file.getName());
			list.add(resourceFileAsString);
		}
		return list;
	}
	
	/* The two below methods are boilerplate that can be completely removed when using Spring Boot */
	
	/**
	 * Gets a specific resource file as String
	 *
	 * @param fileName The file path omitting "resources/"
	 * @return The contents of the file as a String, otherwise throws an exception
	 */
	private static String getResourceFileAsString(String fileName) throws IOException {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		try (InputStream resourceAsStream = classLoader.getResourceAsStream(fileName)) {
			if (resourceAsStream == null) return null;
			try (InputStreamReader inputStreamReader = new InputStreamReader(resourceAsStream);
			     BufferedReader reader = new BufferedReader(inputStreamReader)) {
				return reader.lines().collect(Collectors.joining(System.lineSeparator()));
			}
		}
	}
	
	//Since this will only run once on startup, blocking is okay.
	protected void registerCommands() throws IOException {
		//Create an ObjectMapper that supports Discord4J classes
		final JacksonResources d4jMapper = JacksonResources.create();
		
		// Convenience variables for the sake of easier to read code below
		final ApplicationService applicationService = restClient.getApplicationService();
		final Long applicationId = restClient.getApplicationId().block();
		if(applicationId == null) {
			LOGGER.error("Unable to register slash commands, application ID not found.");
			return;
		}
		
		//Get our commands json from resources as command data
		List<ApplicationCommandRequest> commands = new ArrayList<>();
		for (String json : getCommandsJson()) {
			ApplicationCommandRequest request = d4jMapper.getObjectMapper()
				.readValue(json, ApplicationCommandRequest.class);
			
			commands.add(request); //Add to our array list
		}

        /* Bulk overwrite commands. This is now idempotent, so it is safe to use this even when only 1 command
        is changed/added/removed
        */
		applicationService.bulkOverwriteGlobalApplicationCommand(applicationId, commands)
			.doOnNext(ignore -> LOGGER.debug("Successfully registered Global Commands"))
			.doOnError(e -> LOGGER.error("Failed to register global commands", e))
			.subscribe();
	}
}
