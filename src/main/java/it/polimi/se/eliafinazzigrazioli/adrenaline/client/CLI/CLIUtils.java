package it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * The type Cli utils.
 */
public class CLIUtils {

    private static final String ANSI_ESCAPE_COLOR = "\u001B";

    /**
     * Draw empty box string [ ] [ ].
     *
     * @param width the width
     * @param height the height
     * @param color the color
     * @return the string [ ] [ ]
     */
    public static String[][] drawEmptyBox(int width, int height, Color color){
        if(width <= 0 || height <= 0)
            return null;
        String[][] emptyBox = new String[width][height];
        for(int i = 0; i<width; i++) {
            for(int j = 0; j<height; j++)
                emptyBox[i][j] = " ";
        }

        emptyBox[0][0] = color.toString() + "╔";
        emptyBox[width-1][0] = color.toString() + "╗";

        for(int i = 1; i<width-1; i++) {
            emptyBox[i][0] = color.toString() + "═";
        }

        for(int j=1; j<height-1; j++) {
            emptyBox[0][j] = color.toString() + "║";
            for(int i = 1; i<width-1; i++) {
                emptyBox[i][j] = Color.RESET.toString() + " ";
            }
            emptyBox[width-1][j] = color.toString() + "║";

        }

        emptyBox[0][height-1] = color.toString() + "╚";
        emptyBox[width-1][height-1] = color.toString() + "╝";

        for(int i = 1; i<width-1; i++) {
            emptyBox[i][height-1] = color.toString() + "═";
        }
        return emptyBox;
    }

    /**
     * Insert string to matrix string [ ] [ ].
     *
     * @param matrix the matrix
     * @param string the string
     * @return the string [ ] [ ]
     */
    public static String[][] insertStringToMatrix(String[][] matrix, String string) {
        if(matrix == null || string == null)
            return null;

        int width = matrix.length;
        if(width == 0)
            return null;
        int height = matrix[0].length;
        int length = string.length();

        int poseX = 1;
        int poseY = 1;

        for(int i = 0; i<length; i++) {
            if(string.charAt(i) != "\n".charAt(0) && string.charAt(i) != "\t".charAt(0) && string.charAt(i) != ANSI_ESCAPE_COLOR.charAt(0)) {
                if(poseX >= width-3) {
                    poseX = 2;
                    poseY++;
                } else
                    poseX++;
                if(poseY >= height-1)
                    return matrix;
                matrix[poseX][poseY] = String.valueOf(string.charAt(i));

            } else if(string.charAt(i) == "\n".charAt(0)){
                if(poseY >= height-1)
                    return matrix;
                poseY++;
                poseX = 2;

            } else if(string.charAt(i) == "\t".charAt(0)){
                poseX +=4;
                if(poseX >= width-3) {
                    poseX = 2;
                    poseY++;
                }
                if(poseY >= height-1)
                    return matrix;

            } else if(string.charAt(i) == ANSI_ESCAPE_COLOR.charAt(0)) {
                String color = "";
                while(string.charAt(i) != "m".charAt(0)) {
                    color = color + String.valueOf(string.charAt(i));
                    i++;
                }
                color = color + String.valueOf(string.charAt(i));
                i++; //Prendo il carattere
                color = color + String.valueOf(string.charAt(i));

                if(poseX >= width-3) {
                    poseX = 2;
                    poseY++;
                } else
                    poseX++;
                if(poseY >= height-1)
                    return matrix;
                matrix[poseX][poseY] = color;
                //for windows matrix[poseX+1][poseY] = "";
            }
        }
        return matrix;
    }

    /**
     * Matrix to string string.
     *
     * @param matrix the matrix
     * @return the string
     */
    public static String matrixToString(String[][] matrix) {
        int count = 0;
        String result = "\n";
        for(int j = 0; j<matrix[count].length; j++) {
            for(int i = 0; i<matrix.length; i++) {
                result = result.concat(matrix[i][j]);
            }
            result = result.concat("\n");
            if(count < matrix.length-1) {
                count++;
            }
        }
        return result + Color.RESET;
    }

    /**
     * Serialize map string.
     *
     * @param <K> the type parameter
     * @param <V> the type parameter
     * @param map the map
     * @return the string
     */
    public static <K, V> String serializeMap(Map<K, V> map) {
        String result = "";
        int index = 0;
        for(Map.Entry<K, V> entry : map.entrySet()) {
            index++;
            result = result.concat(String.format("\t%s\t%s%n",  entry.getKey(), entry.getValue()));
        }
        return result;
    }

    /**
     * Serialize list string.
     *
     * @param <T> the type parameter
     * @param list the list
     * @return the string
     */
    public static <T> String serializeList(List<T> list) {
        Iterator<T> iterator = list.iterator();
        int index = 0;
        String result = "";

        while (iterator.hasNext()) {
            index++;
            result = result.concat(String.format("%d) %s%n", index, iterator.next())).concat(Color.RESET.toString());
        }
        return result;
    }

    /**
     * Compose matrix string [ ] [ ].
     *
     * @param squares the squares
     * @return the string [ ] [ ]
     */
    public static String[][] composeMatrix(List<String[][]> squares) {
        if(squares == null)
            return null;
        if(squares.size() == 0)
            return null;

        int width = squares.get(0).length;
        int height = squares.get(0)[0].length;

        int newWidth = width*squares.size() + 2*(squares.size()+2);
        int newHeight = height+1;

        String[][] result = new String[newWidth][newHeight];
        for(int j = 0; j<newHeight; j++){
            for(int i = 0; i<newWidth; i++){
                result[i][j] = " ";
            }
        }

        int currentX = 0;
        int oldX = 0;
        int count = 0;
        int currentY = newHeight - 1;
        for(String[][] iterator : squares) {
            width = iterator.length;
            for(int j=iterator[0].length-1; j>=0; j--){
                currentX = count*width;
                currentX +=2;
                for(int i=0; i<width; i++){
                    try {
                        result[currentX][j] = iterator[i][j];
                    } catch(Exception e) {
                        e.printStackTrace();
                        System.out.println(currentX);
                        System.out.println(j);
                        System.out.println(i);
                        System.out.println(j);
                    }
                    currentX++;
                }
            }
            count++;
        }
        return result;
    }

    /**
     * Align square string.
     *
     * @param squares the squares
     * @return the string
     */
    public static String alignSquare(List<String[][]> squares) {
        return matrixToString(composeMatrix(squares));
    }

    /**
     * Next int int.
     *
     * @param scanner the scanner
     * @param size the size
     * @return the int
     */
    public static int nextInt(Scanner scanner, int size) {

        if (size <= 0)
            return -1;

        int nextInt;
        String inputString;
        do {
            try {
                inputString = scanner.nextLine();
                nextInt = Integer.parseInt(inputString);
            } catch (Exception e) {
                nextInt = -1;
            }
            if (nextInt < 1 || nextInt > size) {
                System.out.println("Choice out of bound. Sit down and focus, you can do it: ");
            }
        } while (nextInt < 1 || nextInt > size);

        return nextInt - 1;
    }

}
