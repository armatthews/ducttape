package ducttape.util

import java.util.logging.Formatter
import java.util.logging.LogRecord

import collection._
import collection.JavaConversions._
import java.lang.management.ManagementFactory

class LogFormatter extends Formatter {
  
    val threadCache = new mutable.HashMap[Long,Thread]
    threadCache
    
    val startTime = ManagementFactory.getRuntimeMXBean.getStartTime
    
    def getThreadName(id: Long): String = threadCache.get(id) match {
      case Some(t) => t.getName
      case None => {
        // expand the cache
        val threads: Iterable[Thread] = Environment.getAllThreads
        for (t <- threads) {
          threadCache += t.getId -> t
        }
        threadCache.get(id) match {
          case Some(t) => t.getName
          case None => id.toString
        }
      }
    }
  
    override def format(record: LogRecord): String = formatMessage(record) + "\n"
    
    override def formatMessage(record: LogRecord): String = {
      "[%d] %s: %s [%s]: %s".format(
          record.getMillis - startTime,
          record.getLevel,
          record.getLoggerName,
          getThreadName(record.getThreadID),
          record.getMessage)
    }
}