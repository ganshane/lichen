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

import org.junit.Test;

/**
 * command line test.
 * @author jcai
 */
public class CommandLineTest {
    @Test
    public void testUp() {
        String file = getClass().getResource("/test_rep").getFile();
        new CommandLine(new String[]{"--path=" + file, "up"}).execute();
        //new CommandLine(new String[]{"--path=support","down"}).execute();
    }
}
