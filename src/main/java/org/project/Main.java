package org.project;
import io.github.cdimascio.dotenv.Dotenv;
public class Main {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().ignoreIfMalformed().load();

    }
}