package com.example.Q3ByReflection;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class Q3ByReflectionApplication {

	public static void main(String[] args) {
		SpringApplication.run(Q3ByReflectionApplication.class, args);
	}

}

@Component
class AppRunner implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		processContract(args[0]);
	}

	public void processContract(String type) {
		try {
			Class<? extends Action> actionClass = Class.forName("com.example.Q3ByReflection." + type+"Action").asSubclass(Action.class);
			Action a = actionClass.newInstance();
			a.process();
		} catch (ClassNotFoundException e) {
			System.out.println("Type not found!");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}
}

interface Action {
	void process ();
}

class DSLAction implements Action {

	@Override
	public void process() {
		System.out.println("dsl processed !");
	}
}

class ElectricityAction implements Action {

	@Override
	public void process() {
		System.out.println("Electricity processed!");
	}
}

