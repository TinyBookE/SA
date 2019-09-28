import Connection
from Client import Client

from multiprocessing import Queue

if __name__ == '__main__':
    send = Queue()
    recv = Queue()
    conn = Connection.Connect(('localhost', 23333), recv, send)
    if not conn.conn():
        exit(1)
    conn.start()
    client = Client(('localhost', 23333), recv, send)
    conn.close()