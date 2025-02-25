/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.conf;

import java.util.HashSet;

import org.apache.hadoop.crypto.key.kms.KMSClientProvider;
import org.apache.hadoop.fs.CommonConfigurationKeys;
import org.apache.hadoop.fs.CommonConfigurationKeysPublic;
import org.apache.hadoop.fs.ftp.FtpConfigKeys;
import org.apache.hadoop.fs.local.LocalConfigKeys;
import org.apache.hadoop.ha.SshFenceByTcpPort;
import org.apache.hadoop.ha.ZKFailoverController;
import org.apache.hadoop.io.erasurecode.CodecUtil;
import org.apache.hadoop.security.CompositeGroupsMapping;
import org.apache.hadoop.security.HttpCrossOriginFilterInitializer;
import org.apache.hadoop.security.LdapGroupsMapping;
import org.apache.hadoop.security.RuleBasedLdapGroupsMapping;
import org.apache.hadoop.security.ssl.SSLFactory;

/**
 * Unit test class to compare the following Hadoop Configuration classes:
 * <p></p>
 * {@link org.apache.hadoop.fs.AbstractFileSystem}
 * {@link org.apache.hadoop.fs.CommonConfigurationKeys}
 * {@link org.apache.hadoop.fs.CommonConfigurationKeysPublic}
 * {@link org.apache.hadoop.fs.ftp.FtpConfigKeys}
 * {@link org.apache.hadoop.fs.local.LocalConfigKeys}
 * {@link org.apache.hadoop.ha.SshFenceByTcpPort}
 * {@link org.apache.hadoop.http.HttpServer2}
 * {@link org.apache.hadoop.security.LdapGroupsMapping}
 * {@link org.apache.hadoop.security.http.CrossOriginFilter}
 * {@link org.apache.hadoop.security.ssl.SSLFactory}
 * {@link org.apache.hadoop.io.erasurecode.rawcoder.CoderUtil}
 * <p></p>
 * against core-site.xml for missing properties.  Currently only
 * throws an error if the class is missing a property.
 * <p></p>
 * Refer to {@link org.apache.hadoop.conf.TestConfigurationFieldsBase}
 * for how this class works.
 */
public class TestCommonConfigurationFields extends TestConfigurationFieldsBase {

