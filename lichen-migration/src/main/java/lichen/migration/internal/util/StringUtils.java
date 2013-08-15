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
package lichen.migration.internal.util;

/**
 * 字符串操作类.
 *
 * @author zhaoyong
 */
public final class StringUtils {
    /**
     * 构造函数.
     */
    private StringUtils() {
    }

    /**
     * 将指定的字符串数组，通过指定的分隔符连接.
     *
     * @param array    源字符串数组
     * @param sperator 指定的分隔符
     * @return String 拼接后的字符串
     */
    public static String join(final String[] array, final String sperator) {
        StringBuffer sb = new StringBuffer();
        String newSeparator = sperator;
        if (array != null) {
            if (sperator == null) {
                newSeparator = "";
            }
            for (int i = 0; i < array.length; i++) {
                sb.append(array[i]);
                if (i < array.length - 1) { //非最后一个元素，需要添加分隔符
                    sb.append(newSeparator);
                }
            }
        }
        return sb.toString();
    }
}
