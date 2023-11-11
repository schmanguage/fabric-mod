package net.schmanguage;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Schmanguage implements ModInitializer {
    public static Set<String> languageKeys = new CopyOnWriteArraySet<>();
    public static boolean isEnabled = false;
    private static final Pattern PATTERN = Pattern.compile("(§[0-9a-fklmnor]|\\b)([b-df-hj-np-tv-xz]*[aeiouy])(\\w*)\\b", Pattern.CASE_INSENSITIVE);

    public static String translate(String text) {
        if (text.trim().length() < 2)
            return text;

        Matcher matcher = PATTERN.matcher(text);
        if (!matcher.find()) {
            return text;
        }

        StringBuilder result = new StringBuilder();
        do {
            String word = modifyWord(matcher.group(2), isUpperCase(matcher.group(2)+matcher.group(3)));
            if ((word+matcher.group(3)).length() <= 5) {
                continue;
            }
            matcher.appendReplacement(result, "$1" + word + "$3");
        } while (matcher.find());
        matcher.appendTail(result);

        return result.toString();
    }

    private static String modifyWord(String word, boolean isUpperCase) {
        if (Character.isLowerCase(word.charAt(0))) {
            return word;
        }

        char vowel = word.charAt(word.length()-1);
        if (isUpperCase) {
            return "SCHM" + vowel;
        }
        return "Schm" + Character.toLowerCase(vowel);
    }

    private static boolean isUpperCase(String s) {
        return s.toUpperCase().equals(s);
    }

    @Override
    public void onInitialize() {
        FabricLoader.getInstance().getModContainer("schmanguage").ifPresent(modContainer -> ResourceManagerHelper.registerBuiltinResourcePack(new ResourceLocation("schmanguage", "schmanguage"), modContainer, Component.literal("Schmanguage"), ResourcePackActivationType.ALWAYS_ENABLED));
    }
}
