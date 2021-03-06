/**
 * Copyright 2015-2016 Red Hat, Inc, and individual contributors.
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
package org.wildfly.swarm.jolokia.runtime;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.jboss.shrinkwrap.api.Archive;
import org.wildfly.swarm.jolokia.JolokiaFraction;
import org.wildfly.swarm.spi.api.ArtifactLookup;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;
import org.wildfly.swarm.undertow.WARArchive;

/**
 * @author Bob McWhirter
 */
@Singleton
public class JolokiaWarDeploymentProducer {

    @Inject
    @Any
    private JolokiaFraction fraction;

    @Inject
    private ArtifactLookup lookup;

    @Inject
    @ConfigurationValue( "swarm.jolokia.context")
    private String context;

    @Produces
    public Archive jolokiaWar() throws Exception {

        if ( this.context == null ) {
            this.context = this.fraction.context();
        }

        Archive deployment = this.lookup.artifact("org.jolokia:jolokia-war:war:*", "jolokia.war");

        deployment.as( WARArchive.class).setContextRoot( this.context );

        return deployment;
    }
}
