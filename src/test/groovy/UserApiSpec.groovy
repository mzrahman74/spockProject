import io.restassured.http.ContentType
import spock.lang.Specification
import io.restassured.RestAssured

class UserApiSpec extends Specification {

    def setupSpec() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com"

    }

    def "should fetch user by id"() {
        when: "get call /user/1"
        def response = RestAssured.given().contentType(ContentType.JSON).get("/users/1")
                .then().extract().response()

        then:
        response.statusCode() == 200
        response.jsonPath().getString("company.name") == "Romaguera-Crona"
    }

    def "should create a new post"() {
        given: "a new post payload"
        def payload = [
                userId: 1,
                title : "spock project",
                body  : "API automation made easy!"
        ]
        when: "I send post request to /posts"
        def response = RestAssured
                .given().contentType(ContentType.JSON).body(payload).when().post("/posts").then().extract().response()

        then: 'response status is 201'
        response.statusCode() == 201
        and: "response contains the same title"
        response.jsonPath().getString("title") == payload.title
    }

    def "Should fetch postId for comments"() {
        when: "comments end points"
        def response = RestAssured.given().contentType(ContentType.JSON).queryParam("postId", 1).get("/comments")
                .then().extract().response()

        then:
        response.statusCode() == 200
    }

    def "Should update put body for 5"() {
        given: "update put payload"
        def payload = [
                userId: 1,
                id    : 5,
                title : "nesciunt quas odio",
                body  : "test"

        ]
        when: "I update request to /posts/5"
        def response = RestAssured.given().contentType(ContentType.JSON).body(payload).when().put("/posts/5")
                .then().extract().response()

        then: 'response status is 200'
        response.statusCode() == 200
        and: "response contains body"
        response.jsonPath().getString("body") == payload.body

    }

    def "Should update patch body for post 5"() {
        given: "patch payload"
        def payload = [
                title: "title test",
        ]
        when: "I patch the request to /posts/5"
        def response = RestAssured.given().contentType(ContentType.JSON).body(payload).when().patch("/posts/5").then().extract().response()

        then: 'response status is 200'
        response.statusCode() == 200
        and: "response contains body"
        response.jsonPath().getString("title") == payload.title
    }

    def "Should show all the posts" () {
        when: "get call for /posts"
        def response = RestAssured.given().contentType(ContentType.JSON).get("/posts")
        .then().extract().response()

        then: "response status is 200"
        response.statusCode() == 200
    }

}
