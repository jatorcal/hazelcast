/*
 * Copyright (c) 2008-2020, Hazelcast, Inc. All Rights Reserved.
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

package com.hazelcast.sql.impl.state;

import com.hazelcast.sql.impl.QueryMetadata;
import com.hazelcast.sql.impl.QueryResultProducer;
import com.hazelcast.sql.impl.fragment.QueryFragment;
import com.hazelcast.sql.impl.QueryId;
import com.hazelcast.sql.impl.QueryPlan;

import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.UUID;

/**
 * Query state which is specific to the initiator node only.
 */
public class QueryInitiatorState {

    private final QueryId queryId;
    private final QueryPlan plan;
    private final QueryMetadata metadata;
    private final IdentityHashMap<QueryFragment, Collection<UUID>> fragmentMappings;
    private final QueryResultProducer rowSource;
    private final long timeout;

    public QueryInitiatorState(
        QueryId queryId,
        QueryPlan plan,
        QueryMetadata metadata,
        IdentityHashMap<QueryFragment, Collection<UUID>> fragmentMappings,
        QueryResultProducer rowSource,
        long timeout
    ) {
        this.queryId = queryId;
        this.plan = plan;
        this.metadata = metadata;
        this.fragmentMappings = fragmentMappings;
        this.rowSource = rowSource;
        this.timeout = timeout;
    }

    public QueryId getQueryId() {
        return queryId;
    }

    public QueryPlan getPlan() {
        return plan;
    }

    public QueryMetadata getMetadata() {
        return metadata;
    }

    public IdentityHashMap<QueryFragment, Collection<UUID>> getFragmentMappings() {
        return fragmentMappings;
    }

    public QueryResultProducer getRowSource() {
        return rowSource;
    }

    public long getTimeout() {
        return timeout;
    }
}