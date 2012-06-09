/*
 * Copyright 2012 redwarp
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.redwarp.tool.resizer.worker;

import java.util.ArrayList;
import java.util.List;

public class ScreenDensity {
	private float density;
	private String name;

	private static List<ScreenDensity> list = null;

	public static final int LDPI = 0;
	public static final int MDPI = 1;
	public static final int HDPI = 2;
	public static final int XHDPI = 3;

	private ScreenDensity(String name, float density) {
		this.density = density;
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name + String.format(" (%.2f)", this.density);
	}

	public String getName() {
		return this.name;
	}

	public float getDensity() {
		return this.density;
	}

	public static ScreenDensity getDensity(int density)
			throws UnsupportedDensityException {
		if (density == LDPI) {
			return new ScreenDensity("ldpi", 0.75f);
		}
		if (density == MDPI) {
			return new ScreenDensity("mdpi", 1f);
		}
		if (density == HDPI) {
			return new ScreenDensity("hdpi", 1.5f);
		}
		if (density == XHDPI) {
			return new ScreenDensity("xhdpi", 2f);
		}
		throw new UnsupportedDensityException();
	}

	public static List<ScreenDensity> getSupportedScreenDensity() {
		if (list == null) {
			list = new ArrayList<ScreenDensity>();
			list.add(getDensity(LDPI));
			list.add(getDensity(MDPI));
			list.add(getDensity(HDPI));
			list.add(getDensity(XHDPI));
		}
		return list;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ScreenDensity) {
			return this.name.equals(((ScreenDensity) obj).getName());
		}
		return false;
	}
}
