package dev.cammiescorner.arcanus.common.components;

import dev.upcraft.sparkweave.api.color.Color;
import org.ladysnake.cca.api.v3.component.Component;

import java.util.UUID;

public interface MagicColorComponent extends Component {

	String SOURCE_ID_KEY = "SourceId";

	Color getColor();

	Color getPocketDimensionColor();

	UUID getSourceId();

	void setSourceId(UUID ownerId);

}
