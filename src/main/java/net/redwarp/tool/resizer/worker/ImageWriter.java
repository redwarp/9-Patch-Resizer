/*
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
 *
 * Copyright 2013-2015 Redwarp
 */

package net.redwarp.tool.resizer.worker;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.stream.FileImageOutputStream;

public class ImageWriter {

  public static void write(BufferedImage outputImage, Output output, File outputFile)
      throws IOException {
    if (output == Output.JPG) {
      // Need to strip alpha;
      BufferedImage
          img =
          new BufferedImage(outputImage.getWidth(), outputImage.getHeight(),
                            BufferedImage.TYPE_INT_RGB);
      Graphics2D g2d = img.createGraphics();
      g2d.drawImage(outputImage, 0, 0, null);
      g2d.dispose();
      outputImage = img;

      Iterator<javax.imageio.ImageWriter> itor = ImageIO.getImageWritersByFormatName("jpeg");
      javax.imageio.ImageWriter writer = itor.next();
      ImageWriteParam imageWriteParam = writer.getDefaultWriteParam();
      imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
      imageWriteParam.setCompressionQuality(0.9f);

      FileImageOutputStream outputStream = new FileImageOutputStream(outputFile);
      writer.setOutput(outputStream);
      IIOImage image = new IIOImage(outputImage, null, null);
      writer.write(null, image, imageWriteParam);
      writer.dispose();

    } else {
      ImageIO.write(outputImage, output.getFormat(), outputFile);
    }
  }

  public static void copy(File inputFile, File outputFile) throws IOException {
    if (inputFile != null && outputFile != null) {
      FileChannel sourceChannel = null;
      FileChannel destChannel = null;
      try {
        sourceChannel = new FileInputStream(inputFile).getChannel();
        destChannel = new FileOutputStream(outputFile).getChannel();
        destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
      } finally {
        if (sourceChannel != null) {
          sourceChannel.close();
        }
        if (destChannel != null) {
          destChannel.close();
        }
      }
    }
  }
}
