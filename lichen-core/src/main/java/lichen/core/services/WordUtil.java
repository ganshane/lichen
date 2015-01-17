// Copyright 2013 the original author or authors.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
/*
 *   This software is distributed under the terms of the FSF
 *   Gnu Lesser General Public License (see lgpl.txt).
 *
 *   This program is distributed WITHOUT ANY WARRANTY. See the
 *   GNU General Public License for more details.
 */
package lichen.core.services;

import java.util.*;


/**
 * 针对英文单词的复数转换.
 * 此文件来自 http://www.scooterframework.com.
 * <p/>
 * <p>Conversion between singular and plural form of a noun word.</p>
 *
 * @author (Fei) John Chen
 */
public final class WordUtil {

    //单数到复数的映射
    private static final Map<String, String> RESOLVED_SINGLE_2_PLURALS =
            new HashMap<String, String>();
    //已经处理过的复数
    private static final List<String> RESOLVED_PLURALS =
            new ArrayList<String>();
    //复数到单数的映射
    private static final Map<String, String> RESOLVED_PLURAL_2_SINGLES =
            new HashMap<String, String>();
    private static final List<String> RESOLVED_SINGLES =
            new ArrayList<String>();

    public static final Map<String, String> SINGLE_2PLURALS =
            new HashMap<String, String>();
    public static final List<String> PLURALS =
            new ArrayList<String>();
    public static final Map<String, String> PLURAL_2SINGLES =
            new HashMap<String, String>();
    public static final List<String> SINGLES =
            new ArrayList<String>();

