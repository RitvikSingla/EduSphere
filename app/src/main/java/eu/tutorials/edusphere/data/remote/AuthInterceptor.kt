package eu.tutorials.edusphere.data.remote

import eu.tutorials.edusphere.data.util.TokenManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // Skip auth for login/register endpoints
//        if (request.url.encodedPath().contains("/auth/")) {
//            return chain.proceed(request)
//        }

        val token = runBlocking { tokenManager.getAccessToken() }

        val authenticatedRequest = if (token != null) {
            request.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        } else {
            request
        }

        return chain.proceed(authenticatedRequest)
    }
}