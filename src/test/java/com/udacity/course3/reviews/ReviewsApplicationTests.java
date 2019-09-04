package com.udacity.course3.reviews;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.udacity.course3.reviews.model.Comment;
import com.udacity.course3.reviews.model.Product;
import com.udacity.course3.reviews.model.Review;
import com.udacity.course3.reviews.repository.CommentRepository;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ReviewsApplicationTests {

	@Autowired private ProductRepository productRepository;
	@Autowired private ReviewRepository reviewRepository;
	@Autowired private CommentRepository commentRepository;

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


	private Product saveProduct() throws IOException {
		Properties props= new Properties();
		props.load(getClass().getClassLoader().getResourceAsStream("testProduct.properties"));

		Product product = new Product(
				props.getProperty("name"),
				props.getProperty("text"),
				Float.parseFloat(props.getProperty("price")));
		product.setCreationtime(new Date());
		System.out.println(product);

		productRepository.save(product);
		return product;
	}

	private Review saveReview() throws IOException {
		Properties props= new Properties();
		props.load(getClass().getClassLoader().getResourceAsStream("testReview.properties"));

		Product product = saveProduct();

		Review review = new Review(
				product,
				props.getProperty("text"),
				Integer.parseInt(props.getProperty("star")));
		review.setCreationtime(new Date());
		System.out.println(review);

		reviewRepository.save(review);
		return review;
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
	public void testSaveAndFindProduct() throws IOException {
		System.out.println("testSaveAndFindProduct..");

		Product productSave = saveProduct();
		int productId = productSave.getId();
		Product productRead = productRepository.findById(productId).get();
		assertEquals(productSave, productRead);
	}



	@Test
	public void testCreateAndFindProducts() throws IOException, URISyntaxException {
		System.out.println("testSaveAndFindAllProducts..");

		reader = getReader("testSaveAndFindAllProducts.csv");

		CSVParser parser = new CSVParserBuilder()
					.withSeparator(';')
					.withIgnoreQuotations(true)
					.build();

		csvReader = getParser();

		List<Product> productCreateList = new ArrayList<>();
		String[] line;
		int i = 0;
		while ((line = csvReader.readNext()) != null) {
			//System.out.println("### " + Arrays.toString(line));
			Product productCreate = new Product(line[0], line[1], Float.parseFloat(line[2]));
			productCreate.setCreationtime(new Date());

			productRepository.save(productCreate);
			productCreateList.add(productCreate);
		}

		List<Product> productReadList = new ArrayList<>();
		productRepository.findAll().forEach(product -> productReadList.add(product));
		assertTrue(productCreateList.size()==productReadList.size());
	}

	@Test
	public void testCreateAndFindReviews() throws IOException, URISyntaxException {
		System.out.println("testSaveAndFindReviews..");

		Product product = saveProduct();

		reader = getReader("testSaveAndFindReviews.csv");
		csvReader = getParser();

		List<Review> reviewCreateList = new ArrayList<>();
		String[] line;
		int i = 0;
		while ((line = csvReader.readNext()) != null) {
			System.out.println("### " + Arrays.toString(line));
			Review review = new Review(product, line[0], Integer.parseInt(line[1]));
			review.setCreationtime(new Date());
			reviewRepository.save(review);
			reviewCreateList.add(review);
		}

		List<Review> reviewReadList = new ArrayList<>();
		reviewRepository.findByProduct(product).forEach(review -> reviewReadList.add(review));

		System.out.println("### " + reviewCreateList.size() + ";" + reviewReadList.size());
		assertTrue(reviewCreateList.size()==reviewReadList.size());
	}

	@Test
	public void testCreateAndFindComments() throws IOException, URISyntaxException {
		System.out.println("testSaveAndFindComments..");

		Review review = saveReview();

		reader = getReader("testSaveAndFindComments.csv");
		csvReader = getParser();

		List<Comment> commentCreateList = new ArrayList<>();
		String[] line;
		int i = 0;
		while ((line = csvReader.readNext()) != null) {
			System.out.println("### " + Arrays.toString(line));
			Comment comment = new Comment(review, line[0]);
			comment.setCreationtime(new Date());
			commentRepository.save(comment);
			commentCreateList.add(comment);
		}

		List<Comment> commentReadList = new ArrayList<>();
		commentRepository.findByReview(review).forEach(comment -> commentReadList.add(comment));

		System.out.println("### " + commentCreateList.size() + ";" + commentReadList.size());
		assertTrue(commentCreateList.size()==commentReadList.size());
	}

}