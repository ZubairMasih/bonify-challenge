package com.example.Q3ByRuleEngine;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Q3ByRuleEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(Q3ByRuleEngineApplication.class, args);
	}

}

@Component
@Slf4j
class AppRunner implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		try{
			processContract(args[0]);

		}catch (IllegalArgumentException e){
			log.error(e.getMessage());
		}
	}

	public void processContract(String type) throws IllegalArgumentException {
		RuleEngine.process(type);
	}

}

interface Rule {
	boolean evaluate (String type);

	void process ();
}

class ElectricityRule implements Rule {

	@Override
	public boolean evaluate(String type) {
		return "electricity".equalsIgnoreCase(type) || "elect".equalsIgnoreCase(type);
	}

	@Override
	public void process() {
		System.out.println("electricity processed!");
	}
}

class DSLRule implements Rule {

	@Override
	public boolean evaluate(String type) {
		return "dsl".equalsIgnoreCase(type);
	}

	@Override
	public void process() {
		System.out.println("dsl processed !");
	}
}

class RuleEngine {

	static List<Rule> rules = new ArrayList<>();

	static {
		rules.add(new DSLRule());
		rules.add(new ElectricityRule());
	}

	static void process (String type) {
		Rule rule = rules.stream()
				.filter(r -> r.evaluate(type))
				.findFirst()
				.orElseThrow( () -> new IllegalArgumentException("type not supported yet!"));

		rule.process();
	}
}
