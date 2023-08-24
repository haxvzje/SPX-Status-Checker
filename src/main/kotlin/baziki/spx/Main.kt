package baziki.spx

import baziki.spx.utils.ApiGet
import baziki.spx.utils.color.*

fun main() {
    // Bro im too fuckin' lazy to clean anything
    val api = ApiGet()
    val version = "1.0.0"
    print("${"   SPX-VN".lightRed().blackBackground()}${" STATUS".lightGreen().blackBackground()}${" TRACKER   ".lightYellow().blackBackground()}" + " (build-$version)\n\nEnter Tracking ID(s) (id1 id2 id3...): ")
    val stringInput = readln()
    val stringData = stringInput.split(' ')
    println("Start tracking...")
    for (x in stringData.distinct()) {
        val request = api.createData(x)
        val status = request?.get("meta")!!.asJsonObject.get("code").asInt
        when (status) {
            4101 -> {
                println("\nStatus for [${x.lightRed()}]:")
                try {
                    val requestData = api.getData(x)
                    val data = requestData?.getAsJsonArray("data")!![0].asJsonObject
                    val dataID = data.get("id").asString
                    val dataStatus = data.get("delivery_status").asString
                    val dataLatestEvent = "${data.get("latest_event").asString.split(',')[0]} (Updated at ${data.get("latest_event").asString.split(',')[1]})"
                    val dataStatusList = data.get("origin_info").asJsonObject.get("trackinfo").asJsonArray
                    println("  - ID: ${" ${dataID} ".blackBackground()}")
                    println("  - Status: ${" ${dataStatus} ".lightGreen().blackBackground()}")
                    println("  - Latest Event: ${" ${dataLatestEvent} ".lightCyan().blackBackground()}")
                    println("  - Total Event:")
                    for (e in dataStatusList) {
                        val subdata = e.asJsonObject
                        val subdataCheckPointDate = "${subdata.get("checkpoint_date").asString.split("T")[0]} | ${subdata.get("checkpoint_date").asString.split("T")[1]}"
                        val subdataDeliveryStatus = subdata.get("checkpoint_delivery_status").asString
                        val subdataTrackingDetail = subdata.get("tracking_detail").asString
                        println("      + " + " [$subdataCheckPointDate]".blackBackground() + " [${subdataDeliveryStatus.lightGreen()}".blackBackground() + "] ".blackBackground() + subdataTrackingDetail.blackBackground() + " ".blackBackground())
                    }
                } catch (e: Exception) {
                    println("  - Invalid Tracking ID or not exist")
                }
            }
            200 -> {
                println("\nStatus for [${x.lightRed()}]:")
                try {
                    val data = request.get("data").asJsonObject
                    val dataID = data.get("id").asString
                    val dataStatus = data.get("delivery_status").asString
                    val dataLatestEvent = "${data.get("latest_event").asString.split(',')[0]} (Updated at ${data.get("latest_event").asString.split(',')[1]})"
                    val dataStatusList = data.get("origin_info").asJsonObject.get("trackinfo").asJsonArray
                    println("  - ID: $dataID")
                    println("  - Status: $dataStatus")
                    println("  - Latest Event: $dataLatestEvent")
                    println("  - Total Event:")
                    for (e in dataStatusList) {
                        val subdata = e.asJsonObject
                        val subdataCheckPointDate = "${subdata.get("checkpoint_date").asString.split("T")[0]} | ${subdata.get("checkpoint_date").asString.split("T")[1]}"
                        val subdataDeliveryStatus = subdata.get("checkpoint_delivery_status").asString
                        val subdataTrackingDetail = subdata.get("tracking_detail").asString
                        println("      + [$subdataCheckPointDate] [$subdataDeliveryStatus] $subdataTrackingDetail")
                    }
                } catch (e: Exception) {
                    println("  - Invalid Tracking ID or not exist")
                }
            }
            else -> {
                println("\nStatus for [${x.lightRed()}]:")
                println("  - Invalid Tracking ID or not exist")
            }
        }
    }
    println("\nDone!")
}