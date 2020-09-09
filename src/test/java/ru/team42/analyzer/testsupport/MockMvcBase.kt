package ru.team42.analyzer.testsupport

import capital.scalable.restdocs.AutoDocumentation.*
import capital.scalable.restdocs.jackson.JacksonResultHandlers.prepareJackson
import capital.scalable.restdocs.misc.AuthorizationSnippet.documentAuthorization
import capital.scalable.restdocs.response.ResponseModifyingPreprocessors.limitJsonArrayLength
import capital.scalable.restdocs.response.ResponseModifyingPreprocessors.replaceBinaryContent
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.tomcat.util.json.JSONParserTokenManager
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.convert.ConversionService
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.cli.CliDocumentation.curlRequest
import org.springframework.restdocs.http.HttpDocumentation.httpRequest
import org.springframework.restdocs.http.HttpDocumentation.httpResponse
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor
import org.springframework.restdocs.operation.preprocess.Preprocessors.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.RequestPostProcessor
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.util.Base64Utils
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter
import ru.team42.analyzer.dto.request.AuthRequest
import ru.team42.analyzer.dto.response.AuthResponse
import ru.team42.analyzer.dto.response.ButtonDto
import ru.team42.analyzer.dto.response.RoleDto
import ru.team42.analyzer.dto.response.UserDto
import ru.team42.analyzer.entities.RoleEntity
import ru.team42.analyzer.entities.UserEntity
import ru.team42.analyzer.jsonApi.ApiResponse
import ru.team42.analyzer.repositories.RoleRepository
import ru.team42.analyzer.repositories.UserRepository
import ru.team42.analyzer.services.interfaces.UserService
import javax.servlet.Filter


private const val DEFAULT_AUTHORIZATION = "Resource is public."

/**
 * Required set up code for MockMvc tests.
 */
@ExtendWith(RestDocumentationExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class MockMvcBase {

    @Autowired
    private lateinit var context: WebApplicationContext

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var springSecurityFilterChain: Filter

    @Autowired
    private lateinit var requestMappingHandlerAdapter: RequestMappingHandlerAdapter

    protected lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var userRepository:UserRepository

    @Autowired
    private lateinit var userService:UserService

    @JvmField protected var TEST_USER_LOGIN:String = "test_user"
    @JvmField protected var TEST_USER_PASSWORD:String = "test_user123"
    @JvmField protected var TEST_USER_ROLE:String = "ROLE_USER"

    private lateinit var testUser:UserEntity

    @Autowired
    private lateinit var conversionService:ConversionService

    private fun createTestUser() {

        val userDto:UserDto = userService.createUser(TEST_USER_LOGIN, TEST_USER_PASSWORD, setOf(RoleDto(null, TEST_USER_ROLE)))
        this.testUser = conversionService.convert(userDto, UserEntity::class.java)!!
    }

    @AfterEach
    fun deleteTestUser() {
        userRepository.delete(this.testUser)
    }

    @BeforeEach
    fun setUp(restDocumentation: RestDocumentationContextProvider) {

        this.createTestUser()

        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .addFilters<DefaultMockMvcBuilder>(springSecurityFilterChain)
            .alwaysDo<DefaultMockMvcBuilder>(prepareJackson(objectMapper))
            .alwaysDo<DefaultMockMvcBuilder>(commonDocumentation())
            .apply<DefaultMockMvcBuilder>(documentationConfiguration(restDocumentation)
                    .uris()
                    .withScheme("http")
                    .withHost("localhost")
                    .withPort(8080)
                    .and().snippets()
                    .withDefaults(curlRequest(), httpRequest(), httpResponse(),
                            requestFields(), responseFields(), pathParameters(),
                            requestParameters(), authorization(DEFAULT_AUTHORIZATION), description(), methodAndPath(),
                            section(), modelAttribute(requestMappingHandlerAdapter.argumentResolvers)))
            .build()
    }

    protected fun commonDocumentation(): RestDocumentationResultHandler {
        return document("{class-name}/{method-name}",
                preprocessRequest(), commonResponsePreprocessor())
    }

    protected fun commonResponsePreprocessor(): OperationResponsePreprocessor {
        return preprocessResponse(replaceBinaryContent(), limitJsonArrayLength(objectMapper),
                prettyPrint())
    }

    protected fun userToken(): RequestPostProcessor {
        return RequestPostProcessor { request ->
            // If the tests requires setup logic for users, you can place it here.
            // Authorization headers or cookies for users should be added here as well.
            val accessToken: String
            try {
                accessToken = getAccessToken(TEST_USER_LOGIN, TEST_USER_PASSWORD)
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

            request.addHeader("Authorization", "Bearer $accessToken")
            documentAuthorization(request, "User access token required.")
        }
    }



    @Throws(Exception::class)
    private fun getAccessToken(username: String, password: String): String {

        val dto = AuthRequest(username, password)
        val o = ObjectMapper()
        val j = o.writeValueAsString(dto)

        val body = mockMvc
            .perform(
                    post("/login")
                            .contentType(
                                    MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(j)
            )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.data.token", `is`(notNullValue())))
            .andReturn().response.contentAsString

        val response:ApiResponse<AuthResponse> = objectMapper.readValue(body, object:TypeReference<ApiResponse<AuthResponse>>(){})

        return response.data.token
    }
}
