/*
 * Copyright (c) 2008-2017, Hazelcast, Inc. All Rights Reserved.
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

package com.hazelcast.internal.networking;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentMap;

/**
 * Wraps a {@link java.nio.channels.SocketChannel}.
 *
 * The reason this class exists is because for enterprise encryption. Ideally the SocketChannel should have been decorated
 * with this encryption functionality, but unfortunately that isn't possible with this class.
 *
 * That is why a new 'wrapper' interface is introduced which acts like a SocketChannel and the implementations wrap a
 * SocketChannel.
 *
 * In the future we should get rid of this class and rely on {@link ChannelInboundHandler}/{@link ChannelOutboundHandler}
 * chaining to add encryption. This will remove more artifacts from the architecture that can't carry their weight.
 */
public interface Channel extends Closeable {

    /**
     * Returns the attribute map.
     *
     * Attribute map can be used to store data into a socket. For example to find the Connection for a Channel, one can
     * store the Connection in this channel using some well known key.
     *
     * @return the attribute map.
     */
    ConcurrentMap attributeMap();

    /**
     * @see java.nio.channels.SocketChannel#socket()
     *
     * This method will be removed from the interface. Only an explicit cast to NioChannel will expose the Socket.
     */
    Socket socket();

    /**
     * @return the remote address. Returned value could be null.
     */
    SocketAddress getRemoteAddress();

    /**
     * @return the local address. Returned value could be null
     */
    SocketAddress getLocalAddress();

    /**
     * This method will be removed from the interface. Only an explicit cast to NioChannel will expose the SocketChannel.
     */
    SocketChannel socketChannel();

    /**
     * @see java.nio.channels.SocketChannel#read(ByteBuffer)
     */
    int read(ByteBuffer dst) throws IOException;

    /**
     * @see java.nio.channels.SocketChannel#write(ByteBuffer)
     */
    int write(ByteBuffer src) throws IOException;

    /**
     * @see java.nio.channels.SocketChannel#configureBlocking(boolean)
     */
    SelectableChannel configureBlocking(boolean block) throws IOException;

    /**
     * @see java.nio.channels.SocketChannel#isOpen()
     */
    boolean isOpen();

    /**
     * Closes inbound.
     *
     * <p>Not thread safe. Should be called in channel reader thread.</p>
     *
     * @throws IOException
     */
    void closeInbound() throws IOException;

    /**
     * Closes outbound.
     *
     * <p>Not thread safe. Should be called in channel writer thread.</p>
     *
     * @throws IOException
     */
    void closeOutbound() throws IOException;

    /**
     * @see java.nio.channels.SocketChannel#close()
     */
    void close() throws IOException;
}
