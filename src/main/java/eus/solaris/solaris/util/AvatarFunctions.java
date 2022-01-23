/***********************************************************************************
* MIT License
* 
* Copyright (c) 2021 boringdesigners
* 
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
* 
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
***********************************************************************************/

package eus.solaris.solaris.util;

public class AvatarFunctions {

  private AvatarFunctions(){
  }

  public static int getHashCode(String name){
    int hash = 0;
    for (int i = 0; i < name.length(); i++){
      int character = name.charAt(i);
      hash = ((hash << 5)-hash)+character;
    }
    return Math.abs(hash);
  }

  public static int getModulus(int num, int max) {
    return num % max;
  }

  public static int getDigit(int number, int ntn)  {
    return (int) Math.floor((number/ Math.pow(10, ntn)) % 10);
  }

  public static Boolean getBoolean(int number, int ntn) {
    return (getDigit(number, ntn) % 2) == 0;
  }

  public static Double getAngle(Double x, Double y) {
    return Math.atan2(y, x) * 180 / Math.PI;
  }

  public static int getUnit(int number, int range, int index) {
    int value = number % range;
  
    if(index > 0 && ((getDigit(number, index) % 2) == 0)) {
      return -value;
    } else return value;
  }

  public static String getRandomColor(int number, String[] colors, int range) {
    return colors[number % range];
  }
  
  public static String getContrast (String hexcolor) {

    // If a leading # is provided, remove it
    if (hexcolor.substring(0, 1).equals("#")) {
      hexcolor = hexcolor.substring(1);
    }
  
    // Convert to RGB value
    int r = Integer.parseInt(hexcolor.substring(0,2),16);
    int g = Integer.parseInt(hexcolor.substring(2,4),16);
    int b = Integer.parseInt(hexcolor.substring(4,6),16);
  
    // Get YIQ ratio
    int yiq = ((r * 299) + (g * 587) + (b * 114)) / 1000;
  
    // Check contrast
    return (yiq >= 128) ? "#000000" : "#FFFFFF";
  }

}
