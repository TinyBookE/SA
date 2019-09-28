import boto3
from botocore.exceptions import ClientError
import tkinter as tk
import time
from threading import Thread


class Sender:

    def __init__(self, QueueName = 'test.fifo'):
        self.draw()
        self.init_conn(QueueName)
        self.window.mainloop()

    def init_conn(self, QueueName):
        self.statu = '0'
        self.msgBox.insert('end', 'Link Start...\n', 'red')
        self.conn = SendConnect()
        result = self.conn.init(QueueName)
        if result == 0:
            self.msgBox.insert('end', 'Link Success!\n\n', 'red')
        else:
            self.msgBox.insert('end', 'Link Fail!\n', 'red')

    def draw(self):
        self.window = tk.Tk()
        self.window.resizable(width=False, height=False)
        self.window.title('发布者')
        self.window.protocol("WM_DELETE_WINDOW", self.on_close)

        frame_top = tk.Frame(self.window, width=380, height=270, bg='white')
        frame_center = tk.Frame(self.window, width=380, height=100, bg='white')
        frame_bottom = tk.Frame(self.window, width=380, height=30)

        self.msgBox = tk.Text(frame_top)
        self.sendBox = tk.Text(frame_center)
        self.sendButton = tk.Button(frame_bottom, text='发送',command=self.send)
        self.changeButton = tk.Button(frame_bottom, text='更换消息类型', command=self.changeSend)
        self.channelLabel = tk.Label(frame_bottom, text='频道0')
        vbar = tk.Scrollbar(frame_top, orient=tk.VERTICAL)

        self.msgBox['yscrollcommand'] = vbar.set
        vbar['command'] = self.msgBox.yview

        frame_top.grid(row=0, column=0, padx=2, pady=5)
        frame_center.grid(row=1, column=0, padx=2, pady=5)
        frame_bottom.grid(row=2, column=0, padx=2, pady=5)

        frame_top.grid_propagate(0)
        frame_center.grid_propagate(0)
        frame_bottom.grid_propagate(0)

        self.msgBox.tag_config('green', foreground='#008B00')
        self.msgBox.tag_config('red', foreground='#ff0000')

        self.msgBox.place(x=0,width=360,height=270)
        self.sendBox.grid()
        self.sendButton.grid(row=2,column=0)
        self.changeButton.grid(row=2,column=1)
        self.channelLabel.grid(row=2,column=2)
        vbar.place(x=360, width=20, height=270)

    def on_close(self):
        self.window.destroy()

    def send(self):
        msg = time.strftime("%Y-%m-%d %H:%M:%S", time.localtime()) + '\n '
        self.msgBox.insert("end", 'Sender{}:'.format(self.statu), "green")
        self.msgBox.insert("end", msg, "green")
        self.msgBox.insert("end", self.sendBox.get('0.0', "end"))
        msg += self.sendBox.get('0.0', "end")
        self.sendBox.delete('0.0', "end")
        self.conn.send(msg, self.statu)

    def changeSend(self):
        self.statu = str((int(self.statu)+1)%3)
        self.channelLabel.config(text='频道'+self.statu)


class SendConnect:
    def init(self, QueueName):
        try:
            self.sqs = boto3.resource('sqs')
            self.queue = self.sqs.get_queue_by_name(QueueName=QueueName)
            return 0
        except ClientError:
            print('Keys are out-of-date.Go awseducte and get new credentials.')
            return 1

        except Exception as e:
            print(e)
            return 2

    def send(self, msg, chan):
        t = Thread(target=self.conn, args=(msg, chan,))
        t.start()

    def conn(self, msg, chan):
        attributes = {
            'Channel': {
                'StringValue': chan,
                'DataType': 'String'
            }
        }
        self.queue.send_message(MessageBody=msg, MessageAttributes=attributes, MessageGroupId=chan)


if __name__ == '__main__':
    sender = Sender()


