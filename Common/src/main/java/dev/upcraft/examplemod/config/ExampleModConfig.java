package dev.upcraft.examplemod.config;

import com.teamresourceful.resourcefulconfig.api.annotations.Config;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigInfo;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigOption;
import dev.upcraft.examplemod.ExampleMod;

@ConfigInfo(
        titleTranslation = "examplemod.title",
        links = {
                @ConfigInfo.Link(value = "https://upcraft.dev", icon = "globe", text = "Website", textTranslation = "examplemod.config.links.website"),
                @ConfigInfo.Link(value = "https://mods.upcraft.dev/discord", icon = "gamepad-2", text = "Discord", textTranslation = "examplemod.config.links.discord")
        }
)
@Config(ExampleMod.MOD_ID)
public class ExampleModConfig {

    @ConfigOption.Color
    @ConfigEntry(id = "color")
    public static int exampleColor = 0xFFFFFF;
}
