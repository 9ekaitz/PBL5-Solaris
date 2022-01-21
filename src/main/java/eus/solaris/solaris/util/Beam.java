package eus.solaris.solaris.util;


public class Beam {

  private static final int SIZE = 36;
  private static final String[] COLORS = {"#F9AA31","#FA8832","#FACC32","#FAEE32","#E3FA32","#C1FA32"};

  private Data generateData(String name, String[] colors) {
    Data data = new Data();
    
    int numFromName = Functions.getHashCode(name);
    int range = colors.length;
    String wrapperColor = Functions.getRandomColor(numFromName, colors, range);
    int preTranslateX = Functions.getUnit(numFromName, 10, 1);
    int wrapperTranslateX = (preTranslateX < 5 ? preTranslateX + SIZE / 9 : preTranslateX);
    int preTranslateY = Functions.getUnit(numFromName, 10, 2);
    int wrapperTranslateY = (preTranslateY < 5 ? preTranslateY + SIZE / 9 : preTranslateY);

    data.wrapperColor = wrapperColor;
    data.faceColor = Functions.getContrast(wrapperColor);
    data.backgroundColor = Functions.getRandomColor(numFromName + 13, colors, range);
    data.wrapperTranslateX = wrapperTranslateX;
    data.wrapperTranslateY = wrapperTranslateY;
    data.wrapperRotate = Functions.getUnit(numFromName, 360, 0);
    data.wrapperScale = 1 + Functions.getUnit(numFromName, SIZE, 0) / 10;
    data.isMouthOpen = Functions.getBoolean(numFromName, 2);
    data.isCircle = Functions.getBoolean(numFromName, 1);
    data.eyeSpread = Functions.getUnit(numFromName, 5, 0);
    data.mouthSpread= Functions.getUnit(numFromName, 3, 0);
    data.faceRotate = Functions.getUnit(numFromName, 10, 3);
    data.faceTranslateX = (wrapperTranslateX > (double)SIZE / 6 ? wrapperTranslateX / (double)2 : Functions.getUnit(numFromName, 8, 1));
    data.faceTranslateY = (wrapperTranslateY > (double)SIZE / 6 ? wrapperTranslateY / (double)2 : Functions.getUnit(numFromName, 7, 2));

    return data;
  }

  public String getAvatarBeam(String name, int size, boolean square){
    Data data = generateData(name, COLORS);

    return "<svg " +
      "viewBox=\"0 0 " + SIZE + " " + SIZE+"\" "+
      "fill=\"none\" " +
      "role=\"img\" " +
      "xmlns=\"http://www.w3.org/2000/svg\" " +
      "width=\""+size+"\" " +
      "height=\""+size+"\" " +
    ">" +
    "<mask id=\"mask__beam\" maskUnits=\"userSpaceOnUse\" x=\"0\" y=\"0\" width=\""+SIZE+"\" height=\""+SIZE+"\">" +
        "<rect width=\""+SIZE+"\" height=\""+SIZE+"\" rx=\""+(square ?  "undefined" : SIZE * 2 )+"\" fill=\"#FFFFFF\" />" +
      "</mask>" +
      "<g mask=\"url(#mask__beam)\">" +
        "<rect width=\""+ SIZE +"\" height=\""+SIZE+"\" fill=\""+data.backgroundColor+"\" /> " +
        "<rect " +
          "x=\"0\" " +
          "y=\"0\" " +
          "width=\""+SIZE+"\" " +
          "height=\""+SIZE+"\" " +
          "transform=\" " +
            "translate("+data.wrapperTranslateX+" "+data.wrapperTranslateY +
            ") rotate("+data.wrapperRotate+" "+(SIZE / 2)+" "+(SIZE / 2) +
            ") scale("+data.wrapperScale+")"+"\" " +
          "fill=\""+data.wrapperColor+"\" " +
          "rx=\""+(data.isCircle ? SIZE : SIZE / 6)+"\" " +
        "/>" +
        "<g " +
          "transform=\"" +
            "translate("+data.faceTranslateX+" "+data.faceTranslateY +
              ") rotate("+data.faceRotate+" "+(SIZE / 2)+" "+(SIZE / 2)+")\" " +
        ">" +
        (data.isMouthOpen ? (
                      "<path " +
                        "d=\"M15 "+(19 + data.mouthSpread)+"c2 1 4 1 6 0\" " +
                        "stroke=\""+data.faceColor+"\" " +
                        "fill=\"none\" " +
                        "strokeLinecap=\"round\" " +
                      "/>"
                    ) : (
                      "<path " +
                        "d=\"M13, "+(19 + data.mouthSpread)+" a1,0.75 0 0,0 10,0\" " +
                        "fill=\""+data.faceColor+"\" " +
                      "/>"
                    )) +
                    "<rect " +
                      "x=\""+(14 - data.eyeSpread)+"\" " +
                      "y=\"14\" " +
                      "width=\"1.5\" " +
                      "height=\"2\" " +
                      "rx=\"1\" " +
                      "stroke=\"none\" " +
                      "fill=\""+data.faceColor+"\" " +
                    "/>" +
                    "<rect " +
                      "x=\""+(20 + data.eyeSpread)+"\" " +
                      "y=\"14\" " +
                      "width=\"1.5\" " +
                      "height=\"2\" " +
                      "rx=\"1\" " +
                      "stroke=\"none\" " +
                      "fill=\""+data.faceColor+"\" " +
                    "/>" +
                  "</g>" +
                "</g>" +
              "</svg>";
  }

private class Data{
  String wrapperColor;
  String faceColor;
  String backgroundColor;
  double wrapperTranslateX;
  double wrapperTranslateY;
  int wrapperRotate;
  int wrapperScale;
  boolean isMouthOpen;
  boolean isCircle;
  int eyeSpread;
  int mouthSpread;
  int faceRotate;
  double faceTranslateX;
  double faceTranslateY;
}
}
