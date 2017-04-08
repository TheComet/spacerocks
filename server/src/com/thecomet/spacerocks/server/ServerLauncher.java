package com.thecomet.spacerocks.server;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.thecomet.spacerocks.HeadlessSpaceRocks;
import com.thecomet.spacerocks.SpaceRocks;

public class ServerLauncher {
	public static void main (String[] arg) {
		HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
		new HeadlessApplication(new HeadlessSpaceRocks(), config);
	}
}
