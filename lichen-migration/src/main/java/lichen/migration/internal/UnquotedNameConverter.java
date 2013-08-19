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
package lichen.migration.internal;

/**
 * converter.
 *
 * @author jcai
 */
abstract class UnquotedNameConverter {
    abstract String apply(String name);

    private static UnquotedNameConverter casePreservingUnquotedNameConverter = new UnquotedNameConverter() {
        @Override
        String apply(String name) {
            return name;
        }
    };
    private static UnquotedNameConverter lowercaseUnquotedNameConverter = new UnquotedNameConverter() {
        @Override
        String apply(String name) {
            return name.toLowerCase();
        }
    };
    private static UnquotedNameConverter uppercaseUnquotedNameConverter = new UnquotedNameConverter() {
        @Override
        String apply(String name) {
            return name.toUpperCase();
        }
    };

    /**
     * setter和getter方法.
     */

    protected static UnquotedNameConverter getCasePreservingUnquotedNameConverter() {
        return casePreservingUnquotedNameConverter;
    }
    protected static void setCasePreservingUnquotedNameConverter(
            UnquotedNameConverter newCasePreservingUnquotedNameConverter) {
        casePreservingUnquotedNameConverter = newCasePreservingUnquotedNameConverter;
    }
    protected static UnquotedNameConverter getLowercaseUnquotedNameConverter() {
        return lowercaseUnquotedNameConverter;
    }
    protected static void setLowercaseUnquotedNameConverter(
            UnquotedNameConverter newLowercaseUnquotedNameConverter) {
        lowercaseUnquotedNameConverter = newLowercaseUnquotedNameConverter;
    }
    protected static UnquotedNameConverter getUppercaseUnquotedNameConverter() {
        return uppercaseUnquotedNameConverter;
    }
    protected static void setUppercaseUnquotedNameConverter(
            UnquotedNameConverter newUppercaseUnquotedNameConverter) {
        uppercaseUnquotedNameConverter = newUppercaseUnquotedNameConverter;
    }

}