    static {
        //Irregular plurals:
        SINGLE_2PLURALS.put("child", "children");
        SINGLE_2PLURALS.put("corpus", "corpora");
        SINGLE_2PLURALS.put("foot", "feet");
        SINGLE_2PLURALS.put("goose", "geese");
        SINGLE_2PLURALS.put("louse", "lice");
        SINGLE_2PLURALS.put("man", "men");
        SINGLE_2PLURALS.put("mouse", "mice");
        SINGLE_2PLURALS.put("ox", "oxen");
        SINGLE_2PLURALS.put("person", "people");
        SINGLE_2PLURALS.put("tooth", "teeth");
        SINGLE_2PLURALS.put("woman", "women");

        //Some nouns do not change at all:
        SINGLE_2PLURALS.put("cod", "cod");
        SINGLE_2PLURALS.put("deer", "deer");
        SINGLE_2PLURALS.put("fish", "fish");
        SINGLE_2PLURALS.put("offspring", "offspring");
        SINGLE_2PLURALS.put("perch", "perch");
        SINGLE_2PLURALS.put("sheep", "sheep");
        SINGLE_2PLURALS.put("trout", "trout");
        SINGLE_2PLURALS.put("species", "species");
        SINGLE_2PLURALS.put("series", "series");

        //Other nouns that do not change:
        SINGLE_2PLURALS.put("data", "data");
        SINGLE_2PLURALS.put("dice", "dice");
        SINGLE_2PLURALS.put("media", "media");

        //Singular ends in -us, plural ends in
        // -i: alumnus/alumni, focus/foci, nucleus/nuclei,
        //octopus/octopi, radius/radii, stimulus/stimuli, virus/viri
        //Exceptions to the above
        SINGLE_2PLURALS.put("bus", "buses");

        //Singular ends in -ex,
        // plural ends in -ices: appendix/appendices, index/indices
        SINGLE_2PLURALS.put("index", "indices");
        SINGLE_2PLURALS.put("vertex", "vertices");

        //These include nouns that are traditionally plural,
        // but are also used for singular forms:
        SINGLE_2PLURALS.put("barracks", "barracks");
        SINGLE_2PLURALS.put("crossroads", "crossroads");
        SINGLE_2PLURALS.put("die", "dice");
        SINGLE_2PLURALS.put("gallows", "gallows");
        SINGLE_2PLURALS.put("headquarters", "headquarters");
        SINGLE_2PLURALS.put("means", "means");
        SINGLE_2PLURALS.put("series", "series");
        SINGLE_2PLURALS.put("species", "species");

        //Exception to Rule 6: Some nouns ending in f or fe are made plural
        //by changing f or fe to ves. with the following exceptions:
        SINGLE_2PLURALS.put("chief", "chiefs");
        SINGLE_2PLURALS.put("chef", "chefs");
        SINGLE_2PLURALS.put("dwarf", "dwarfs");
        SINGLE_2PLURALS.put("hoof", "hoofs");
        SINGLE_2PLURALS.put("kerchief", "kerchiefs");
        SINGLE_2PLURALS.put("fife", "fifes");
        SINGLE_2PLURALS.put("proof", "proofs"); //m-w.com
        SINGLE_2PLURALS.put("roof", "roofs");
        SINGLE_2PLURALS.put("safe", "safes");
        SINGLE_2PLURALS.put("mischief", "mischiefs");
        SINGLE_2PLURALS.put("grief", "griefs");

        //Rule 7b: All musical terms
        // ending in -o have plurals ending in just -s.
        SINGLE_2PLURALS.put("cello", "cellos");
        SINGLE_2PLURALS.put("photo", "photos");
        SINGLE_2PLURALS.put("solo", "solos");
        SINGLE_2PLURALS.put("soprano", "sopranos");
        SINGLE_2PLURALS.put("studio", "studios");

        //Exception to Rule 7: Most nouns ending in o preceded by a consonant
        //is formed into a plural by adding es with the following exceptions:
        SINGLE_2PLURALS.put("canto", "cantos");
        SINGLE_2PLURALS.put("lasso", "lassos");
        SINGLE_2PLURALS.put("halo", "halos");
        SINGLE_2PLURALS.put("memento", "mementos");
        SINGLE_2PLURALS.put("photo", "photos");
        SINGLE_2PLURALS.put("sirocco", "siroccos");

        //Rule 7c: Plural forms of words ending in -o (-os):
        SINGLE_2PLURALS.put("albino", "albinos");
        SINGLE_2PLURALS.put("armadillo", "armadillos");
        SINGLE_2PLURALS.put("auto", "autos");
        SINGLE_2PLURALS.put("bravo", "bravos");
        SINGLE_2PLURALS.put("bronco", "broncos");
        SINGLE_2PLURALS.put("canto", "cantos");
        SINGLE_2PLURALS.put("casino", "casinos");
        SINGLE_2PLURALS.put("combo", "combos");
        SINGLE_2PLURALS.put("gazebo", "gazebos");
        SINGLE_2PLURALS.put("inferno", "infernos");
        SINGLE_2PLURALS.put("kangaroo", "kangaroos");
        SINGLE_2PLURALS.put("kilo", "kilos");
        SINGLE_2PLURALS.put("kimono", "kimonos");
        SINGLE_2PLURALS.put("logo", "logos");
        SINGLE_2PLURALS.put("maraschino", "maraschinos");
        SINGLE_2PLURALS.put("memo", "memos");
        SINGLE_2PLURALS.put("photo", "photos");
        SINGLE_2PLURALS.put("pimento", "pimentos");
        SINGLE_2PLURALS.put("poncho", "ponchos");
        SINGLE_2PLURALS.put("pro", "pros");
        SINGLE_2PLURALS.put("sombrero", "sombreros");
        SINGLE_2PLURALS.put("taco", "tacos");
        SINGLE_2PLURALS.put("tattoo", "tattoos");
        SINGLE_2PLURALS.put("torso", "torsos");
        SINGLE_2PLURALS.put("tobacco", "tobaccos");
        SINGLE_2PLURALS.put("typo", "typos");

        //Rule 7c: Plural forms of words ending in -o (-oes):
        SINGLE_2PLURALS.put("echo", "echoes");
        SINGLE_2PLURALS.put("embargo", "embargoes");
        SINGLE_2PLURALS.put("hero", "heroes");
        SINGLE_2PLURALS.put("potato", "potatoes");
        SINGLE_2PLURALS.put("tomato", "tomatoes");
        SINGLE_2PLURALS.put("torpedo", "torpedoes");
        SINGLE_2PLURALS.put("veto", "vetoes");

        //Rule 7c: Plural forms of words ending in -o (-os or -oes):
        SINGLE_2PLURALS.put("avocado", "avocados");
        SINGLE_2PLURALS.put("buffalo", "buffaloes");
        SINGLE_2PLURALS.put("cargo", "cargoes");
        SINGLE_2PLURALS.put("desperado", "desperadoes");
        SINGLE_2PLURALS.put("dodo", "dodoes");
        SINGLE_2PLURALS.put("domino", "dominoes");
        SINGLE_2PLURALS.put("ghetto", "ghettos");
        SINGLE_2PLURALS.put("grotto", "grottoes");
        SINGLE_2PLURALS.put("hobo", "hoboes");
        SINGLE_2PLURALS.put("innuendo", "innuendoes");
        SINGLE_2PLURALS.put("lasso", "lassos");
        SINGLE_2PLURALS.put("mango", "mangoes");
        SINGLE_2PLURALS.put("mosquito", "mosquitoes");
        SINGLE_2PLURALS.put("motto", "mottoes");
        SINGLE_2PLURALS.put("mulatto", "mulattos");
        SINGLE_2PLURALS.put("no", "noes");
        SINGLE_2PLURALS.put("peccadillo", "peccadilloes");
        SINGLE_2PLURALS.put("tornado", "tornadoes");
        SINGLE_2PLURALS.put("volcano", "volcanoes");
        SINGLE_2PLURALS.put("zero", "zeros");

        //others
        SINGLE_2PLURALS.put("forum", "forums");

        //Things that come in pairs
        PLURALS.add("binoculars");
        PLURALS.add("forceps");
        PLURALS.add("jeans");
        PLURALS.add("glasses");
        PLURALS.add("pajamas");
        PLURALS.add("pants");
        PLURALS.add("scissors");
        PLURALS.add("shorts");
        PLURALS.add("tongs");
        PLURALS.add("trousers");
        PLURALS.add("tweezers");

        //Nouns that end in -s but have no singular (aggregate nouns)
        PLURALS.add("accommodations");
        PLURALS.add("amends");
        PLURALS.add("archives");
        PLURALS.add("arms");
        PLURALS.add("bellows");
        PLURALS.add("bowels");
        PLURALS.add("brains");
        PLURALS.add("clothes");
        PLURALS.add("communications");
        PLURALS.add("congratulations");
        PLURALS.add("contents");
        PLURALS.add("dregs");
        PLURALS.add("goods");
        PLURALS.add("measles");
        PLURALS.add("mumps");
        PLURALS.add("oats");
        PLURALS.add("pinchers");
        PLURALS.add("shears");
        PLURALS.add("snuffers");
        PLURALS.add("stairs");
        PLURALS.add("thanks");
        PLURALS.add("vespers");
        PLURALS.add("victuals");

        //Nouns that are plural but do not end in -s
        PLURALS.add("children");
        PLURALS.add("cattle");
        PLURALS.add("corpora");
        PLURALS.add("data");
        PLURALS.add("men");
        PLURALS.add("people");
        PLURALS.add("police");
        PLURALS.add("women");

        //Nouns that are always singular -- uncountable
        SINGLES.add("cooper");
        SINGLES.add("corn");
        SINGLES.add("cotton");
        SINGLES.add("gold");
        SINGLES.add("information");
        SINGLES.add("money");
        SINGLES.add("news");
        SINGLES.add("rice");
        SINGLES.add("silver");
        SINGLES.add("sugar");
        SINGLES.add("wheat");

        //PLURAL_2SINGLES.put("data", "data");
        //PLURAL_2SINGLES.put("media", "media");
        PLURAL_2SINGLES.put("dice", "dice");
        PLURAL_2SINGLES.put("indices", "index");
        PLURAL_2SINGLES.put("vertices", "vertex");
        PLURAL_2SINGLES.put("movies", "movie");
        PLURAL_2SINGLES.put("viri", "virus");

        PLURAL_2SINGLES.put("axes", "axis");
        PLURAL_2SINGLES.put("crises", "crisis");
        PLURAL_2SINGLES.put("analyses", "analysis");
        PLURAL_2SINGLES.put("diagnoses", "diagnosis");
        PLURAL_2SINGLES.put("synopses", "synopsis");
        PLURAL_2SINGLES.put("theses", "thesis");
        PLURAL_2SINGLES.put("moves", "move");
        PLURAL_2SINGLES.put("caves", "cave");
        PLURAL_2SINGLES.put("toes", "toe");

        //merge PLURAL_2SINGLES with SINGLE_2PLURALS
        for (Map.Entry<String, String> entry : SINGLE_2PLURALS.entrySet()) {
            String sk = entry.getKey();
            String sv = entry.getValue();
            String pv = PLURAL_2SINGLES.get(sv);
            if (pv == null) {
                PLURAL_2SINGLES.put(sv, sk);
            }
        }

        //merge SINGLE_2PLURALS with PLURAL_2SINGLES
        for (Map.Entry<String, String> entry : PLURAL_2SINGLES.entrySet()) {
            String pk = entry.getKey();
            String pv = entry.getValue();
            String sv = SINGLE_2PLURALS.get(pv);
            if (sv == null) {
                SINGLE_2PLURALS.put(pv, pk);
            }
        }
    }

