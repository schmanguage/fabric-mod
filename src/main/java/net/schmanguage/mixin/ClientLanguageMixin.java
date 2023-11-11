package net.schmanguage.mixin;

import net.minecraft.client.resources.language.ClientLanguage;
import net.minecraft.locale.Language;
import net.minecraft.server.packs.resources.Resource;
import net.schmanguage.Schmanguage;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Mixin(ClientLanguage.class)
public abstract class ClientLanguageMixin {
    @Shadow @Final private static Logger LOGGER;

    @Redirect(method = "loadFrom", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/language/ClientLanguage;appendFrom(Ljava/lang/String;Ljava/util/List;Ljava/util/Map;)V"))
    private static void appendFrom(String languageIdentifier, List<Resource> resourceStack, Map<String, String> map) {
        Schmanguage.isEnabled = languageIdentifier.equals("en_schm");

        for (Resource resource : resourceStack) {
            try {
                try (InputStream inputStream = resource.open()) {
                    Language.loadFromJson(inputStream, (key, value) -> {
                        if(languageIdentifier.equals("en_schm"))
                            Schmanguage.languageKeys.add(key);

                        map.put(key, value);
                    });
                }
            } catch (IOException iOException) {
                LOGGER.warn("Failed to load translations for {} from pack {}", languageIdentifier, resource.sourcePackId(), iOException);
            }
        }
    }
}
