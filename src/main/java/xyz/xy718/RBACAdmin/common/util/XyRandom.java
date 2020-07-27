package xyz.xy718.RBACAdmin.common.util;

public class XyRandom {
    private static String[] chars_62 = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C",
            "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
            "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
    private static String string_62 ="0123456789abcdefghijklmnopqrstuvwxyzMLPOKIJNUHBYGVTFCRDXESZWQA";
    public static String get62ByteString() {
        return string_62;
    }
}