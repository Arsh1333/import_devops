package io.onedev.server.plugin.executor.kubernetes;

import java.io.File;
import java.util.Collection;

import org.apache.commons.lang.SystemUtils;
import org.glassfish.jersey.server.ResourceConfig;

import com.google.common.collect.Sets;

import io.onedev.commons.loader.AbstractPluginModule;
import io.onedev.commons.loader.ImplementationProvider;
import io.onedev.commons.utils.ExceptionUtils;
import io.onedev.commons.utils.TaskLogger;
import io.onedev.commons.utils.command.Commandline;
import io.onedev.commons.utils.command.LineConsumer;
import io.onedev.server.buildspec.job.JobExecutorDiscoverer;
import io.onedev.server.model.support.administration.jobexecutor.JobExecutor;
import io.onedev.server.rest.jersey.JerseyConfigurator;

/**
 * NOTE: Do not forget to rename moduleClass property defined in the pom if you've renamed this class.
 *
 */
public class KubernetesModule extends AbstractPluginModule {

	@Override
	protected void configure() {
		super.configure();
		
		// put your guice bindings here
		contribute(ImplementationProvider.class, new ImplementationProvider() {

			@Override
			public Class<?> getAbstractClass() {
				return JobExecutor.class;
			}

			@Override
			public Collection<Class<?>> getImplementations() {
				return Sets.newHashSet(KubernetesExecutor.class);
			}
			
		});
		contribute(JobExecutorDiscoverer.class, new JobExecutorDiscoverer() {
			
			@Override
			public JobExecutor discover(TaskLogger jobLogger) {
				jobLogger.log("Checking if there is a Kubernetes cluster...");

				Commandline kubectl;
				if (SystemUtils.IS_OS_MAC_OSX && new File("/usr/local/bin/kubectl").exists())
					kubectl = new Commandline("/usr/local/bin/kubectl");
				else
					kubectl = new Commandline("kubectl");
				kubectl.addArgs("cluster-info");
				try {
					kubectl.execute(new LineConsumer() {
			
						@Override
						public void consume(String line) {
						}
						
					}, new LineConsumer() {
			
						@Override
						public void consume(String line) {
						}
						
					}).checkReturnCode();
					
					return new KubernetesExecutor();
				} catch (Exception e) {
					if (ExceptionUtils.find(e, InterruptedException.class) != null)
						throw ExceptionUtils.unchecked(e);
					else
						return null;
				}
			}

			@Override
			public int getOrder() {
				return KubernetesExecutor.ORDER;
			}
			
		});
		
		contribute(JerseyConfigurator.class, new JerseyConfigurator() {
			
			@Override
			public void configure(ResourceConfig resourceConfig) {
				resourceConfig.register(KubernetesResource.class);
			}
			
		});
		
	}

}
