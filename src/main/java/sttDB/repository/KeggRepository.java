package sttDB.repository;

import sttDB.domain.Kegg;

public interface KeggRepository extends CustomQueriesRepository <Kegg, String> {
    Kegg findByKeggId(String keggId);
}
