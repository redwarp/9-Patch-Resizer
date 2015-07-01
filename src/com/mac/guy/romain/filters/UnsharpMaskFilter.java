/*
 * Copyright (c) 2007, Romain Guy
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   * Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above
 *     copyright notice, this list of conditions and the following
 *     disclaimer in the documentation and/or other materials provided
 *     with the distribution.
 *   * Neither the name of the TimingFramework project nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.mac.guy.romain.filters;

import java.awt.image.BufferedImage;

/**
 * @author Romain Guy <romain.guy@mac.com>
 */
public class UnsharpMaskFilter extends AbstractFilter {

  private final float amount;
  private final int radius;
  private final int threshold;

  public UnsharpMaskFilter() {
    this(0.7f, 2, 2);
  }

  public UnsharpMaskFilter(float amount, int radius, int threshold) {
    this.amount = amount;
    this.radius = radius;
    this.threshold = threshold;
  }

  public float getAmount() {
    return amount;
  }

  public int getRadius() {
    return radius;
  }

  public int getThreshold() {
    return threshold;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BufferedImage filter(BufferedImage src, BufferedImage dst) {
    int width = src.getWidth();
    int height = src.getHeight();

    if (dst == null) {
      dst = createCompatibleDestImage(src, null);
    }

    int[] srcPixels = new int[width * height];
    int[] dstPixels = new int[width * height];

    float[] kernel = GaussianBlurFilter.createGaussianKernel(radius);

    GraphicsUtilities.getPixels(src, 0, 0, width, height, srcPixels);
    // horizontal pass
    GaussianBlurFilter.blur(srcPixels, dstPixels, width, height, kernel, radius);
    // vertical pass
    //noinspection SuspiciousNameCombination
    GaussianBlurFilter.blur(dstPixels, srcPixels, height, width, kernel, radius);

    // blurred image is in srcPixels, we copy the original in dstPixels
    GraphicsUtilities.getPixels(src, 0, 0, width, height, dstPixels);
    // we compare original and blurred images,
    // and store the result in srcPixels
    sharpen(dstPixels, srcPixels, width, height, amount, threshold);

    // the result is now stored in srcPixels due to the 2nd pass
    GraphicsUtilities.setPixels(dst, 0, 0, width, height, srcPixels);

    return dst;
  }

  static void sharpen(int[] original, int[] blurred, int width, int height,
                      float amount, int threshold) {

    int index = 0;

    int srcR, srcB, srcG;
    int dstR, dstB, dstG;

    amount *= 1.6f;

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int srcColor = original[index];
        srcR = (srcColor >> 16) & 0xFF;
        srcG = (srcColor >> 8) & 0xFF;
        srcB = (srcColor) & 0xFF;

        int dstColor = blurred[index];
        dstR = (dstColor >> 16) & 0xFF;
        dstG = (dstColor >> 8) & 0xFF;
        dstB = (dstColor) & 0xFF;

        if (Math.abs(srcR - dstR) >= threshold) {
          srcR = (int) (amount * (srcR - dstR) + srcR);
          srcR = srcR > 255 ? 255 : srcR < 0 ? 0 : srcR;
        }

        if (Math.abs(srcG - dstG) >= threshold) {
          srcG = (int) (amount * (srcG - dstG) + srcG);
          srcG = srcG > 255 ? 255 : srcG < 0 ? 0 : srcG;
        }

        if (Math.abs(srcB - dstB) >= threshold) {
          srcB = (int) (amount * (srcB - dstB) + srcB);
          srcB = srcB > 255 ? 255 : srcB < 0 ? 0 : srcB;
        }

        int alpha = srcColor & 0xFF000000;
        blurred[index] = alpha | (srcR << 16) | (srcG << 8) | srcB;

        index++;
      }
    }
  }
}
