package co.edu.uptc.aj.os.logic

import co.edu.uptc.aj.os.intBetween
import java.util.Random

class ProcessManager(
    private val first: Scheduler,
    private val second: Scheduler,
    private val third: Scheduler,
    private val debug: Boolean = false
                    ) {
    
    companion object {
        private const val PROCESS_CREATION_DELAY = 3
    }
    
    init {
        first.quantum = 2
        first.onStatusChanged = { pro, time ->
            postToSecondQueue(pro, time)
        }
        second.quantum = 4
        second.onStatusChanged = { pro, time ->
            postToThirdQueue(pro, time)
        }
        third.quantum = 8
        third.onStatusChanged = { pro, _ ->
            postToFinish(pro)
        }
    }
    
    var queueListener: QueueListener? = null
    var count = 0
        private set
    private var execute = false
    
    private val maxProcesses = if (debug) 10 else Integer.MAX_VALUE
    
    private val timerRun by lazy {
        TimerRunnable { createNewProcess(it.toInt()) }
    }
    private var timer: Thread? = null
    
    private val rand: Random
        get() = if (debug) Random(12345) else Random()
    
    private val finishedProcesses = ArrayList<Process>()
    
    fun start() {
        execute = true
        timer = Thread(timerRun)
        timer?.start()
    }
    
    fun stop() {
        execute = false
    }
    
    fun executing() = execute
    
    fun isFinished() = finishedProcesses.size >= maxProcesses || finishedProcesses.size >= count
    
    fun getTime(): String {
        val millis = timerRun.getTime()
        val seconds = (millis / 1000) % 60
        val minutes = (millis / (1000 * 60) % 60)
        val hours = (millis / (1000 * 60 * 60) % 24)
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
    
    private fun internalStop() {
        timerRun.stop()
        timer?.interrupt()
        timer = null
    }
    
    private fun createNewProcess(secs: Int) {
        if (!isFinished())
            queueListener?.updateTime()
        
        first.checkStatus(secs)
        second.checkStatus(secs)
        third.checkStatus(secs)
        
        if (!execute) return
        if ((secs == 0 || secs % PROCESS_CREATION_DELAY == 0) && count < maxProcesses) {
            count += 1
            val newProcess = Process(
                "P$count",
                rand.intBetween(1, 14),
                rand.intBetween(-19, 19))
            first.addProcess(newProcess, secs) { pro, index ->
                queueListener?.onAddedToFirstQueue(pro, index)
            }
        }
    }
    
    private fun postToSecondQueue(process: Process, secs: Int) {
        first.removeProcess(process) { queueListener?.onRemovedFromFirstQueue(it) }
        if (process.isFinished) {
            reportFinished(process)
        } else {
            second.addProcess(process, secs) { pro, index ->
                queueListener?.onAddedToSecondQueue(pro, index)
            }
        }
    }
    
    private fun postToThirdQueue(process: Process, secs: Int) {
        second.removeProcess(process) { queueListener?.onRemovedFromSecondQueue(it) }
        if (process.isFinished) {
            reportFinished(process)
        } else {
            third.addProcess(process, secs) { pro, index ->
                queueListener?.onAddedToThirdQueue(pro, index)
            }
        }
    }
    
    private fun postToFinish(process: Process) {
        third.removeProcess(process) { queueListener?.onRemovedFromThirdQueue(it) }
        reportFinished(process)
    }
    
    private fun reportFinished(process: Process) {
        finishedProcesses.add(process)
        queueListener?.onProcessFinished(process)
        if (isFinished() && !execute)
            internalStop()
    }
}