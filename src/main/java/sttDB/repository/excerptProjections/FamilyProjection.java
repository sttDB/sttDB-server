package sttDB.repository.excerptProjections;

import org.springframework.data.rest.core.config.Projection;
import sttDB.domain.Family;

@Projection(name = "familyProjection", types = {Family.class})
public interface FamilyProjection {
    String getInterproId();
}
