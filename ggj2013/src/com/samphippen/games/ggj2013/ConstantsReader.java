package com.samphippen.games.ggj2013;

import java.io.FileInputStream;
import java.util.Scanner;

public class ConstantsReader {
    public static void readConstants(FileInputStream fileStream) {
        Scanner scanner = new Scanner(fileStream);
        while (scanner.hasNext()) {
            String key = scanner.next();
            double value = Double.parseDouble(scanner.next());
            Constants.sConstants.put(key, value);
        }
    }
}
