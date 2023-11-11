package net.schmanguage.mixin;

import net.minecraft.network.chat.contents.TranslatableContents;
import net.schmanguage.Schmanguage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(TranslatableContents.class)
public abstract class TranslatableContentsMixin {
    @Shadow @Final private String key;


    @ModifyVariable(method = "decompose", at = @At(value = "STORE", ordinal = 0), ordinal = 0)
    String translate(String string) {
        if(Schmanguage.languageKeys.contains(this.key) || !Schmanguage.isEnabled)
            return string;

        return Schmanguage.translate(string);
    }
}
