package pl.butajlo.androidadvanced.networking

import okhttp3.*
import pl.butajlo.androidadvanced.settings.DebugPreferences
import javax.inject.Inject

class MockInterceptor @Inject constructor(val mockResponseFactory: MockResponseFactory, val debugPreferences: DebugPreferences)
    : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if(debugPreferences.useMockResponsesEnabled()) {
            val mockResponse = mockResponseFactory.getMockResponse(chain.request())
            if(mockResponse != null) {
                return Response.Builder()
                        .message("")
                        .protocol(Protocol.HTTP_1_1)
                        .request(chain.request())
                        .code(200)
                        .body(ResponseBody.create(MediaType.parse("text/json"), mockResponse))
                        .build()
            }
        }
        return chain.proceed(chain.request())
    }
}