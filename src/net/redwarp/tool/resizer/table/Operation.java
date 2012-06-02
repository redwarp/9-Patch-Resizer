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
package net.redwarp.tool.resizer.table;

import java.io.File;

public class Operation {
	private volatile OperationStatus status;
	private File file;

	public Operation(File f) {
		this.file = f;
		this.status = OperationStatus.PENDING;
	}

	public OperationStatus getStatus() {
		return this.status;
	}

	public File getFile() {
		return this.file;
	}

	public void setStatus(OperationStatus status) {
		this.status = status;
	}
}
