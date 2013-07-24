// Copyright 2013 the original author or authors.
// site: http://lichen.ganshane.com
package lichen.migration.internal;

/**
 * converter
 * @author jcai
 */
abstract class UnquotedNameConverter {
    abstract String apply(String name);
    static UnquotedNameConverter CasePreservingUnquotedNameConverter = new UnquotedNameConverter(){
        @Override
        String apply(String name) {
            return name;
        }
    };
    static UnquotedNameConverter LowercaseUnquotedNameConverter = new UnquotedNameConverter() {
        @Override
        String apply(String name) {
            return name.toLowerCase();
        }
    };
    static UnquotedNameConverter UppercaseUnquotedNameConverter = new UnquotedNameConverter() {
        @Override
        String apply(String name) {
            return name.toUpperCase();
        }
    };
}
