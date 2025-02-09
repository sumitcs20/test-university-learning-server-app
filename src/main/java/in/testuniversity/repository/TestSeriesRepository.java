package in.testuniversity.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import in.testuniversity.entity.TestSeries;

@Repository
public interface TestSeriesRepository extends CrudRepository<TestSeries, Long> {

	List<TestSeries> findByStreamId(Long streamId);
}
