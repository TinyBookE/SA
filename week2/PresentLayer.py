import tkinter as tk
from threading import Thread

class Present:
    def __init__(self, recv_queue, send_queue):
        self.recv_queue = recv_queue
        self.send_queue = send_queue
        self._initial()
        self._on__recv()
        self.window.mainloop()

    def _initial(self):
        self.window = tk.Tk()
        self.window.resizable(width=False, height=False)
        self.window.title('数据库查询')
        self.window.protocol("WM_DELETE_WINDOW", self._on__close)

        frame_top = tk.Frame(self.window, width=380, height=270, bg='white')
        frame_bottom = tk.Frame(self.window, width=380, height=80, bg='white')

        self.msgBox = tk.Text(frame_top)
        self.dateBox = tk.Text(frame_bottom)
        self.fieldBox = tk.Text(frame_bottom)
        self.sendButton = tk.Button(frame_bottom, text='查询', command=self._on__send)
        vbar = tk.Scrollbar(frame_top, orient=tk.VERTICAL)

        self.msgBox['yscrollcommand'] = vbar.set
        vbar['command'] = self.msgBox.yview

        frame_top.grid(row=0, column=0, padx=2, pady=5)
        frame_bottom.grid(row=1, column=0, padx=2, pady=5)

        frame_top.grid_propagate(0)
        frame_bottom.grid_propagate(0)

        self.msgBox.place(x=0, width=360, height=270)
        vbar.place(x=360, width=20, height=270)
        self.fieldBox.place(x=20, y=10, width=340, height=20)
        self.dateBox.place(x=20, y=50, width=100, height=20)
        self.sendButton.place(x=200, y=50, width=40, height=20)

    def _on__send(self):
        try:
            request = {}

            request['field'] = self.fieldBox.get('0.0', 'end')
            request['date'] = self.dateBox.get('0.0', 'end')
            self.send_queue.put(request)
            self.fieldBox.delete('0.0', 'end')
            self.dateBox.delete('0.0', 'end')
        except Exception as e:
            print(e)

    def _on__close(self):
        self.window.destroy()

    def _show(self):
        try:
            while True:
                msg = self.recv_queue.get()
                self.msgBox.insert('end', msg)
        except Exception as e:
            print(e)

    def _on__recv(self):
        self.recv_proc = Thread(target=self._show)
        self.recv_proc.setDaemon(True)
        self.recv_proc.start()
