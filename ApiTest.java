package ElsevierAPI;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ApiTest {

    @BeforeClass
    public static void setUp() {

        baseURI = "http://api.url.org";
    }
    @AfterClass
    public static void tearDown() {

        reset();
    }
    //Add a new user via POST Method
    @DisplayName("A New User with Json String POST /user")
    @Test
    public void PostANewUserString() {

        String NewUserStr =
                "    {\n" +
                        "      \"name\": \"Tanya\",\n" +
                        "       \"age\": 30\n" +
                        "        \"country\": \"USA\n" +
                        "    }";

        System.out.println(NewUserStr);

        given()
                .contentType(ContentType.JSON)
                .body(NewUserStr).
                when()
                .post("/user").
                then()
                .statusCode(is(201)) // StatusCode should be 201/Created
                .contentType(ContentType.JSON)
                .body("data.name", is("Tanya"))
                .body("data.age", is(30))
                .body("data.country", is("USA"))
        ;

    }

    //Add a New user #2 option
    @DisplayName("Adding New User With Map/POST")
    @Test
    public void testAddNewUserWithMapAsBody() {

        Map<String, Object> requestMap = new LinkedHashMap<>();
        requestMap.put("name", "Tanya");
        requestMap.put("age", 30);
        requestMap.put("country", "USA");

        System.out.println(requestMap);

        given()
                .contentType(ContentType.JSON)
                .body(requestMap).
                when()
                .post("/user").
                then()
                .statusCode(is(201))
                .contentType(ContentType.JSON)
                .body("data.name", is("Tanya"))
                .body("data.age", is(30))
                .body("data.country", is("USA"))
        ;
    }

    @DisplayName("Testing PUT /user/{id} with String body")
    @Test
    public void testUpdatingUserWithStringBody() {

        String updateStrPayload =
                "    {\n" +
                        "      \"name\": \"Lucy\",\n" +
                        "       \"age\": 20\n" +
                        "        \"country\": \"USA\n" +
                        "    }";

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", 101)
                .body(updateStrPayload).
                when()
                .put("/user/{id}").
                then()

                .statusCode(is(204))
                .header("Date", is(notNullValue()))
        ;

    }

    @DisplayName("Testing Get /user/{id} endpoint")
    @Test
    public void test1Spartan() {

        given()
                .accept(ContentType.JSON).
                when()
                .get("/user/101").
                then()
                .statusCode(is(200))
                .contentType(ContentType.JSON)
        ;
    }

    }







