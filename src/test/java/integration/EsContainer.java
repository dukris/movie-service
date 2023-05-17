package integration;


import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.utility.DockerImageName;

public class EsContainer extends ElasticsearchContainer {

    public EsContainer() {
        super(DockerImageName.parse("elasticsearch:8.5.3")
                .asCompatibleSubstituteFor(
                        "docker.elastic.co/elasticsearch/elasticsearch")
        );
        this.addFixedExposedPort(9200, 9200);
        this.addEnv("discovery.type", "single-node");
        this.addEnv("xpack.security.enabled", "false");
    }

}
