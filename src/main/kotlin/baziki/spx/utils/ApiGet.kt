package baziki.spx.utils

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import org.jetbrains.annotations.NotNull
import org.json.JSONObject

@SuppressWarnings("all")
class ApiGet {
    // i7v35a0u-ftsr-iaqa-fppf-naq1yys6x70u
    private val apiKey = "i7v35a0u-ftsr-iaqa-fppf-naq1yys6x70u"

    // Class function
    fun createData(id: String): JsonObject? {
        val apiUrl = "https://api.trackingmore.com/v4/trackings/create"

        // Create an HTTP client with custom timeout settings
        val requestConfig = RequestConfig.custom()
            .setConnectTimeout(5000) // Timeout for establishing connection
            .setSocketTimeout(5000) // Timeout for waiting for data
            .build()

        val httpClient = HttpClients.custom()
            .setDefaultRequestConfig(requestConfig)
            .build()

        // Create the POST request
        val postRequest = HttpPost(apiUrl)

        // Set headers
        postRequest.addHeader("Tracking-Api-Key", apiKey)
        postRequest.addHeader("Content-Type", "application/json")

        // Create JSON object for the request body
        val jsonObject = JSONObject()
        jsonObject.put("tracking_number", id)
        jsonObject.put("courier_code", "spx-vn")

        // Set request body
        postRequest.entity = StringEntity(jsonObject.toString(), "UTF-8")

        try {
            // Execute the request
            val response = httpClient.execute(postRequest)

            // Extract and print the response content
            val entity = response.entity
            val responseBody = EntityUtils.toString(entity)
            // Ensure the response entity is properly closed
            EntityUtils.consume(entity)
            return JsonParser().parse(responseBody).getAsJsonObject()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            // Close the HTTP client
            httpClient.close()
        }
        return null
    }

    fun getData(id: String): JsonObject? {
        val apiUrl = "https://api.trackingmore.com/v4/trackings/get"

        // Create an HTTP client with custom timeout settings
        val requestConfig = RequestConfig.custom()
            .setConnectTimeout(5000) // Timeout for establishing connection
            .setSocketTimeout(5000) // Timeout for waiting for data
            .build()

        val httpClient = HttpClients.custom()
            .setDefaultRequestConfig(requestConfig)
            .build()

        // Create the GET request
        val getRequest = HttpGet("$apiUrl?tracking_numbers=$id")

        // Set headers
        getRequest.addHeader("Tracking-Api-Key", apiKey)
        getRequest.addHeader("Content-Type", "application/json")

        try {
            // Execute the request
            val response = httpClient.execute(getRequest)

            // Extract and print the response content
            val entity = response.entity
            val responseBody = EntityUtils.toString(entity)

            // Ensure the response entity is properly closed
            EntityUtils.consume(entity)
            return JsonParser().parse(responseBody).getAsJsonObject()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            // Close the HTTP client
            httpClient.close()
        }
        return null
    }
}