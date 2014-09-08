package net.redwarp.tool.resizer;

import java.io.File;

import net.redwarp.tool.resizer.table.Operation;
import net.redwarp.tool.resizer.table.OperationStatus;
import net.redwarp.tool.resizer.worker.ImageScaler;
import net.redwarp.tool.resizer.worker.ScreenDensity;

public class FileProcessor {

	public interface FileProcessorStatusListener {
		void onSuccess();

		void onFailure(String msg);
	}

	private ImageScaler scaler;
	FileProcessorStatusListener listener;
	String fileName;

	public FileProcessor(String name, FileProcessorStatusListener l) {
		fileName = name;
		listener = l;
		if (name.endsWith(".png") || name.endsWith(".jpg")) {
			Operation operation = new Operation(new File(name));

			scaler = new ImageScaler(operation,
					ScreenDensity.getDefaultInputDensity()) {
				@Override
				protected void process(java.util.List<Operation> chunks) {
					for (Operation operation : chunks) {
						OperationStatus status = operation.getStatus();
						if (status == OperationStatus.FINISH) {
							if (listener != null) {
								listener.onSuccess();
							}
						} else if (status == OperationStatus.ERROR) {
							listener.onFailure(operation.getMessage());
						}
					}
				}
			};
		}
	}

	public void process() {
		if (scaler != null) {
			scaler.post();
		} else {
			if (listener != null) {
				listener.onFailure("processor for argument:" + fileName
						+ " is null");
			}
		}
	}

}
