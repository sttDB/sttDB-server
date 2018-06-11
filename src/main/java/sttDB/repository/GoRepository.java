package sttDB.repository;

import sttDB.domain.Go;

public interface GoRepository extends CustomQueriesRepository <Go, String> {
    Go findGoByGoId(String goId);
}
