package sttDB.repository.excerptProjections;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "familyProjection", types = {FamilyProjection.class})
public interface FamilyProjection {
    String getInterproId();
}
