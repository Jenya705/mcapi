/**
 *
 * This package describes proxy and backend server
 *
 * There is two module classes:
 *
 * - {@link com.github.jenya705.mcapi.server.ss.back.BackEndServerModule}
 * This module class opens the tcpserver to communicate with proxy server and share information about objects.
 *
 * - {@link com.github.jenya705.mcapi.server.ss.proxy.ProxyServerModule}
 * This module class connects to the tcpservers to get information about objects when it needed.
 * It will overwrite some {@link com.github.jenya705.mcapi.server.ServerCore} functionality.
 *
 * @author Jenya705
 */
package com.github.jenya705.mcapi.server.ss;