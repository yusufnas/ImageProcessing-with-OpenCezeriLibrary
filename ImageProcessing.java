/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgisleme;

import cezeri.image_processing.ImageProcess;
import static cezeri.image_processing.ImageProcess.pixelsToImageColor;
import cezeri.matrix.CMatrix;
import java.awt.image.BufferedImage;

/**
 *
 * @author YUSUFNAS
 */
public class ImageProcessing {

    public static void main(String[] args) {

        String path = "D:\\images\\picture_1.jpg";
        CMatrix cm = CMatrix.getInstance().imread(path).imresize(400, 400).imshow("Orijinal Resim");

        CMatrix cmred = RedChannel(cm);
        cmred.imhist("Red Hist");
        CMatrix cmgreen = GreenChannel(cm);
        cmgreen.imhist("Green Hist");
        CMatrix cmblue = BlueChannel(cm);
        cmblue.imhist();
        cmred = Fonc(cmred);
        cmgreen = Fonc(cmgreen);
        cmblue = Fonc(cmblue);

    }

    public static CMatrix Fonc(CMatrix cm) {
        CMatrix cm2 = CMatrix.getInstance().vector(0, 1, 255);
        cm2 = inversePower(4, cm2);
        cm = CMatrix.getInstance()
                .vector(0, 1, 255)
                .pow(2).multiplyScalar(3)
                .minus(cm2);

        return cm;
    }

    public static CMatrix inversePower(double x, CMatrix cm) {
        double[][] d = cm.toDoubleArray2D();
        int nr = d.length;
        int nc = d[0].length;
        double[][] ret = new double[nr][nc];
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                ret[i][j] = Math.pow(x, d[i][j]);
            }
        }
        cm = CMatrix.getInstance(ret);
        return cm;
    }

    public static CMatrix RedChannel(CMatrix cm) {
        int[][][] pixels = ImageProcess.imageToPixelsColorInt(cm.getImage());
        int[][][] d = new int[pixels.length][pixels[0].length][4];

        int r = pixels.length;
        int c = pixels[0].length;
        int[] oneDPixels = new int[r * c];

        int index = 0;
        for (int row = 0; row < r; row++) {
            for (int col = 0; col < c; col++) {
                oneDPixels[index] = ((pixels[row][col][0] << 24) & 0xFF000000)
                        | ((pixels[row][col][1] << 16) & 0x00FF0000);
                index++;
                d[row][col][0] = pixels[row][col][0];
                d[row][col][1] = pixels[row][col][1];

            }
        }
        BufferedImage image = pixelsToImageColor(d);
        CMatrix cmred = CMatrix.getInstance(image);
        return cmred;
    }

    public static CMatrix GreenChannel(CMatrix cm) {
        int[][][] pixels = ImageProcess.imageToPixelsColorInt(cm.getImage());
        int[][][] d = new int[pixels.length][pixels[0].length][4];

        int r = pixels.length;
        int c = pixels[0].length;
        int[] oneDPixels = new int[r * c];

        int index = 0;
        for (int row = 0; row < r; row++) {
            for (int col = 0; col < c; col++) {
                oneDPixels[index] = ((pixels[row][col][0] << 24) & 0xFF000000)
                        | ((pixels[row][col][2] << 8) & 0x0000FF00);
                index++;
                d[row][col][0] = pixels[row][col][0];
                d[row][col][2] = pixels[row][col][2];

            }
        }
        BufferedImage image = pixelsToImageColor(d);
        CMatrix cmred = CMatrix.getInstance(image);
        return cmred;
    }

    public static CMatrix BlueChannel(CMatrix cm) {
        int[][][] pixels = ImageProcess.imageToPixelsColorInt(cm.getImage());
        int[][][] d = new int[pixels.length][pixels[0].length][4];

        int r = pixels.length;
        int c = pixels[0].length;
        int[] oneDPixels = new int[r * c];

        int index = 0;
        for (int row = 0; row < r; row++) {
            for (int col = 0; col < c; col++) {
                oneDPixels[index] = ((pixels[row][col][0] << 16) & 0xFF000000)
                        | ((pixels[row][col][3]) & 0x000000FF);
                index++;
                d[row][col][0] = pixels[row][col][0];
                d[row][col][3] = pixels[row][col][3];

            }
        }
        BufferedImage image = pixelsToImageColor(d);
        CMatrix cmred = CMatrix.getInstance(image);
        return cmred;
    }

}
