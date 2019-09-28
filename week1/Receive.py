import boto3
from botocore.exceptions import ClientError
from threading import Thread
import tkinter as tk


class Receiver:
    def __init__(self, QueueName = 'test.fifo'):
        self.draw()
        self.init_conn(QueueName)
        self.register_timer()
        self.window.mainloop()

    def init_conn(self, QueueName):
        self.statu = '0'
        self.msgBox.insert('end', 'Link Start...\n', 'red')
        self.conn = RecvConn()
        result = self.conn.init(QueueName)
        if result == 0:
            self.msgBox.insert('end', 'Link Success!\n\n', 'red')
        else:
            self.msgBox.insert('end', 'Link Fail!\n', 'red')

    def draw(self):
        self.window = tk.Tk()
        self.window.resizable(width=False, height=False)
        self.window.title('接收者')
        self.window.protocol("WM_DELETE_WINDOW", self.on_close)

        frame_top = tk.Frame(self.window, width=380, height=370, bg='white')
        frame_bottom = tk.Frame(self.window, width=380, height=30)

        self.msgBox = tk.Text(frame_top)
        self.changeButton = tk.Button(frame_bottom, text='更换消息类型', command=self.change_send)
        self.channelLabel = tk.Label(frame_bottom, text='频道0')
        vbar = tk.Scrollbar(frame_top, orient=tk.VERTICAL)

        self.msgBox['yscrollcommand'] = vbar.set
        vbar['command'] = self.msgBox.yview

        frame_top.grid(row=0, column=0, padx=2, pady=5)
        frame_bottom.grid(row=2, column=0, padx=2, pady=5)

        frame_top.grid_propagate(0)
        frame_bottom.grid_propagate(0)

        self.msgBox.tag_config('green', foreground='#008B00')
        self.msgBox.tag_config('red', foreground='#ff0000')

        self.msgBox.place(x=0,width=360,height=370)
        self.changeButton.grid(row=2, column=1)
        self.channelLabel.grid(row=2, column=2)
        vbar.place(x = 360, width=20, height=370)

    def on_close(self):
        self.window.destroy()

    def change_send(self):
        self.statu = str((int(self.statu)+1)%3)
        self.channelLabel.config(text='频道' + self.statu)

    def register_timer(self):
        self.conn.recv(self.statu, self.msgBox)
        self.window.after(1000, self.register_timer)


class RecvConn:
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

    def recv(self, chan, box):
        t = Thread(target=self.conn, args=(chan, box,))
        t.start()

    def conn(self, chan, box):
        for msg in self.queue.receive_messages(MessageAttributeNames=['Channel']):
            if msg.message_attributes is not None:
                chan_id = msg.message_attributes.get('Channel').get('StringValue')
                if chan_id == chan:
                    msgbody = msg.body.split('\n')
                    box.insert('end', 'Receiver{}:'.format(chan), 'green')
                    box.insert('end', msgbody[0]+'\n', 'green')
                    for i in range(1, len(msgbody)):
                        box.insert('end', msgbody[i]+'\n')

                    msg.delete()


if __name__ == '__main__':
    receiver = Receiver()