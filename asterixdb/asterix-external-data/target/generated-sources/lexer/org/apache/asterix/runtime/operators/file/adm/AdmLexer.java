/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.asterix.runtime.operators.file.adm;

import java.io.IOException;
import org.apache.asterix.runtime.operators.file.adm.AdmLexerException;

public class AdmLexer {

    public static final int
        TOKEN_EOF = 0, TOKEN_AUX_NOT_FOUND = 1 , TOKEN_BOOLEAN_CONS=2 , TOKEN_INT8_CONS=3 , TOKEN_INT16_CONS=4 , TOKEN_INT32_CONS=5 , TOKEN_INT64_CONS=6 , TOKEN_FLOAT_CONS=7 , TOKEN_DOUBLE_CONS=8 , TOKEN_DATE_CONS=9 , TOKEN_DATETIME_CONS=10 , TOKEN_DURATION_CONS=11 , TOKEN_STRING_CONS=12 , TOKEN_HEX_CONS=13 , TOKEN_BASE64_CONS=14 , TOKEN_POINT_CONS=15 , TOKEN_POINT3D_CONS=16 , TOKEN_LINE_CONS=17 , TOKEN_POLYGON_CONS=18 , TOKEN_RECTANGLE_CONS=19 , TOKEN_CIRCLE_CONS=20 , TOKEN_TIME_CONS=21 , TOKEN_INTERVAL_CONS=22 , TOKEN_YEAR_MONTH_DURATION_CONS=23 , TOKEN_DAY_TIME_DURATION_CONS=24 , TOKEN_UUID_CONS=25 , TOKEN_NULL_LITERAL=26 , TOKEN_TRUE_LITERAL=27 , TOKEN_FALSE_LITERAL=28 , TOKEN_CONSTRUCTOR_OPEN=29 , TOKEN_CONSTRUCTOR_CLOSE=30 , TOKEN_START_RECORD=31 , TOKEN_END_RECORD=32 , TOKEN_COMMA=33 , TOKEN_COLON=34 , TOKEN_START_ORDERED_LIST=35 , TOKEN_END_ORDERED_LIST=36 , TOKEN_START_UNORDERED_LIST=37 , TOKEN_STRING_LITERAL=38 , TOKEN_INT_LITERAL=39 , TOKEN_INT8_LITERAL=40 , TOKEN_INT16_LITERAL=41 , TOKEN_INT32_LITERAL=42 , TOKEN_INT64_LITERAL=43 , TOKEN_aux_EXPONENT=44 , TOKEN_DOUBLE_LITERAL=45 , TOKEN_FLOAT_LITERAL=46 ;

    // Human representation of tokens. Useful for debug.
    // Is possible to convert a TOKEN_CONSTANT in its image through
    // AdmLexer.tokenKindToString(TOKEN_CONSTANT);
    private static final String[] tokenImage = {
            "<EOF>", "<AUX_NOT_FOUND>" , "<BOOLEAN_CONS>" , "<INT8_CONS>" , "<INT16_CONS>" , "<INT32_CONS>" , "<INT64_CONS>" , "<FLOAT_CONS>" , "<DOUBLE_CONS>" , "<DATE_CONS>" , "<DATETIME_CONS>" , "<DURATION_CONS>" , "<STRING_CONS>" , "<HEX_CONS>" , "<BASE64_CONS>" , "<POINT_CONS>" , "<POINT3D_CONS>" , "<LINE_CONS>" , "<POLYGON_CONS>" , "<RECTANGLE_CONS>" , "<CIRCLE_CONS>" , "<TIME_CONS>" , "<INTERVAL_CONS>" , "<YEAR_MONTH_DURATION_CONS>" , "<DAY_TIME_DURATION_CONS>" , "<UUID_CONS>" , "<NULL_LITERAL>" , "<TRUE_LITERAL>" , "<FALSE_LITERAL>" , "<CONSTRUCTOR_OPEN>" , "<CONSTRUCTOR_CLOSE>" , "<START_RECORD>" , "<END_RECORD>" , "<COMMA>" , "<COLON>" , "<START_ORDERED_LIST>" , "<END_ORDERED_LIST>" , "<START_UNORDERED_LIST>" , "<STRING_LITERAL>" , "<INT_LITERAL>" , "<INT8_LITERAL>" , "<INT16_LITERAL>" , "<INT32_LITERAL>" , "<INT64_LITERAL>" , "<aux_EXPONENT>" , "<DOUBLE_LITERAL>" , "<FLOAT_LITERAL>" 
          };

    private static final char EOF_CHAR = 4;
    protected java.io.Reader inputStream;
    protected int column;
    protected int line;
    protected boolean prevCharIsCR;
    protected boolean prevCharIsLF;
    protected boolean containsEscapes;
    protected char[] buffer;
    protected int bufsize;
    protected int bufpos;
    protected int tokenBegin;
    protected int endOf_USED_Buffer;
    protected int endOf_UNUSED_Buffer;
    protected int maxUnusedBufferSize;

// ================================================================================
//  Auxiliary functions. Can parse the tokens used in the grammar as partial/auxiliary
// ================================================================================

