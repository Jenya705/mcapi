/**
 * <pre>
 * This package describes proxy and backend server
 *
 * <strong>There is two module classes:</strong>
 *
 * - {@link com.github.jenya705.mcapi.server.ss.back.BackEndServerModule}
 * This module class opens the tcpserver to communicate with proxy server and share information about objects.
 *
 * - {@link com.github.jenya705.mcapi.server.ss.proxy.ProxyServerModule}
 * This module class connects to the tcpservers to get information about objects when it needed.
 * It will overwrite some {@link com.github.jenya705.mcapi.server.ServerCore} functionality.
 *
 * <strong>Protocol:</strong>
 *
 * There is client (ProxyServer) and server (BackEndServer).
 *
 * Client sends a json packet with information which server need to return.
 * {
 *     "type": model-type,
 *     "data": model-data
 * }
 *
 * Model data depends on the model type.
 * For the example:
 *
 * - The player id is UUID or Nickname (String)
 *
 * - The block id is location
 *
 * - The entity id is UUID
 *
 * - The world id is NamespacedKey
 *
 * and so on.
 *
 * Server can return another json with information about object
 * {
 *     "type": model-type,
 *     "data": model-data
 * }
 *
 * </pre>
 *
 * @author Jenya705
 */
package com.github.jenya705.mcapi.server.ss;