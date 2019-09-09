package cn.mzhong.test.elasticsearch;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class QuickStart {

    RestHighLevelClient client;

    @BeforeEach
    void init() {
        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));
    }

    @AfterEach
    void destroy() {
        if (client != null) {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void ping() {
        try {
            System.out.println(client.ping(RequestOptions.DEFAULT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void put() {
        IndexRequest mzhong = new IndexRequest("user");
        Map<String, String> data = new HashMap<>();
        data.put("name", "牟老头");
        data.put("age", "26");
        mzhong.source(JSON.toJSONString(data), XContentType.JSON);
        try {
            client.index(mzhong, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void get() {
        assert false;
        try {
            GetResponse response = client.get(new GetRequest("user", "mzhong"), RequestOptions.DEFAULT);
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void search() {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("user");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("name", "牟"));
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println(search);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