    private int parse_aux_EXPONENT(char currentChar) throws IOException {
if (currentChar=='e'){
currentChar = readNextChar();
if (currentChar=='+'){
currentChar = readNextChar();
if(currentChar >= '0' && currentChar<='9'){
currentChar = readNextChar();
while(currentChar >= '0' && currentChar<='9'){
currentChar = readNextChar();
}

return TOKEN_aux_EXPONENT;

}return parseError(TOKEN_aux_EXPONENT);
}if (currentChar=='-'){
currentChar = readNextChar();
if(currentChar >= '0' && currentChar<='9'){
currentChar = readNextChar();
while(currentChar >= '0' && currentChar<='9'){
currentChar = readNextChar();
}

return TOKEN_aux_EXPONENT;

}return parseError(TOKEN_aux_EXPONENT);
}if(currentChar >= '0' && currentChar<='9'){
currentChar = readNextChar();
while(currentChar >= '0' && currentChar<='9'){
currentChar = readNextChar();
}

return TOKEN_aux_EXPONENT;

}return parseError(TOKEN_aux_EXPONENT);
}if (currentChar=='E'){
currentChar = readNextChar();
if (currentChar=='+'){
currentChar = readNextChar();
if(currentChar >= '0' && currentChar<='9'){
currentChar = readNextChar();
while(currentChar >= '0' && currentChar<='9'){
currentChar = readNextChar();
}

return TOKEN_aux_EXPONENT;

}return parseError(TOKEN_aux_EXPONENT);
}if (currentChar=='-'){
currentChar = readNextChar();
if(currentChar >= '0' && currentChar<='9'){
currentChar = readNextChar();
while(currentChar >= '0' && currentChar<='9'){
currentChar = readNextChar();
}

return TOKEN_aux_EXPONENT;

}return parseError(TOKEN_aux_EXPONENT);
}if(currentChar >= '0' && currentChar<='9'){
currentChar = readNextChar();
while(currentChar >= '0' && currentChar<='9'){
currentChar = readNextChar();
}

return TOKEN_aux_EXPONENT;

}return parseError(TOKEN_aux_EXPONENT);
}return TOKEN_AUX_NOT_FOUND;

}



// ================================================================================
//  Main method. Return a TOKEN_CONSTANT
// ================================================================================

