package com.couchbase.docker;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.query.*;
import com.couchbase.client.java.query.consistency.ScanConsistency;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.document.JsonDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@RestController
@RequestMapping("/")
public class DockerApplication {

	@Value("${couchbase_host}")	
	private String hostname;

	@Value("${couchbase_bucket}")
	private String bucket;

	@Value("${couchbase_bucket_password}")
	private String password;

	public @Bean
	Cluster cluster() {
			return CouchbaseCluster.create(hostname);
	}

	public @Bean
	Bucket bucket() {
			return cluster().openBucket(bucket, password);
	}

	@RequestMapping(value="/", method= RequestMethod.GET)
	public String root() {
			return "Try visiting the `/get` or `/save` endpoints";
	}

	@RequestMapping(value="/get", method= RequestMethod.GET)
	public Object get() {
			String query = "SELECT `" + bucket().name() + "`.* FROM `" + bucket().name() + "`";
			return bucket().async().query(N1qlQuery.simple(query, N1qlParams.build().consistency(ScanConsistency.REQUEST_PLUS)))
							.flatMap(AsyncN1qlQueryResult::rows)
							.map(result -> result.value().toMap())
							.toList()
							.timeout(10, TimeUnit.SECONDS)
							.toBlocking()
							.single();
	}

	@RequestMapping(value="/save", method=RequestMethod.POST)
	public Object save(@RequestBody String json) {
			JsonObject jsonData = JsonObject.fromJson(json);
			JsonDocument document = JsonDocument.create(UUID.randomUUID().toString(), jsonData);
			bucket().insert(document);
			return new ResponseEntity<String>(json, HttpStatus.OK);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(DockerApplication.class, args);
	}
}
