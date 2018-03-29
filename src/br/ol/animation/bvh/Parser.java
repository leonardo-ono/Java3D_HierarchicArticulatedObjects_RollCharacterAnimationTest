package br.ol.animation.bvh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Parser class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Parser {
    
    private boolean endOfFile = false;
    private String line = "";
    private BufferedReader br;
    
    public void load(String resource) {
        InputStream is = getClass().getResourceAsStream("/res/" + resource);
        InputStreamReader isr = new InputStreamReader(is);
        br = new BufferedReader(isr);
        nextLine();
    }

    public boolean isEndOfFile() {
        return endOfFile;
    }
    
    public String getLine() {
        return line;
    }
    
    public void nextLine() {
        try {
            line = br.readLine();
            if (line == null) {
                line = "";
                endOfFile = true;
            }
            line = line.trim();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public String[] expect(String token) {
        if (!line.startsWith(token)) {
            throw new RuntimeException("Expected '" + token + "' token !");
        }
        String[] tokens = line.split("\\ ");
        nextLine();
        return tokens;
    }
    
    public void close() {
        try {
            br.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
}