    public int next() throws AdmLexerException, IOException {
        char currentChar = buffer[bufpos];
        while (currentChar == ' ' || currentChar=='\t' || currentChar == '\n' || currentChar=='\r')
            currentChar = readNextChar();
        tokenBegin = bufpos;
        containsEscapes = false;
        if (currentChar==EOF_CHAR) return TOKEN_EOF;

        switch(currentChar){
case 'b':
currentChar = readNextChar();
if (currentChar=='o'){
currentChar = readNextChar();
if (currentChar=='o'){
currentChar = readNextChar();
if (currentChar=='l'){
currentChar = readNextChar();
if (currentChar=='e'){
currentChar = readNextChar();
if (currentChar=='a'){
currentChar = readNextChar();
if (currentChar=='n'){
currentChar = readNextChar();
return TOKEN_BOOLEAN_CONS;
}return parseError(TOKEN_BOOLEAN_CONS);
}return parseError(TOKEN_BOOLEAN_CONS);
}return parseError(TOKEN_BOOLEAN_CONS);
}return parseError(TOKEN_BOOLEAN_CONS);
}return parseError(TOKEN_BOOLEAN_CONS);
}if (currentChar=='a'){
currentChar = readNextChar();
if (currentChar=='s'){
currentChar = readNextChar();
if (currentChar=='e'){
currentChar = readNextChar();
if (currentChar=='6'){
currentChar = readNextChar();
if (currentChar=='4'){
currentChar = readNextChar();
return TOKEN_BASE64_CONS;
}return parseError(TOKEN_BASE64_CONS);
}return parseError(TOKEN_BASE64_CONS);
}return parseError(TOKEN_BASE64_CONS);
}return parseError(TOKEN_BASE64_CONS);
}return parseError(TOKEN_BOOLEAN_CONS,TOKEN_BASE64_CONS);
case 'i':
currentChar = readNextChar();
if (currentChar=='n'){
currentChar = readNextChar();
if (currentChar=='t'){
currentChar = readNextChar();
switch(currentChar){
case '8':
currentChar = readNextChar();
return TOKEN_INT8_CONS;
case '1':
currentChar = readNextChar();
if (currentChar=='6'){
currentChar = readNextChar();
return TOKEN_INT16_CONS;
}return parseError(TOKEN_INT16_CONS);
case '3':
currentChar = readNextChar();
if (currentChar=='2'){
currentChar = readNextChar();
return TOKEN_INT32_CONS;
}return parseError(TOKEN_INT32_CONS);
case '6':
currentChar = readNextChar();
if (currentChar=='4'){
currentChar = readNextChar();
return TOKEN_INT64_CONS;
}return parseError(TOKEN_INT64_CONS);
case 'e':
currentChar = readNextChar();
if (currentChar=='r'){
currentChar = readNextChar();
if (currentChar=='v'){
currentChar = readNextChar();
if (currentChar=='a'){
currentChar = readNextChar();
if (currentChar=='l'){
currentChar = readNextChar();
return TOKEN_INTERVAL_CONS;
}return parseError(TOKEN_INTERVAL_CONS);
}return parseError(TOKEN_INTERVAL_CONS);
}return parseError(TOKEN_INTERVAL_CONS);
}return parseError(TOKEN_INTERVAL_CONS);
}
return TOKEN_INT64_CONS;
}return parseError(TOKEN_INT8_CONS,TOKEN_INT16_CONS,TOKEN_INT32_CONS,TOKEN_INT64_CONS,TOKEN_INT64_CONS,TOKEN_INTERVAL_CONS);
}return parseError(TOKEN_INT8_CONS,TOKEN_INT16_CONS,TOKEN_INT32_CONS,TOKEN_INT64_CONS,TOKEN_INT64_CONS,TOKEN_INTERVAL_CONS);
case 'f':
currentChar = readNextChar();
if (currentChar=='l'){
currentChar = readNextChar();
if (currentChar=='o'){
currentChar = readNextChar();
if (currentChar=='a'){
currentChar = readNextChar();
if (currentChar=='t'){
currentChar = readNextChar();
return TOKEN_FLOAT_CONS;
}return parseError(TOKEN_FLOAT_CONS);
}return parseError(TOKEN_FLOAT_CONS);
}return parseError(TOKEN_FLOAT_CONS);
}if (currentChar=='a'){
currentChar = readNextChar();
if (currentChar=='l'){
currentChar = readNextChar();
if (currentChar=='s'){
currentChar = readNextChar();
if (currentChar=='e'){
currentChar = readNextChar();
return TOKEN_FALSE_LITERAL;
}return parseError(TOKEN_FALSE_LITERAL);
}return parseError(TOKEN_FALSE_LITERAL);
}return parseError(TOKEN_FALSE_LITERAL);
}return parseError(TOKEN_FLOAT_CONS,TOKEN_FALSE_LITERAL);
case 'd':
currentChar = readNextChar();
switch(currentChar){
case 'o':
currentChar = readNextChar();
if (currentChar=='u'){
currentChar = readNextChar();
if (currentChar=='b'){
currentChar = readNextChar();
if (currentChar=='l'){
currentChar = readNextChar();
if (currentChar=='e'){
currentChar = readNextChar();
return TOKEN_DOUBLE_CONS;
}return parseError(TOKEN_DOUBLE_CONS);
}return parseError(TOKEN_DOUBLE_CONS);
}return parseError(TOKEN_DOUBLE_CONS);
}return parseError(TOKEN_DOUBLE_CONS);
case 'a':
currentChar = readNextChar();
if (currentChar=='t'){
currentChar = readNextChar();
if (currentChar=='e'){
currentChar = readNextChar();
if (currentChar=='t'){
currentChar = readNextChar();
if (currentChar=='i'){
currentChar = readNextChar();
if (currentChar=='m'){
currentChar = readNextChar();
if (currentChar=='e'){
currentChar = readNextChar();
return TOKEN_DATETIME_CONS;
}return parseError(TOKEN_DATETIME_CONS);
}return parseError(TOKEN_DATETIME_CONS);
}return parseError(TOKEN_DATETIME_CONS);
}return TOKEN_DATE_CONS;
}return parseError(TOKEN_DATE_CONS,TOKEN_DATETIME_CONS);
}if (currentChar=='y'){
currentChar = readNextChar();
if (currentChar=='-'){
currentChar = readNextChar();
if (currentChar=='t'){
currentChar = readNextChar();
if (currentChar=='i'){
currentChar = readNextChar();
if (currentChar=='m'){
currentChar = readNextChar();
if (currentChar=='e'){
currentChar = readNextChar();
if (currentChar=='-'){
currentChar = readNextChar();
if (currentChar=='d'){
currentChar = readNextChar();
if (currentChar=='u'){
currentChar = readNextChar();
if (currentChar=='r'){
currentChar = readNextChar();
if (currentChar=='a'){
currentChar = readNextChar();
if (currentChar=='t'){
currentChar = readNextChar();
if (currentChar=='i'){
currentChar = readNextChar();
if (currentChar=='o'){
currentChar = readNextChar();
if (currentChar=='n'){
currentChar = readNextChar();
return TOKEN_DAY_TIME_DURATION_CONS;
}return parseError(TOKEN_DAY_TIME_DURATION_CONS);
}return parseError(TOKEN_DAY_TIME_DURATION_CONS);
}return parseError(TOKEN_DAY_TIME_DURATION_CONS);
}return parseError(TOKEN_DAY_TIME_DURATION_CONS);
}return parseError(TOKEN_DAY_TIME_DURATION_CONS);
}return parseError(TOKEN_DAY_TIME_DURATION_CONS);
}return parseError(TOKEN_DAY_TIME_DURATION_CONS);
}return parseError(TOKEN_DAY_TIME_DURATION_CONS);
}return parseError(TOKEN_DAY_TIME_DURATION_CONS);
}return parseError(TOKEN_DAY_TIME_DURATION_CONS);
}return parseError(TOKEN_DAY_TIME_DURATION_CONS);
}return parseError(TOKEN_DAY_TIME_DURATION_CONS);
}return parseError(TOKEN_DAY_TIME_DURATION_CONS);
}return parseError(TOKEN_DAY_TIME_DURATION_CONS);
}return parseError(TOKEN_DATE_CONS,TOKEN_DATETIME_CONS,TOKEN_DAY_TIME_DURATION_CONS);
case 'u':
currentChar = readNextChar();
if (currentChar=='r'){
currentChar = readNextChar();
if (currentChar=='a'){
currentChar = readNextChar();
if (currentChar=='t'){
currentChar = readNextChar();
if (currentChar=='i'){
currentChar = readNextChar();
if (currentChar=='o'){
currentChar = readNextChar();
if (currentChar=='n'){
currentChar = readNextChar();
return TOKEN_DURATION_CONS;
}return parseError(TOKEN_DURATION_CONS);
}return parseError(TOKEN_DURATION_CONS);
}return parseError(TOKEN_DURATION_CONS);
}return parseError(TOKEN_DURATION_CONS);
}return parseError(TOKEN_DURATION_CONS);
}return parseError(TOKEN_DURATION_CONS);
}
return parseError(TOKEN_DOUBLE_CONS,TOKEN_DATE_CONS,TOKEN_DATETIME_CONS,TOKEN_DURATION_CONS,TOKEN_DAY_TIME_DURATION_CONS);
case 's':
currentChar = readNextChar();
if (currentChar=='t'){
currentChar = readNextChar();
if (currentChar=='r'){
currentChar = readNextChar();
if (currentChar=='i'){
currentChar = readNextChar();
if (currentChar=='n'){
currentChar = readNextChar();
if (currentChar=='g'){
currentChar = readNextChar();
return TOKEN_STRING_CONS;
}return parseError(TOKEN_STRING_CONS);
}return parseError(TOKEN_STRING_CONS);
}return parseError(TOKEN_STRING_CONS);
}return parseError(TOKEN_STRING_CONS);
}return parseError(TOKEN_STRING_CONS);
case 'h':
currentChar = readNextChar();
if (currentChar=='e'){
currentChar = readNextChar();
if (currentChar=='x'){
currentChar = readNextChar();
return TOKEN_HEX_CONS;
}return parseError(TOKEN_HEX_CONS);
}return parseError(TOKEN_HEX_CONS);
case 'p':
currentChar = readNextChar();
if (currentChar=='o'){
currentChar = readNextChar();
if (currentChar=='i'){
currentChar = readNextChar();
if (currentChar=='n'){
currentChar = readNextChar();
if (currentChar=='t'){
currentChar = readNextChar();
if (currentChar=='3'){
currentChar = readNextChar();
if (currentChar=='d'){
currentChar = readNextChar();
return TOKEN_POINT3D_CONS;
}return parseError(TOKEN_POINT3D_CONS);
}return TOKEN_POINT_CONS;
}return parseError(TOKEN_POINT_CONS,TOKEN_POINT3D_CONS);
}return parseError(TOKEN_POINT_CONS,TOKEN_POINT3D_CONS);
}if (currentChar=='l'){
currentChar = readNextChar();
if (currentChar=='y'){
currentChar = readNextChar();
if (currentChar=='g'){
currentChar = readNextChar();
if (currentChar=='o'){
currentChar = readNextChar();
if (currentChar=='n'){
currentChar = readNextChar();
return TOKEN_POLYGON_CONS;
}return parseError(TOKEN_POLYGON_CONS);
}return parseError(TOKEN_POLYGON_CONS);
}return parseError(TOKEN_POLYGON_CONS);
}return parseError(TOKEN_POLYGON_CONS);
}return parseError(TOKEN_POINT_CONS,TOKEN_POINT3D_CONS,TOKEN_POLYGON_CONS);
}return parseError(TOKEN_POINT_CONS,TOKEN_POINT3D_CONS,TOKEN_POLYGON_CONS);
case 'l':
currentChar = readNextChar();
if (currentChar=='i'){
currentChar = readNextChar();
if (currentChar=='n'){
currentChar = readNextChar();
if (currentChar=='e'){
currentChar = readNextChar();
return TOKEN_LINE_CONS;
}return parseError(TOKEN_LINE_CONS);
}return parseError(TOKEN_LINE_CONS);
}return parseError(TOKEN_LINE_CONS);
case 'r':
currentChar = readNextChar();
if (currentChar=='e'){
currentChar = readNextChar();
if (currentChar=='c'){
currentChar = readNextChar();
if (currentChar=='t'){
currentChar = readNextChar();
if (currentChar=='a'){
currentChar = readNextChar();
if (currentChar=='n'){
currentChar = readNextChar();
if (currentChar=='g'){
currentChar = readNextChar();
if (currentChar=='l'){
currentChar = readNextChar();
if (currentChar=='e'){
currentChar = readNextChar();
return TOKEN_RECTANGLE_CONS;
}return parseError(TOKEN_RECTANGLE_CONS);
}return parseError(TOKEN_RECTANGLE_CONS);
}return parseError(TOKEN_RECTANGLE_CONS);
}return parseError(TOKEN_RECTANGLE_CONS);
}return parseError(TOKEN_RECTANGLE_CONS);
}return parseError(TOKEN_RECTANGLE_CONS);
}return parseError(TOKEN_RECTANGLE_CONS);
}return parseError(TOKEN_RECTANGLE_CONS);
case 'c':
currentChar = readNextChar();
if (currentChar=='i'){
currentChar = readNextChar();
if (currentChar=='r'){
currentChar = readNextChar();
if (currentChar=='c'){
currentChar = readNextChar();
if (currentChar=='l'){
currentChar = readNextChar();
if (currentChar=='e'){
currentChar = readNextChar();
return TOKEN_CIRCLE_CONS;
}return parseError(TOKEN_CIRCLE_CONS);
}return parseError(TOKEN_CIRCLE_CONS);
}return parseError(TOKEN_CIRCLE_CONS);
}return parseError(TOKEN_CIRCLE_CONS);
}return parseError(TOKEN_CIRCLE_CONS);
case 't':
currentChar = readNextChar();
if (currentChar=='i'){
currentChar = readNextChar();
if (currentChar=='m'){
currentChar = readNextChar();
if (currentChar=='e'){
currentChar = readNextChar();
return TOKEN_TIME_CONS;
}return parseError(TOKEN_TIME_CONS);
}return parseError(TOKEN_TIME_CONS);
}if (currentChar=='r'){
currentChar = readNextChar();
if (currentChar=='u'){
currentChar = readNextChar();
if (currentChar=='e'){
currentChar = readNextChar();
return TOKEN_TRUE_LITERAL;
}return parseError(TOKEN_TRUE_LITERAL);
}return parseError(TOKEN_TRUE_LITERAL);
}return parseError(TOKEN_TIME_CONS,TOKEN_TRUE_LITERAL);
case 'y':
currentChar = readNextChar();
if (currentChar=='e'){
currentChar = readNextChar();
if (currentChar=='a'){
currentChar = readNextChar();
if (currentChar=='r'){
currentChar = readNextChar();
if (currentChar=='-'){
currentChar = readNextChar();
if (currentChar=='m'){
currentChar = readNextChar();
if (currentChar=='o'){
currentChar = readNextChar();
if (currentChar=='n'){
currentChar = readNextChar();
if (currentChar=='t'){
currentChar = readNextChar();
if (currentChar=='h'){
currentChar = readNextChar();
if (currentChar=='-'){
currentChar = readNextChar();
if (currentChar=='d'){
currentChar = readNextChar();
if (currentChar=='u'){
currentChar = readNextChar();
if (currentChar=='r'){
currentChar = readNextChar();
if (currentChar=='a'){
currentChar = readNextChar();
if (currentChar=='t'){
currentChar = readNextChar();
if (currentChar=='i'){
currentChar = readNextChar();
if (currentChar=='o'){
currentChar = readNextChar();
if (currentChar=='n'){
currentChar = readNextChar();
return TOKEN_YEAR_MONTH_DURATION_CONS;
}return parseError(TOKEN_YEAR_MONTH_DURATION_CONS);
}return parseError(TOKEN_YEAR_MONTH_DURATION_CONS);
}return parseError(TOKEN_YEAR_MONTH_DURATION_CONS);
}return parseError(TOKEN_YEAR_MONTH_DURATION_CONS);
}return parseError(TOKEN_YEAR_MONTH_DURATION_CONS);
}return parseError(TOKEN_YEAR_MONTH_DURATION_CONS);
}return parseError(TOKEN_YEAR_MONTH_DURATION_CONS);
}return parseError(TOKEN_YEAR_MONTH_DURATION_CONS);
}return parseError(TOKEN_YEAR_MONTH_DURATION_CONS);
}return parseError(TOKEN_YEAR_MONTH_DURATION_CONS);
}return parseError(TOKEN_YEAR_MONTH_DURATION_CONS);
}return parseError(TOKEN_YEAR_MONTH_DURATION_CONS);
}return parseError(TOKEN_YEAR_MONTH_DURATION_CONS);
}return parseError(TOKEN_YEAR_MONTH_DURATION_CONS);
}return parseError(TOKEN_YEAR_MONTH_DURATION_CONS);
}return parseError(TOKEN_YEAR_MONTH_DURATION_CONS);
}return parseError(TOKEN_YEAR_MONTH_DURATION_CONS);
}return parseError(TOKEN_YEAR_MONTH_DURATION_CONS);
case 'u':
currentChar = readNextChar();
if (currentChar=='u'){
currentChar = readNextChar();
if (currentChar=='i'){
currentChar = readNextChar();
if (currentChar=='d'){
currentChar = readNextChar();
return TOKEN_UUID_CONS;
}return parseError(TOKEN_UUID_CONS);
}return parseError(TOKEN_UUID_CONS);
}return parseError(TOKEN_UUID_CONS);
case 'n':
currentChar = readNextChar();
if (currentChar=='u'){
currentChar = readNextChar();
if (currentChar=='l'){
currentChar = readNextChar();
if (currentChar=='l'){
currentChar = readNextChar();
return TOKEN_NULL_LITERAL;
}return parseError(TOKEN_NULL_LITERAL);
}return parseError(TOKEN_NULL_LITERAL);
}return parseError(TOKEN_NULL_LITERAL);
case '(':
currentChar = readNextChar();
return TOKEN_CONSTRUCTOR_OPEN;
case ')':
currentChar = readNextChar();
return TOKEN_CONSTRUCTOR_CLOSE;
case '{':
currentChar = readNextChar();
if (currentChar=='{'){
currentChar = readNextChar();
return TOKEN_START_UNORDERED_LIST;
}return TOKEN_START_RECORD;
case '}':
currentChar = readNextChar();
return TOKEN_END_RECORD;
case ',':
currentChar = readNextChar();
return TOKEN_COMMA;
case ':':
currentChar = readNextChar();
return TOKEN_COLON;
case '[':
currentChar = readNextChar();
return TOKEN_START_ORDERED_LIST;
case ']':
currentChar = readNextChar();
return TOKEN_END_ORDERED_LIST;
case '"':
currentChar = readNextChar();
boolean escaped = false;
while (currentChar != '"' || escaped) {
if(!escaped && currentChar == '\\') {
escaped = true;
containsEscapes = true;
} else {
escaped = false;
}
currentChar = readNextChar();
}
if (currentChar == '"') {
currentChar = readNextChar();
return TOKEN_STRING_LITERAL;
}
return parseError(TOKEN_STRING_LITERAL);
case '+':
currentChar = readNextChar();
if(currentChar >= '0' && currentChar<='9'){
currentChar = readNextChar();
while(currentChar >= '0' && currentChar<='9'){
currentChar = readNextChar();
}

if (currentChar=='i'){
currentChar = readNextChar();
switch(currentChar){
case '8':
currentChar = readNextChar();
return TOKEN_INT8_LITERAL;
case '1':
currentChar = readNextChar();
if (currentChar=='6'){
currentChar = readNextChar();
return TOKEN_INT16_LITERAL;
}return parseError(TOKEN_INT16_LITERAL);
case '3':
currentChar = readNextChar();
if (currentChar=='2'){
currentChar = readNextChar();
return TOKEN_INT32_LITERAL;
}return parseError(TOKEN_INT32_LITERAL);
case '6':
currentChar = readNextChar();
if (currentChar=='4'){
currentChar = readNextChar();
return TOKEN_INT64_LITERAL;
}return parseError(TOKEN_INT64_LITERAL);
}
return parseError(TOKEN_INT8_LITERAL,TOKEN_INT16_LITERAL,TOKEN_INT32_LITERAL,TOKEN_INT64_LITERAL);
}if (currentChar=='.'){
currentChar = readNextChar();
if(currentChar >= '0' && currentChar<='9'){
currentChar = readNextChar();
while(currentChar >= '0' && currentChar<='9'){
currentChar = readNextChar();
}

switch(currentChar){
case 'd':
currentChar = readNextChar();
if (currentChar=='f'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}if (currentChar=='F'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}return TOKEN_DOUBLE_LITERAL;
case 'D':
currentChar = readNextChar();
if (currentChar=='f'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}if (currentChar=='F'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}return TOKEN_DOUBLE_LITERAL;
case 'f':
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
case 'F':
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}
if (parse_aux_EXPONENT(currentChar)==TOKEN_aux_EXPONENT){currentChar = buffer[bufpos];
switch(currentChar){
case 'd':
currentChar = readNextChar();
if (currentChar=='f'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}if (currentChar=='F'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}return TOKEN_DOUBLE_LITERAL;
case 'D':
currentChar = readNextChar();
if (currentChar=='f'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}if (currentChar=='F'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}return TOKEN_DOUBLE_LITERAL;
case 'f':
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
case 'F':
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}
return TOKEN_DOUBLE_LITERAL;
}else { currentChar = buffer[bufpos];}return TOKEN_DOUBLE_LITERAL;

}return parseError(TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_FLOAT_LITERAL);
}if (parse_aux_EXPONENT(currentChar)==TOKEN_aux_EXPONENT){currentChar = buffer[bufpos];
switch(currentChar){
case 'd':
currentChar = readNextChar();
if (currentChar=='f'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}if (currentChar=='F'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}return TOKEN_DOUBLE_LITERAL;
case 'D':
currentChar = readNextChar();
if (currentChar=='f'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}if (currentChar=='F'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}return TOKEN_DOUBLE_LITERAL;
case 'f':
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
case 'F':
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}
return TOKEN_DOUBLE_LITERAL;
}else { currentChar = buffer[bufpos];}return TOKEN_INT_LITERAL;

}if (currentChar=='.'){
currentChar = readNextChar();
if(currentChar >= '0' && currentChar<='9'){
currentChar = readNextChar();
while(currentChar >= '0' && currentChar<='9'){
currentChar = readNextChar();
}

switch(currentChar){
case 'd':
currentChar = readNextChar();
if (currentChar=='f'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}if (currentChar=='F'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}return TOKEN_DOUBLE_LITERAL;
case 'D':
currentChar = readNextChar();
if (currentChar=='f'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}if (currentChar=='F'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}return TOKEN_DOUBLE_LITERAL;
case 'f':
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
case 'F':
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}
return TOKEN_DOUBLE_LITERAL;

}return parseError(TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_FLOAT_LITERAL);
}return parseError(TOKEN_INT_LITERAL,TOKEN_INT_LITERAL,TOKEN_INT8_LITERAL,TOKEN_INT_LITERAL,TOKEN_INT16_LITERAL,TOKEN_INT_LITERAL,TOKEN_INT32_LITERAL,TOKEN_INT_LITERAL,TOKEN_INT64_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_FLOAT_LITERAL);
case '-':
currentChar = readNextChar();
if(currentChar >= '0' && currentChar<='9'){
currentChar = readNextChar();
while(currentChar >= '0' && currentChar<='9'){
currentChar = readNextChar();
}

if (currentChar=='i'){
currentChar = readNextChar();
switch(currentChar){
case '8':
currentChar = readNextChar();
return TOKEN_INT8_LITERAL;
case '1':
currentChar = readNextChar();
if (currentChar=='6'){
currentChar = readNextChar();
return TOKEN_INT16_LITERAL;
}return parseError(TOKEN_INT16_LITERAL);
case '3':
currentChar = readNextChar();
if (currentChar=='2'){
currentChar = readNextChar();
return TOKEN_INT32_LITERAL;
}return parseError(TOKEN_INT32_LITERAL);
case '6':
currentChar = readNextChar();
if (currentChar=='4'){
currentChar = readNextChar();
return TOKEN_INT64_LITERAL;
}return parseError(TOKEN_INT64_LITERAL);
}
return parseError(TOKEN_INT8_LITERAL,TOKEN_INT16_LITERAL,TOKEN_INT32_LITERAL,TOKEN_INT64_LITERAL);
}if (currentChar=='.'){
currentChar = readNextChar();
if(currentChar >= '0' && currentChar<='9'){
currentChar = readNextChar();
while(currentChar >= '0' && currentChar<='9'){
currentChar = readNextChar();
}

switch(currentChar){
case 'd':
currentChar = readNextChar();
if (currentChar=='f'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}if (currentChar=='F'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}return TOKEN_DOUBLE_LITERAL;
case 'D':
currentChar = readNextChar();
if (currentChar=='f'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}if (currentChar=='F'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}return TOKEN_DOUBLE_LITERAL;
case 'f':
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
case 'F':
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}
if (parse_aux_EXPONENT(currentChar)==TOKEN_aux_EXPONENT){currentChar = buffer[bufpos];
switch(currentChar){
case 'd':
currentChar = readNextChar();
if (currentChar=='f'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}if (currentChar=='F'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}return TOKEN_DOUBLE_LITERAL;
case 'D':
currentChar = readNextChar();
if (currentChar=='f'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}if (currentChar=='F'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}return TOKEN_DOUBLE_LITERAL;
case 'f':
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
case 'F':
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}
return TOKEN_DOUBLE_LITERAL;
}else { currentChar = buffer[bufpos];}return TOKEN_DOUBLE_LITERAL;

}return parseError(TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_FLOAT_LITERAL);
}if (parse_aux_EXPONENT(currentChar)==TOKEN_aux_EXPONENT){currentChar = buffer[bufpos];
switch(currentChar){
case 'd':
currentChar = readNextChar();
if (currentChar=='f'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}if (currentChar=='F'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}return TOKEN_DOUBLE_LITERAL;
case 'D':
currentChar = readNextChar();
if (currentChar=='f'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}if (currentChar=='F'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}return TOKEN_DOUBLE_LITERAL;
case 'f':
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
case 'F':
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}
return TOKEN_DOUBLE_LITERAL;
}else { currentChar = buffer[bufpos];}return TOKEN_INT_LITERAL;

}if (currentChar=='.'){
currentChar = readNextChar();
if(currentChar >= '0' && currentChar<='9'){
currentChar = readNextChar();
while(currentChar >= '0' && currentChar<='9'){
currentChar = readNextChar();
}

switch(currentChar){
case 'd':
currentChar = readNextChar();
if (currentChar=='f'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}if (currentChar=='F'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}return TOKEN_DOUBLE_LITERAL;
case 'D':
currentChar = readNextChar();
if (currentChar=='f'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}if (currentChar=='F'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}return TOKEN_DOUBLE_LITERAL;
case 'f':
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
case 'F':
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}
return TOKEN_DOUBLE_LITERAL;

}return parseError(TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_FLOAT_LITERAL);
}if (currentChar=='I'){
currentChar = readNextChar();
if (currentChar=='N'){
currentChar = readNextChar();
if (currentChar=='F'){
currentChar = readNextChar();
switch(currentChar){
case 'd':
currentChar = readNextChar();
if (currentChar=='f'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}if (currentChar=='F'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}return TOKEN_DOUBLE_LITERAL;
case 'D':
currentChar = readNextChar();
if (currentChar=='f'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}if (currentChar=='F'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}return TOKEN_DOUBLE_LITERAL;
case 'f':
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
case 'F':
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}
return TOKEN_DOUBLE_LITERAL;
}return parseError(TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_FLOAT_LITERAL);
}return parseError(TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_FLOAT_LITERAL);
}return parseError(TOKEN_INT_LITERAL,TOKEN_INT_LITERAL,TOKEN_INT8_LITERAL,TOKEN_INT_LITERAL,TOKEN_INT16_LITERAL,TOKEN_INT_LITERAL,TOKEN_INT32_LITERAL,TOKEN_INT_LITERAL,TOKEN_INT64_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_FLOAT_LITERAL);
case '.':
currentChar = readNextChar();
if(currentChar >= '0' && currentChar<='9'){
currentChar = readNextChar();
while(currentChar >= '0' && currentChar<='9'){
currentChar = readNextChar();
}

switch(currentChar){
case 'd':
currentChar = readNextChar();
if (currentChar=='f'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}if (currentChar=='F'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}return TOKEN_DOUBLE_LITERAL;
case 'D':
currentChar = readNextChar();
if (currentChar=='f'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}if (currentChar=='F'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}return TOKEN_DOUBLE_LITERAL;
case 'f':
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
case 'F':
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}
return TOKEN_DOUBLE_LITERAL;

}return parseError(TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_FLOAT_LITERAL);
case 'N':
currentChar = readNextChar();
if (currentChar=='a'){
currentChar = readNextChar();
if (currentChar=='N'){
currentChar = readNextChar();
switch(currentChar){
case 'd':
currentChar = readNextChar();
if (currentChar=='f'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}if (currentChar=='F'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}return TOKEN_DOUBLE_LITERAL;
case 'D':
currentChar = readNextChar();
if (currentChar=='f'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}if (currentChar=='F'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}return TOKEN_DOUBLE_LITERAL;
case 'f':
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
case 'F':
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}
return TOKEN_DOUBLE_LITERAL;
}return parseError(TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_FLOAT_LITERAL);
}return parseError(TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_FLOAT_LITERAL);
case 'I':
currentChar = readNextChar();
if (currentChar=='N'){
currentChar = readNextChar();
if (currentChar=='F'){
currentChar = readNextChar();
switch(currentChar){
case 'd':
currentChar = readNextChar();
if (currentChar=='f'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}if (currentChar=='F'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}return TOKEN_DOUBLE_LITERAL;
case 'D':
currentChar = readNextChar();
if (currentChar=='f'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}if (currentChar=='F'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}return TOKEN_DOUBLE_LITERAL;
case 'f':
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
case 'F':
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}
return TOKEN_DOUBLE_LITERAL;
}return parseError(TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_FLOAT_LITERAL);
}return parseError(TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_FLOAT_LITERAL);
}
if(currentChar >= '0' && currentChar<='9'){
currentChar = readNextChar();
while(currentChar >= '0' && currentChar<='9'){
currentChar = readNextChar();
}

if (currentChar=='i'){
currentChar = readNextChar();
switch(currentChar){
case '8':
currentChar = readNextChar();
return TOKEN_INT8_LITERAL;
case '1':
currentChar = readNextChar();
if (currentChar=='6'){
currentChar = readNextChar();
return TOKEN_INT16_LITERAL;
}return parseError(TOKEN_INT16_LITERAL);
case '3':
currentChar = readNextChar();
if (currentChar=='2'){
currentChar = readNextChar();
return TOKEN_INT32_LITERAL;
}return parseError(TOKEN_INT32_LITERAL);
case '6':
currentChar = readNextChar();
if (currentChar=='4'){
currentChar = readNextChar();
return TOKEN_INT64_LITERAL;
}return parseError(TOKEN_INT64_LITERAL);
}
return parseError(TOKEN_INT8_LITERAL,TOKEN_INT16_LITERAL,TOKEN_INT32_LITERAL,TOKEN_INT64_LITERAL);
}if (currentChar=='.'){
currentChar = readNextChar();
if(currentChar >= '0' && currentChar<='9'){
currentChar = readNextChar();
while(currentChar >= '0' && currentChar<='9'){
currentChar = readNextChar();
}

switch(currentChar){
case 'd':
currentChar = readNextChar();
if (currentChar=='f'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}if (currentChar=='F'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}return TOKEN_DOUBLE_LITERAL;
case 'D':
currentChar = readNextChar();
if (currentChar=='f'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}if (currentChar=='F'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}return TOKEN_DOUBLE_LITERAL;
case 'f':
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
case 'F':
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}
if (parse_aux_EXPONENT(currentChar)==TOKEN_aux_EXPONENT){currentChar = buffer[bufpos];
switch(currentChar){
case 'd':
currentChar = readNextChar();
if (currentChar=='f'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}if (currentChar=='F'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}return TOKEN_DOUBLE_LITERAL;
case 'D':
currentChar = readNextChar();
if (currentChar=='f'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}if (currentChar=='F'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}return TOKEN_DOUBLE_LITERAL;
case 'f':
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
case 'F':
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}
return TOKEN_DOUBLE_LITERAL;
}else { currentChar = buffer[bufpos];}return TOKEN_DOUBLE_LITERAL;

}return parseError(TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_FLOAT_LITERAL);
}if (parse_aux_EXPONENT(currentChar)==TOKEN_aux_EXPONENT){currentChar = buffer[bufpos];
switch(currentChar){
case 'd':
currentChar = readNextChar();
if (currentChar=='f'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}if (currentChar=='F'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}return TOKEN_DOUBLE_LITERAL;
case 'D':
currentChar = readNextChar();
if (currentChar=='f'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}if (currentChar=='F'){
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}return TOKEN_DOUBLE_LITERAL;
case 'f':
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
case 'F':
currentChar = readNextChar();
return TOKEN_FLOAT_LITERAL;
}
return TOKEN_DOUBLE_LITERAL;
}else { currentChar = buffer[bufpos];}return TOKEN_INT_LITERAL;

}return parseError(TOKEN_BOOLEAN_CONS,TOKEN_INT8_CONS,TOKEN_INT16_CONS,TOKEN_INT32_CONS,TOKEN_INT64_CONS,TOKEN_INT64_CONS,TOKEN_FLOAT_CONS,TOKEN_DOUBLE_CONS,TOKEN_DATE_CONS,TOKEN_DATETIME_CONS,TOKEN_DURATION_CONS,TOKEN_STRING_CONS,TOKEN_HEX_CONS,TOKEN_BASE64_CONS,TOKEN_POINT_CONS,TOKEN_POINT3D_CONS,TOKEN_LINE_CONS,TOKEN_POLYGON_CONS,TOKEN_RECTANGLE_CONS,TOKEN_CIRCLE_CONS,TOKEN_TIME_CONS,TOKEN_INTERVAL_CONS,TOKEN_YEAR_MONTH_DURATION_CONS,TOKEN_DAY_TIME_DURATION_CONS,TOKEN_UUID_CONS,TOKEN_NULL_LITERAL,TOKEN_TRUE_LITERAL,TOKEN_FALSE_LITERAL,TOKEN_CONSTRUCTOR_OPEN,TOKEN_CONSTRUCTOR_CLOSE,TOKEN_START_RECORD,TOKEN_END_RECORD,TOKEN_COMMA,TOKEN_COLON,TOKEN_START_ORDERED_LIST,TOKEN_END_ORDERED_LIST,TOKEN_START_UNORDERED_LIST,TOKEN_STRING_LITERAL,TOKEN_INT_LITERAL,TOKEN_INT_LITERAL,TOKEN_INT8_LITERAL,TOKEN_INT_LITERAL,TOKEN_INT16_LITERAL,TOKEN_INT_LITERAL,TOKEN_INT32_LITERAL,TOKEN_INT_LITERAL,TOKEN_INT64_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_DOUBLE_LITERAL,TOKEN_FLOAT_LITERAL);

    }

// ================================================================================
//  Public interface
// ================================================================================

