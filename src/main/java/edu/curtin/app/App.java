package edu.curtin.app;

import edu.curtin.app.library.TownsInput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App
{
    private static final Logger logger = Logger.getLogger(App.class.getName());

    public static void main(String[] args) throws IOException { //NOPMD
        List<String> allowedKeywords = new ArrayList<>()
        {{
            add("town-founding");
            add("town-population");
            add("railway-construction");
            add("railway-duplication");
        }};

        TownsInput input = new TownsInput();
        while (System.in.available() == 0) {
            try {
                String[] parts = new String[3];
                String message = input.nextMessage();
                if (message != null) {
                    parts = message.split(" ");
                    if (parts.length != 3 || !allowedKeywords.contains(parts[0])) {
                        throw new IllegalArgumentException("Message must have 3 parts");
                    }

                }
                switch (parts[0]) {
                    case "town-founding":
                        break;
                    case "town-population":
                        break;
                    case "railway-construction":
                        break;
                    case "railway-duplication":
                        break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new AssertionError(e);
                }
            }
            catch (IllegalArgumentException e) {
                logger.log(Level.WARNING, "Illegal message");
            }
        }

    }
}
