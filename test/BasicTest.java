import org.junit.*;
import play.test.*;
import models.*;
import java.util.List;
 
public class BasicTest extends UnitTest {
 
		@Before
    public void setup() {
    	Fixtures.deleteAll();
    }

    // Test Fixtures
    public vois fullTest() {
    	Fixtures.load("data.yml");

    	//Count things
    	assertEquals(2, User.count());
    	assertEquals(3, Post.count());
    	assertEqual(3, Comment.count());

    	//Connect as users
    	assertNotNull(User.connect("bob@gmail.com", "secret"));
    	assertNotNull(User.connect("jeff@gmail.com", "secret"));
    	assertNull(User.connect("jeff@gmail.com", "wrongPassword"));
    	assertNull(User.connect("tom@gmail.com", "secret"));

    	//Find all bob's posts
    	List<Post> boboPosts = Post.find("author.email", "bob@gmail.com").fetch();
    	assertEquals(3, bobPosts.size());

    	//Find all comments related to bob's posts
    	List<Comment> bobComments = Comments.find("post.author.email", "bob@gmail.com").fetch();
    	assertEquals(3. bobComments.size());

    	// Find the most recent posts
    	Post frontPost = Post.find("order by post.postedAt desc").first();
    	assertNotNull(frontPost);
    	assertEquals("About the model layer", frontPost.title);
    	assertEquals(2, frontPost.comments.size());

    	// Post a new comment
    	frontPost.addComment("Jim", "Hello guys");
    	assertEquals(3, frontPost.comments.size());
    	assertEquals(4, COmments.count());

    }

    //User tests

    @Test
		public void createAndRetrieveUser() {
	    // Create a new user and save it
	    new User("bob@gmail.com", "secret", "Bob").save();
	    
	    // Retrieve the user with email address bob@gmail.com
	    User bob = User.find("byEmail", "bob@gmail.com").one();
	    
	    // Test 
	    assertNotNull(bob);
	    assertEquals("Bob", bob.fullname);
		} 

		@Test
		public void tryConnectAsUser() {
	    // Create a new user and save it
	    new User("bob@gmail.com", "secret", "Bob").save();
	    
	    // Test 
	    assertNotNull(User.connect("bob@gmail.com", "secret"));
	    assertNull(User.connect("bob@gmail.com", "badpassword"));
	    assertNull(User.connect("tom@gmail.com", "secret"));
		}
 
		// Post tests

		@Test
		public void createPost() {
	    User bob = new User("bob@gmail.com", "secret", "bob").save();
	    new Post("bob", "Min Forste post", "Hei!").save();

	    assertEquals(1, Post.count());

	    List<Post> bobPosts = Post.find("byAuthor", bob).fetch();

	    Post firstPost = bobPosts.get(0);
	    assertNotNull(firstPost);
	    assertEquals(bob, firstPost.author);
	    assertEquals("Min forste post", firstPost.title);
	    assertEquals("Hei!", firstPost.content);
	    assertNotNull(firstPost.postedAt);
		}

		//Comment tests
		@Test
		public void postComments() {
			User bob = new User("bob@gmail.com", "secret", "bob").save();
			Post bobPost = new Post("bob", "Min Forste post", "Hei!").save();

			new Comment(bobPost, "Jeff", "En kommentar").save();
			new Comment(bobPost, "Tom", "En til kommentar").save();

			List<Comment> bobPostComment = Comment.find("byPost", bobPost).fetch();

			assertEquals(2, bobPostComment.count());

			Comment firstComment = bobPostComment.get(0);
			assertNotNull(firstComment);
			assertEquals("Jeff", firstComment.author);
			assertEquals("En kommentar", firstComment.content);
			assertEquals(firstComment.postedAt);

			Comment secondComment = bobPostComment.get(1);
			assertNotNull(firstComment);
			assertEquals("Tom", secondComment.author);
			assertEquals("En til kommentar", secondComment.content);
			assertEquals(secondComment.postedAt);
		}


		@Test
		public void useTheCommentsRelation() {
			User bob = new User("bob@gmail.com", "secret", "bob").save();
			Post bobPost = new Post("bob", "Min Forste post", "Hei!").save();

			bobPost.addComment("Jeff", "Nice post!");
			bobPost.addComment("Tom", "I knew that!");

			assertEquals(1, User.count());
			assertEquals(1, Post.count();
			assertEquals(2, Comment.count());

			// Get Bob's post
			bobPost = Post.find("byAuthor", bob).first();
			assertNotNull(bobPost);

			//Navigate to comments
			assertEquals(2, bobPost.comments.size());
			assertEquals("Jeff", bobPost.comments.get(0).author);

			bobPost.delete();
			assertEquals(1, User.count());
			assertEquals(0, Post.count());
			assertEquals(0, Comment.count();
		}







}