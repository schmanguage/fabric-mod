package net.schmanguage.mixin;

import net.minecraft.network.chat.contents.PlainTextContents;
import net.schmanguage.Schmanguage;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlainTextContents.LiteralContents.class)
public class LiteralContentsMixin {
    @Mutable
    @Shadow @Final private String text;

    @Inject(at = @At("RETURN"), method = "<init>")
    void init(String text, CallbackInfo ci) {
        if(Schmanguage.isEnabled)
            this.text = Schmanguage.translate(text);
    }
}
