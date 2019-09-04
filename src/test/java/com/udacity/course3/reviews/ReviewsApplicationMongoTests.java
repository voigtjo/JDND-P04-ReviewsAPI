package com.udacity.course3.reviews;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.udacity.course3.reviews.model.*;
import com.udacity.course3.reviews.repository.CommentRepository;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewDRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReviewsApplicationMongoTests {

//	@Autowired private ProductRepository productRepository;
//	@Autowired private ReviewRepository reviewRepository;
//	@Autowired private CommentRepository commentRepository;

	@Autowired
	MongoTemplate mongoTemplate;

	Reader reader;
	CSVReader csvReader;


	@Before
	public void setUp() {
	}

	@After
	public void tearDown() throws IOException {
		System.out.println("..tearDown");
		if (reader != null) reader.close();
		if (csvReader != null) csvReader.close();
	}

	private ReviewD saveReview() throws IOException {
		Properties props= new Properties();
		props.load(getClass().getClassLoader().getResourceAsStream("testReview.properties"));

		Product product = new Product(1);

		Review review = new Review(
				product,
				props.getProperty("text"),
				Integer.parseInt(props.getProperty("star")));
		review.setCreationtime(new Date());

		//save to mongodb
		ReviewD reviewD = new ReviewD(review);
		mongoTemplate.save(reviewD);
		return reviewD;
	}

	private Reader getReader(String fileName) throws IOException, URISyntaxException {
		ClassLoader classLoader = getClass().getClassLoader();
		URI uri = classLoader.getResource(fileName).toURI();
		return Files.newBufferedReader(Paths.get(uri));
	}

	private CSVReader getParser(){
		CSVParser parser = new CSVParserBuilder()
				.withSeparator(';')
				.withIgnoreQuotations(true)
				.build();

		return new CSVReaderBuilder(reader)
				.withSkipLines(0)
				.withCSVParser(parser)
				.build();
	}


	@Test
	public void testCreateAndFindReviews() throws IOException, URISyntaxException {
		System.out.println("testSaveAndFindReviews..");

		reader = getReader("testSaveAndFindReviews.csv");
		csvReader = getParser();

		Product product = new Product(1);
		List<ReviewD> reviewDCreateList = new ArrayList<>();
		String[] line;
		int i = 0;
		while ((line = csvReader.readNext()) != null) {
			System.out.println("### " + i  + ": " + Arrays.toString(line));
			Review review = new Review(product, line[0], Integer.parseInt(line[1]));
			review.setCreationtime(new Date());
			review.setId(i++);
			//save to mongodb
			ReviewD reviewD = new ReviewD(review);
			reviewD = mongoTemplate.save(reviewD);
			reviewDCreateList.add(reviewD);
		}

		//read reviews from mongodb
		Query query = new Query(Criteria.where("productId").is(product.getId()));
		List<ReviewD> reviewDReadList = mongoTemplate.find(query, ReviewD.class);

		System.out.println("### MongoDB-reviews: " + reviewDCreateList.size() + ";" + reviewDReadList.size());

		assertTrue(reviewDCreateList.size()==reviewDReadList.size());
	}



	@Test
	public void testCreateAndFindComments() throws IOException, URISyntaxException {
		System.out.println("testSaveAndFindComments..");

		reader = getReader("testSaveAndFindComments.csv");
		csvReader = getParser();

		ReviewD reviewDSave = saveReview();
		List<CommentD> commentDCreateList = new ArrayList<>();
		String[] line;
		int i = 0;
		while ((line = csvReader.readNext()) != null) {
			System.out.println("### " + Arrays.toString(line));

			//save to mongodb
			CommentD commentD = new CommentD(line[0], new Date());
			reviewDSave.getComments().add(commentD);
			mongoTemplate.save(reviewDSave);
			commentDCreateList.add(commentD);
		}


		//read comments from mongodb
		ReviewD reviewDRead = mongoTemplate.findById(reviewDSave.get_id(), ReviewD.class);
		List<CommentD> commentDReadList = reviewDRead.getComments();

		System.out.println("### MongoDb-comments: " + commentDCreateList.size() + ";" + commentDReadList.size());
		assertTrue(commentDCreateList.size()==commentDReadList.size());
	}

}