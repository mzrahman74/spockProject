import io.restassured.http.ContentType
import spock.lang.Specification
import io.restassured.RestAssured

class UserApiSpec extends Specification{

    def setupSpec() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com"

    }
    def "should fetch user by id" () {
        when:
        def response = RestAssured.given().contentType(ContentType.JSON).get("/users/1")
        .then().extract().response()

        then:
        response.statusCode() == 200
        System.out.println(response.asString())
        response.prettyPrint()
        response.jsonPath().getString("company.name") == "Romaguera-Crona"
    }

    def "should create a new post" () {
        given: "a new post payload"
        def payload = [
                userId: 1,
                title: "spock project",
                body: "API automation made easy!"
        ]
        when: "I send post request to /posts"
        def response = RestAssured
                .given().contentType(ContentType.JSON).body(payload).when().post("/posts").then().extract().response();

        then: 'response status is 201'
        response.statusCode() == 201
        and: "response contains the same title"
        response.jsonPath().getString("title") == payload.title
    }
}
