package lichen.activiti.internal;

import lichen.activiti.services.LichenWorkflowManager;
import lichen.core.services.LichenException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.apache.commons.io.IOUtils;
import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Worker;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;

/**
 * 针对工作流的管理实现类
 * @author jcai
 */
public class LichenWorkflowManagerImpl implements LichenWorkflowManager {
    private RepositoryService _repositoryService;
    private Collection<Resource> workflows;

    public LichenWorkflowManagerImpl(Collection<Resource> workflows,
                                     RepositoryService repositoryService) {
        _repositoryService = repositoryService;
        this.workflows = Collections.synchronizedCollection(workflows);
    }

    @Override
    public void deploy() {
        final DeploymentBuilder deployment = _repositoryService.createDeployment();
        F.flow(workflows).each(new Worker<Resource>() {
            @Override
            public void work(Resource element) {
                InputStream inputStream = null;
                try {
                    inputStream = element.getInputStream();
                    deployment.addInputStream(element.toString(),inputStream);
                } catch (IOException e) {
                    throw LichenException.wrap(e);
                }finally{
                	IOUtils.closeQuietly(inputStream);
                }
            }
        });
        deployment.deploy();
    }
}
