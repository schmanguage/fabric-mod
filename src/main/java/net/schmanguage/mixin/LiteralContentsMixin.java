package net.schmanguage.mixin;

import net.schmanguage.Schmanguage;
import net.minecraft.network.chat.contents.LiteralContents;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LiteralContents.class)
public class LiteralContentsMixin {
    @Mutable
    @Shadow @Final private String text;

    @Inject(at = @At("RETURN"), method = "<init>")
    void text(String text, CallbackInfo ci) {
        this.text = Schmanguage.translate(text);
    }
}
