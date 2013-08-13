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

import junit.framework.Assert;
import lichen.migration.services.Options;
import org.junit.Test;

/**
 *
 * @author jcai
 */
public class ColumnDefinitionTest {
    @Test
    public void test_autoincrement(){
        A a = new A();
        a.columnNameOpt = Option.Some("test_column");
        a.initialize();
        Assert.assertTrue(a.isAutoIncrement);
        Assert.assertEquals(a.options.size(),0);
    }
    @ColumnSupportsAutoIncrement
    class A extends ColumnDefinition{
        public A(){
            Options optionsService = new OptionsImpl();
            options.add(optionsService.AutoIncrement());
            options.add(optionsService.AutoIncrement());
        }

        @Override
        protected String sql() {
            return null;
        }
    }
}
