package net.schmanguage;

import java.util.Arrays;

public class Schmanguage {
    public static String translate(String text) {
        if(text.trim().length() < 2)
            return text;

        var words = text.split(" ");

        var modifiedWords = Arrays.stream(words).map((word) -> {
            if (word.length() < 2)
                return word;

            var uppercaseWord = word.toUpperCase();
            var lowercaseWord = word.toLowerCase();

            // Check if the word is an actual word
            if(uppercaseWord.equals(lowercaseWord))
                return word;

            var isUppercase = word.equals(uppercaseWord);

            // Is the first letter uppercase?
            if (uppercaseWord.charAt(0) != word.charAt(0))
                return word;

            var modifiedWord = word;
            while(!startsWithVowel(modifiedWord) && modifiedWord.length() > 2) {
                modifiedWord = modifiedWord.substring(1);
            }

            if(modifiedWord.length() == 2)
                return word;

            if(isUppercase)
                return "SCHM" + modifiedWord;

            String modifiedWordWithFirstLetterLowercase = Character.toLowerCase(modifiedWord.charAt(0)) + modifiedWord.substring(1);
            return "Schm" + modifiedWordWithFirstLetterLowercase;
        });

        // Join words together again
        return modifiedWords.reduce((word1, word2) -> word1 + " " + word2).orElse("");
    }

    private static boolean startsWithVowel(String string) {
        return "AEIOUaeiou".indexOf(string.charAt(0)) != -1;
    }
}