    public AdmLexer(java.io.Reader stream) throws IOException {
        reInit(stream);
    }

    public AdmLexer() {
        reInit();
    }

    public void setBuffer(char[] buffer) {
        this.buffer = buffer;
        tokenBegin = bufpos = 0;
        containsEscapes = false;
        tokenBegin = -1;
    }

    public void reInit() {
        bufsize        = Integer.MAX_VALUE;
        endOf_UNUSED_Buffer = bufsize;
        endOf_USED_Buffer = bufsize;
        line           = 0;
        prevCharIsCR   = false;
        prevCharIsLF   = false;
        tokenBegin     = -1;
        maxUnusedBufferSize = bufsize;
    }

    public void reInit(java.io.Reader stream) throws IOException {
        done();
        inputStream    = stream;
        bufsize        = 4096;
        line           = 1;
        column         = 0;
        bufpos         = -1;
        endOf_UNUSED_Buffer = bufsize;
        endOf_USED_Buffer = 0;
        prevCharIsCR   = false;
        prevCharIsLF   = false;
        buffer         = new char[bufsize];
        tokenBegin     = -1;
        maxUnusedBufferSize = 4096/2;
        readNextChar();
    }

    public String getLastTokenImage() {
        if (bufpos >= tokenBegin) {
            return new String(buffer, tokenBegin, bufpos - tokenBegin);
        } else {
            return new String(buffer, tokenBegin, bufsize - tokenBegin) + new String(buffer, 0, bufpos);
        }
    }

