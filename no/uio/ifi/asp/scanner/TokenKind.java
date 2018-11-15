package no.uio.ifi.asp.scanner;

import no.uio.ifi.asp.main.*;

public enum TokenKind {
    // Names and literals:
    nameToken("name"),
    integerToken("integer literal"),
    floatToken("float literal"),
    stringToken("string literal"),

    // Keywords:
    andToken("and"),
    returnToken("return"),
    trueToken("True"),
    defToken("def"),
    elifToken("elif"),
    elseToken("else"),
    falseToken("False"),
    forToken("for"),
    notToken("not"),
    orToken("or"),
    passToken("pass"),
    noneToken("None"),
    ifToken("if"),
    inToken("in"),
    whileToken("while"),
    asToken("as"),              // Not used in Asp
    assertToken("assert"),      // Not used in Asp
    breakToken("break"),        // Not used in Asp
    classToken("class"),        // Not used in Asp
    continueToken("continue"),  // Not used in Asp
    delToken("del"),            // Not used in Asp
    exceptToken("except"),      // Not used in Asp
    finallyToken("finally"),    // Not used in Asp
    fromToken("from"),          // Not used in Asp
    globalToken("global"),      // Not used in Asp
    importToken("import"),      // Not used in Asp
    isToken("is"),              // Not used in Asp
    lambdaToken("lambda"),      // Not used in Asp
    nonlocalToken("nonlocal"),  // Not used in Asp
    raiseToken("raise"),        // Not used in Asp
    tryToken("try"),            // Not used in Asp
    withToken("with"),          // Not used in Asp
    yieldToken("yield"),        // Not used in Asp

    // Operators:
    astToken("*"),
    doubleEqualToken("=="),
    doubleSlashToken("//"),
    greaterToken(">"),
    greaterEqualToken(">="),
    lessToken("<"),
    lessEqualToken("<="),
    minusToken("-"),
    notEqualToken("!="),
    percentToken("%"),
    plusToken("+"),
    slashToken("/"),
    // ampToken("&"),
    // barToken("|"),
    // doubleAstToken("**"),
    // doubleGreaterToken(">>"),
    // doubleLessToken("<<"),
    // hatToken("^"),
    // tildeToken("~"),

    // Delimiters:
    colonToken(":"),
    commaToken(","),
    equalToken("="),
    rightBraceToken("}"),
    rightBracketToken("]"),
    rightParToken(")"),
    leftBraceToken("{"),
    leftBracketToken("["),
    leftParToken("("),
    // ampEqualToken("&="),
    // astEqualToken("*="),
    // atToken("@"),
    // barEqualToken("|="),
    // dotToken("."),
    // doubleAstEqualToken("**="),
    // doubleGreaterEqualToken(">>="),
    // doubleLessEqualToken("<<="),
    // doubleSlashEqualToken("//="),
    // hatEqualToken("^="),
    // minusEqualToken("-="),
    // percentEqualToken("%="),
    // plusEqualToken("+="),
    // semicolonToken(";"),
    // slashEqualToken("/="),

    // Format tokens:
    indentToken("INDENT"),
    dedentToken("DEDENT"),
    newLineToken("NEWLINE"),
    eofToken("E-o-f");

    String image;

    TokenKind(String s) {
        image = s;
    }

    protected static final TokenKind[] compOps = new TokenKind[6];

    protected static final TokenKind[] values = values();

    public String toString() {
	    return image;
    }

    protected static void addCompOps(){
        compOps[0] = lessToken;
        compOps[1] = lessEqualToken;
        compOps[2] = greaterToken;
        compOps[3] = greaterEqualToken;
        compOps[4] = doubleEqualToken;
        compOps[5] = notEqualToken;
    }

}
