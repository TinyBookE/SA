import LogicLayer
from PresentLayer import Present
import sys
import getopt
from multiprocessing import Queue

if __name__ == '__main__':
    ip = 'localhost'
    port = 23333

    try:
        options, args = getopt.getopt(sys.argv[1:], "i:p:", ['ip=', 'port='])
        for n, v in options:
            if n in ('-i', '--ip'):
                ip = v
            elif n in ('-p', '--port'):
                port = v
    except:
        options = None

    send = Queue()
    recv = Queue()
    conn = LogicLayer.Logic((ip, port), recv, send)
    if not conn.conn():
        exit(1)
    conn.start()
    client = Present(recv, send)
    conn.close()