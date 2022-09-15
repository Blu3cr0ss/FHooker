package idk.bluecross.fhooker.util

import java.net.InetSocketAddress
import java.net.Proxy
import java.net.URL

private var currentProxy = 0;
private var thisProxyUsedTimes = 0

private var proxyList = arrayListOf<String>(
    "http://185.171.231.81:80",
    "http://203.24.103.105:80",
    "http://141.193.213.66:80",
    "http://172.67.172.175:80",
    "http://106.107.203.151:80",
    "http://172.67.0.16:80",
    "http://172.67.70.199:80",
    "http://172.67.70.28:80",
    "http://172.67.3.96:80",
    "http://172.67.181.109:80",
    "http://172.67.177.199:80",
    "http://185.162.228.22:80",
    "http://23.227.38.250:80",
    "http://185.162.230.119:80",
    "http://185.162.228.82:80",
    "http://203.34.28.192:80",
    "http://203.24.108.107:80",
    "http://173.245.49.105:80",
    "http://203.23.103.69:80",
    "http://203.32.121.82:80",
    "http://203.34.28.242:80",
    "http://203.28.8.196:80",
    "http://203.24.102.64:80",
    "http://203.24.108.181:80",
    "http://45.8.107.222:80",
    "http://185.171.230.109:80",
    "http://203.30.191.191:80",
    "http://45.8.104.229:80",
    "http://203.13.32.147:80",
    "http://45.8.104.29:80",
    "http://203.23.103.114:80",
    "http://203.30.191.217:80",
    "http://91.226.97.28:80",
    "http://45.8.104.156:80",
    "http://172.67.2.94:80",
    "http://172.67.75.194:80",
    "http://172.67.43.217:80",
    "http://172.67.185.150:80",
    "http://172.64.149.94:80",
    "http://141.193.213.211:80",
    "http://172.67.25.30:80",
    "http://172.67.67.120:80",
    "http://172.64.137.111:80",
    "http://141.193.213.228:80",
    "http://172.67.208.171:80",
    "http://173.245.49.30:80",
    "http://172.67.35.115:80",
    "http://185.162.230.17:80",
    "http://203.34.28.36:80",
    "http://203.24.109.60:80",
    "http://203.24.109.172:80",
    "http://45.8.106.25:80",
    "http://45.14.174.86:80",
    "http://185.162.231.116:80",
    "http://203.30.189.226:80",
    "http://23.227.38.229:80",
    "http://203.13.32.70:80",
    "http://203.24.103.81:80",
    "http://203.22.223.27:80",
    "http://203.13.32.246:80",
    "http://203.30.191.129:80",
    "http://172.67.8.19:80"
)

fun getCurrent(): Proxy? {
    var isValid = false;
    for (i in 0 until proxyList.size) {
        val request = URL(
            "https://proxycheck.io/v2/${
                proxyList[currentProxy].removePrefix("http://").removeSuffix(":80")
            }?vpn=1&asn=1"
        ).readText()
        isValid = request.contains("\"status\": \"ok\",")
        if (!isValid) {
            proxyList.remove(proxyList[currentProxy])
        } else {
            break
        }
    }
    if (thisProxyUsedTimes == 5) {      // how many repeats for 1 proxy to use
        thisProxyUsedTimes = 0
        if (currentProxy == proxyList.size) currentProxy = 0
        else currentProxy += 1
    }
    thisProxyUsedTimes += 1
    log("current proxy: " + proxyList[currentProxy])
    return if (isValid) {
        Proxy(
            Proxy.Type.HTTP,
            InetSocketAddress(proxyList[currentProxy].removePrefix("http://").removeSuffix(":80"), 80)
        )
    } else {
        Proxy.NO_PROXY
    }
}

fun checkIp() {
    log(URL("https://api.ipify.org/").readText().removeSuffix("\n"))
}