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

package com.hazelcast.client.impl.protocol.task.map;

import com.hazelcast.client.impl.protocol.ClientMessage;
import com.hazelcast.client.impl.protocol.codec.MapUnlockCodec;
import com.hazelcast.client.impl.protocol.task.AbstractPartitionMessageTask;
import com.hazelcast.cp.internal.datastructures.unsafe.lock.LockService;
import com.hazelcast.cp.internal.datastructures.unsafe.lock.operations.UnlockOperation;
import com.hazelcast.instance.Node;
import com.hazelcast.map.impl.MapService;
import com.hazelcast.nio.Connection;
import com.hazelcast.security.permission.ActionConstants;
import com.hazelcast.security.permission.MapPermission;
import com.hazelcast.spi.ObjectNamespace;
import com.hazelcast.spi.impl.operationservice.Operation;

import java.security.Permission;

public class MapUnlockMessageTask
        extends AbstractPartitionMessageTask<MapUnlockCodec.RequestParameters> {

    public MapUnlockMessageTask(ClientMessage clientMessage, Node node, Connection connection) {
        super(clientMessage, node, connection);
    }

    @Override
    protected Operation prepareOperation() {
        return new UnlockOperation(getNamespace(), parameters.key, parameters.threadId, false, parameters.referenceId);
    }

    @Override
    protected MapUnlockCodec.RequestParameters decodeClientMessage(ClientMessage clientMessage) {
        return MapUnlockCodec.decodeRequest(clientMessage);
    }

    @Override
    protected ClientMessage encodeResponse(Object response) {
        return MapUnlockCodec.encodeResponse();
    }

    @Override
    public String getServiceName() {
        return LockService.SERVICE_NAME;
    }

    @Override
    public String getDistributedObjectType() {
        return MapService.SERVICE_NAME;
    }


    public Permission getRequiredPermission() {
        return new MapPermission(parameters.name, ActionConstants.ACTION_LOCK);
    }

    @Override
    public String getDistributedObjectName() {
        return parameters.name;
    }

    private ObjectNamespace getNamespace() {
        return MapService.getObjectNamespace(parameters.name);
    }

    @Override
    public String getMethodName() {
        return "unlock";
    }

    @Override
    public Object[] getParameters() {
        return new Object[]{parameters.key};
    }
}