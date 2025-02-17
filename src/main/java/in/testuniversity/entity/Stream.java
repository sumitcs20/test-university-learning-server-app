package in.testuniversity.entity;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "streams")
@Entity
public class Stream {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "stream_name", nullable = false, unique = true)
	private String streamName;
	
	//mapping and relationship- not the columns of stream table... 
	//orphan removal means if Stream will be deleted the all related test series will also be deleted
	@OneToMany(mappedBy = "stream", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Topic> testSeries;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreamName() {
		return streamName;
	}

	public void setStreamName(String streamName) {
		this.streamName = streamName;
	}

	public List<Topic> getTestSeries() {
		return testSeries;
	}

	public void setTestSeries(List<Topic> testSeries) {
		this.testSeries = testSeries;
	}
}