    /**
     * 把给定的单词转换为复数.
     *
     * @param word 待转换的单词
     * @return 复数字符串
     */
    public static String pluralize(String word) {
        if (word == null || "".equals(word)) {
            return word;
        }

        //先判断是否处理过了
        String plform = RESOLVED_SINGLE_2_PLURALS.get(word);
        if (plform == null
                && (RESOLVED_PLURALS.contains(word)
                || RESOLVED_PLURAL_2_SINGLES.containsKey(word))) {
            plform = word;
        }
        if (plform != null) return plform;

        String tmp = word.toLowerCase();
        plform = SINGLE_2PLURALS.get(tmp);
        if (plform == null && (PLURALS.contains(tmp) || SINGLES.contains(tmp) || PLURAL_2SINGLES.containsKey(tmp))) {
            plform = tmp;
        }

        if (plform != null) { //映射里面已经得到
        } else if (tmp.endsWith("is")) {
            //Rule #5: For words that end in -is, change the -is to -es to make the plural form
            plform = replaceLast(tmp, "is", "es");
        } else if (tmp.endsWith("ix")) {
            //Singular ends in -ix, plural ends in -ices: appendix/appendices, index/indices
            plform = replaceLast(tmp, "ix", "ices");
        } else if (tmp.endsWith("us")) {
            //Singular ends in -us, plural ends in -i: alumnus/alumni, focus/foci, nucleus/nuclei,
            //octopus/octopi, radius/radii, stimulus/stimuli, virus/viri
            plform = replaceLast(tmp, "us", "i");
        } else if (!tmp.endsWith("es") && (tmp.endsWith("z")
                || tmp.endsWith("x") || tmp.endsWith("ch") || tmp.endsWith("sh"))) {
            //Rule #2: For words that end in a "hissing" sound (-s, -z, -x, -ch, -sh), add an -es to form the plural.
            //Note: I removed tmp.endsWith("s") || as this cause "posts"->"postses".
            plform = tmp + "es";
        } else if (tmp.endsWith("y")) {
            //Rule #3: If the word ends in a vowel plus -y (-ay, -ey, -iy, -oy, -uy), add an -s to the word.
            if (tmp.endsWith("ay") || tmp.endsWith("ey") || tmp.endsWith("iy")
                    || tmp.endsWith("oy") || tmp.endsWith("uy")) {
                plform = word + "s";
            } else {
                //Rule #4: If the word ends in a consonant plus -y, change the -y into -ie and add an -s to form the plural.
                plform = replaceLast(tmp, "y", "ies");
            }
        }
        //Rule #6: Some words that end in -f or -fe have plurals that end in -ves.
        else if (tmp.endsWith("f")) {
            plform = replaceLast(tmp, "f", "ves");
        } else if (tmp.endsWith("fe")) {
            plform = replaceLast(tmp, "fe", "ves");
        }
        //Rule #7: The plurals of words ending in -o are formed by either adding -s or by adding -es
        else if (tmp.endsWith("o")) {
            //All words that end in a vowel plus -o (-ao, -eo, -io, -oo, -uo)
            // have plurals that end in just -s:
            if (tmp.endsWith("ao")
                    || tmp.endsWith("eo") || tmp.endsWith("io")
                    || tmp.endsWith("oo") || tmp.endsWith("uo")) {
                plform = word + "s";
            } else {
                //All musical terms ending in -o have plurals ending in just -s.
                //Most others by adding -es with exceptions
                plform = word + "es";
            }
        } else if (tmp.endsWith("um")) {
            //Singular ends in -um, plural ends in -a: datum/data, curriculum/curricula
            plform = replaceLast(tmp, "um", "a");
        } else if (tmp.endsWith("on") && !tmp.endsWith("ation")) {
            //Singular ends in -on, plural ends in -a: criterion/criteria, phenomenon/phenomena
            plform = replaceLast(tmp, "on", "a");
        } else if (tmp.endsWith("a")) {
            //Singular ends in -a, plural ends in -ae: alumna/alumnae, formula/formulae, antenna/antennae
            plform = replaceLast(tmp, "a", "ae");
        } else if (tmp.endsWith("eau")) {
            //Singular ends in -eau, plural ends in -eaux: bureau/bureaux, beau/beaux
            plform = replaceLast(tmp, "eau", "eaux");
        } else if (tmp.endsWith("man")) {
            //special
            plform = replaceLast(tmp, "man", "men");
        } else if (!tmp.endsWith("s")) {
            //Rule #1: Add an -s to form the plural of most words.
            plform = word + "s";
        } else if (word.toUpperCase().equals(word)) {
            //Rule #8: The plurals of single capital letters, acronyms, and Arabic numerals
            //(1,2,3,...) take an -s WITHOUT an apostrophe:
            plform = word + "s";
        } else {
            RESOLVED_PLURALS.add(word);
            return word;
        }

        //check cases
        boolean caseChanged = false;
        int wl = word.length();
        int pl = plform.length();
        char[] pChars = plform.toCharArray();
        int length = wl < pl ? wl : pl;
        for (int i = 0; i < length; i++) {
            char wChar = word.charAt(i);
            char pChar = plform.charAt(i);
            if (((int) wChar - (int) pChar) == -32) {
                pChars[i] = wChar;
                caseChanged = true;
            }
        }

        if (caseChanged) plform = new String(pChars);
        if (!plform.equalsIgnoreCase(word)) {
            RESOLVED_SINGLE_2_PLURALS.put(word, plform);
            RESOLVED_PLURAL_2_SINGLES.put(plform, word);
        }

        return plform;
    }


