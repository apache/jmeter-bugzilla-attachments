#!/usr/bin/python
import csv

with open('tests.jtl', 'w') as f:
    w = csv.writer(f)
    w.writerow(['timeStamp','elapsed','label','responseCode','responseMessage','threadName','dataType','success','failureMessage','bytes','sentBytes','grpThreads','allThreads','Latency','IdleTime','Connect'])
    label = 'HTTP Request'
    responseCode = 200
    responseMessage = 'OK'
    dataType = 'text'
    success = 'true'
    failureMessage = ''
    bytesReceived = 4320
    bytesSent = 121
    grpThreads = 100
    allThreads = 100
    timestamp = 1529430552272
    elapsed = 144
    latency = 143
    idleTime = 0
    connectTime = 69
    for i in range(50000):
        timestamp += elapsed
        for t in range(allThreads):
            threadName = 'Thread Group %d-%d' % (t, allThreads)
            w.writerow([timestamp, elapsed, label, responseCode, responseMessage, threadName, dataType, success, failureMessage, bytesReceived, bytesSent, grpThreads, allThreads, latency, idleTime, connectTime])

