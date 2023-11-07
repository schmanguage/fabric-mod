package com.tomate.schmoahify.mixin;

import net.minecraft.network.chat.contents.LiteralContents;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(LiteralContents.class)
public class LiteralContentsMixin {

    @Mutable
    @Shadow @Final private String text;

    @Inject(at = @At("RETURN"), method = "<init>")
    void text(String text, CallbackInfo ci) {
        if(text.trim().length() < 2)
            return;

        var words = text.split(" ");

        var modifiedWords = Arrays.stream(words).map((word) -> {
            if (word.length() < 2) {
                return word;
            }

            var uppercaseText = word.toUpperCase();

            if (uppercaseText.startsWith("SCH")) {
                return "Schm" + word.substring(3);
            }
            else if (uppercaseText.charAt(0) != word.charAt(0))
                return word;

            // Check if the word begins with a vowel
            var modifiedWord = word;
            while(!startsWithVowel(modifiedWord) && modifiedWord.length() > 2) {
                modifiedWord = modifiedWord.substring(1);
            }

            if(word.length() == 2)
                return word;

            return "Schm" + modifiedWord;
        });

        // Join words together again
        this.text = modifiedWords.reduce((word1, word2) -> word1 + " " + word2).orElse("");
    }

    @Unique
    boolean startsWithVowel(String string) {
        return "AEIOUaeiou".indexOf(string.charAt(0)) != -1;
    }
}
