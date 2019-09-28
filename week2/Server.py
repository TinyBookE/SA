import pymysql
import socket
from multiprocessing.pool import Pool
import json
import sys
import getopt


class DBServer:
    def __init__(self, user, passwd, host, port, dbname, tablename):
        self.host = host
        self.port = port
        self.dbname = dbname
        self.user = user
        self.passwd = passwd
        self.charset = 'utf8'
        self.table = tablename
        self.pool = Pool(processes=4)

    def connect(self, conn):
        cursor, db = self.new_cursor()

        while True:
            try:
                msg = conn.recv(1024)
                msg = json.loads(msg.decode())

                result = {'code': 0, 'result': {}}
                dateall = msg['date']
                field = msg['field']
                for date in dateall:
                    result['result'][date] = {}
                    sql = self.get_sql(date, field)
                    cursor.execute(sql)
                    record = cursor.fetchone()
                    for i in range(len(field)):
                        result['result'][date][field[i]] = record[i]

                result = json.dumps(result)
                conn.sendall(result.encode('UTF-8'))
            except TypeError:
                result = {'code':1, 'msg':'not exist'}
                result = json.dumps(result).encode('UTF-8')
                conn.sendall(result)

            except Exception as e:
                print(e)
                print(type(e))
                cursor.close()
                db.close()
                return

    def listen(self):
        self.socket = socket.socket()
        self.socket.bind(('localhost', 23333))
        self.socket.listen(10)

        while True:
            conn, addr = self.socket.accept()
            print(addr)
            self.pool.apply_async(self.connect, (conn, ))


    def new_cursor(self):
        try:
            db = pymysql.connect(host=self.host, port=self.port, db=self.dbname,
                                 user=self.user, passwd=self.passwd, charset=self.charset)
            cursor = db.cursor()
            return cursor, db
        except Exception as e:
            raise e

    def get_sql(self, date, args):
        field = ''
        for i in args:
            field += i
            field += ','
        field = field[:-1]
        sql = 'select {} from {} where 日期 = \'{}\''.format(field, self.table, date)
        print(sql)
        return sql

    def close(self):
        try:
            self.socket.close()
        except:
            return

    def __getstate__(self):
        self_dict = self.__dict__.copy()
        del self_dict['pool']
        return self_dict

    def __setstate__(self, state):
        self.__dict__.update(state)

if __name__ == '__main__':
    host = 'localhost'
    port = 3306
    user = 'user'
    passwd = 'passwd'
    dbname = 'test'
    tablename = 't'
    try:
        options, args = getopt.getopt(sys.argv[1:], "u:P:p:", ['user=', 'port=','passwd='])
    except:
        options = None
        exit(1)
    for n, v in options:
        if n in ('-u', '--user'):
            user = v
        elif n in ('-P', '--port'):
            port = v
        elif n in ('-p', '--passwd'):
            passwd = v
    print('Server start!')

    server = DBServer(user, passwd, host, port, dbname, tablename)
    server.listen()
