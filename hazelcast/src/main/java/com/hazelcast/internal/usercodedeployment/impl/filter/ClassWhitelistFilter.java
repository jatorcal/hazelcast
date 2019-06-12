/*
 * Copyright (c) 2008-2019, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.internal.usercodedeployment.impl.filter;

import com.hazelcast.internal.util.filter.Filter;
import com.hazelcast.util.collection.ArrayUtils;

/**
 * Only classes in the whitelist match. No other classes match.
 */
public class ClassWhitelistFilter implements Filter<String> {

    private final String[] whitelist;

    public ClassWhitelistFilter(String... whitelisted) {
        whitelist = ArrayUtils.createCopy(whitelisted);
    }

    @Override
    public boolean accept(String className) {
        for (String blacklisted : whitelist) {
            if (className.startsWith(blacklisted)) {
                return true;
            }
        }
        return false;
    }
}