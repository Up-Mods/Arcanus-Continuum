package dev.upcraft.examplemod.client;

import com.google.auto.service.AutoService;
import dev.upcraft.sparkweave.api.entrypoint.ClientEntryPoint;
import dev.upcraft.sparkweave.api.platform.ModContainer;

@AutoService(ClientEntryPoint.class)
public class ExampleModClient implements ClientEntryPoint {
	@Override
	public void onInitializeClient(ModContainer mod) {

	}
}