    public int getColumn() {
        return column;
    }

    public int getLine() {
        return line;
    }

    public boolean containsEscapes() {
        return containsEscapes;
    }

    public static String tokenKindToString(int token) {
        return tokenImage[token];
    }

    public void done(){
        buffer = null;
    }

// ================================================================================
//  Parse error management
// ================================================================================

    protected int parseError(int ... tokens) throws AdmLexerException {
        StringBuilder message = new StringBuilder();
        message.append("Parse error at (").append(line).append(", ").append(column).append(")");
        if (tokens.length > 0) {
            message.append(" expecting:");
            for (int tokenId : tokens){
                message.append(" ").append(AdmLexer.tokenKindToString(tokenId));
            }
        }
        throw new AdmLexerException(message.toString());
    }

    protected void updateLineColumn(char c){
        column++;

        if (prevCharIsLF) {
            prevCharIsLF = false;
            line += (column = 1);
        } else if (prevCharIsCR) {
            prevCharIsCR = false;
            if (c == '\n') {
                prevCharIsLF = true;
            } else {
                line += (column = 1);
            }
        }

        if (c=='\r') {
            prevCharIsCR = true;
        } else if (c == '\n') {
            prevCharIsLF = true;
        }
    }

// ================================================================================
//  Read data, buffer management. It uses a circular (and expandable) buffer
// ================================================================================