    /**
     * 从复数变换为单数
     *
     * @param word 复数形式的单词
     * @return singularized string
     */
    public static String singularize(String word) {
        if (word == null || "".equals(word)) return word;

        String sgform = RESOLVED_PLURAL_2_SINGLES.get(word);
        if (sgform == null && (RESOLVED_SINGLES.contains(word) || RESOLVED_SINGLE_2_PLURALS.containsKey(word))) {
            sgform = word;
        }
        if (sgform != null) return sgform;

        String tmp = word.toLowerCase();
        sgform = PLURAL_2SINGLES.get(tmp);
        if (sgform == null && (PLURALS.contains(tmp) || SINGLES.contains(tmp) || SINGLE_2PLURALS.containsKey(tmp))) {
            sgform = tmp;
        }

        if (sgform != null) {
        } else if (tmp.endsWith("ices")) {
            sgform = replaceLast(tmp, "ices", "ix");
        } else if (tmp.endsWith("i")) {
            sgform = replaceLast(tmp, "i", "us");
        } else if (tmp.endsWith("ses") && !tmp.endsWith("bases") ||
                tmp.endsWith("zes") || tmp.endsWith("xes") ||
                tmp.endsWith("ches") || tmp.endsWith("shes")) {
            sgform = replaceLast(tmp, "es", "");
        } else if (tmp.endsWith("ays") || tmp.endsWith("eys") || tmp.endsWith("iys") ||
                tmp.endsWith("oys") || tmp.endsWith("uys")) {
            sgform = replaceLast(tmp, "ys", "y");
        } else if (tmp.endsWith("ies")) {
            sgform = replaceLast(tmp, "ies", "y");
        }
        //Rule #7
        else if (tmp.endsWith("aos") || tmp.endsWith("eos") || tmp.endsWith("ios") ||
                tmp.endsWith("oos") || tmp.endsWith("uos")) {
            sgform = replaceLast(tmp, "os", "o");
        }
        //Rule #7
        else if (tmp.endsWith("oes")) {
            sgform = replaceLast(tmp, "oes", "o");
        } else if (tmp.endsWith("ives")) {
            sgform = replaceLast(tmp, "ves", "fe");
        } else if (tmp.endsWith("lves") || tmp.endsWith("rves") || tmp.endsWith("aves")) {
            sgform = replaceLast(tmp, "ves", "f");
        } else if (tmp.endsWith("ae")) {
            sgform = replaceLast(tmp, "ae", "a");
        } else if (tmp.endsWith("eaux")) {
            sgform = replaceLast(tmp, "eaux", "eau");
        } else if (tmp.endsWith("men")) {
            sgform = replaceLast(tmp, "men", "man");
        } else if (tmp.endsWith("s")) {
            sgform = replaceLast(tmp, "s", "");
        } else {
            sgform = tmp;
            RESOLVED_SINGLES.add(word);
            return word;
        }

        //check cases
        boolean caseChanged = false;
        int wl = word.length();
        int pl = sgform.length();
        char[] sChars = sgform.toCharArray();
        int length = wl < pl ? wl : pl;
        for (int i = 0; i < length; i++) {
            char wChar = word.charAt(i);
            char pChar = sgform.charAt(i);
            if ((int) wChar - (int) pChar == -32) {
                sChars[i] = wChar;
                caseChanged = true;
            }
        }

        if (caseChanged) sgform = new String(sChars);
        if (!sgform.equalsIgnoreCase(word)) {
            RESOLVED_PLURAL_2_SINGLES.put(word, sgform);
            RESOLVED_SINGLE_2_PLURALS.put(sgform, word);
        }

        return sgform;
    }

