package com.github.jenya705.mcapi.standalone;

import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.application.guice.ApplicationBuilder;

import java.util.Scanner;

/**
 * @author Jenya705
 */
public class StandaloneLoader {

    public static void main(String[] args) {
        ServerApplication application = new ApplicationBuilder()
                .defaultLayers()
                .build();
        application.start();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String command = scanner.nextLine();
            if (command.equals("stop")) break;
        }
    }

}