    protected char readNextChar() throws IOException {
        if (++bufpos >= endOf_USED_Buffer) {
            fillBuff();
        }
        char c = buffer[bufpos];
        updateLineColumn(c);
        return c;
    }

    protected boolean fillBuff() throws IOException {
        if (endOf_UNUSED_Buffer == endOf_USED_Buffer) {
            // If no more unused buffer space
            if (endOf_UNUSED_Buffer == bufsize) {
                // If the previous unused space was at the end of the buffer
                if (tokenBegin > maxUnusedBufferSize) {
                    // If the first N bytes before the current token are enough
                    // setup buffer to use that fragment
                    bufpos = endOf_USED_Buffer = 0;
                    endOf_UNUSED_Buffer = tokenBegin;
                } else if (tokenBegin < 0){
                    // If no token yet
                    // reuse the whole buffer
                    bufpos = endOf_USED_Buffer = 0;
                } else {
                    // Otherwise expand buffer after its end
                    ExpandBuff(false);
                }
            } else if (endOf_UNUSED_Buffer > tokenBegin){
                // If the endOf_UNUSED_Buffer is after the token
                // set endOf_UNUSED_Buffer to the end of the buffer
                endOf_UNUSED_Buffer = bufsize;
            } else if ((tokenBegin - endOf_UNUSED_Buffer) < maxUnusedBufferSize) {
                // If between endOf_UNUSED_Buffer and the token there is NOT enough space expand the buffer
                // reorganizing it
                ExpandBuff(true);
            } else {
                // Otherwise there is enough space at the start
                // so we set the buffer to use that fragment
                endOf_UNUSED_Buffer = tokenBegin;
            }
        }
        int i;
        if ((i = inputStream.read(buffer, endOf_USED_Buffer, endOf_UNUSED_Buffer - endOf_USED_Buffer)) == -1) {
            inputStream.close();
            buffer[endOf_USED_Buffer] = (char)EOF_CHAR;
            endOf_USED_Buffer++;
            return false;
        } else {
            endOf_USED_Buffer += i;
        }
        return true;
    }


    protected void ExpandBuff(boolean wrapAround) {
        char[] newbuffer = new char[bufsize + maxUnusedBufferSize];

        try {
            if (wrapAround) {
                System.arraycopy(buffer, tokenBegin, newbuffer, 0, bufsize - tokenBegin);
                System.arraycopy(buffer, 0, newbuffer, bufsize - tokenBegin, bufpos);
                buffer = newbuffer;
                endOf_USED_Buffer = (bufpos += (bufsize - tokenBegin));
            } else {
                System.arraycopy(buffer, tokenBegin, newbuffer, 0, bufsize - tokenBegin);
                buffer = newbuffer;
                endOf_USED_Buffer = (bufpos -= tokenBegin);
            }
        } catch (Throwable t) {
            throw new Error(t.getMessage());
        }

        bufsize += maxUnusedBufferSize;
        endOf_UNUSED_Buffer = bufsize;
        tokenBegin = 0;
    }
}
