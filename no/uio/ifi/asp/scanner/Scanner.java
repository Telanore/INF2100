package no.uio.ifi.asp.scanner;

import java.io.*;
import java.util.*;

import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class Scanner {
    private LineNumberReader sourceFile = null;
    private String curFileName;
    private ArrayList<Token> curLineTokens = new ArrayList<>();
    private Stack<Integer> indents = new Stack<>();
    protected int numIndents = 0;
    private final int tabDist = 4;
    private static int firstLine = -1;

    public Scanner(String fileName) {
        curFileName = fileName;
        indents.push(0);  
        numIndents = 1;

        try {
            sourceFile = new LineNumberReader(
                    new InputStreamReader(
                    new FileInputStream(fileName),
                    "UTF-8"));
        } catch (IOException e) {
            scannerError("Cannot read " + fileName + "!");
        }
        TokenKind.addCompOps();
    }


    private void scannerError(String message) {
        String m = "Asp scanner error";
        if (curLineNum() > 0)
            m += " on line " + curLineNum();
        
        m += ": " + message;
        Main.error(m);
    }


    public Token curToken() {
        while (curLineTokens.isEmpty()) {
            readNextLine();
        }
        return curLineTokens.get(0);
    }


    public void readNextToken() {
        if (! curLineTokens.isEmpty())
            curLineTokens.remove(0);
    }


    public boolean anyEqualToken() {
        for (Token t: curLineTokens) {
            if (t.kind == equalToken) return true;
        }
        return false;
    }


    private void readNextLine() {
        curLineTokens.clear();

        // Read the next line:
        String line = null;
        try {
            line = sourceFile.readLine();
            if (line == null) {
                sourceFile.close();
                sourceFile = null;
                curLineTokens.add(new Token(eofToken, curLineNum()));
                for (Token t: curLineTokens) 
                    Main.log.noteToken(t);
                return;
            } else {
                Main.log.noteSourceLine(curLineNum(), line);
            }
        } catch (IOException e) {
            sourceFile = null;
            scannerError("Unspecified I/O error!");
        }

        //-- Must be changed in part 1:
        expandLeadingTabs(line);
        numIndents = findIndent(line);
        line.trim();
        
        //Skip empty or commented line 
        if(line.trim().isEmpty() || line.charAt(numIndents) == '#'){
        
            return;
        }else if(firstLine < 0){
            firstLine = curLineNum();
        }


        //Control correct indentation
        if(curLineNum() == firstLine && numIndents > 0){
            scannerError("Unexpected indent");
        }else if(numIndents > indents.peek()){
            indents.push(numIndents);
            curLineTokens.add(new Token(indentToken, curLineNum()));
        }else if(numIndents < indents.peek()){
            if(!indents.contains(numIndents)){
                scannerError("Unexpected indent");
            }
            do{
                indents.pop();
                curLineTokens.add(new Token(dedentToken, curLineNum()));
            }while(numIndents < indents.peek());
        }
        System.out.println("\nCurrent line nr: "+curLineNum());
        System.out.println("Current indentation: "+indents.peek());

        String str;
        Token tkn;
        TokenKind tknKind = null;


        //Iterate through current line char for char
        for(int i = 0; i < line.length(); i++){
            String c = Character.toString(line.charAt(i));

            //If quotation marks are found, make string literal
            if(c.equals("\'") || c.equals("\"")){
                str = "";
                String quotationType = c;
                
                for(int j = i+1; j < line.length(); j++){
                    c = Character.toString(line.charAt(j));
                    if(c.equals("\'") || c.equals("\"")){
                        break;
                    }else{
                        str += c;
                        i++;

                    }

                }
                i++;

                if(!c.equals(quotationType)){
                    scannerError("String literal not terminated");
                }

                tkn = new Token(stringToken, curLineNum());
                tkn.stringLit = str;
                curLineTokens.add(tkn);
            }
            //If letter is found, make string and search for syntax token, else make varname
            else if(c.matches("[a-zA-Z]")){
                str = "";
                
                for(int j = i; j < line.length(); j++){
                    c = Character.toString(line.charAt(j));
                    if(c.matches("\\p{Alnum}") || c.equals("_")){
                        str += ""+c;
                        i++;
                    }else{
                        break;
                    }
                }
                i--;
                

                tknKind = findTokenKind(str);
                
                if(tknKind == null){
                    tkn = new Token(nameToken, curLineNum());
                    tkn.name = str;
                    curLineTokens.add(tkn);
                }else{
                    curLineTokens.add(new Token(tknKind, curLineNum()));
                }

            }

            //If digit is found, read all digits and make int or float token
            else if(c.matches("\\d")){
                
                str = "";
                boolean isFloat = false;
                
                for(int j = i; j < line.length(); j++){
                    c = Character.toString(line.charAt(j));
                    if(c.matches("\\d")){
                        str += ""+c;
                        i++;
                    }else if(c.equals(".")){
                        isFloat=true;
                        str += ""+c;
                        i++;
                    }else{
                        break;
                    }
                }

                i--;
                
                if(isFloat && str.charAt(str.length()-1) == '.'){
                    str += "0";
                }
                
                if(isFloat){
                    tkn = new Token(floatToken, curLineNum());
                    tkn.floatLit = Double.parseDouble(str);
                }else{
                    tkn = new Token(integerToken, curLineNum());
                    tkn.integerLit = Integer.parseInt(str);
                }
                curLineTokens.add(tkn);

            }
            //If char is comment, stop reading line
            else if(c.equals("#")){
                break;
            }
            //If char is anything else (and not whitespace), check for matching tokenkind
            else if(c.equals("#")){
                break;
            }
            //If char is anything else (and not whitespace), check for matching tokenkind
            else if(!c.matches("[a-zA-Z\\s]")){
                tknKind = findTokenKind(c);


                if((i+1) < line.length()){
                    String nextC = Character.toString(line.charAt(i+1));
                    TokenKind tk2 = findTokenKind(c+""+nextC);

                    if(tk2 != null){
                        i++;
                        curLineTokens.add(new Token(tk2, curLineNum()));
                    }else if(tk2 == null && tknKind != null){
                        curLineTokens.add(new Token(tknKind, curLineNum()));
                    }else{
                        scannerError("'"+c+"' is not a valid token");
                    }
                }else{
                    curLineTokens.add(new Token(tknKind, curLineNum()));
                }

            }

        }
            
        

        // Terminate line:
        curLineTokens.add(new Token(newLineToken,curLineNum()));
        System.out.println(line);
        System.out.println("********Line done********");

        for (Token t: curLineTokens) 
            Main.log.noteToken(t);
    }


    public TokenKind findTokenKind(String find){
        for(TokenKind tk: TokenKind.values){
            if(find.equals(tk.image)){
                return tk;
            }
        }
        return null;
    }



    public int curLineNum() {
        return sourceFile!=null ? sourceFile.getLineNumber() : 0;
    }

    private int findIndent(String s) {
        int indent = 0;

        while (indent<s.length() && s.charAt(indent)==' ') indent++;
        return indent;
    }

    private String expandLeadingTabs(String s) {
        String newS = "";
        for (int i = 0;  i < s.length();  i++) {
            char c = s.charAt(i);
            if (c == '\t') {
                do {
                    newS += " ";
                } while (newS.length()%tabDist != 0);
            } else if (c == ' ') {
                newS += " ";
            } else {
                newS += s.substring(i);
                break;
            }
        }
        return newS;
    }


    private boolean isLetterAZ(char c) {
        return ('A'<=c && c<='Z') || ('a'<=c && c<='z') || (c=='_');
    }


    private boolean isDigit(char c) {
        return '0'<=c && c<='9';
    }


    public boolean isCompOp() {
        TokenKind k = curToken().kind;
        //-- Must be changed in part 2:
        for(TokenKind tk: TokenKind.compOps){
            if(k == tk) return true;
        }
        return false;
    }


    public boolean isFactorPrefix() {
        TokenKind k = curToken().kind;
        //-- Must be changed in part 2:
        return k == plusToken || k == minusToken;
    }


    public boolean isFactorOp() {
        TokenKind k = curToken().kind;
        //-- Must be changed in part 2:
        if(k == astToken) return true;
        else if(k == slashToken) return true;
        else if(k == percentToken) return true;
        else if(k == doubleSlashToken) return true;
        return false;
    }
	

    public boolean isTermOp() {
        TokenKind k = curToken().kind;
        //-- Must be changed in part 2:
        if(k == plusToken || k == minusToken) return true;
        return false;
    }

    public boolean isBoolean(){
        TokenKind k = curToken().kind;
        if(k == trueToken || k == falseToken) return true;
        return false;
    }

    public int getNumIndents(){
        return numIndents;
    }
}
