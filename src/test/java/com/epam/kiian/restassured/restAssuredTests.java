package com.epam.kiian.restassured;

import io.restassured.RestAssured;
import model.Comment;
import model.Post;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import parser.JsonParser;

import java.util.List;

import static io.restassured.RestAssured.given;

public class restAssuredTests {
    JsonParser parser = new JsonParser();

    @BeforeClass
    public static void setupURL() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void testGetPostById() {
        Post post = parser.convertResponseToObject(given().when().get("/posts/1").body().asString(), Post.class);

        Assert.assertTrue(post.getTitle().contains(" sunt aut facere"));
    }

    @Test
    public void testGetAllPosts() {
        List<Post> posts = parser.convertResponseToListOfObjects(given().when().get("/posts").body().asString(), Post.class);

        Assert.assertEquals(posts.get(0).getTitle(), "sunt aut facere repellat provident occaecati excepturi optio reprehenderit");
    }

    @Test
    public void testGetPostsByUserId() {
        List<Post> posts = parser.convertResponseToListOfObjects(given().when().get("/posts?userId=1").body().asString(), Post.class);

        Assert.assertEquals(posts.size(), 10);
        Assert.assertEquals(posts.get(9).getId(), 10);
        Assert.assertEquals(posts.get(9).getUserId(), 1);
        Assert.assertEquals(posts.get(9).getTitle(), "optio molestias id quia eum");
    }

    @Test
    public void testGetCommentsByPostId() {
        List<Comment> comments = parser.convertResponseToListOfObjects(given().when().get("/posts/1/comments").body().asString(), Comment.class);

        Assert.assertEquals(comments.size(), 5);
        Assert.assertEquals(comments.get(0).getName(), "id labore ex et quam laborum");
        Assert.assertEquals(comments.get(1).getName(), "quo vero reiciendis velit similique earum");
        Assert.assertEquals(comments.get(2).getName(), "odio adipisci rerum aut animi");
        Assert.assertEquals(comments.get(3).getName(), "alias odio sit");
        Assert.assertEquals(comments.get(4).getName(), "vero eaque aliquid doloribus et culpa");
    }

    @Test
    public void createPost() {
        Post post = new Post() {{
            setUserId(1);
            setId(211);
            setTitle("magnam facilis autem");
            setBody("dolore placeat quibusdam ea quo vitae nmagni quis enim qui quis");
        }};

        Post receivedPost = parser.convertResponseToObject(given()
                .when()
                .contentType("application/json")
                .body(post)
                .post("/posts")
                .body()
                .asString(), Post.class);

        Assert.assertEquals(post.getUserId(), receivedPost.getUserId());
        Assert.assertEquals(post.getId(), receivedPost.getId());
        Assert.assertEquals(post.getTitle(), receivedPost.getTitle());
        Assert.assertEquals(post.getBody(), receivedPost.getBody());
    }

    @Test
    public void testUpdatePost() {
        Post post = new Post() {{
            setUserId(1);
            setId(21);
            setTitle("magnam facilis autem");
            setBody("dolore placeat quibusdam ea quo vitae nmagni quis enim qui quis");
        }};

        Post receivedPost = parser.convertResponseToObject(given()
                .when()
                .contentType("application/json")
                .body(post)
                .put("/posts/21")
                .body()
                .asString(), Post.class);

        Assert.assertEquals(post.getUserId(), receivedPost.getUserId());
        Assert.assertEquals(post.getId(), receivedPost.getId());
        Assert.assertEquals(post.getTitle(), receivedPost.getTitle());
        Assert.assertEquals(post.getBody(), receivedPost.getBody());
    }

    @Test
    public void testDeletePost() {
        given().when().delete("/posts/1").then().statusCode(200);
    }

}
