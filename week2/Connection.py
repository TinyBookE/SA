import socket
import json
from multiprocessing import Process
import re

class Connect:
    def __init__(self, addr, recv_queue, send_queue):
        self.addr = addr
        self.recv_queue = recv_queue
        self.send_queue = send_queue
        self.socket = socket.socket()

    def conn(self):
        try:
            self.socket.connect(self.addr)
        except Exception as e:
            print(e)
            self.recv_queue.put('connect unsuccessfully\n')
            return False
        self.recv_queue.put('connect successfully\n')
        return True

    def start(self):
        self.start_recv()
        self.start_send()

    def _recv(self):
        try:

            while True:
                data = self.socket.recv(1024).decode()
                data = json.loads(data)
                print(data)
                if data['code'] == 1:
                    self.recv_queue.put('----------\n')
                    self.recv_queue.put(data['msg']+'\n')
                else:
                    result = data['result']
                    for date, res in result.items():
                        msg = '----------\n' + date + ':\n'
                        for field, value in res.items():
                            msg += field + ': ' + str(value) + '\n'
                        self.recv_queue.put(msg)

        except socket.error:
            self.recv_queue.put('socket close.')
            return
        except Exception as e:
            print(e)
            return

    def start_recv(self):
        t = Process(target=self._recv)
        t.daemon = True
        t.start()

    def _send(self):
            try:

                while True:
                    msg = self.send_queue.get()
                    date = re.findall(r'(\d{4}[/-]\d{1,2}[/-]\d{1,2})', msg['date'])
                    if len(date) == 0:
                        self.recv_queue.put('The date format should be "xxxx-x-x"')
                        continue
                    field = re.split(r'[\s:;]+', msg['field'])[:-1]
                    if len(field) == 0:
                        self.recv_queue.put('Have no valid field')
                        continue
                    msg = {'date': date, 'field': field}
                    msg = json.dumps(msg)
                    print(msg)
                    self.socket.sendall(msg.encode('UTF-8'))

            except socket.error:
                return
            except Exception as e:
                print(e)
                return

    def start_send(self):
        t = Process(target=self._send)
        t.daemon = True
        t.start()

    def close(self):
        try:
            self.socket.close()
            self.socket = None
        except Exception as e:
            print(e)