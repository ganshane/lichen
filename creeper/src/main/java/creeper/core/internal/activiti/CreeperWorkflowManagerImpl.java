package creeper.core.internal.activiti;

import creeper.core.services.CreeperException;
import creeper.core.services.activiti.CreeperWorkflowManager;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Worker;
import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.ioc.services.SymbolSource;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.Core;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;

/**
 * 针对工作流的管理实现类
 * @author jcai
 */
public class CreeperWorkflowManagerImpl implements CreeperWorkflowManager{
    @Inject
    private RepositoryService _repositoryService;
    @Inject
    private SymbolSource _symbolSource;
    @Inject
    private AssetSource _assetSource;
    private Collection<String> workflows;

    public CreeperWorkflowManagerImpl(Collection<String> workflows, RepositoryService repositoryService, SymbolSource symbolSource, AssetSource assetSource) {
        _repositoryService = repositoryService;
        _symbolSource = symbolSource;
        _assetSource = assetSource;
        this.workflows = Collections.synchronizedCollection(workflows);
    }

    @Override
    public void deploy() {
        final DeploymentBuilder deployment = _repositoryService.createDeployment();
        F.flow(workflows).each(new Worker<String>() {
            @Override
            public void work(String element) {
                String expanded = _symbolSource.expandSymbols(element);
                Asset asset = _assetSource.getAsset(null, expanded, null);
                InputStream inputStream = null;
                try {
                    inputStream = asset.getResource().openStream();
                    deployment.addInputStream(element.toString(),inputStream);
                } catch (IOException e) {
                    throw CreeperException.wrap(e);
                }
            }
        });
        deployment.deploy();
    }
}
