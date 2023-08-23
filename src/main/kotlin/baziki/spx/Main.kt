package baziki.spx

import baziki.spx.utils.ApiGet

fun main(args : Array<String>) {
    val api = ApiGet()
    val version = "1.0.0"
    print("SPX-VN MULTI TRACKING (build-$version)\nEnter Tracking ID (id1 id2 id3...): ")
    val stringInput = readln()
    println("Start tracking...")
    for (x in stringInput.split(' ')) {
        val request = api.createData(x)
        val status = request?.get("meta")!!.asJsonObject.get("code").asInt
        if (status == 4101) {
            // TO - DO
            val requestData = api.getData(x)
            val data = requestData?.getAsJsonArray("data")!![0].asJsonObject
            println("\n\nStatus for [$x]:")
            println("  - ID: ${data.get("id").asString}")
            println("  - Status: ${data.get("delivery_status").asString}")
            println("  - Latest Event: ${data.get("latest_event").asString.split(',')[0]} (Updated at ${data.get("latest_event").asString.split(',')[1]})")
        } else if (status == 200){
            val data = request.get("data").asJsonObject
            println("\n\nStatus for [$x]:")
            println("  - ID: ${data.get("id").asString}")
            println("  - Status: ${data.get("delivery_status").asString}")
            println("  - Latest Event: ${data.get("latest_event").asString.split(',')[0]} (Updated at ${data.get("latest_event").asString.split(',')[1]})")
        } else {
            println("\n\nStatus for [$x]:")
            println("  - Invalid Tracking ID or not exist")
        }
    }
}