  @SuppressWarnings("deprecation")
  @Override
  public void initializeMemberVariables() {
    xmlFilename = new String("core-default.xml");
    configurationClasses = new Class[] {
        CommonConfigurationKeys.class,
        CommonConfigurationKeysPublic.class,
        LocalConfigKeys.class,
        FtpConfigKeys.class,
        SshFenceByTcpPort.class,
        LdapGroupsMapping.class,
        ZKFailoverController.class,
        SSLFactory.class,
        CompositeGroupsMapping.class,
        CodecUtil.class,
        RuleBasedLdapGroupsMapping.class
        };

    // Initialize used variables
    xmlPropsToSkipCompare = new HashSet<>();
    xmlPrefixToSkipCompare = new HashSet<>();
    configurationPropsToSkipCompare = new HashSet<>();

    // Set error modes
    errorIfMissingConfigProps = true;
    errorIfMissingXmlProps = false;

    // Lots of properties not in the above classes
    xmlPropsToSkipCompare.add("fs.ftp.password.localhost");
    xmlPropsToSkipCompare.add("fs.ftp.user.localhost");
    xmlPropsToSkipCompare.add("fs.ftp.data.connection.mode");
    xmlPropsToSkipCompare.add("fs.ftp.transfer.mode");
    xmlPropsToSkipCompare.add("fs.ftp.timeout");
    xmlPropsToSkipCompare.add("hadoop.tmp.dir");
    xmlPropsToSkipCompare.add("nfs3.mountd.port");
    xmlPropsToSkipCompare.add("nfs3.server.port");
    xmlPropsToSkipCompare.add("fs.viewfs.rename.strategy");

    // S3A properties are in a different subtree.
    xmlPrefixToSkipCompare.add("fs.s3a.");

    // O3 properties are in a different subtree.
    xmlPrefixToSkipCompare.add("fs.o3fs.");

    //ftp properties are in a different subtree.
    // - org.apache.hadoop.fs.ftp.FTPFileSystem.
    xmlPrefixToSkipCompare.add("fs.ftp.impl");

    // WASB properties are in a different subtree.
    // - org.apache.hadoop.fs.azure.NativeAzureFileSystem
    xmlPrefixToSkipCompare.add("fs.wasb.impl");
    xmlPrefixToSkipCompare.add("fs.wasbs.impl");
    xmlPrefixToSkipCompare.add("fs.azure.");
    xmlPrefixToSkipCompare.add("fs.abfs.impl");
    xmlPrefixToSkipCompare.add("fs.abfss.impl");

    // ADL properties are in a different subtree
    // - org.apache.hadoop.hdfs.web.ADLConfKeys
    xmlPrefixToSkipCompare.add("adl.");
    xmlPrefixToSkipCompare.add("fs.adl.");
    xmlPropsToSkipCompare.add("fs.AbstractFileSystem.adl.impl");

    // ViewfsOverloadScheme target fs impl property keys are dynamically
    // constructed and they are advanced props.
    xmlPropsToSkipCompare.add("fs.viewfs.overload.scheme.target.abfs.impl");
    xmlPropsToSkipCompare.add("fs.viewfs.overload.scheme.target.abfss.impl");
    xmlPropsToSkipCompare.add("fs.viewfs.overload.scheme.target.file.impl");
    xmlPropsToSkipCompare.add("fs.viewfs.overload.scheme.target.ftp.impl");
    xmlPropsToSkipCompare.add("fs.viewfs.overload.scheme.target.gs.impl");
    xmlPropsToSkipCompare.add("fs.viewfs.overload.scheme.target.hdfs.impl");
    xmlPropsToSkipCompare.add("fs.viewfs.overload.scheme.target.http.impl");
    xmlPropsToSkipCompare.add("fs.viewfs.overload.scheme.target.https.impl");
    xmlPropsToSkipCompare.add("fs.viewfs.overload.scheme.target.ofs.impl");
    xmlPropsToSkipCompare.add("fs.viewfs.overload.scheme.target.o3fs.impl");
    xmlPropsToSkipCompare.add("fs.viewfs.overload.scheme.target.oss.impl");
    xmlPropsToSkipCompare.add("fs.viewfs.overload.scheme.target.s3a.impl");
    xmlPropsToSkipCompare.
        add("fs.viewfs.overload.scheme.target.swebhdfs.impl");
    xmlPropsToSkipCompare.add("fs.viewfs.overload.scheme.target.webhdfs.impl");
    xmlPropsToSkipCompare.add("fs.viewfs.overload.scheme.target.wasb.impl");

    // Azure properties are in a different class
    // - org.apache.hadoop.fs.azure.AzureNativeFileSystemStore
    // - org.apache.hadoop.fs.azure.SASKeyGeneratorImpl
    xmlPropsToSkipCompare.add("fs.azure.sas.expiry.period");
    xmlPropsToSkipCompare.add("fs.azure.local.sas.key.mode");
    xmlPropsToSkipCompare.add("fs.azure.secure.mode");
    xmlPropsToSkipCompare.add("fs.azure.authorization");
    xmlPropsToSkipCompare.add("fs.azure.authorization.caching.enable");
    xmlPropsToSkipCompare.add("fs.azure.saskey.usecontainersaskeyforallaccess");
    xmlPropsToSkipCompare.add("fs.azure.user.agent.prefix");

    // Properties in enable callqueue overflow trigger failover for stateless servers.
    xmlPropsToSkipCompare.add("ipc.[port_number].callqueue.overflow.trigger.failover");
    xmlPropsToSkipCompare.add("ipc.callqueue.overflow.trigger.failover");

    // FairCallQueue configs that includes dynamic ports in its keys
    xmlPropsToSkipCompare.add("ipc.[port_number].backoff.enable");
    xmlPropsToSkipCompare.add("ipc.backoff.enable");
    xmlPropsToSkipCompare.add("ipc.[port_number].callqueue.impl");
    xmlPropsToSkipCompare.add("ipc.callqueue.impl");
    xmlPropsToSkipCompare.add("ipc.[port_number].scheduler.impl");
    xmlPropsToSkipCompare.add("ipc.scheduler.impl");
    xmlPropsToSkipCompare.add("ipc.[port_number].scheduler.priority.levels");
    xmlPropsToSkipCompare.add("ipc.[port_number].callqueue.capacity.weights");
    xmlPropsToSkipCompare.add(
        "ipc.[port_number].faircallqueue.multiplexer.weights");
    xmlPropsToSkipCompare.add("ipc.[port_number].identity-provider.impl");
    xmlPropsToSkipCompare.add("ipc.identity-provider.impl");
    xmlPropsToSkipCompare.add("ipc.[port_number].cost-provider.impl");
    xmlPropsToSkipCompare.add("ipc.cost-provider.impl");
    xmlPropsToSkipCompare.add("ipc.[port_number].decay-scheduler.period-ms");
    xmlPropsToSkipCompare.add("ipc.[port_number].decay-scheduler.decay-factor");
    xmlPropsToSkipCompare.add("ipc.[port_number].decay-scheduler.thresholds");
    xmlPropsToSkipCompare.add(
        "ipc.[port_number].decay-scheduler.backoff.responsetime.enable");
    xmlPropsToSkipCompare.add(
        "ipc.[port_number].decay-scheduler.backoff.responsetime.thresholds");
    xmlPropsToSkipCompare.add(
        "ipc.[port_number].decay-scheduler.metrics.top.user.count");
    xmlPropsToSkipCompare.add(
        "ipc.[port_number].decay-scheduler.service-users");
    xmlPropsToSkipCompare.add("ipc.[port_number].weighted-cost.lockshared");
    xmlPropsToSkipCompare.add("ipc.[port_number].weighted-cost.lockexclusive");
    xmlPropsToSkipCompare.add("ipc.[port_number].weighted-cost.handler");
    xmlPropsToSkipCompare.add("ipc.[port_number].weighted-cost.lockfree");
    xmlPropsToSkipCompare.add("ipc.[port_number].weighted-cost.response");

    // Deprecated properties.  These should eventually be removed from the
    // class.
    configurationPropsToSkipCompare
        .add(CommonConfigurationKeysPublic.IO_SORT_MB_KEY);
    configurationPropsToSkipCompare
        .add(CommonConfigurationKeysPublic.IO_SORT_FACTOR_KEY);

    // Irrelevant property
    configurationPropsToSkipCompare.add("dr.who");

    // XML deprecated properties.
    // - org.apache.hadoop.hdfs.client.HdfsClientConfigKeys
    xmlPropsToSkipCompare
        .add("io.bytes.per.checksum");

    // Properties in other classes that aren't easily determined
    // (not following naming convention, in a different project, not public,
    // etc.)
    // - org.apache.hadoop.http.HttpServer2.FILTER_INITIALIZER_PROPERTY
    xmlPropsToSkipCompare.add("hadoop.http.filter.initializers");
    // - org.apache.hadoop.security.HttpCrossOriginFilterInitializer
    xmlPrefixToSkipCompare.add(HttpCrossOriginFilterInitializer.PREFIX);
    xmlPrefixToSkipCompare.add("fs.AbstractFileSystem.");
    // - org.apache.hadoop.ha.SshFenceByTcpPort
    xmlPrefixToSkipCompare.add("dfs.ha.fencing.ssh.");
    // - org.apache.hadoop.classification.RegistryConstants
    xmlPrefixToSkipCompare.add("hadoop.registry.");
    // - org.apache.hadoop.security.AuthenticationFilterInitializer
    xmlPrefixToSkipCompare.add("hadoop.http.authentication.");
    // - org.apache.hadoop.crypto.key.kms.KMSClientProvider;
    xmlPropsToSkipCompare.add(KMSClientProvider.AUTH_RETRY);
    // - org.apache.hadoop.io.nativeio.NativeIO
    xmlPropsToSkipCompare.add("hadoop.workaround.non.threadsafe.getpwuid");
    // - org.apache.hadoop.hdfs.DFSConfigKeys
    xmlPropsToSkipCompare.add("dfs.ha.fencing.methods");
    // - org.apache.hadoop.fs.CommonConfigurationKeysPublic
    xmlPrefixToSkipCompare
        .add(CommonConfigurationKeysPublic.HADOOP_SECURITY_CRYPTO_CODEC_CLASSES_KEY_PREFIX);
    // - org.apache.hadoop.hdfs.server.datanode.DataNode
    xmlPropsToSkipCompare.add("hadoop.common.configuration.version");
    // - org.apache.hadoop.fs.FileSystem
    xmlPropsToSkipCompare.add("fs.har.impl.disable.cache");

    // - package org.apache.hadoop.tracing.TraceUtils ?
    xmlPropsToSkipCompare.add("hadoop.htrace.span.receiver.classes");
    // Private keys
    // - org.apache.hadoop.ha.ZKFailoverController;
    xmlPropsToSkipCompare.add("ha.zookeeper.parent-znode");
    xmlPropsToSkipCompare.add("ha.zookeeper.session-timeout.ms");
    // - Where is this used?
    xmlPrefixToSkipCompare
        .add(CommonConfigurationKeys.FS_CLIENT_HTRACE_PREFIX);
    // - org.apache.hadoop.security.UserGroupInformation
    xmlPropsToSkipCompare.add("hadoop.kerberos.kinit.command");
    // - org.apache.hadoop.net.NetUtils
    xmlPropsToSkipCompare
        .add("hadoop.rpc.socket.factory.class.ClientProtocol");

    // Keys with no corresponding variable
    // - org.apache.hadoop.io.compress.bzip2.Bzip2Factory
    xmlPropsToSkipCompare.add("io.compression.codec.bzip2.library");
    // - org.apache.hadoop.io.SequenceFile
    xmlPropsToSkipCompare.add("io.seqfile.local.dir");

    xmlPropsToSkipCompare.add("hadoop.http.sni.host.check.enabled");
  }
}
