package com.betterreads;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import com.betterreads.author.Author;
import com.betterreads.author.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import connection.DataStaxAstraProperties;

import javax.annotation.PostConstruct;

/**
 * Main application class with main method that runs the Spring Boot app
 */

@SpringBootApplication
@EnableConfigurationProperties(DataStaxAstraProperties.class)
public class BookTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookTrackerApplication.class, args);
	}

	@Autowired
	AuthorRepository authorRepository;

	@Value("${datadump.location.author}")
	private String authorDumpInformation;

	@Value("${datadump.location.works}")
	private String worksDumpInformation;

	private void initAuthors(){
		Path path=Paths.get(authorDumpInformation);
		try(Stream<String> line=Files.lines(path)){

		}catch(IOException e){
			e.printStackTrace();
		}

	}

	private void initWorks(){

	}

	@PostConstruct
	public void start(){
		initAuthors();
		initWorks();
	}

	/**
	 * This is necessary to have the Spring Boot app use the Astra secure bundle
	 * to connect to the database
	 */
	@Bean
	public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties astraProperties) {
		Path bundle = astraProperties.getSecureConnectBundle().toPath();
		return builder -> builder.withCloudSecureConnectBundle(bundle);
	}


}
