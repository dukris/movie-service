package integration;


import org.testcontainers.elasticsearch.ElasticsearchContainer;

@SuppressWarnings("JTCOP.RuleAllTestsHaveProductionClass")
public class EsContainer extends ElasticsearchContainer {

    public EsContainer() {
        super("docker.elastic.co/elasticsearch/elasticsearch:8.5.3");
    }

    public EsContainer init() {
        this.addFixedExposedPort(9200, 9200);
        this.addEnv("discovery.type", "single-node");
        this.addEnv("xpack.security.enabled", "false");
        return this;
    }

}