    /**
     * Replaces the last occurance of an old symbol with a new symbol.
     *
     * @param data      the original string
     * @param oldSymbol the old symbols to be replaced
     * @param newSymbol the corresponding new symbol
     * @return a new string
     */
    public static String replaceLast(String data, String oldSymbol, String newSymbol) {
        if (data == null || data.indexOf(oldSymbol) == -1) return data;

        int lastIndex = data.lastIndexOf(oldSymbol);
        int oldLength = oldSymbol.length();
        String result = data.substring(0, lastIndex) + newSymbol +
                data.substring(lastIndex + oldLength);

        return result;
    }

    /**
     * Adds more pairs of single and plural words.
     *
     * @param single singular form of the word
     * @param plural plural form of the word
     */
    public static void addPlural(String single, String plural) {
        RESOLVED_SINGLE_2_PLURALS.put(single, plural);
        RESOLVED_PLURAL_2_SINGLES.put(plural, single);
    }

    /**
     * Converts string to Camel case.
     *
     * @param word the word to be converted to camelized form
     * @return a camelized string
     */
    public static String camelize(String word) {
        return camelize(word, false);
    }

    /**
     * Converts string to Camel case. If <tt>firstLetterInLowerCase</tt>
     * is true, then the first letter of the result string is in lower case.
     * <p/>
     * <pre>
     * Examples:
     *   camelize("hello")               ==> "Hello"
     *   camelize("hello world")         ==> "Hello world"
     *   camelize("active_record")       ==> "ActiveRecord"
     *   camelize("active_record", true) ==> "activeRecord"
     * </pre>
     *
     * @param word                   the word to be converted to camelized form
     * @param firstLetterInLowerCase true if the first character should be in lower case
     * @return a camelized string
     */
    public static String camelize(String word, boolean firstLetterInLowerCase) {
        if (word == null || "".equals(word)) return word;

        String result = "";
        if (word.indexOf('_') != -1) {
            StringBuilder sb = new StringBuilder();
            int count = 0;
            StringTokenizer st = new StringTokenizer(word, "_");
            while (st.hasMoreTokens()) {
                String token = st.nextToken();
                count++;
                if (count == 1) {
                    sb.append(camelizeOneWord(token, firstLetterInLowerCase));
                } else {
                    sb.append(camelizeOneWord(token, false));
                }
            }
            result = sb.toString();
        } else {
            result = camelizeOneWord(word, firstLetterInLowerCase);
        }
        return result;
    }

