
package tarea3.francomendoza;

import java.util.IllegalFormatException;
import java.io.*;
import java.util.IllegalFormatException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class TextIO {
    
    public final static char EOF = (char)0xFFFF; 

    public final static char EOLN = '\n';        
    
    public static void readStandardInput() {
        if (readingStandardInput)
            return;
        try {
            in.close();
        }
        catch (Exception e) {
        }
        emptyBuffer(); 
        in = standardInput;
        inputFileName = null;
        readingStandardInput = true;
        inputErrorCount = 0;
    }
    
    public static void readStream(InputStream inputStream) {
        if (inputStream == null)
            readStandardInput();
        else
            readStream(new InputStreamReader(inputStream));
    }
    
    public static void readStream(Reader inputStream) {
        if (inputStream == null)
            readStandardInput();
        else {
            if ( inputStream instanceof BufferedReader)
                in = (BufferedReader)inputStream;
            else
                in = new BufferedReader(inputStream);
            emptyBuffer(); 
            inputFileName = null;
            readingStandardInput = false;
            inputErrorCount = 0;
        }
    }
    
    public static void readFile(String fileName) {
        if (fileName == null) 
            readStandardInput();
        else {
            BufferedReader newin;
            try {
                newin = new BufferedReader( new FileReader(fileName) );
            }
            catch (Exception e) {
                throw new IllegalArgumentException("Can't open file \"" + fileName + "\" for input.\n"
                                 + "(Error :" + e + ")");
            }
            if (! readingStandardInput) { 
                try {
                    in.close();
                }
                catch (Exception e) {
                }
            }
            emptyBuffer();  
            in = newin;
            readingStandardInput = false;
            inputErrorCount = 0;
            inputFileName = fileName;
        }
    }

    public static boolean readUserSelectedFile() {
        if (fileDialog == null)
            fileDialog = new JFileChooser();
        fileDialog.setDialogTitle("Select File for Input");
        int option = fileDialog.showOpenDialog(null);
        if (option != JFileChooser.APPROVE_OPTION)
            return false;
        File selectedFile = fileDialog.getSelectedFile();
        BufferedReader newin;
        try {
            newin = new BufferedReader( new FileReader(selectedFile) );
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Can't open file \"" + selectedFile.getName() + "\" for input.\n"
                             + "(Error :" + e + ")");
        }
        if (!readingStandardInput) { // close current file
            try {
                in.close();
            }
            catch (Exception e) {
            }
        }
        emptyBuffer();
        in = newin;
        inputFileName = selectedFile.getName();
        readingStandardInput = false;
        inputErrorCount = 0;
        return true;
    }
    
    public static void writeStandardOutput() {
        if (writingStandardOutput)
            return;
        try {
            out.close();
        }
        catch (Exception e) {
        }
        outputFileName = null;
        outputErrorCount = 0;
        out = standardOutput;
        writingStandardOutput = true;
    }
    
    public static void writeStream(OutputStream outputStream) {
        if (outputStream == null)
            writeStandardOutput();
        else
            writeStream(new PrintWriter(outputStream));
    }
    
    public static void writeStream(PrintWriter outputStream) {
        if (outputStream == null)
            writeStandardOutput();
        else {
            out = outputStream;
            outputFileName = null;
            outputErrorCount = 0;
            writingStandardOutput = false;
        }
    }
    
    public static void writeFile(String fileName) {
        if (fileName == null)  // Go back to reading standard output
            writeStandardOutput();
        else {
            PrintWriter newout;
            try {
                newout = new PrintWriter(new FileWriter(fileName));
            }
            catch (Exception e) {
                throw new IllegalArgumentException("Can't open file \"" + fileName + "\" for output.\n"
                                 + "(Error :" + e + ")");
            }
            if (!writingStandardOutput) {
                try {
                    out.close();
                }
                catch (Exception e) {
                }
            }
            out = newout;
            writingStandardOutput = false;
            outputFileName = fileName;
            outputErrorCount = 0;
        }
    }
    
    public static boolean writeUserSelectedFile() {
        if (fileDialog == null)
            fileDialog = new JFileChooser();
        fileDialog.setDialogTitle("Select File for Output");
        File selectedFile;
        while (true) {
            int option = fileDialog.showSaveDialog(null);
            if (option != JFileChooser.APPROVE_OPTION)
                return false;  // user canceled
            selectedFile = fileDialog.getSelectedFile();
            if (selectedFile.exists()) {
                int response = JOptionPane.showConfirmDialog(null,
                        "The file \"" + selectedFile.getName() + "\" already exists.  Do you want to replace it?",
                        "Replace existing file?",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (response == JOptionPane.YES_OPTION)
                    break;
            }
            else {
                break;
            }
        }
        PrintWriter newout;
        try {
            newout = new PrintWriter(new FileWriter(selectedFile));
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Can't open file \"" + selectedFile.getName() + "\" for output.\n"
                             + "(Error :" + e + ")");
        }
        if (!writingStandardOutput) {
            try {
                out.close();
            }
            catch (Exception e) {
            }
        }
        out = newout;
        writingStandardOutput = false;
        outputFileName = selectedFile.getName();
        outputErrorCount = 0;
        return true;
    }
   
    public static String getInputFileName() {
        return inputFileName;
    }
    
    public static String getOutputFileName() {
        return outputFileName;
    }
    

    // *************************** Output Methods *********************************
        
    
    public static void put(Object x) { 
        out.print(x); 
        out.flush();
        if (out.checkError())
            outputError("Error while writing output.");
    }
    
    public static void put(Object x, int minChars)  { 
        if (minChars <= 0)
            out.print(x);
        else
            out.printf("%" + minChars + "s", x);
        out.flush();
        if (out.checkError())
            outputError("Error while writing output.");
    }
    
    public static void putln(Object x) { 
        out.println(x);
        out.flush();
        if (out.checkError())
            outputError("Error while writing output.");
    }
    
    public static void putln(Object x, int minChars) {
        put(x,minChars);
        out.println();
        out.flush();
        if (out.checkError())
            outputError("Error while writing output.");
    }

    /**
     * Write an end-of-line character to the current output destination.
     */
    public static void putln() {
        out.println();
        out.flush();
        if (out.checkError())
            outputError("Error while writing output.");
    }
    
    public static void putf(String format, Object... items) {
        if (format == null)
            throw new IllegalArgumentException("Null format string in TextIO.putf() method.");
        try {
            out.printf(format,items);
        }
        catch (IllegalFormatException e) {
            throw new IllegalArgumentException("Illegal format string in TextIO.putf() method.");
        }
        out.flush();
        if (out.checkError())
            outputError("Error while writing output.");
    }
    
    // *************************** Input Methods *********************************

    public static boolean eoln() { 
        return peek() == '\n'; 
    }

    public static boolean eof()  { 
        return peek() == EOF; 
    }
    
    public static char getAnyChar() { 
        return readChar(); 
    }

    public static char peek() { 
        return lookChar();
    }
    
    public static void skipBlanks() { 
        char ch=lookChar();
        while (ch != EOF && ch != '\n' && Character.isWhitespace(ch)) {
            readChar();
            ch = lookChar();
        }
    }

    private static void skipWhitespace() {
        char ch=lookChar();
        while (ch != EOF && Character.isWhitespace(ch)) {
            readChar();
            if (ch == '\n' && readingStandardInput && writingStandardOutput) {
                out.print("? ");
                out.flush();
            }
            ch = lookChar();
        }
    }

    public static byte getlnByte() { 
        byte x=getByte(); 
        emptyBuffer(); 
        return x; 
    }
    
    public static short getlnShort() {
        short x=getShort();
        emptyBuffer(); 
        return x; 
    }
    
    public static int getlnInt() { 
        int x=getInt(); 
        emptyBuffer(); 
        return x; 
    }
    
    public static long getlnLong() {
        long x=getLong(); 
        emptyBuffer(); 
        return x;
    }
    
    public static float getlnFloat() {
        float x=getFloat(); 
        emptyBuffer(); 
        return x;
    }
    
    public static double getlnDouble() { 
        double x=getDouble(); 
        emptyBuffer(); 
        return x; 
    }
    
    public static char getlnChar() {
        char x=getChar(); 
        emptyBuffer(); 
        return x;
    }
    
    public static boolean getlnBoolean() { 
        boolean x=getBoolean(); 
        emptyBuffer();
        return x; 
    }
    
    public static String getlnWord() {
        String x=getWord(); 
        emptyBuffer(); 
        return x; 
    }
    
    public static String getlnString() {
        return getln();
    } 
    
    public static String getln() {
        StringBuffer s = new StringBuffer(100);
        char ch = readChar();
        while (ch != '\n') {
            s.append(ch);
            ch = readChar();
        }
        return s.toString();
    }
    
    public static byte getByte()   { 
        return (byte)readInteger(-128L,127L); 
    }

    public static short getShort() { 
        return (short)readInteger(-32768L,32767L);
    }   
    
    public static int getInt()     { 
        return (int)readInteger(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    public static long getLong()   { 
        return readInteger(Long.MIN_VALUE, Long.MAX_VALUE); 
    }
    
    public static char getChar() { 
        skipWhitespace();
        return readChar();
    }
    
    public static float getFloat() {
        float x = 0.0F;
        while (true) {
            String str = readRealString();
            if (str == null) {
                errorMessage("Floating point number not found.",
                        "Real number in the range " + (-Float.MAX_VALUE) + " to " + Float.MAX_VALUE);
            }
            else {
                try { 
                    x = Float.parseFloat(str); 
                }
                catch (NumberFormatException e) {
                    errorMessage("Illegal floating point input, " + str + ".",
                            "Real number in the range " +  (-Float.MAX_VALUE) + " to " + Float.MAX_VALUE);
                    continue;
                }
                if (Float.isInfinite(x)) {
                    errorMessage("Floating point input outside of legal range, " + str + ".",
                            "Real number in the range " +  (-Float.MAX_VALUE) + " to " + Float.MAX_VALUE);
                    continue;
                }
                break;
            }
        }
        inputErrorCount = 0;
        return x;
    }
    
    public static double getDouble() {
        double x = 0.0;
        while (true) {
            String str = readRealString();
            if (str == null) {
                errorMessage("Floating point number not found.",
                        "Real number in the range " + (-Double.MAX_VALUE) + " to " + Double.MAX_VALUE);
            }
            else {
                try { 
                    x = Double.parseDouble(str); 
                }
                catch (NumberFormatException e) {
                    errorMessage("Illegal floating point input, " + str + ".",
                            "Real number in the range " + (-Double.MAX_VALUE) + " to " + Double.MAX_VALUE);
                    continue;
                }
                if (Double.isInfinite(x)) {
                    errorMessage("Floating point input outside of legal range, " + str + ".",
                            "Real number in the range " + (-Double.MAX_VALUE) + " to " + Double.MAX_VALUE);
                    continue;
                }
                break;
            }
        }
        inputErrorCount = 0;
        return x;
    }
    
    public static String getWord() {
        skipWhitespace();
        StringBuffer str = new StringBuffer(50);
        char ch = lookChar();
        while (ch == EOF || !Character.isWhitespace(ch)) {
            str.append(readChar());
            ch = lookChar();
        }
        return str.toString();
    }
    
    public static boolean getBoolean() {
        boolean ans = false;
        while (true) {
            String s = getWord();
            if ( s.equalsIgnoreCase("true") || s.equalsIgnoreCase("t") ||
                    s.equalsIgnoreCase("yes")  || s.equalsIgnoreCase("y") ||
                    s.equals("1") ) {
                ans = true;
                break;
            }
            else if ( s.equalsIgnoreCase("false") || s.equalsIgnoreCase("f") ||
                    s.equalsIgnoreCase("no")  || s.equalsIgnoreCase("n") ||
                    s.equals("0") ) {
                ans = false;
                break;
            }
            else
                errorMessage("Illegal boolean input value.",
                "one of:  true, false, t, f, yes, no, y, n, 0, or 1");
        }
        inputErrorCount = 0;
        return ans;
    }
    
    private static String inputFileName;  
    private static String outputFileName; 
    
    private static JFileChooser fileDialog; 
    
    private final static BufferedReader standardInput = new BufferedReader(new InputStreamReader(System.in)); 
    private final static PrintWriter standardOutput = new PrintWriter(System.out); 

    private static BufferedReader in = standardInput; 
    private static PrintWriter out = standardOutput;  
    
    private static boolean readingStandardInput = true;
    private static boolean writingStandardOutput = true;
    
    private static int inputErrorCount;  
    private static int outputErrorCount;  
    
    private static Matcher integerMatcher; 
    private static Matcher floatMatcher;   
    private final static Pattern integerRegex = Pattern.compile("(\\+|-)?[0-9]+");
    private final static Pattern floatRegex = Pattern.compile("(\\+|-)?(([0-9]+(\\.[0-9]*)?)|(\\.[0-9]+))((e|E)(\\+|-)?[0-9]+)?");
    
    private static String buffer = null;  
    private static int pos = 0;           
    
    private static String readRealString() {  
        skipWhitespace();
        if (lookChar() == EOF)
            return null;
        if (floatMatcher == null)
            floatMatcher = floatRegex.matcher(buffer);
        floatMatcher.region(pos,buffer.length());
        if (floatMatcher.lookingAt()) {
            String str = floatMatcher.group();
            pos = floatMatcher.end();
            return str;
        }
        else 
            return null;
    }
    
    private static String readIntegerString() { 
        skipWhitespace();
        if (lookChar() == EOF)
            return null;
        if (integerMatcher == null)
            integerMatcher = integerRegex.matcher(buffer);
        integerMatcher.region(pos,buffer.length());
        if (integerMatcher.lookingAt()) {
            String str = integerMatcher.group();
            pos = integerMatcher.end();
            return str;
        }
        else 
            return null;
    }
    
    private static long readInteger(long min, long max) {  
        long x=0;
        while (true) {
            String s = readIntegerString();
            if (s == null){
                errorMessage("Integer value not found in input.",
                        "Integer in the range " + min + " to " + max);
            }
            else {
                String str = s.toString();
                try { 
                    x = Long.parseLong(str);
                }
                catch (NumberFormatException e) {
                    errorMessage("Illegal integer input, " + str + ".",
                            "Integer in the range " + min + " to " + max);
                    continue;
                }
                if (x < min || x > max) {
                    errorMessage("Integer input outside of legal range, " + str + ".",
                            "Integer in the range " + min + " to " + max);
                    continue;
                }
                break;
            }
        }
        inputErrorCount = 0;
        return x;
    }
    
    private static void errorMessage(String message, String expecting) {  
        if (readingStandardInput && writingStandardOutput) {
                
            out.println();
            out.print("  *** Error in input: " + message + "\n");
            out.print("  *** Expecting: " + expecting + "\n");
            out.print("  *** Discarding Input: ");
            if (lookChar() == '\n')
                out.print("(end-of-line)\n\n");
            else {
                while (lookChar() != '\n')    
                    out.print(readChar());
                out.print("\n\n");
            }
            out.print("Please re-enter: ");
            out.flush();
            readChar();  
            inputErrorCount++;
            if (inputErrorCount >= 10)
                throw new IllegalArgumentException("Too many input consecutive input errors on standard input.");
        }
        else if (inputFileName != null)
            throw new IllegalArgumentException("Error while reading from file \"" + inputFileName + "\":\n" 
                    + message + "\nExpecting " + expecting);
        else
            throw new IllegalArgumentException("Error while reading from inptu stream:\n" 
                    + message + "\nExpecting " + expecting);
    }
    
    private static char lookChar() { 
        if (buffer == null || pos > buffer.length())
            fillBuffer();
        if (buffer == null)
            return EOF;
        else if (pos == buffer.length())
            return '\n';
        else 
            return buffer.charAt(pos);
    }
    
    private static char readChar() {  
        char ch = lookChar();
        if (buffer == null) {
            if (readingStandardInput)
                throw new IllegalArgumentException("Attempt to read past end-of-file in standard input???");
            else
                throw new IllegalArgumentException("Attempt to read past end-of-file in file \"" + inputFileName + "\".");
        }
        pos++;
        return ch;
    }
        
    private static void fillBuffer() {    
        try {
            buffer = in.readLine();
        }
        catch (Exception e) {
            if (readingStandardInput)
                throw new IllegalArgumentException("Error while reading standard input???");
            else if (inputFileName != null)
                throw new IllegalArgumentException("Error while attempting to read from file \"" + inputFileName + "\".");
            else
                throw new IllegalArgumentException("Errow while attempting to read form an input stream.");
        }
        pos = 0;
        floatMatcher = null;
        integerMatcher = null;
    }
    
    private static void emptyBuffer() {   
        buffer = null;
    }
    
    private static void outputError(String message) { 
        if (writingStandardOutput) {
            System.err.println("Error occurred in TextIO while writing to standard output!!");
            outputErrorCount++;
            if (outputErrorCount >= 10) {
                outputErrorCount = 0;
                throw new IllegalArgumentException("Too many errors while writing to standard output.");
            }
        }
        else if (outputFileName != null){
            throw new IllegalArgumentException("Error occurred while writing to file \"" 
                    + outputFileName+ "\":\n   " + message);
        }
        else {
            throw new IllegalArgumentException("Error occurred while writing to output stream:\n   " + message);
        }
    }
        
} 
