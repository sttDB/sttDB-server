package sttDB.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import sttDB.domain.Family;
import sttDB.domain.Go;
import sttDB.domain.Kegg;
import sttDB.domain.Sequence;

@Configuration
public class ExposeEntityIdRestConfiguration extends RepositoryRestConfigurerAdapter {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Family.class);
        config.exposeIdsFor(Sequence.class);
        config.exposeIdsFor(Go.class);
        config.exposeIdsFor(Kegg.class);
    }
}