package sttDB.repository.excerptProjections;

import org.springframework.data.rest.core.config.Projection;
import sttDB.domain.Experiment;

@Projection(name = "experimentProjection", types = {Experiment.class})
public interface ExperimentProjection {
    String getName();
}
