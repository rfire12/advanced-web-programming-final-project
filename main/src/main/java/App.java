package main;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        String path = "..";

        System.out.println("============================");
        System.out.println("WELCOME TO OUR FINAL PROJECT");
        System.out.println("============================");

        Runtime rt = Runtime.getRuntime();

        System.out.println("Running Configuration Server...");
        Process config = rt.exec("bash ../configurationserver/gradlew bootRun");
    }
}
