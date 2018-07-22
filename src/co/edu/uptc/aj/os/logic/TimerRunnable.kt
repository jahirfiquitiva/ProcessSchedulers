package co.edu.uptc.aj.os.logic

class TimerRunnable(private val onTime: (Long) -> Unit = {}) : Runnable {
    private var canRun = true
    private var start = 0L
    private var current = 0L
    
    override fun run() {
        start = System.currentTimeMillis()
        while (canRun) {
            current = System.currentTimeMillis()
            onTime(getTimeInSecs())
            try {
                Thread.sleep(1000)
            } catch (ignored: Exception) {
            }
        }
    }
    
    fun getTime(): Long = current - start
    fun getTimeInSecs(): Long = getTime() / 1000
    
    fun stop() {
        canRun = false
    }
}