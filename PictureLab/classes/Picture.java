import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List
/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture 
{
  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture ()
  {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName)
  {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param height the height of the desired picture
   * @param width the width of the desired picture
   */
  public Picture(int height, int width)
  {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   * @param copyPicture the picture to copy
   */
  public Picture(Picture copyPicture)
  {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image)
  {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString()
  {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  
  /** Method to set the blue to 0 */
  public void zeroBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setBlue(0);
      }
    }
  }
  

  /**
   * Method that keeps all blue values and sets red and green values to 0
   */
  public void keepOnlyBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setRed(0);
        pixelObj.setGreen(0);
      }
    }
  }


  /**
   * <Ethod that negates all colors
   */
  public void negate()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setRed(255 - pixelObj.getRed());
        pixelObj.setGreen(255 - pixelObj.getGreen());
        pixelObj.setBlue(255 - pixelObj.getBlue());
      }
    }
  }


  /**
   * Copies a region of the spcified source Picture object into this picture object at the 
   * specified location
   */
  public void cropAndCopy( Picture sourcePicture, int startSourceRow, int endSourceRow, 
             int startSourceCol, int endSourceCol, int startDestRow, int startDestCol )
  {
      Pixel[][] source_pixels = sourcePicture.getPixels2D(); 
      Pixel[][] dest_pixels = this.getPixels2D();
      for (int i = startSourceRow; i <= endSourceRow; i++)
      {
          for (int j = startSourceCol; j <= endSourceRow; j++)
          {
              Color source_color = source_pixels[i][j].getColor();
              dest_pixels[i - startSourceRow + startDestRow][j - startSourceCol + startDestCol].setColor(source_color);
          }
      }
  }

  /**
   * MEthod that applies a greyscale (set color values to averages of prior values)
   */
  public void grayscale()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        int red = pixelObj.getRed();
        int green = pixelObj.getGreen();
        int blue = pixelObj.getBlue();
        int average = (red + green + blue) / 3;
        
        pixelObj.setRed(average);
        pixelObj.setGreen(average);
        pixelObj.setBlue(average);
        
      }
    }
  }

    /** Method that mirrors the picture around a 
    * vertical mirror in the center of the picture
    * from left to right */
  public void mirrorVertical()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; col < width / 2; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
  
  /** Mirror just part of a picture of a temple */
  public void mirrorTemple()
  {
    int mirrorPoint = 276;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int count = 0;
    Pixel[][] pixels = this.getPixels2D();
    
    // loop through the rows
    for (int row = 27; row < 97; row++)
    {
      // loop from 13 to just before the mirror point
      for (int col = 13; col < mirrorPoint; col++)
      {
        
        leftPixel = pixels[row][col];      
        rightPixel = pixels[row]                       
                         [mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
  }
  
  /** copy from the passed fromPic to the
    * specified startRow and startCol in the
    * current picture
    * @param fromPic the picture to copy from
    * @param startRow the start row to copy to
    * @param startCol the start col to copy to
    */
  public void copy(Picture fromPic, 
                 int startRow, int startCol)
  {
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = 0, toRow = startRow; 
         fromRow < fromPixels.length &&
         toRow < toPixels.length; 
         fromRow++, toRow++)
    {
      for (int fromCol = 0, toCol = startCol; 
           fromCol < fromPixels[0].length &&
           toCol < toPixels[0].length;  
           fromCol++, toCol++)
      {
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }   
  }

  /** Method to create a collage of several pictures */
  public void createCollage()
  {
     Picture canvas = new Picture(1700, 1620);
     Picture original = new Picture("collage_base.jpg");
     canvas.copy(original, 0, 0);
     
     Picture negate = new Picture("collage_base.jpg");
     negate.negate();
     canvas.copy(negate, 0, 405);
     
     Picture mirrorHorizontal_negate = new Picture(negate);
     mirrorHorizontal_negate.mirrorHorizontalTopToBottom();
     canvas.copy(mirrorHorizontal_negate, 0, 810);
     
     Picture grayscale_negative_mirrorHorizontal = new Picture(mirrorHorizontal_negate);
     grayscale_negative_mirrorHorizontal.grayscale();
     canvas.copy(grayscale_negative_mirrorHorizontal, 0, 1215);
     
     Picture grayscale_mirrorVertical = new Picture(original);
     grayscale_mirrorVertical.grayscale();
     grayscale_mirrorVertical.mirrorVertical();
     canvas.copy(grayscale_mirrorVertical, 600, 0);
     
     Picture grayscale_mirrorVert_mirrorHoriTopBottom = new Picture(grayscale_mirrorVertical);
     canvas.explore();
  }
  
  
  /** Method to show large changes in color 
    * @param edgeDist the distance for finding edges
    */
  public void edgeDetection(int edgeDist)
  {
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    Color rightColor = null;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; 
           col < pixels[0].length-1; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][col+1];
        rightColor = rightPixel.getColor();
        if (leftPixel.colorDistance(rightColor) > 
            edgeDist)
          leftPixel.setColor(Color.BLACK);
        else
          leftPixel.setColor(Color.WHITE);
      }
    }
  }
  
  /**
  * Mirros a picture around a mirror placed vertially right to left
  */
  public void mirrorVerticalRightToLeft()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; col < width / 2; col++)
      {
        rightPixel = pixels[row][col];
        leftPixel = pixels[row][width - 1 - col];
        leftPixel.setColor(leftPixel.getColor());
      }
    } 
  }


  /**
  * Mirrors a picture around a mirror placed horizontally top to bottom
  */
  public void mirrorHorizontalTopToBottom()
  {
    Pixel[][] pixels = this.getPixels2D();
    int width = pixels[0].length;
    int height = pixels.length;
    for (int row = 0; row < height/2; row++)
    {
        for (int col = 0; col < width; col++)
        {
            pixels[height - 1 -row][col].setColor(pixels[row][col].getColor());
        }
    }
  }

  /**
   * Mirros a picture aorund the mirrior placed horizontally bottom to top
   */
  public void mirrorHorizontalBottomToTop()
  {
    Pixel[][] pixels = this.getPixels2D();
    int width = pixels[0].length;
    int height = pixels.length;
    for (int row = 0; row < height/2; row++)
    {
        for (int col = 0; col < width; col++)
        {
            pixels[height - 1 - row][col].setColor(pixels[row][col].getColor());
        }
    }
  }

    /* Main method for testing - each class in Java can have a main 
   * method 
   */
  public static void main(String[] args) 
  {
    Picture beach = new Picture("beach.jpg");
    beach.explore();
    beach.zeroBlue();
    beach.explore();
  }
  
} // this } is the end of class Picture, put all new methods before this
