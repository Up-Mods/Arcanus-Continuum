package dev.cammiescorner.arcanus.common.component;

import dev.upcraft.sparkweave.api.color.Color;
import org.ladysnake.cca.api.v8.component.CardinalComponent;

import java.util.UUID;

public interface MagicColorComponent extends CardinalComponent {
	String SOURCE_ID_KEY = "SourceId";

	Color getColor();

	Color getPocketDimensionColor();

	UUID getSourceId();

	void setSourceId(UUID ownerId);

}