    private static String camelizeOneWord(String word, boolean firstLetterInLowerCase) {
        if (word == null || "".equals(word)) return word;

        String firstChar = word.substring(0, 1);
        String result = firstLetterInLowerCase ? firstChar.toLowerCase() : firstChar.toUpperCase();
        if (word.length() > 1) {
            result += word.substring(1);
        }
        return result;
    }

    /**
     * <tt>underscore</tt> is the reverse of <tt>camelize</tt> method.
     * <p/>
     * <pre>
     * Examples:
     *   underscore("Hello world")  ==> "hello world"
     *   underscore("ActiveRecord") ==> "active_record"
     *   underscore("The RedCross") ==> "the red_cross"
     *   underscore("ABCD")         ==> "abcd"
     * </pre>
     *
     * @param phase the original string
     * @return an underscored string
     */
    public static String underscore(String phase) {
        if (phase == null || "".equals(phase)) return phase;

        phase = phase.replace('-', '_');
        StringBuilder sb = new StringBuilder();
        int total = phase.length();
        for (int i = 0; i < total; i++) {
            char c = phase.charAt(i);
            if (i == 0) {
                if (isInA2Z(c)) {
                    sb.append(String.valueOf(c).toLowerCase());
                } else {
                    sb.append(c);
                }
            } else {
                if (isInA2Z(c)) {
                    if (isIna2z(phase.charAt(i - 1))) {
                        sb.append("_").append(String.valueOf(c).toLowerCase());
                    } else {
                        sb.append(String.valueOf(c).toLowerCase());
                    }
                } else {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    private static String A2Z = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static String a2z = "abcdefghijklmnopqrstuvwxyz";

    private static boolean isInA2Z(char c) {
        return (A2Z.indexOf(c) != -1) ? true : false;
    }

    private static boolean isIna2z(char c) {
        return (a2z.indexOf(c) != -1) ? true : false;
    }

    /**
     * Replaces all dashes and underscores by spaces and capitalizes all the words.
     * <p/>
     * <pre>
     * Examples:
     *   titleize("ch 1:  Java-ActiveRecordIsFun") ==> "Ch 1:  Java Active Record Is Fun"
     * </pre>
     *
     * @param phase the original string
     * @return a titleized string
     */
    public static String titleize(String phase) {
        if (phase == null || "".equals(phase)) return phase;

        phase = humanize(phase);
        StringBuilder sb = new StringBuilder();
        int total = phase.length();
        for (int i = 0; i < total; i++) {
            char c = phase.charAt(i);
            if (i == 0) {
                if (isIna2z(c)) {
                    sb.append(String.valueOf(c).toUpperCase());
                } else {
                    sb.append(c);
                }
            } else {
                if (isIna2z(c) && ' ' == phase.charAt(i - 1)) {
                    sb.append(String.valueOf(c).toUpperCase());
                } else {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    /**
     * Replaces all dashes and underscores by spaces and capitalizes the first
     * word. Also removes
     * <p/>
     * <pre>
     * Examples:
     *   humanize("active_record") ==> "Active record"
     *   humanize("post_id")       ==> "Post"
     * </pre>
     *
     * @param phase the original string
     * @return a humanized string
     */
    public static String humanize(String phase) {
        if (phase == null || "".equals(phase)) return phase;
        phase = underscore(phase);
        if (phase.endsWith("_id")) phase += " ";
        return camelize(phase.replaceAll("_id ", " ").replace('_', ' ').trim());
    }

    /**
     * Returns a database table name corresponding to the input model class
     * name.
     * <p/>
     * <pre>
     * Examples:
     *   tableize("Person")   ==> "people"
     *   tableize("LineItem") ==> "line_items"
     * </pre>
     *
     * @param modelClassName
     * @return the table name of the java model class name
     */
    public static String tableize(String modelClassName) {
        return pluralize(underscore(modelClassName));
    }

    /**
     * Returns a model class name corresponding to the input database
     * table name.
     * <p/>
     * <pre>
     * Examples:
     *   classify("people")   ==> "Person"
     *   classify("line_items") ==> "LineItem"
     * </pre>
     *
     * @param tableName java class name of the model
     * @return a java model class name
     */
    public static String classify(String tableName) {
        return camelize(singularize(tableName));
    }

    /**
     * Returns an ordinalized string.
     * <p/>
     * <pre>
     * Examples:
     *   ordinalize(100)  ==> "100th"
     *   ordinalize(1003) ==> "1003rd"
     * </pre>
     *
     * @param number the number
     * @return an ordinalized string for the number
     */
    public static String ordinalize(int number) {
        String result = "" + number;
        if (result.endsWith("1")) result = result + "st";
        else if (result.endsWith("2")) result = result + "nd";
        else if (result.endsWith("3")) result = result + "rd";
        else result = result + "th";
        return result;
    }
}
