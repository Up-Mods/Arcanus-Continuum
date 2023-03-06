package dev.cammiescorner.arcanuscontinuum.common.util;

import com.google.gson.*;
import com.mojang.util.UUIDTypeAdapter;
import net.minecraft.util.JsonHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.UUID;

public class SupporterData {
	public static final Gson GSON = new GsonBuilder().registerTypeAdapter(Supporter.class, new Supporter.Serializer()).create();
	public final Supporter[] supporters = {};

	public static SupporterData fromJson(InputStream stream) throws IOException {
		try(Reader reader = new InputStreamReader(stream)) {
			return GSON.fromJson(reader, SupporterData.class);
		}
	}

	public record Supporter(String username, UUID uuid, int magicColour) {
		public static class Serializer implements JsonDeserializer<Supporter> {
			@Override
			public Supporter deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
				JsonObject json = jsonElement.getAsJsonObject();
				String username = JsonHelper.getString(json, "username");
				UUID uuid = UUIDTypeAdapter.fromString(JsonHelper.getString(json, "uuid"));
				int magicColour = Integer.decode(JsonHelper.getString(json, "magic_color", "#68e1ff"));

				return new Supporter(username, uuid, magicColour);
			}
		}
	}
}
