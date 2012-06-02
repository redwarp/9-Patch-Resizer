/*
 * Copryright (C) 2012 Redwarp
 * 
 * This file is part of 9Patch Resizer.
 * 9Patch Resizer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * 9Patch Resizer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with 9Patch Resizer.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.redwarp.tool.resizer;

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
