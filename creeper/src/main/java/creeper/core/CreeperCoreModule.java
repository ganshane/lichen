package creeper.core;

import creeper.core.internal.MenuSourceImpl;
import creeper.core.services.MenuSource;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.services.LibraryMapping;

public class CreeperCoreModule {
    public static void bind(ServiceBinder binder){
        binder.bind(MenuSource.class,MenuSourceImpl.class);
    }
    /**
     * Contribution to the
     * {@link org.apache.tapestry5.services.ComponentClassResolver} service
     * configuration.
     */
    public static void contributeComponentClassResolver(
            Configuration<LibraryMapping> configuration) {
        configuration.add(new LibraryMapping("creeper","creeper.core"));
    }
}
